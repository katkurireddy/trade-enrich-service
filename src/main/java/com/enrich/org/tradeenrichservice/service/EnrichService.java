package com.enrich.org.tradeenrichservice.service;

import com.enrich.org.tradeenrichservice.model.EnrichModel;
import com.enrich.org.tradeenrichservice.model.TradeModel;
import com.enrich.org.tradeenrichservice.repository.TradeEnrichment;
import com.enrich.org.tradeenrichservice.util.EnrichUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EnrichService implements IEnrichService {

    private static final Logger logger = LoggerFactory.getLogger(EnrichService.class);
    private final EnrichUtils enrichUtils;
    private final TradeEnrichment tradeEnrichment;

    @Autowired
    public EnrichService(TradeEnrichment tradeEnrichment) {
        this.tradeEnrichment = tradeEnrichment;
        this.enrichUtils = new EnrichUtils();
    }

    public String enrichTradeData(File file) {
        List<TradeModel> tradeModels = enrichUtils.parseAndValidateTradeCsv(file);
        List<String[]> enrichedTrades = new ArrayList<>();
        Map<Integer, EnrichModel> allEnrichProducts = tradeEnrichment.findAllProducts();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        for (TradeModel tradeModel : tradeModels) {
            String date = tradeModel.date().format(formatter);
            if(allEnrichProducts.containsKey(tradeModel.productId())) {
                EnrichModel enrichModel = allEnrichProducts.get(tradeModel.productId());
                enrichedTrades.add(new String[]{date,
                        enrichModel.productName(),
                        tradeModel.currency(),
                        tradeModel.price().toString()});
            }
            else {
                logger.info("Product Name is missing for productId {} in {} ", tradeModel.productId(), tradeModel.toString());
                enrichedTrades.add(new String[]{date,
                        "Missing Product Name", tradeModel.currency(),
                        tradeModel.price().toString()});
            }
        }
        return EnrichUtils.writeListToCSV(enrichedTrades);
    }


}
