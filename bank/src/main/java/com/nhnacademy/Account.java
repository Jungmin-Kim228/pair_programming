package com.nhnacademy;

import com.nhnacademy.exceptions.AccountGetNotNegativeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Account {
    private Map<Integer, String>

    public Account(Integer moneyAmt, MoneyType moneyType) {
        this.moneyAmt = moneyAmt;
        this.moneyType = moneyType;
    }

    public void addMoney(Integer money, MoneyType type) {
        if (money < 0)
            throw new AccountGetNotNegativeException(money);
        this.money += money;
    }

    public int getMoney() {
        return money;
    }
}
