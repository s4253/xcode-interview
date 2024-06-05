package com.example.xcodeinterviewtask.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CurrencyNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public CurrencyNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }
}
