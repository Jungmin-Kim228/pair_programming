package com.nhnacademy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class bankServiceTest {

    @BeforeEach
    void setUp() {

    }

    @DisplayName("1,000원 + 1,000원 = 2,000원")
    @Test
    void addWonTest() {
        Money money1 = new Money(1000L);
        Money money2 = new Money(1000L);

        Money result = money1.addMoney(money2);

        assertThat(result.getMoney()).isEqualTo(2000L);
    }

    @DisplayName("2,000원과 2,000원은 같다.(equals)")
    @Test
    void checkEqualMoney(){
        Money money1 = new Money(2000L);
        Money money2 = new Money(2000L);

        assertThat(money1.equals(money2)).isTrue();

    }

    @DisplayName("2,000원과 3,000원은 같지않다.(equals)")
    @Test
    void checkNotEqualMoney(){
        Money money1 = new Money(2000L);
        Money money2 = new Money(3000L);

        assertThat(money1.equals(money2)).isFalse();

    }

    @DisplayName("돈은 음수일 수 없다.")
    @Test
    void IfMoneyIsNegative_throwIllegalArgumentException() {
        Money money1 = new Money(-1000L);

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
}