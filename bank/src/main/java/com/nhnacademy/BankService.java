package com.nhnacademy;

import com.nhnacademy.exceptions.ConvertRateIsNotMatchException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BankService {

    public enum ConvertRate {
        DOLLAR_TO_WON(BigDecimal.valueOf(1000)),
        WON_TO_DOLLAR(BigDecimal.valueOf(0.001)),
        WON_TO_YEN(BigDecimal.valueOf(0.1)),
        YEN_TO_WON(BigDecimal.valueOf(10)),
        //DOLLAR_TO_YEN(BigDecimal.valueOf(100)), // 미지원
        YEN_TO_DOLLAR(BigDecimal.valueOf(0.01));

        final BigDecimal convertRate;

        ConvertRate(BigDecimal convertRate) {
            this.convertRate = convertRate;
        }
    }

    public Money convert(Money money, Currency convertCurrency) {
        String strCvtCur = convertCurrency.toString();
        String strMonCur = money.getMoneyCur().toString();
        String type = strMonCur.concat("_TO_"+strCvtCur);
        BigDecimal resultAmt = null;

        BigDecimal convertedAmt = money.getMoneyAmt().multiply(getInConvertRateEnum(type));
        switch (convertCurrency) {
            case WON: case YEN:
                resultAmt = convertedAmt.setScale(-1, RoundingMode.HALF_UP).setScale(0);
                break;
            case DOLLAR:
                resultAmt = convertedAmt.setScale(2, RoundingMode.HALF_UP);
                break;
        }
        return new Money(resultAmt, convertCurrency);
    }

    public BigDecimal getInConvertRateEnum(String type) {
        if (checkInConvertRateEnum(type))
            return ConvertRate.valueOf(type).convertRate;
        throw new ConvertRateIsNotMatchException("not match rate. " + type);
    }

    public boolean checkInConvertRateEnum(String type) {
        for (ConvertRate c : ConvertRate.values())
            if (c.toString().equals(type))
                return true;
        return false;
    }
}
