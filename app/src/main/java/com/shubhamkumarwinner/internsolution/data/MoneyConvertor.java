package com.shubhamkumarwinner.internsolution.data;


public class MoneyConvertor{
    private boolean success;
    private String base;
    private String date;
    private Rates rates;

    public MoneyConvertor(boolean success, String base, String date, Rates rates) {
        this.success = success;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

    public Rates getRates() {
        return rates;
    }
}

