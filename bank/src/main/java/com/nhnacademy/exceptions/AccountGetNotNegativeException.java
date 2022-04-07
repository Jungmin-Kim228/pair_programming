package com.nhnacademy.exceptions;

public class AccountGetNotNegativeException extends IllegalArgumentException {
    public AccountGetNotNegativeException(String message) {
        super(message);
    }
}
