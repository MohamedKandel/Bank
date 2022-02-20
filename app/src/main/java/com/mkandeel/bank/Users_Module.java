package com.mkandeel.bank;

public class Users_Module {
    private int ID;
    private String name;
    private String mail;
    private double balance;

    public Users_Module(int ID, String name, String mail, double balance) {
        this.ID = ID;
        this.name = name;
        this.mail = mail;
        this.balance = balance;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public double getBalance() {
        return balance;
    }
}