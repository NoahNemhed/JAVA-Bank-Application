package com.company;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private String SSN;
    private List<Account> accountList;

    public Customer(String name, String SSN) {
        this.name = name;
        this.SSN = SSN;
        this.accountList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getSSN() {
        return SSN;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void addAccount(Account account){
        accountList.add(account);
    }


    public void removeAccount(Account account){
        accountList.remove(account);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", SSN='" + SSN + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}
