package com.nhnacademy;

import static com.nhnacademy.BankService.ConvertRate.DOLLAR_TO_WON;
import static com.nhnacademy.BankService.ConvertRate.WON_TO_DOLLAR;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BankService {
    private ConvertRate rate;

    public enum ConvertRate {
        DOLLAR_TO_WON(BigDecimal.valueOf(1000)),
        WON_TO_DOLLAR(BigDecimal.valueOf(0.001));

        final BigDecimal convertRate;

        ConvertRate(BigDecimal convertRate) {
            this.convertRate = convertRate;
        }
    }

    public Money convert(Money money, Currency convertCurrency) {
        BigDecimal convertedAmt = BigDecimal.ZERO;

        if (convertCurrency == Currency.WON){
            convertedAmt = money.getMoneyAmt().multiply(DOLLAR_TO_WON.convertRate);
            convertedAmt = convertedAmt.setScale(-1, RoundingMode.HALF_UP).setScale(0);

        } else if(convertCurrency == Currency.DOLLAR){
            convertedAmt = money.getMoneyAmt().multiply(WON_TO_DOLLAR.convertRate);
            convertedAmt = convertedAmt.setScale(2, RoundingMode.HALF_UP);
        }

        return new Money(convertedAmt, convertCurrency);
    }

}
