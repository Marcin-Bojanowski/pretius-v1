package com.example.demo.service;

import com.example.demo.connector.NbpConnector;
import com.example.demo.exception.CurrencyException;
import com.example.demo.model.Currency;
import com.example.demo.model.CurrencyHistory;
import com.example.demo.model.dto.CurrencyExchangeRequest;
import com.example.demo.model.dto.CurrencyExchangeResponse;
import com.example.demo.repositories.CurrencyHistoryRepository;
import com.example.demo.utilities.CustomMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CurrencyExchangeService {

    private final NbpConnector nbpConnector;
    private final String FILE_NAME = "currency.txt";
    private final Gson gson;
    private final CurrencyHistoryRepository currencyHistoryRepository;
    private final CustomMapper customMapper;

    public CurrencyExchangeResponse exchange(CurrencyExchangeRequest currencyExchangeRequest) throws IOException {
        return exchangeCurrency(currencyExchangeRequest);
    }

    public CurrencyExchangeResponse exchangeAndSave(CurrencyExchangeRequest currencyExchangeRequest) throws IOException {
        CurrencyExchangeResponse currencyExchangeResponse = exchangeCurrency(currencyExchangeRequest);
        FileWriter fileWriter = new FileWriter(FILE_NAME, true);
        String json = gson.toJson(currencyExchangeResponse);
        fileWriter.write(json + System.lineSeparator());
        fileWriter.close();
        return currencyExchangeResponse;
    }

    private CurrencyExchangeResponse exchangeCurrency(CurrencyExchangeRequest currencyExchangeRequest) throws IOException {
        Double value;
        try {
            value = Double.parseDouble(currencyExchangeRequest.getValue());
        } catch (NumberFormatException exception) {
            throw new CurrencyException("Value must be a number");
        }
        Currency currency = nbpConnector.getCurrency(currencyExchangeRequest.getCurrency());
        Currency targetCurrency = nbpConnector.getCurrency(currencyExchangeRequest.getTargetCurrency());
        CurrencyExchangeResponse currencyExchangeResponse = new CurrencyExchangeResponse();
        currencyExchangeResponse.setCurrency(currencyExchangeRequest.getTargetCurrency());
        currencyExchangeResponse.setPreviousCurrency(currencyExchangeRequest.getCurrency());
        currencyExchangeResponse.setPreviousValue(Double.parseDouble(currencyExchangeRequest.getValue()));
        currencyExchangeResponse.setValue((value * currency.getRates()[0].mid) / targetCurrency.getRates()[0].mid);
        return currencyExchangeResponse;
    }


    public List<CurrencyExchangeResponse> getHistory() throws FileNotFoundException {
        File file = new File(FILE_NAME);
        List<CurrencyExchangeResponse> currencyExchangeResponseList = new ArrayList<>();
        StringBuilder reading = new StringBuilder();
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            currencyExchangeResponseList.add(gson.fromJson(scan.nextLine(), CurrencyExchangeResponse.class));
        }
        return currencyExchangeResponseList;
    }

    public CurrencyExchangeResponse exchangeAndSaveToDatabase(CurrencyExchangeRequest currencyExchangeRequest) throws IOException {
        CurrencyExchangeResponse currencyExchangeResponse = exchangeCurrency(currencyExchangeRequest);
        currencyHistoryRepository.save(customMapper.map(currencyExchangeResponse));
        return currencyExchangeResponse;

    }

    public List<CurrencyExchangeResponse> getHistoryFromDatabase() {
        List<CurrencyHistory> currencyHistoryList = currencyHistoryRepository.findAll();
        return currencyHistoryList.stream().map(customMapper::map).collect(Collectors.toList());

    }
}
