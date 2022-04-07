package com.nhnacademy.exceptions;

public class CurrencyIsNotMatchException extends IllegalArgumentException {
    public CurrencyIsNotMatchException(String s) {
        super(s);
    }
}
