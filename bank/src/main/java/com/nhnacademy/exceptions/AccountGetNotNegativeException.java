package com.nhnacademy.exceptions;

public class AccountGetNotNegativeException extends IllegalArgumentException {
    public AccountGetNotNegativeException(int addMoneyAmt) {
        super("money is not negative" + addMoneyAmt);
    }
}
