package com.nhnacademy;

import static com.nhnacademy.Currency.DOLLAR;
import static com.nhnacademy.Currency.WON;
import static com.nhnacademy.Currency.YEN;
import static com.nhnacademy.Currency.getInCurrencyEnum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class bankServiceTest {

    @DisplayName("통화는 Currency에 등록된 것만 존재")
    @Test
    void onlyUseExistCurrency () {
        String type = "EUR";

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Money(BigDecimal.valueOf(5.25), getInCurrencyEnum(type)))
            .withMessageContaining("not match", type);
    }


    @DisplayName("환율은 1달러 <-> 1,000원")
    @Test
    void oneDollarEqualsOne1000WonTest() {
        Money money1 = new Money(BigDecimal.ONE, DOLLAR);
        Money money2 = new Money(BigDecimal.valueOf(1000), WON);

        Money result1 = new BankService().convert(money1, WON);
        Money result2 = new BankService().convert(money2, DOLLAR);

        assertThat(result1.getMoneyAmt()).isEqualByComparingTo(money2.getMoneyAmt());
        assertThat(result2.getMoneyAmt()).isEqualByComparingTo(money1.getMoneyAmt());
    }

    @DisplayName("1000원 -> 100엔")
    @Test
    void wonConvertYenTest() {
        BankService bankService = new BankService();
        Money money = new Money(BigDecimal.valueOf(1000), WON);

        Money result = bankService.convert(money, YEN);

        assertThat(result.getMoneyAmt()).isEqualByComparingTo(BigDecimal.valueOf(100));
        assertThat(result.getMoneyCur()).isEqualTo(YEN);
    }

    @DisplayName("YEN -> $ 지원, $ -> YEN 미지원")
    @Test
    void yenConvertDollar_and_NotDollarConvertYen() {
        BankService bankService = new BankService();
        Money money1 = new Money(BigDecimal.valueOf(100), YEN);
        Money money2 = new Money(BigDecimal.valueOf(1), DOLLAR);

        Money result = bankService.convert(money1, DOLLAR);
        assertThat(result.getMoneyAmt()).isEqualByComparingTo(BigDecimal.ONE);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> bankService.convert(money2, YEN))
            .withMessageContaining("not match rate.", money2.getMoneyCur(), YEN);
    }

    @DisplayName("5.25$ -> 5,250원")
    @Test
    void wonConvertDollarTest() {
        BankService bankService = new BankService();
        Money money = new Money(BigDecimal.valueOf(5.25), DOLLAR);

        Money result = bankService.convert(money, WON);

        assertThat(result.getMoneyAmt()).isEqualByComparingTo(BigDecimal.valueOf(5250));
        assertThat(result.getMoneyCur()).isEqualTo(WON);
    }
    @DisplayName("달러 -> 원화: 5원 이상 -> 10원으로 반올림")
    @Test
    void dollarToWonRoundingOff () {
        BankService bankService = new BankService();
        Money money = new Money(BigDecimal.valueOf(0.005), DOLLAR);

        Money result = bankService.convert(money, WON);

        assertThat(result.getMoneyAmt()).isEqualByComparingTo(BigDecimal.TEN);
        assertThat(result.getMoneyCur()).isEqualTo(WON);
    }

    @DisplayName("원화 -> 달러: $0.005 이상 -> $0.01 반올림")
    @Test
    void wonToDollarRoundingOff() {
        BankService bankService = new BankService();
        Money money = new Money(BigDecimal.valueOf(5), WON);

        Money result = bankService.convert(money, DOLLAR);

        assertThat(result.getMoneyAmt()).isEqualByComparingTo(BigDecimal.valueOf(0.01));
        assertThat(result.getMoneyCur()).isEqualTo(DOLLAR);
    }

}