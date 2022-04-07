package com.nhnacademy;

import static com.nhnacademy.Currency.DOLLAR;
import static com.nhnacademy.Currency.WON;
import static com.nhnacademy.Currency.getInEnum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class bankServiceTest {

    @DisplayName("1,000원 + 1,000원 = 2,000원")
    @Test
    void addWonTest() {
        Money money1 = new Money(1000L, WON);
        Money money2 = new Money(1000L, WON);

        Money result = money1.addMoney(money2);

        assertThat(result.getMoneyAmt()).isEqualTo(2000L);
    }

    @DisplayName("2,000원과 2,000원은 같다.(equals)")
    @Test
    void checkEqualMoney(){
        Money money1 = new Money(2000L, WON);
        Money money2 = new Money(2000L, WON);

        assertThat(money1.equals(money2)).isTrue();
    }

    @DisplayName("2,000원과 3,000원은 같지 않다.(equals)")
    @Test
    void checkNotEqualMoney(){
        Money money1 = new Money(2000L, WON);
        Money money2 = new Money(3000L, WON);

        assertThat(money1.equals(money2)).isFalse();
    }

    @DisplayName("돈은 음수일 수 없다.")
    @Test
    void IfMoneyIsNegative_throwMoneyIsNotNegativeException() {
        long amount = -1000L;
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Money(amount, WON))
            .withMessageContaining("negative", amount);
    }

    @DisplayName("5$ + 5$ = 10$")
    @Test
    void addDollarTest() {
        Money money1 = new Money(5L, DOLLAR);
        Money money2 = new Money(5L, DOLLAR);

        Money result = money1.addMoney(money2);

        assertThat(result.getMoneyAmt()).isEqualTo(10L);
        assertThat(result.getMoneyCur()).isEqualTo(DOLLAR);
    }

    @DisplayName("5$ - 6$ = 오류")
    @Test
    void substractDollar_checkResultNegative_throwResultIsNotNegativeException() {
        Money money1 = new Money(5L, DOLLAR);
        Money money2 = new Money(6L, DOLLAR);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> money1.subtractMoney(money2))
            .withMessageContaining("negative", money1.getMoneyAmt());
    }

    @DisplayName("통화 종류가 다를 때 더하기 오류")
    @Test
    void checkAddMoneyCurrency_throwCurrencyIsNotMatchException() {
        Money money1 = new Money(5L, DOLLAR);
        Money money2 = new Money(5L, WON);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> money1.addMoney(money2))
            .withMessageContaining("not match", money1.getMoneyCur(), money2.getMoneyCur());
    }

    @DisplayName("통화 종류가 다를 때 빼기 오류")
    @Test
    void checkSubMoneyCurrency_throwCurrencyIsNotMatchException() {
        Money money1 = new Money(5L, DOLLAR);
        Money money2 = new Money(5L, WON);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> money1.subtractMoney(money2))
            .withMessageContaining("not match", money1.getMoneyCur(), money2.getMoneyCur());
    }

    @DisplayName("5.25$ + 5.25$ = 10.50$ (소숫점 이하 2자리)")
    @Test
    void checkDecimalPointCalcuation () {
        Money money1 = new Money(5.25D, DOLLAR);
        Money money2 = new Money(5.25D, DOLLAR);

        Money result = money1.addMoney(money2);

        assertThat(result.getMoneyAmt()).isEqualTo(10.50D);
        assertThat(result.getMoneyCur()).isEqualTo(DOLLAR);
    }

    @DisplayName("통화는 달러화와 원화만이 존재")
    @Test
    void onlyUseExistCurrency () {
        String type = "YEN";

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Money(5.25D, getInEnum(type)))
            .withMessageContaining("not match", type);
    }


    @DisplayName("환율은 1달러 <-> 1,000원")
    @Test
    void oneDollarEqualsOne1000WonTest() {
        Money money1 = new Money(1, DOLLAR);
        Money money2 = new Money(1000, WON);

        Money result1 = new BankService().convert(money1, WON);
        Money result2 = new BankService().convert(money2, DOLLAR);

        assertThat(result1.getMoneyAmt()).isEqualTo(money2.getMoneyAmt());
        assertThat(result2.getMoneyAmt()).isEqualTo(money1.getMoneyAmt());
    }

    @DisplayName("5.25$ -> 5,250원")
    @Test
    void wonConvertDollarTest() {
        BankService bankService = new BankService();
        Money money = new Money(5.25, DOLLAR);

        Currency convertCurrency = WON;
        Money result = bankService.convert(money, convertCurrency);

        assertThat(result.getMoneyAmt()).isEqualTo(5250);
        assertThat(result.getMoneyCur()).isEqualTo(WON);
    }
    @DisplayName("달러 -> 원화: 5원 이상 -> 10원으로 반올림")
    @Test
    void dollarToWonRoundingOff () {
        BankService bankService = new BankService();
        Money money = new Money(0.005, DOLLAR);

        Currency convertCurrency = WON;
        Money result = bankService.convert(money, convertCurrency);

        assertThat(result.getMoneyAmt()).isEqualTo(10);
        assertThat(result.getMoneyCur()).isEqualTo(WON);
    }

    @DisplayName("원화 -> 달러: $0.005 이상 -> $0.01 반올림")
    @Test
    void wonToDollarRoundingOff() {
        BankService bankService = new BankService();
        Money money = new Money(5, WON);

        Currency convertCurrency = DOLLAR;
        Money result = bankService.convert(money, convertCurrency);

        assertThat(result.getMoneyAmt()).isEqualTo(0.01);
        assertThat(result.getMoneyCur()).isEqualTo(DOLLAR);
    }

}