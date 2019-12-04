package com.eisgroup.PracticalTest.controllers;

import org.apache.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Arrays;

@ControllerAdvice
public class ExceptionHandler {

    private static Logger _logger = Logger.getLogger(ExceptionHandler.class);

    public static class CurrencyException extends Exception {
        final String message;
        final HttpStatus status;

        public CurrencyException(String message, HttpStatus status) {
            this.message = message;
            this.status = status;
        }
    }

    private static class ExceptionResponse {
        final String message;
        final String stacktrace;
        final long time;

        ExceptionResponse(String message, String stacktrace, long time) {
            this.message = message;
            this.stacktrace = stacktrace;
            this.time = time;
        }
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = CurrencyException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity<Object> currencyException(CurrencyException ex) {
        _logger.error(ex.message);
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(), null, System.currentTimeMillis()), ex.status);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    public ResponseEntity<Object> unknownException(Exception ex) {
        _logger.error("An unknown exception occurred: " + ex.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(), Arrays.toString(ex.getStackTrace()), System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
