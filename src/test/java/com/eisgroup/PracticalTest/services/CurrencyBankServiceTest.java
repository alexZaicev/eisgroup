package com.eisgroup.PracticalTest.services;

import com.eisgroup.PracticalTest.models.CurrencyRequest;
import com.eisgroup.PracticalTest.models.CurrencyResponse;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CurrencyBankServiceTest {

    private RequestService reqMock = Mockito.mock(RequestService.class);

    @Test
    public void getCurrencyData() {
        boolean failed = false;
        List<CurrencyResponse> responses = null;
        CurrencyBankService service = new CurrencyBankService();
        service.requestService = reqMock;
        try {
            responses = service.getCurrencyData(null);
        } catch (Exception ex) {
            failed = true;
        }

        Assert.assertTrue(failed);
        Assert.assertNull(responses);

        CurrencyRequest req = new CurrencyRequest();
        failed = false;
        responses = null;
        try {
            responses = service.getCurrencyData(req);
        } catch (Exception ex) {
            failed = true;
        }

        Assert.assertTrue(failed);
        Assert.assertNull(responses);

        req = new CurrencyRequest(new ArrayList<>(), null, null);
        failed = false;
        responses = null;
        try {
            responses = service.getCurrencyData(req);
        } catch (Exception ex) {
            failed = true;
        }

        Assert.assertTrue(failed);
        Assert.assertNull(responses);

        List<String> codes =  new ArrayList<>();
        codes.add("GBP");
        codes.add("USD");
        req = new CurrencyRequest(codes, null, null);
        failed = false;
        responses = null;
        try {
            responses = service.getCurrencyData(req);
        } catch (Exception ex) {
            failed = true;
        }

        Assert.assertTrue(failed);
        Assert.assertNull(responses);
        req = new CurrencyRequest(codes, new Date(2019, Calendar.NOVEMBER, 27), null);
        failed = false;
        responses = null;
        try {
            responses = service.getCurrencyData(req);
        } catch (Exception ex) {
            failed = true;
        }

        Assert.assertTrue(failed);
        Assert.assertNull(responses);
    }

}
