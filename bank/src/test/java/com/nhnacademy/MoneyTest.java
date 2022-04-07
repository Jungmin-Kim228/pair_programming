package com.nhnacademy;

import static com.nhnacademy.Currency.DOLLAR;
import static com.nhnacademy.Currency.WON;
import static com.nhnacademy.Currency.YEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyTest {
    @DisplayName("1,000원 + 1,000원 = 2,000원")
    @Test
    void addWonTest() {
        Money money1 = new Money(BigDecimal.valueOf(1000), WON);
        Money result = money1.addMoney(money1);

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
        Money result = money1.addMoney(money1);

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

    @DisplayName("100Y + 100Y = 200Y")
    @Test
    void addYenTest() {
        Money money1 = new Money(BigDecimal.valueOf(100), YEN);
        Money result = money1.addMoney(money1);

        assertThat(result.getMoneyAmt()).isEqualByComparingTo(BigDecimal.valueOf(200));
        assertThat(result.getMoneyCur()).isEqualTo(YEN);
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
        Money money2 = new Money(BigDecimal.valueOf(5), YEN);

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
}