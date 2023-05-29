package com.enrich.org.tradeenrichservice.util;

import com.enrich.org.tradeenrichservice.model.Currency;
import com.enrich.org.tradeenrichservice.model.TradeHeaders;
import com.enrich.org.tradeenrichservice.model.TradeModel;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TradeUtils {

    private static final Logger logger = LoggerFactory.getLogger(TradeUtils.class);

    public static TradeModel getTradeModelFromCSVLine(CSVRecord trade) {
        if(isTradeValid(trade)) {
            LocalDate date = LocalDate.parse(trade.get(TradeHeaders.date), DateTimeFormatter.ofPattern("yyyyMMdd"));
            BigDecimal price = new BigDecimal(trade.get(TradeHeaders.price));
            return new TradeModel(date, Integer.valueOf(trade.get(TradeHeaders.product_id)), trade.get(TradeHeaders.currency), price);
        }
        else return null;
    }

    private static boolean isDateValid(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return false;
        }
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isTradeValid(CSVRecord trade) {
        if(trade.size() != 4) {
            logger.error("Supplied trade is not valid. Missing Columns {}", trade.toString());
            return false;
        }
        if (! isDateValid(trade.get(TradeHeaders.date))) {
            logger.error("Supplied date {} is not valid for trade [date {}, productId {}, currency {}, price {}]", trade.get(TradeHeaders.date),
                    trade.get("product_id"), trade.get(TradeHeaders.date), trade.get("currency"),trade.get("price"));
            return false;
        }

        if(! isCcyValid(trade.get(TradeHeaders.currency))) {
            logger.error("Supplied currency {} is not valid for [trade date {}, productId {}, currency {}, price {}]", trade.get("currency"),
                    trade.get(TradeHeaders.date), trade.get("product_id"), trade.get("currency"), trade.get("price"));
            return false;
        }

        if(! isPriceValid(trade.get(TradeHeaders.price))) {
            logger.error("Supplied price {} is not valid for [trade date {}, productId {}, currency {}, price {}]", trade.get("price"),
                    trade.get(TradeHeaders.date), trade.get("product_id"), trade.get("currency"), trade.get("price"));
            return false;
        }
        return true;
    }

    private static boolean isCcyValid(String ccy) {
        return EnumUtils.isValidEnum(Currency.class, ccy.toUpperCase());
    }

    private static boolean isPriceValid(String price) {
        try {
            new BigDecimal(price);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
