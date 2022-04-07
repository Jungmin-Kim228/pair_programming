package com.nhnacademy;

import com.nhnacademy.exceptions.CurrencyIsNotMatchException;

public enum Currency {
    WON("원"),
    DOLLAR("달러"),
    YEN("엔");
    private String type;

    Currency(String type) {
        this.type = type;
    }

    public static Currency getInCurrencyEnum(String type) {
        if (checkInCurrencyEnum(type))
            return Currency.valueOf(type);
        throw new CurrencyIsNotMatchException("not match currnecy. " + type);
    }

    public static boolean checkInCurrencyEnum(String type) {
        for (Currency c : Currency.values())
            if (c.toString().equals(type))
                return true;
        return false;
    }
}
