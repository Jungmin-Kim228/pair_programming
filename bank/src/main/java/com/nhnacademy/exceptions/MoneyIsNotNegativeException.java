package com.nhnacademy.exceptions;

public class MoneyIsNotNegativeException extends IllegalArgumentException {
    public MoneyIsNotNegativeException(String s) {
        super(s);
    }
}
