package com.example.demo.controllers;

import com.example.demo.model.dto.CurrencyExchangeResponse;
import com.example.demo.service.CurrencyExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExchangeHistoryFromDatabaseController {

    private final CurrencyExchangeService currencyExchangeService;

    @GetMapping("/historyFromDatabase")
    public ResponseEntity exchange() throws IOException {
        List<CurrencyExchangeResponse> currencyExchangeResponses=currencyExchangeService.getHistoryFromDatabase();
        return ResponseEntity.status(HttpStatus.OK).body(currencyExchangeResponses);
    }
}
