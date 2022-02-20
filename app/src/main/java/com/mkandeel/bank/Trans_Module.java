package com.mkandeel.bank;

public class Trans_Module {
    private int ID;
    private int user_one;
    private int user_two;
    private double money;

    public Trans_Module(int ID, int user_one, int user_two, double money) {
        this.ID = ID;
        this.user_one = user_one;
        this.user_two = user_two;
        this.money = money;
    }

    public int getID() {
        return ID;
    }

    public int getUser_one() {
        return user_one;
    }

    public int getUser_two() {
        return user_two;
    }

    public double getMoney() {
        return money;
    }
}