package com.example.xcodeinterviewtask.rest;

import com.example.xcodeinterviewtask.model.UserCurrencyAsk;
import com.example.xcodeinterviewtask.model.dto.AskCurrencyRequestBody;
import com.example.xcodeinterviewtask.service.NbpApiService;
import com.example.xcodeinterviewtask.service.UserCurrencyAskService;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetExchangeRateFromNBP {

    private final UserCurrencyAskService userCurrencyAskService;

    private final NbpApiService nbpApiService;

    @PostMapping("/currencies/get-current-currency-value-command")
    public ResponseEntity getCurrentCurrencyValueCommand(@RequestBody AskCurrencyRequestBody request) {
        BigDecimal currencyRate = nbpApiService.getCurrencyRate(request.getCurrency(), request.getName());

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
