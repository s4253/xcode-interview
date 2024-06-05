package com.example.xcodeinterviewtask.rest;

import com.example.xcodeinterviewtask.exception.CurrencyNotFoundException;
import com.example.xcodeinterviewtask.model.UserCurrencyAsk;
import com.example.xcodeinterviewtask.model.dto.AskCurrencyRequestBody;
import com.example.xcodeinterviewtask.model.dto.NbpAskSingleCurrencyRateResponse;
import com.example.xcodeinterviewtask.service.UserCurrencyAskService;
import com.example.xcodeinterviewtask.validators.NbpAskCurrencyResponseValidator;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetExchangeRateFromNBP {

    private final RestTemplate restTemplate;

    private final UserCurrencyAskService userCurrencyAskService;

    @PostMapping("/currencies/get-current-currency-value-command")
    public ResponseEntity getCurrentCurrencyValueCommand(@RequestBody AskCurrencyRequestBody request) {
        String nbpUrl = String.format("https://api.nbp.pl/api/exchangerates/rates/a/%s?format=json", request.getCurrency());
        ResponseEntity<NbpAskSingleCurrencyRateResponse> nbpResponse = null;
        try {
            nbpResponse = restTemplate.getForEntity(nbpUrl, NbpAskSingleCurrencyRateResponse.class);
        } catch (HttpClientErrorException e) {
            throw new CurrencyNotFoundException("NBP service is down or user asking for wrong currency!", HttpStatus.BAD_REQUEST);
        }

        boolean validationResult = NbpAskCurrencyResponseValidator.isValid(nbpResponse.getBody());
        if (!validationResult) {
            throw new CurrencyNotFoundException("Cannot find currency rate!", HttpStatus.NO_CONTENT);
        }

        BigDecimal currencyRate = nbpResponse.getBody().getRates().stream().findFirst().get().getMid();
        userCurrencyAskService.saveUserCurrencyAsk(request.getCurrency(), request.getName(), currencyRate);
        return ResponseEntity.ok(GetCurrentCurrencyValueCommandResponse.builder().value(currencyRate).build());
    }

    @GetMapping("/currencies/requests")
    public ResponseEntity<List<UserCurrencyAsk>> getCurrenciesRequests() {
        return ResponseEntity.ok(userCurrencyAskService.getAllCurrencyAsk());
    }


}

@Data
@Builder
class GetCurrentCurrencyValueCommandResponse {
    private BigDecimal value;
}
