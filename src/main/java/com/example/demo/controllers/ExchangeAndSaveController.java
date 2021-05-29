package com.example.demo.controllers;

import com.example.demo.model.dto.CurrencyExchangeRequest;
import com.example.demo.model.dto.CurrencyExchangeResponse;
import com.example.demo.service.CurrencyExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExchangeAndSaveController {

    private final CurrencyExchangeService currencyExchangeService;

    @PostMapping("/exchangeAndSave")
    public ResponseEntity<?> exchangeAndSave(@Valid @RequestBody CurrencyExchangeRequest currencyExchangeRequest) throws IOException {
        CurrencyExchangeResponse currencyExchangeResponse = currencyExchangeService.exchangeAndSave(currencyExchangeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(currencyExchangeResponse);
    }
}
