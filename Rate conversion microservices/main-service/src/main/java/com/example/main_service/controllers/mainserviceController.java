package com.example.main_service.controllers;

import com.example.main_service.Entity.ConversionsEntity;
import com.example.main_service.models.ConversionRequest;
import com.example.main_service.models.ConversionResponse;
import com.example.main_service.service.ConversionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;


@RestController
@RequestMapping("/convert")
public class mainserviceController {

    @Autowired
    private ConversionService conversionService;

    @PostMapping
    public Mono<ResponseEntity<ConversionResponse>> convert(@Valid @RequestBody ConversionRequest request) {
        return conversionService.getExchangeRate(request.getFrom(), request.getTo())
                .flatMap(rate -> {
                    if (rate == null) {
                        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .<ConversionResponse>body(null));
                    }

                    double convertedAmount = request.getAmount() * rate;
                    ConversionsEntity entity = new ConversionsEntity(
                            null,
                            request.getFrom(),
                            request.getTo(),
                            request.getAmount(),
                            convertedAmount,
                            LocalDateTime.now()
                    );

                    return conversionService.saveConversion(entity)
                            .map(saved -> ResponseEntity.ok(new ConversionResponse(convertedAmount)));
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).<ConversionResponse>body(null));
    }

    @GetMapping("/status")
    public Map<String, String> status() {
        return Map.of("status", "UP");
    }
}