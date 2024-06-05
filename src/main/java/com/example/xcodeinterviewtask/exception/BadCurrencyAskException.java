package com.example.xcodeinterviewtask.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadCurrencyAskException extends RuntimeException{

    private final HttpStatus status;

    public BadCurrencyAskException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }
}
