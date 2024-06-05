package com.example.xcodeinterviewtask.service;

import com.example.xcodeinterviewtask.exception.BadCurrencyAskException;
import com.example.xcodeinterviewtask.exception.CurrencyNotFoundException;
import com.example.xcodeinterviewtask.model.dto.NbpAskSingleCurrencyRateResponse;
import com.example.xcodeinterviewtask.validators.NbpAskCurrencyResponseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NbpApiService {

    private final RestTemplate restTemplate;

    private final UserCurrencyAskService userCurrencyAskService;

    public BigDecimal getCurrencyRate(String currencyCode, String userFirstNameAndSurname) {

        /**
         * request validation (mozna wydzielić na to osobną klasę)
         */

        if (currencyCode == null || userFirstNameAndSurname == null) {
            throw new BadCurrencyAskException("Request with incorrect data", HttpStatus.BAD_REQUEST);
        }

        String nbpUrl = String.format("https://api.nbp.pl/api/exchangerates/rates/a/%s?format=json", currencyCode);
        ResponseEntity<NbpAskSingleCurrencyRateResponse> nbpResponse;
        try {
            nbpResponse = restTemplate.getForEntity(nbpUrl, NbpAskSingleCurrencyRateResponse.class);
        } catch (HttpClientErrorException e) {
            throw new CurrencyNotFoundException("NBP service is down or user asking for wrong currency!", HttpStatus.BAD_REQUEST);
        }

        boolean validationResult = NbpAskCurrencyResponseValidator.isValid(nbpResponse.getBody());
        if (!validationResult) {
            throw new CurrencyNotFoundException("NBP response with empty rates!", HttpStatus.NO_CONTENT);
        }

        BigDecimal currencyRate = nbpResponse.getBody().getRates().stream().findFirst().get().getMid();

        userCurrencyAskService.saveUserCurrencyAsk(currencyCode, userFirstNameAndSurname, currencyRate);

        return currencyRate;
    }
}
