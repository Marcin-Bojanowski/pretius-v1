package com.example.demo.utilities;

import com.example.demo.model.CurrencyHistory;
import com.example.demo.model.dto.CurrencyExchangeResponse;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class CustomMapper {

    public CurrencyHistory map(CurrencyExchangeResponse currencyExchangeResponse){
        Mapper<CurrencyExchangeResponse,CurrencyHistory> mapper= Mapping
                .from(CurrencyExchangeResponse.class)
                .to(CurrencyHistory.class)
                .omitInDestination(CurrencyHistory::getId)
                .mapper();
        return mapper.map(currencyExchangeResponse);
    }


    public CurrencyExchangeResponse map(CurrencyHistory currencyHistory){
        Mapper<CurrencyHistory,CurrencyExchangeResponse> mapper= Mapping
                .from(CurrencyHistory.class)
                .to(CurrencyExchangeResponse.class)
                .omitInSource(CurrencyHistory::getId)
                .mapper();
        return mapper.map(currencyHistory);
    }
}
