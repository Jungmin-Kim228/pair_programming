package com.nhnacademy;

import static org.assertj.core.api.Assertions.assertThat;

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
}