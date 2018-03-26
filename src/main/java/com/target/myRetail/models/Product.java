package com.target.myRetail.models;

import org.springframework.data.annotation.Id;

public class Product {

    @Id
    private long id;
    private String name;
    private Price current_price;

    public long getId() {
        return id;
    }

    public Price getCurrent_price() {
        return current_price;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrent_price(double value, String code) {
        this.current_price = new Price(value, code);
    }
}
