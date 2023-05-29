package com.enrich.org.tradeenrichservice.util;

import com.enrich.org.tradeenrichservice.model.EnrichHeaders;
import com.enrich.org.tradeenrichservice.model.EnrichModel;
import com.enrich.org.tradeenrichservice.model.TradeHeaders;
import com.enrich.org.tradeenrichservice.model.TradeModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrichUtils {
    private static final Logger logger = LoggerFactory.getLogger(EnrichUtils.class);

    public static EnrichModel getEnrichModelFromProductIdFromFile(Integer productId, String fileName) {
        try (Reader in = new FileReader(fileName)) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(EnrichHeaders.class)
                    .setSkipHeaderRecord(true)
                    .build();
            CSVParser parser = csvFormat.parse(in);
            for (CSVRecord record : parser) {
                Integer id = Integer.valueOf(record.get(EnrichHeaders.product_id));
                if (productId.equals(id))
                    return new EnrichModel(id, record.get(EnrichHeaders.product_name));
            }
        } catch (IOException e) {
            logger.error("Error while opening enrichment file", e);
        }
        return null;
    }

    public static Map<Integer, EnrichModel> getAllProductsFromFile(String fileName) {
        Map<Integer, EnrichModel> enrichModelMap = new HashMap<>();
        try (Reader in = new FileReader(fileName)) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(EnrichHeaders.class)
                    .setSkipHeaderRecord(true)
                    .build();
            CSVParser parser = csvFormat.parse(in);
            for (CSVRecord record : parser) {
                Integer productId = Integer.valueOf(record.get(EnrichHeaders.product_id));
                String productName = record.get(EnrichHeaders.product_name);
                if(StringUtils.isNotBlank(productName)) {
                    enrichModelMap.put(productId, new EnrichModel(productId, record.get(EnrichHeaders.product_name)));
                }
                else {
                    logger.error("Product Name is blank for Product id {} in static enrichment data", productName);
                }
            }
        } catch (IOException e) {
            logger.error("Error while opening enrichment file", e);
        }
        return enrichModelMap;
    }

    public List<TradeModel> parseAndValidateTradeCsv(String tradeData) {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(TradeHeaders.class)
                .setSkipHeaderRecord(true)
                .build();
        List<TradeModel> tradeModels = new ArrayList<>();
        try (CSVParser parser = csvFormat.parse(new StringReader(tradeData))) {
            for (CSVRecord tradeInfo : parser) {
                TradeModel tradeModel = TradeUtils.getTradeModelFromCSVLine(tradeInfo);
                if (tradeModel != null) {
                    tradeModels.add(tradeModel);
                }
            }
        } catch (IOException e) {
            logger.error("Error while opening enrichment file", e);
        }
        return tradeModels;
    }

    public static String writeListToCSV(List<String[]> enrichedTrades) {
        StringBuilder builder = new StringBuilder();
        String header = StringUtils.joinWith(",", new String[]{"date", "product_name", "currency", "price"});
        builder.append(header);
        builder.append("\n");
        if(CollectionUtils.isEmpty(enrichedTrades)) {
            logger.info("No Trades were enriched");
            return builder.toString();
        }
        for (String[] trade : enrichedTrades) {
            String tradeLine = StringUtils.joinWith(",", trade);
            builder.append(tradeLine);
            builder.append(StringUtils.LF);
        }
        return builder.toString();
    }
}
