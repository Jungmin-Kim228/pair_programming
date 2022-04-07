package com.nhnacademy;

import com.nhnacademy.exceptions.MoneyIsNotNullException;
import java.util.Objects;

public class Money {
    private final long amount;
    private Currency currency;

    public Money(long amount, Currency currency) {
        if(amount < 0){
            throw new MoneyIsNotNullException("money is not negative "+ amount);
        }
        this.amount = amount;
        this.currency = currency;
    }

    public Money addMoney(Money money2) {
        return new Money(this.amount + money2.amount, this.currency);
    }

    public long getMoney() {
        return this.amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return amount == money.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    public Long getMoneyAmt() {
        return this.amount;
    }

    public Currency getMoneyCur() {
        return this.currency;
    }
}
