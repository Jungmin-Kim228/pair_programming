package com.nhnacademy.exceptions;

public class CurrencyIsNotMatchException extends IllegalArgumentException {
    public CurrencyIsNotMatchException(String s, String currency1, String currency2) {
        super(s + currency1 + currency2);
    }
}
