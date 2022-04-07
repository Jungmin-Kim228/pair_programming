package com.nhnacademy;

import static com.nhnacademy.Currency.getInCurrencyEnum;

import com.nhnacademy.exceptions.CurrencyIsNotMatchException;
import com.nhnacademy.exceptions.MoneyIsNotNegativeException;
import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    private final BigDecimal amount;
    private final Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        int compareAmount = amount.compareTo(BigDecimal.ZERO);
        if(compareAmount < 0){
            throw new MoneyIsNotNegativeException("money is not negative "+ amount);
        }
        Currency cur = getInCurrencyEnum(currency.toString());

        this.amount = amount;
        this.currency = cur;
    }

    public BigDecimal getMoneyAmt() {
        return this.amount;
    }

    public Currency getMoneyCur() {
        return this.currency;
    }

    public Money addMoney(Money money2) {
        checkSameCurrency(money2);
        return new Money(this.amount.add(money2.amount), this.currency);
    }

    public Money subtractMoney(Money money2) {
        int compareAmount = this.amount.compareTo(money2.amount);
        if (compareAmount < 0)
            throw new MoneyIsNotNegativeException("negative");
        checkSameCurrency(money2);
        return new Money(this.amount.subtract(money2.amount), this.currency);
    }

    public void checkSameCurrency(Money money2) {
        if(!this.currency.equals(money2.getMoneyCur())){
            throw new CurrencyIsNotMatchException("not match" + this.currency.toString() + " " + money2.currency.toString());
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
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
