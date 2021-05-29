package com.example.demo.connector;

import com.example.demo.exception.CurrencyException;
import com.example.demo.model.Currency;
import com.example.demo.model.Rate;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
@Service
public class NbpConnector {

    private final String[] TABLES = {"A", "B", "C"};
    private final String NBP_URL = "http://api.nbp.pl/api/exchangerates";

    public Currency getCurrency(String code) throws IOException {
        Currency currency = new Currency();
        if (code.equals("PLN")){
            currency.setCode("PLN");
            Rate rate=new Rate();
            rate.setMid(1);
            currency.rates= new Rate[]{rate};
            return currency;
        }
        StringBuilder jsonText=new StringBuilder();
        InputStream inputStream;
        for (String table : TABLES) {
            URL url = urlBuilder(table, code);
            try {
                inputStream = url.openStream();
            } catch (FileNotFoundException exception){
                throw new CurrencyException("Currency not found");
            }

            if (inputStream!=null){
                Scanner scanner=new Scanner(inputStream);
                while (scanner.hasNext()){
                    jsonText.append(scanner.nextLine());
                }
                Gson gson=new Gson();
                currency=gson.fromJson(jsonText.toString(),Currency.class);
                return currency;
            }
        }
        return currency;
    }

    private URL urlBuilder(String table, String code) throws MalformedURLException {
        return new URL(NBP_URL + "/rates/" + table + "/" + code + "?format=json");
    }
}
