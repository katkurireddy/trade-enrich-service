package com.enrich.org.tradeenrichservice.repository;

import com.enrich.org.tradeenrichservice.model.EnrichModel;

import java.util.Map;

public interface TradeEnrichment {

    public Map<Integer, EnrichModel> findAllProducts();
}
