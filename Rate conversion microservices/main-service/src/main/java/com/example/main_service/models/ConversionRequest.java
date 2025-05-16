package com.example.main_service.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ConversionRequest {


    @NotNull(message = "Source currency is required")
    private String from;

    @NotNull(message = "Target currency is required")
    private String to;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be non-negative")
    private Double amount;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}