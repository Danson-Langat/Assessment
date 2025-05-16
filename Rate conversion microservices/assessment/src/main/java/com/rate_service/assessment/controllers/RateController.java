package com.rate_service.assessment.controllers;

import com.rate_service.assessment.service.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/rate")
public class RateController {

    private static final Logger logger = LoggerFactory.getLogger(RateController.class);

    @Autowired
    private ExchangeRateService exchangeRateService;


    @GetMapping
    public Mono<ResponseEntity<Map<String, Double>>> getRate(@RequestParam String from, @RequestParam String to) {
        logger.info("Received request for exchange rate from {} to {}", from, to);

        String fromCurrency = from.toUpperCase();
        String toCurrency = to.toUpperCase();

        if (fromCurrency.equals(toCurrency)) {
            logger.info("From and To currencies are the same: {}, returning 1.0", fromCurrency);
            return Mono.just(ResponseEntity.ok(Collections.singletonMap(toCurrency, 1.0)));
        }

        return exchangeRateService.getRate(fromCurrency, toCurrency)
                .flatMap(rate -> {
                    if (rate != null) {
                        logger.info("Returning rate {} for {} to {}", rate, from, to);
                        return Mono.just(ResponseEntity.ok(Collections.singletonMap(toCurrency, rate)));
                    } else {
                        logger.warn("Exchange rate not found for {} to {}", from, to);
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
                    }
                });
    }

    @GetMapping("/status")
    public Map<String, String> status() {
        logger.debug("Status check requested");
        return Map.of("status", "UP");
    }
}