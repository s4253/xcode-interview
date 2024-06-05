package com.example.xcodeinterviewtask.service;

import com.example.xcodeinterviewtask.model.UserCurrencyAsk;
import com.example.xcodeinterviewtask.repository.UserCurrencyAskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCurrencyAskService {

    private final UserCurrencyAskRepository userCurrencyAskRepository;


    public void saveUserCurrencyAsk(String currency, String name, BigDecimal mid) {
        UserCurrencyAsk userCurrencyAsk = new UserCurrencyAsk();
        userCurrencyAsk.setCurrency(currency);
        userCurrencyAsk.setUsername(name);
        userCurrencyAsk.setExchangeRate(mid);
        userCurrencyAsk.setAskDateTime(new Timestamp(System.currentTimeMillis()));

        userCurrencyAskRepository.save(userCurrencyAsk);
    }

    public List<UserCurrencyAsk> getAllCurrencyAsk() {
        return userCurrencyAskRepository.findAll();
    }

}
