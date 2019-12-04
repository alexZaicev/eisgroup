package com.eisgroup.PracticalTest.controllers;

import com.eisgroup.PracticalTest.models.CurrencyRequest;
import com.eisgroup.PracticalTest.models.CurrencyResponse;
import com.eisgroup.PracticalTest.services.CurrencyBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class Controller {

    @Autowired
    public CurrencyBankService currencyBankService;

    @RequestMapping(value="/currency", method = RequestMethod.POST)
    public ResponseEntity<List<CurrencyResponse>> currency(@RequestBody final CurrencyRequest cr) throws ExceptionHandler.CurrencyException {
        if (cr == null) {
            throw new ExceptionHandler.CurrencyException("Request body is not provided", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.currencyBankService.getCurrencyData(cr), HttpStatus.OK);
    }
}
