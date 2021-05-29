package com.example.demo.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CurrencyExchangeRequest {

    @NotBlank
    private String value;
    @NotBlank
    private String currency;
    @NotBlank
    private String targetCurrency;

}
