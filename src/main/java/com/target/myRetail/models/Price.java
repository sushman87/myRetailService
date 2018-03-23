package com.target.myRetail.models;

public class Price {

    private double value;
    private String currencyCode;

    public Price(){
        this.value = 0.0;
        this.currencyCode = "";
    }

    public Price(double value, String currencyCode){
        this.value = value;
        this.currencyCode = currencyCode;
    }

    public void setValue(double value){
        this.value = value;
    }

    public double getValue(){
        return value;
    }

    public void setCurrencyCode(String code){
        this.currencyCode = code;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
