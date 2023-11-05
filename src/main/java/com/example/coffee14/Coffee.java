package com.example.coffee14;

import java.math.BigDecimal;
import java.util.UUID;

public class Coffee {
    private String id;
    private String name;
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Coffee() {
        id = UUID.randomUUID().toString();
    }

    public Coffee(String name, BigDecimal price) {
        this();
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }
}
