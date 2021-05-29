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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExchangeHistoryController {

    private final CurrencyExchangeService currencyExchangeService;

    @GetMapping("/history")
    public ResponseEntity exchange() throws IOException {
       List <CurrencyExchangeResponse> currencyExchangeResponses=currencyExchangeService.getHistory();
        return ResponseEntity.status(HttpStatus.OK).body(currencyExchangeResponses);
    }
}
