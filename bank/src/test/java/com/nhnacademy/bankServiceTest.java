package com.nhnacademy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nhnacademy.exceptions.AccountGetNotNegativeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class bankServiceTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void addMoneyTest() {
        Account account = new Account(1000);
        account.addMoney(1000);

        assertThat(account.getMoney()).isEqualTo(2000);
    }

    @Test
    void addNegativeMoneyExceptioinTest() {
        Account account = new Account(1000);

        assertThatThrownBy(() -> account.addMoney(-2000))
            .isInstanceOf(AccountGetNotNegativeException.class)
                .hasMessageContainingAll("money is not negative");
    }
}