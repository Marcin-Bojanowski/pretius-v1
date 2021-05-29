package com.example.demo.model.dto;

import lombok.Data;

@Data
public class CurrencyExchangeResponse {

    private Double value;
    private String currency;
    private Double previousValue;
    private String previousCurrency;
}
