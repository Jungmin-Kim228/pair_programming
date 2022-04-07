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

        System.out.println(money.getMoneyAmt());
        if(convertCurrency.toString().equals("WON")){
            convertedAmt = money.getMoneyAmt() * DOLLAR_TO_WON.convertRate;
            System.out.println(convertedAmt);
        } else if(convertCurrency.toString().equals("DOLLAR")){
            convertedAmt = money.getMoneyAmt() * WON_TO_DOLLAR.convertRate;
        }

        return new Money(convertedAmt, convertCurrency);
    }

}
