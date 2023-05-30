package com.enrich.org.tradeenrichservice.repository;

import com.enrich.org.tradeenrichservice.model.EnrichModel;
import com.enrich.org.tradeenrichservice.util.EnrichUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.util.Map;

@Component
public class CSVTradeEnrichmentImpl implements TradeEnrichment {
    private static final Logger logger = LoggerFactory.getLogger(CSVTradeEnrichmentImpl.class);

    @Cacheable("productsMap")
    @Override
    public Map<Integer, EnrichModel> findAllProducts() {
        logger.info("Retrieving all product names");
        return EnrichUtils.getAllProductsFromFile("classpath:product.csv");
    }
}
