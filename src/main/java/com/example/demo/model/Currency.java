package com.example.demo.model;

import lombok.Data;

@Data
public class Currency {
    public String table;
    public String currency;
    public String code;
    public Rate[] rates;
}
