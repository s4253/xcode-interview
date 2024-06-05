package com.example.xcodeinterviewtask.exception;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {CurrencyNotFoundException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(CurrencyNotFoundException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorDto(ex.getMessage()));

    }
    @ExceptionHandler(value = {BadCurrencyAskException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleExceptionRequestWithWrongData(BadCurrencyAskException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorDto(ex.getMessage()));

    }

    @Getter
    class ErrorDto {
        private final String message;

        public ErrorDto(String message) {
            this.message = message;

        }

    }
}
