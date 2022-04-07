package com.nhnacademy;

import static com.nhnacademy.BankService.ConvertRate.DOLLAR_TO_WON;
import static com.nhnacademy.BankService.ConvertRate.WON_TO_DOLLAR;

public class BankService {
    private ConvertRate rate;

    public enum ConvertRate {
        DOLLAR_TO_WON(1000),
        WON_TO_DOLLAR(0.001);

        final double convertRate;

        ConvertRate(double convertRate) {
            this.convertRate = convertRate;
        }

    }

    public Money convert(Money money, Currency convertCurrency) {
        double convertedAmt = 0;

        if(convertCurrency.toString().equals("WON")){
            convertedAmt = money.getMoneyAmt() * DOLLAR_TO_WON.convertRate;

        } else if(convertCurrency.toString().equals("DOLLAR")){
            convertedAmt = money.getMoneyAmt() * WON_TO_DOLLAR.convertRate;
        }

        if (convertCurrency == Currency.WON) {
            convertedAmt = Math.round(convertedAmt / 10) * 10;
        }

        if (convertCurrency == Currency.DOLLAR) {
            convertedAmt = Math.round(convertedAmt * 100) / 100.0;
        }
        return new Money(convertedAmt, convertCurrency);
    }

}
