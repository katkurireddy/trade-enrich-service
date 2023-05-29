package com.enrich.org.tradeenrichservice.util;

import com.enrich.org.tradeenrichservice.model.TradeHeaders;
import com.enrich.org.tradeenrichservice.model.TradeModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class TradeUtilTest {

    private Iterable<CSVRecord> getTestCSVRecords(String csvLine) {
        CSVParser parse = null;
        try {
            Reader in = new StringReader(csvLine);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(TradeHeaders.class)
                    .setSkipHeaderRecord(false)
                    .build();
            parse = csvFormat.parse(in);
        }
        catch (IOException ignored) {}
        return parse;
    }

    @Test
    public void validateDateFromTradeCSVHapppyPathTest() {
        Iterable<CSVRecord> testCSVRecords = getTestCSVRecords("20160101,2,EUR,10.0");
        boolean actual = TradeUtils.isTradeValid(testCSVRecords.iterator().next());
        assertTrue(actual);
    }

    @Test
    public void ValidateDateEmptyStringTest() {
        Iterable<CSVRecord> testCSVRecords = getTestCSVRecords(",2,EUR,10.0");
        boolean actual = TradeUtils.isTradeValid(testCSVRecords.iterator().next());
        assertFalse(actual);
    }

    @Test
    public void ValidateDateIncorrectStringTest() {
        Iterable<CSVRecord> testCSVRecords = getTestCSVRecords("2023061,2,EUR,10.0");
        boolean actual = TradeUtils.isTradeValid(testCSVRecords.iterator().next());
        assertFalse(actual);
    }

    @Test
    public void ValidateDateCurrencyIncorrectStringTest() {
        Iterable<CSVRecord> testCSVRecords = getTestCSVRecords("20230611,2,KLPL,10.0");
        boolean actual = TradeUtils.isTradeValid(testCSVRecords.iterator().next());
        assertFalse(actual);
    }

    @Test
    public void ValidateDateCurrencyLowercaseStringTest() {
        Iterable<CSVRecord> testCSVRecords = getTestCSVRecords("20230611,2,usd,10.0");
        boolean actual = TradeUtils.isTradeValid(testCSVRecords.iterator().next());
        assertTrue(actual);
    }

    @Test
    public void ValidateDateIncorrectPricingStringTest() {
        Iterable<CSVRecord> testCSVRecords = getTestCSVRecords("20230611,2,EUR,10.0k");
        boolean actual = TradeUtils.isTradeValid(testCSVRecords.iterator().next());
        assertFalse(actual);
    }

    @Test
    public void getTradeModelFromCSVLineHappyPathTest() {
        Iterable<CSVRecord> testCSVRecords = getTestCSVRecords("20160101,2,EUR,10.0");
        TradeModel actualTradeModel = TradeUtils.getTradeModelFromCSVLine(testCSVRecords.iterator().next());
        LocalDate expectedDate = LocalDate.parse("20160101", DateTimeFormatter.ofPattern("yyyyMMdd"));
        TradeModel expected = new TradeModel(expectedDate, 2, "EUR", new BigDecimal("10.0"));
        assertEquals(expected, actualTradeModel);
    }

    @Test
    public void getTradeModelFromCSVLineInvalidDateTest() {
        Iterable<CSVRecord> testCSVRecords = getTestCSVRecords("2016010,2,EUR,10.0");
        TradeModel actualTradeModel = TradeUtils.getTradeModelFromCSVLine(testCSVRecords.iterator().next());
        assertNull(actualTradeModel);
    }

    @Test
    public void getTradeModelFromCSVLineInvalidNumberOfHeadersTest() {
        Iterable<CSVRecord> testCSVRecords = getTestCSVRecords("2016010,2,10.0");
        TradeModel actualTradeModel = TradeUtils.getTradeModelFromCSVLine(testCSVRecords.iterator().next());
        assertNull(actualTradeModel);
    }

}
