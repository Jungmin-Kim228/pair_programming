package com.nhnacademy;

import com.nhnacademy.exceptions.CurrencyIsNotMatchException;

public enum Currency {
    WON("원"),
    DOLLAR("달러");
    private String type;

    Currency(String type) {
        this.type = type;
    }

    public static Currency getInEnum(String type) {
        if (checkInEnum(type))
            return Currency.valueOf(type);
        throw new CurrencyIsNotMatchException("not match currnecy. " + type);
    }

    public static boolean checkInEnum(String type) {
        for (Currency c : Currency.values())
            if (c.toString().equals(type))
                return true;
        return false;
    }
}
