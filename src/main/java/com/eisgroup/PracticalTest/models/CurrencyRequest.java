package com.eisgroup.PracticalTest.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class CurrencyRequest {

    private List<String> codes;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date fromDate;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date toDate;

    public CurrencyRequest() {
    }

    public CurrencyRequest(List<String> codes, Date fromDate, Date toDate) {
        this.codes = codes;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
