package com.enrich.org.tradeenrichservice.controller;

import com.enrich.org.tradeenrichservice.service.EnrichService;
import com.enrich.org.tradeenrichservice.service.IEnrichService;
import com.enrich.org.tradeenrichservice.util.TradeUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class EnrichController {

    private final IEnrichService enrichService;

    public EnrichController(EnrichService enrichService) {
        this.enrichService = enrichService;
    }

    @PostMapping(value = "/enrich")
    private ResponseEntity<String> enrich(@RequestParam("file") MultipartFile file) {
        try {
            String enrichedTrades = enrichService.enrichTradeData(TradeUtils.convertMultiPartToFile(file));
            return ResponseEntity.ok().body(enrichedTrades);
        }
        catch (IOException ioException) {
            return ResponseEntity.badRequest().body(ioException.getMessage());
        }
    }
}
