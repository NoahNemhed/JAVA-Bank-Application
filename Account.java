package com.company;

import java.util.UUID;

public class Account {
    private double balance;
    private UUID accountNr;

    public Account(double balance) {
        this.balance = balance;
        this.accountNr = UUID.randomUUID();
    }

    public double getBalance() {
        return balance;
    }

    public UUID getAccountNr() {
        return accountNr;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public void deposit(double amount){
        this.balance = this.balance + amount;
    }


    public void withdraw(double amount){
        if(this.balance > amount){
            this.balance = this.balance - amount;
        }else {

        }

    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", accountNr='" + accountNr + '\'' +
                '}';
    }
}
