package com.nhnacademy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nhnacademy.exceptions.AccountGetNotNegativeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class bankServiceTest {

    @BeforeEach
    void setUp() {

    }

    @DisplayName("계좌에 돈(원) 추가")
    @Test
    void addWonTest() {
        Account account = new Account(1000);
        account.addMoney(1000);

        assertThat(account.getMoney()).isEqualTo(2000);
    }

    @DisplayName("계좌에 음수 돈 추가 불가능")
    @Test
    void addNegativeMoneyExceptioinTest() {
        Account account = new Account(1000);

        int addMoneyAmt = -2000;
        assertThatThrownBy(() -> account.addMoney(addMoneyAmt))
            .isInstanceOf(AccountGetNotNegativeException.class)
                .hasMessageContainingAll("money is not negative");
    }

    @DisplayName("계좌에 돈(달러) 추가")
    @Test
    void addDollarTest() {
        Account account = new Account(5, MoneyType.DOLLAR);

        account.addMoney(5, MoneyType.DOLLAR);

        assertThat(account.getMoney().toString()).isEqualTo("10$");
    }
}