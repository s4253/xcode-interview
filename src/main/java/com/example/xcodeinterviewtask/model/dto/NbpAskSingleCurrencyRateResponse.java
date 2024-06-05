package com.example.xcodeinterviewtask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class NbpAskSingleCurrencyRateResponse {
    private String table;
    private String currency;
    private String code;
    private List<Rate> rates = new ArrayList<>();


    @Data
    public static class Rate {
        private String no;
        private String effectiveDate;
        private BigDecimal mid;
    }
}

