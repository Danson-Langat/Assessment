package com.rate_service.assessment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class ExchangeRateService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);

    @Value("${exchange.api.url}")
    private String apiUrl;

    private final WebClient webClient = WebClient.create();

    public Mono<Double> getRate(String from, String to) {
        String url = String.format("%s/%s", apiUrl, from);
        logger.info("Fetching exchange rate from {} to {} using URL: {}", from, to, url);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(ExchangeApiResponse.class)
                .flatMap(response -> {
                    if (response != null && response.rates.containsKey(to)) {
                        Double rate = response.rates.get(to);
                        logger.info("Rate fetched successfully: 1 {} = {} {}", from, rate, to);
                        return Mono.just(rate);
                    } else {
                        logger.warn("No rate found for currency: {}", to);
                        return Mono.empty(); // or Mono.error(...) if you prefer
                    }
                })
                .onErrorResume(e -> {
                    logger.error("Error fetching exchange rate: {}", e.getMessage(), e);
                    return Mono.empty(); // or Mono.error(e) if you want to propagate errors
                });
    }

    public static class ExchangeApiResponse {
        public String base;
        public Map<String, Double> rates;
        public String date;
    }
}