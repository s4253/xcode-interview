package com.example.xcodeinterviewtask.validators;


import com.example.xcodeinterviewtask.model.dto.NbpAskSingleCurrencyRateResponse;
import com.example.xcodeinterviewtask.rest.GetExchangeRateFromNBP;

public class NbpAskCurrencyResponseValidator {

    public static boolean isValid() {
        return true;
    }

    public static boolean isValid(NbpAskSingleCurrencyRateResponse body) {

        return body.getRates() != null  && !body.getRates().isEmpty();

    }
}
