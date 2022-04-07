package com.nhnacademy;

public class Account {
    private int money;

    public Account(int money) {
        this.money = money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public int getMoney() {
        return money;
    }
}
