package com.eisgroup.PracticalTest.services;

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
    private RequestService requestService;

    public List<CurrencyResponse> getCurrencyData(final CurrencyRequest cr) {
        List<CurrencyResponse> responses = null;
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
        return responses;
    }

    private List<CurrencyResponse> getCurrencyChanges(List<Currency> from, List<Currency> to, List<String> codes) {
        List<CurrencyResponse> responses = new ArrayList<>();

        for (final String code : codes) {
            Currency fc = this.findCurrencyByCode(from, code);
            Currency tc = this.findCurrencyByCode(to, code);

            if (fc != null && tc != null) {
                responses.add(new CurrencyResponse(code, fc.getName(), tc.getRatio() - fc.getRatio(), 0.0));
            }

        }

        return responses;
    }

    private Currency findCurrencyByCode(List<Currency> currencies, String code) {
        Currency currency = null;
        for (final Currency c : currencies) {
            if (c.getCode().equals(code)) {
                currency = c;
            }
        }
        return currency;
    }

    private String getUri(final Date date) {
        return String.format("https://www.lb.lt/lt/currency/daylyexport/?csv=1&class=Eu&type=day&date_day=%s", ff.format(date));
    }


    private List<Currency> convertCSV(final String data) {
        List<Currency> currencies = new ArrayList<>();
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

        return currencies;
    }
}
