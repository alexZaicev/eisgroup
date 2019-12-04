package com.eisgroup.PracticalTest.models;

public class CurrencyResponse {

    private String code;
    private String name;
    private double currencyChange;
    private double perCurrencyChange;

    public CurrencyResponse() {
    }

    public CurrencyResponse(String code, String name, double currencyChange, double perCurrencyChange) {
        this.code = code;
        this.name = name;
        this.currencyChange = currencyChange;
        this.perCurrencyChange = perCurrencyChange;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrencyChange() {
        return currencyChange;
    }

    public void setCurrencyChange(double currencyChange) {
        this.currencyChange = currencyChange;
    }

    public double getPerCurrencyChange() {
        return perCurrencyChange;
    }

    public void setPerCurrencyChange(double perCurrencyChange) {
        this.perCurrencyChange = perCurrencyChange;
    }
}
