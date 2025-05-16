package com.example.main_service.models;

public class ConversionResponse {
    private Double convertedAmount;


    public ConversionResponse(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }


    public Double getConvertedAmount() {
        return convertedAmount;
    }


    public void setConvertedAmount(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
}
