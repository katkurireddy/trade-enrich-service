package com.enrich.org.tradeenrichservice.util;

import com.enrich.org.tradeenrichservice.model.EnrichModel;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnrichUtilTest {

    @Test
    public void writeListToCSVEMptyListTest() {
        String s = EnrichUtils.writeListToCSV(new ArrayList<>());
        String expected = "date,product_name,currency,price\n";
        assertEquals(expected,s);
    }

    @Test
    public void writeListToCSVNullListTest() {
        String s = EnrichUtils.writeListToCSV(null);
        String expected = "date,product_name,currency,price\n";
        assertEquals(expected,s);
    }

    @Test
    public void writeListToCSVHappyPathTest() {
        List<String[]> enrichedTrades = new ArrayList<>();
        enrichedTrades.add(new String[] { "20160101","Treasury Bills Domestic","EUR","10.0"});
        enrichedTrades.add(new String[] { "20160102","Treasury2 Bills Domestic","USD","10.0256"});
        enrichedTrades.add(new String[] { "20160103","Treasury3 Bills Domestic","GBP","10"});
        enrichedTrades.add(new String[] { "20160104","Treasury4 Bills Domestic","INR","10.0"});
        enrichedTrades.add(new String[] { "20160105","Treasury5 Bills Domestic","AUD","10.0"});
        String s = EnrichUtils.writeListToCSV(enrichedTrades);
        System.out.println(s);
        String e = "date,product_name,currency,price\n20160101,Treasury Bills Domestic,EUR,10.0\n20160102,Treasury2 Bills Domestic,USD,10.0256\n20160103,Treasury3 Bills Domestic,GBP,10\n20160104,Treasury4 Bills Domestic,INR,10.0\n20160105,Treasury5 Bills Domestic,AUD,10.0\n";
        assertEquals(e, s);
    }

    @Test
    public void getAllProductsFromFileHappyPathTest() {
        Map<Integer, EnrichModel> allProductsFromFile = EnrichUtils.getAllProductsFromFile("src/test/resources/productValid.csv");
        assertEquals(10, allProductsFromFile.size());
        List<Integer> expectedKeys = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        assertTrue(allProductsFromFile.keySet().containsAll(expectedKeys));
    }

    @Test
    public void getAllProductsFromFileBlankProductNameTest() {
        Map<Integer, EnrichModel> allProductsFromFile = EnrichUtils.getAllProductsFromFile("src/test/resources/productInValidRow.csv");
        assertEquals(9, allProductsFromFile.size());
        List<Integer> expectedKeys = Arrays.asList(1,2,3,4,5,7,8,9,10);
        assertTrue(allProductsFromFile.keySet().containsAll(expectedKeys));
    }
}
