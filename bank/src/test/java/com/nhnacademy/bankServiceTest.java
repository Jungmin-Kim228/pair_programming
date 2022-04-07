package com.nhnacademy;

import static com.nhnacademy.Currency.DOLLAR;
import static com.nhnacademy.Currency.WON;
import static com.nhnacademy.Currency.getInEnum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class bankServiceTest {

    @DisplayName("1,000원 + 1,000원 = 2,000원")
    @Test
    void addWonTest() {
        Money money1 = new Money(BigDecimal.valueOf(1000), WON);
        Money money2 = new Money(BigDecimal.valueOf(1000), WON);

        Money result = money1.addMoney(money2);

        assertThat(result.getMoneyAmt()).isEqualByComparingTo(BigDecimal.valueOf(2000));
    }

    @DisplayName("2,000원과 2,000원은 같다.(equals)")
    @Test
    void checkEqualMoney(){
        Money money1 = new Money(BigDecimal.valueOf(2000L), WON);
        Money money2 = new Money(BigDecimal.valueOf(2000L), WON);

        assertThat(money1.equals(money2)).isTrue();
    }

    @DisplayName("2,000원과 3,000원은 같지 않다.(equals)")
    @Test
    void checkNotEqualMoney(){
        Money money1 = new Money(BigDecimal.valueOf(2000), WON);
        Money money2 = new Money(BigDecimal.valueOf(3000), WON);

        assertThat(money1.equals(money2)).isFalse();
    }

    @DisplayName("돈은 음수일 수 없다.")
    @Test
    void IfMoneyIsNegative_throwMoneyIsNotNegativeException() {
        long amount = -1000L;
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Money(BigDecimal.valueOf(amount), WON))
            .withMessageContaining("negative", amount);
    }

    @DisplayName("5$ + 5$ = 10$")
    @Test
    void addDollarTest() {
        Money money1 = new Money(BigDecimal.valueOf(5), DOLLAR);
        Money money2 = new Money(BigDecimal.valueOf(5), DOLLAR);

        Money result = money1.addMoney(money2);

        assertThat(result.getMoneyAmt()).isEqualTo(BigDecimal.TEN);
        assertThat(result.getMoneyCur()).isEqualTo(DOLLAR);
    }

    @DisplayName("5$ - 6$ = 오류")
    @Test
    void substractDollar_checkResultNegative_throwResultIsNotNegativeException() {
        Money money1 = new Money(BigDecimal.valueOf(5), DOLLAR);
        Money money2 = new Money(BigDecimal.valueOf(6), DOLLAR);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> money1.subtractMoney(money2))
            .withMessageContaining("negative", money1.getMoneyAmt());
    }

    @DisplayName("통화 종류가 다를 때 더하기 오류")
    @Test
    void checkAddMoneyCurrency_throwCurrencyIsNotMatchException() {
        Money money1 = new Money(BigDecimal.valueOf(5), DOLLAR);
        Money money2 = new Money(BigDecimal.valueOf(5), WON);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> money1.addMoney(money2))
            .withMessageContaining("not match", money1.getMoneyCur(), money2.getMoneyCur());
    }

    @DisplayName("통화 종류가 다를 때 빼기 오류")
    @Test
    void checkSubMoneyCurrency_throwCurrencyIsNotMatchException() {
        Money money1 = new Money(BigDecimal.valueOf(5), DOLLAR);
        Money money2 = new Money(BigDecimal.valueOf(5), WON);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> money1.subtractMoney(money2))
            .withMessageContaining("not match", money1.getMoneyCur(), money2.getMoneyCur());
    }

    @DisplayName("5.25$ + 5.25$ = 10.50$ (소숫점 이하 2자리)")
    @Test
    void checkDecimalPointCalcuation () {
        Money money1 = new Money(BigDecimal.valueOf(5.25), DOLLAR);
        Money money2 = new Money(BigDecimal.valueOf(5.25), DOLLAR);

        Money result = money1.addMoney(money2);

        System.out.println();
        assertThat(result.getMoneyAmt()).isEqualByComparingTo(BigDecimal.valueOf(10.50));
        assertThat(result.getMoneyCur()).isEqualTo(DOLLAR);
    }

    @DisplayName("통화는 달러화와 원화만이 존재")
    @Test
    void onlyUseExistCurrency () {
        String type = "YEN";

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Money(BigDecimal.valueOf(5.25), getInEnum(type)))
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