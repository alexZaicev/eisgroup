package com.eisgroup.PracticalTest.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Currency {

    private String name;
    private String code;
    private double ratio;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date date;

    public Currency() {
    }

    public Currency(String name, String code, double ratio, Date date) {
        this.name = name;
        this.code = code;
        this.ratio = ratio;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
