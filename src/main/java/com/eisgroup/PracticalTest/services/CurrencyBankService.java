package com.eisgroup.PracticalTest.services;

import com.eisgroup.PracticalTest.controllers.ExceptionHandler;
import com.eisgroup.PracticalTest.models.Currency;
import com.eisgroup.PracticalTest.models.CurrencyRequest;
import com.eisgroup.PracticalTest.models.CurrencyResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CurrencyBankService {

    private static final Logger _logger = Logger.getLogger(CurrencyBankService.class);
    private static final SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    public RequestService requestService;

    public List<CurrencyResponse> getCurrencyData(final CurrencyRequest cr) throws ExceptionHandler.CurrencyException {
        List<CurrencyResponse> responses = null;
        if (cr != null && cr.getCodes() != null && !cr.getCodes().isEmpty()) {
            if (cr.getFromDate() != null && cr.getToDate() != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
                String uri = this.getUri(cr.getFromDate());
                ResponseEntity<byte[]> re = this.requestService.get(uri, byte[].class, headers);
                if (re.getStatusCode() == HttpStatus.OK && re.hasBody() && re.getBody() != null) {
                    final List<Currency> currenciesFrom = this.convertCSV(new String(re.getBody()));

                    uri = this.getUri(cr.getToDate());
                    re = this.requestService.get(uri, byte[].class, headers);
                    if (re.getStatusCode() == HttpStatus.OK && re.hasBody() && re.getBody() != null) {
                        final List<Currency> currenciesTo = this.convertCSV(new String(re.getBody()));

                        responses = this.getCurrencyChanges(currenciesFrom, currenciesTo, cr.getCodes());
                    }
                }
            } else {
                throw new ExceptionHandler.CurrencyException("Date range for currency query not provided", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new ExceptionHandler.CurrencyException("Currency code not provided", HttpStatus.BAD_REQUEST);
        }
        return responses;
    }

    private List<CurrencyResponse> getCurrencyChanges(List<Currency> from, List<Currency> to, List<String> codes) throws ExceptionHandler.CurrencyException {
        List<CurrencyResponse> responses = new ArrayList<>();

        if (from != null && to != null && codes != null) {
            for (final String code : codes) {
                Currency fc = this.findCurrencyByCode(from, code);
                Currency tc = this.findCurrencyByCode(to, code);

                if (fc != null && tc != null) {
                    responses.add(new CurrencyResponse(code, fc.getName(), tc.getRatio() - fc.getRatio(), 0.0));
                }

            }
        } else {
            throw new ExceptionHandler.CurrencyException("One or more parameters were equal to null", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responses;
    }

    private Currency findCurrencyByCode(List<Currency> currencies, String code) throws ExceptionHandler.CurrencyException {
        Currency currency = null;
        if (currencies != null && code != null) {
            for (final Currency c : currencies) {
                if (c.getCode().equals(code)) {
                    currency = c;
                }
            }
        } else {
            throw new ExceptionHandler.CurrencyException("Currency list cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return currency;
    }

    private String getUri(final Date date) {
        return String.format("https://www.lb.lt/lt/currency/daylyexport/?csv=1&class=Eu&type=day&date_day=%s", ff.format(date));
    }


    private List<Currency> convertCSV(final String data) throws ExceptionHandler.CurrencyException {
        List<Currency> currencies = new ArrayList<>();
        if (data != null) {
            List<String> lines = Arrays.asList(data.split("\r\n"));
            for (final String line : lines) {
                if (lines.indexOf(line) > 0) {
                    List<String> content = Arrays.asList(line.split("\\s*;\\s*"));
                    for (String c : content) {
                        content.set(content.indexOf(c), c.replaceAll("\"", ""));
                    }
                    try {
                        _logger.debug(content);
                        double value = Double.parseDouble(content.get(2).replace(',', '.'));
                        currencies.add(new Currency(content.get(0), content.get(1), value, ff.parse(content.get(3))));
                    } catch (NumberFormatException | ParseException ex) {
                        _logger.error(ex.getMessage());
                    }
                }
            }
        } else {
            throw new ExceptionHandler.CurrencyException("CSV data string cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return currencies;
    }
}
