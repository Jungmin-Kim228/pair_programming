package com.nhnacademy;

public class BankService {
    private ConvertRate rate;
    public enum ConvertRate {
        DOLLAR_TO_WON(1000D),
        WON_TO_DOLLAR(0.001D);

        final Double convertRate;

        ConvertRate(Double convertRate) {
            this.convertRate = convertRate;
        }


    }

    public Money convert(Money money, Currency convertCurrency) {
        
    }
}
