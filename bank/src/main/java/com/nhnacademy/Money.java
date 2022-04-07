package com.nhnacademy;

import static com.nhnacademy.Currency.checkInEnum;

import com.nhnacademy.exceptions.CurrencyIsNotMatchException;
import com.nhnacademy.exceptions.MoneyIsNotNegativeException;
import java.util.Objects;

public class Money {
    private final double amount;
    private Currency currency;

    public Money(double amount, Currency currency) {
        if(amount < 0){
            throw new MoneyIsNotNegativeException("money is not negative "+ amount);
        }
        if (checkInEnum(currency.toString()) == null)
            throw new CurrencyIsNotMatchException("not match currency. " + currency);
        this.amount = amount;
        this.currency = currency;
    }

    public double getMoneyAmt() {
        return this.amount;
    }

    public Currency getMoneyCur() {
        return this.currency;
    }

    public Money addMoney(Money money2) { // 메서드 빼내기 리팩토링
        checkSameCurrency(money2);
        return new Money(this.amount + money2.amount, this.currency);
    }

    public Money subtractMoney(Money money2) {
        if (this.amount < money2.amount)
            throw new MoneyIsNotNegativeException("negative");
        checkSameCurrency(money2);
        return new Money(this.amount - money2.amount, this.currency);
    }

    public void checkSameCurrency(Money money2) {
        if(!this.currency.equals(money2.getMoneyCur())){
            throw new CurrencyIsNotMatchException("not match. this currency: " + this.currency.toString() + " " + money2.currency.toString());
        }
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
}
