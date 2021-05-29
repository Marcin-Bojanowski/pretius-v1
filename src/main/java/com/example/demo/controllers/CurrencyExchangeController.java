package com.example.demo.controllers;

import com.example.demo.model.dto.CurrencyExchangeRequest;
import com.example.demo.model.dto.CurrencyExchangeResponse;
import com.example.demo.service.CurrencyExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;

    @PostMapping("/exchange")
    public ResponseEntity exchange(@Valid @RequestBody CurrencyExchangeRequest currencyExchangeRequest) throws IOException {
        CurrencyExchangeResponse currencyExchangeResponse=currencyExchangeService.exchange(currencyExchangeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(currencyExchangeResponse);
    }
}
