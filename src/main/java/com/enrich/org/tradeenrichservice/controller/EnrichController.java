package com.enrich.org.tradeenrichservice.controller;

import com.enrich.org.tradeenrichservice.service.EnrichService;
import com.enrich.org.tradeenrichservice.service.IEnrichService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class EnrichController {

    private final IEnrichService enrichService;

    public EnrichController(EnrichService enrichService) {
        this.enrichService = enrichService;
    }

    @PostMapping(value = "/enrich", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    private ResponseEntity<String> enrich(@RequestBody String tradeData) {
        String enrichedTrades = enrichService.enrichTradeData(tradeData);
        return ResponseEntity.ok().body(enrichedTrades);
    }

}
