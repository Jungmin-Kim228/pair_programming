package com.nhnacademy;

public enum Currency {
    WON("원"),
    DOLLAR("달러");
    private String type;

    Currency(String type) {
        this.type = type;
    }
}
