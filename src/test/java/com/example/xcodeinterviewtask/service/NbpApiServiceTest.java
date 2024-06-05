package com.example.xcodeinterviewtask.service;

import com.example.xcodeinterviewtask.exception.BadCurrencyAskException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NbpApiServiceTest {

    @Test
    public void getSingleCurrencyRateWithWrongDataTest() {

        NbpApiService nbpApiService = new NbpApiService(null, null);

        Exception exception = assertThrows(BadCurrencyAskException.class, () -> {
            BigDecimal result = nbpApiService.getCurrencyRate(null, null);
        });

        assertEquals(exception.getMessage(), "Request with incorrect data");
    }
}
