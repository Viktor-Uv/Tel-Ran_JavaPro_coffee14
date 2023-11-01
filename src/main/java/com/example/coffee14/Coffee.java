package com.example.coffee14;

import java.util.UUID;

public class Coffee {
    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coffee() {
        id = UUID.randomUUID().toString();
    }

    public Coffee(String name) {
        this();
        this.name = name;
    }

    public String getId() {
        return id;
    }
}
