package com.example.main_service.service;

import com.example.main_service.Entity.ConversionsEntity;
import com.example.main_service.Repository.ConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class ConversionService {

    @Value("${RATE_SERVICE_URL}")
    private String rateServiceUrl;

    private final WebClient webClient = WebClient.create();

    @Autowired
    private ConversionRepository repository;

    public Mono<Double> getExchangeRate(String from, String to) {
        String url = String.format("%s?from=%s&to=%s", rateServiceUrl, from, to);
        return webClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Double>>() {})
                .map(response ->
                        response.get(to)
                );
    }

    public Mono<ConversionsEntity> saveConversion(ConversionsEntity entity) {
        return repository.save(entity);
    }
}
