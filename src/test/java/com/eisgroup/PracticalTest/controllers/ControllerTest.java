package com.eisgroup.PracticalTest.controllers;

import com.eisgroup.PracticalTest.models.CurrencyRequest;
import com.eisgroup.PracticalTest.models.CurrencyResponse;
import com.eisgroup.PracticalTest.services.CurrencyBankService;
import com.eisgroup.PracticalTest.services.CurrencyBankServiceTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ControllerTest {

    private CurrencyBankService serMock = Mockito.mock(CurrencyBankService.class);

    @Test
    public void testCurrency() {
        Controller con = new Controller();
        con.currencyBankService = serMock;
        boolean failed = false;
        ResponseEntity<List<CurrencyResponse>> response = null;
        try {
            response = con.currency(null);
        } catch (Exception ex) {
            failed = true;
        }

        Assert.assertTrue(failed);
        Assert.assertNull(response);

        failed = false;
        response = null;
        CurrencyRequest cr = new CurrencyRequest();
        List<CurrencyResponse> result = new ArrayList<>();
        result.add(new CurrencyResponse("GBP", "British Pound", 1.255, 0.0));
        result.add(new CurrencyResponse("USD", "US dollar", 1.5, 0.007));
        try {
            Mockito.when(serMock.getCurrencyData(cr)).thenReturn(result);
        } catch (ExceptionHandler.CurrencyException e) {
            e.printStackTrace();
        }
        try {
            response = con.currency(cr);
        } catch (Exception ex) {
            failed = true;
        }

        Assert.assertFalse(failed);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(2, response.getBody().size());
        Assert.assertEquals("GBP", response.getBody().get(0).getCode());
        Assert.assertEquals(1.5, response.getBody().get(1).getCurrencyChange(), 10);
    }

}
