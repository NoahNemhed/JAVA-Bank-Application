package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Bank {
    private String name;
    private List<Customer> customerList;

    public Bank(String name) {
        this.name = name;
        this.customerList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void loadCustomers() throws IOException {
        Scanner sc = new Scanner(new File("C:\\Users\\noahn\\Desktop\\demo\\BankApplication\\src\\com\\company\\Customers"));
        while(sc.hasNextLine()){
            String[] info = sc.nextLine().split(",");
            Customer customer = new Customer(info[0], info[1]);
            customerList.add(customer);
        }
        loadAccounts();
    }


    private void loadAccounts() throws IOException {
        Scanner sc = new Scanner(new File("C:\\Users\\noahn\\Desktop\\demo\\BankApplication\\src\\com\\company\\Accounts"));
        int lineNr = 0;
        while(sc.hasNextLine()){
            String[] accInfo = sc.nextLine().split(",");
            for(int i = 0; i<accInfo.length-1; i++){
                Account account = new Account(Double.parseDouble(accInfo[i]));
                customerList.get(lineNr).addAccount(account);
                i++;
            }
            lineNr += 1;
        }
    }

    public boolean validSSN(String SSN){
        return customerList.stream().anyMatch(customer -> customer.getSSN().equals(SSN));
    }

    public void changeCustomerName(String SSN, String name){
        if(validSSN(SSN)){
            findCustomer(SSN).get().setName(name);
            System.out.println("Name has been updated.");
        }else{
            System.out.println("No match for given SSN.");
        }
    }


    public void printCustomersAndAccounts(){
        if(!customerList.isEmpty()){
            customerList.stream().forEach(customer -> System.out.println(customer));
        }else{
            System.out.println("Custome List is empty.");
        }

    }

    public void deposit(double amount, String SSN) throws IOException {
        if(findCustomer(SSN) != null){
            if(findCustomer(SSN).get().getAccountList().size() > 1){
                Scanner sc = new Scanner(System.in);
                System.out.println("Theese are the available accounts for given SSN");
                findCustomer(SSN).get().getAccountList().stream().forEach(account -> System.out.println(account.getAccountNr()));
                System.out.println("Enter account number");
                String accountNr = sc.nextLine();
                int count = 0;
                for(Account acc : findCustomer(SSN).get().getAccountList()){
                    if(acc.getAccountNr().equals(accountNr)){
                        findCustomer(SSN).get().getAccountList().get(count).deposit(amount);
                        System.out.println(amount + " has been deposited to account - " + accountNr);
                    }
                    count++;
                }
                updateTxtFiles();

            }else{
                findCustomer(SSN).get().getAccountList().get(0).deposit(amount);
                System.out.println(amount + " has been deposited to " + findCustomer(SSN).get().getAccountList().get(0).getAccountNr());
                updateTxtFiles();
            }
        }
    }

    public void withdraw(double amount,String SSN) throws IOException {
        if(findCustomer(SSN) != null){
            if(findCustomer(SSN).get().getAccountList().size() > 1){
                Scanner sc = new Scanner(System.in);
                System.out.println("Theese are the available accounts for given SSN");
                findCustomer(SSN).get().getAccountList().stream().forEach(account -> System.out.println(account.getAccountNr()));
                System.out.println("Enter account number");
                String accountNr = sc.nextLine();
                int count = 0;
                for(Account acc : findCustomer(SSN).get().getAccountList()){
                    if(acc.getAccountNr().equals(accountNr)){
                        if(findCustomer(SSN).get().getAccountList().get(count).getBalance() > amount){
                            findCustomer(SSN).get().getAccountList().get(count).withdraw(amount);
                            System.out.println(amount + " has been taken out from " + findCustomer(SSN).get().getAccountList().get(count).getAccountNr());
                            updateTxtFiles();
                        }else{
                            System.out.println("insufficient funds.");
                        }
                    }
                    count++;
                }
                updateTxtFiles();

            }else{
                if(findCustomer(SSN).get().getAccountList().get(0).getBalance() > amount){
                    findCustomer(SSN).get().getAccountList().get(0).withdraw(amount);
                    System.out.println(amount + " has been taken out from " + findCustomer(SSN).get().getAccountList().get(0).getAccountNr());
                    updateTxtFiles();
                }else{
                    System.out.println("insufficient funds.");
                }

            }
        }
    }

    public void addAccounts(String SSN) throws IOException {
        if(validSSN(SSN)){
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter balance");
            double balance = sc.nextInt();
            findCustomer(SSN).get().addAccount(new Account(balance));
            System.out.println("Account added...");
            System.out.println(findCustomer(SSN));
            updateTxtFiles();
        }else{
            System.out.println("No customer with given SSN were found.");
        }

    }

    public void addNewCustomer(String SSN, String name) throws IOException {
        if(!findCustomer(SSN).isPresent()){
            Customer customer = new Customer(name, SSN);
            customerList.add(customer);
            addAccounts(SSN);
            updateTxtFiles();
        }else{
            System.out.println("Customer with given SSN already exists");
        }
    }

    public void removeCustomer(String SSN) throws IOException {
        if(findCustomer(SSN) != null){
            int count = 0;
            for(Customer c : customerList){
                if(c.getSSN().equals(SSN)){
                    int numOfAcc = findCustomer(SSN).get().getAccountList().size();
                    for(int i = 0; i<numOfAcc-1; i++){
                        findCustomer(SSN).get().getAccountList().remove(i);
                    }
                    customerList.remove(count);
                    System.out.println("Removed customer...");
                    updateTxtFiles();
                }
                count++;
            }
        }else{
            System.out.println("No customer with " + SSN + " were found.");
        }
    }

    public void deleteAccount(String SSN) throws IOException {
        if(validSSN(SSN)){
            if(findCustomer(SSN).get().getAccountList().size() == 1){
                System.out.println("Account " + findCustomer(SSN).get().getAccountList().get(0).getAccountNr() + " with balance : " + findCustomer(SSN).get().getAccountList().get(0).getBalance() + " has been deleted." );
                findCustomer(SSN).get().getAccountList().remove(0);
                updateTxtFiles();
            }else{
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter accountnr");
                String accnr = sc.next();
                for(int i = 0; i<findCustomer(SSN).get().getAccountList().size(); i++){
                    if(findCustomer(SSN).get().getAccountList().get(i).getAccountNr().equals(accnr)){
                        double balance = findCustomer(SSN).get().getAccountList().get(i).getBalance();
                        System.out.println("Account " + accnr + " with balance : " + balance + " has been deleted." );
                        findCustomer(SSN).get().getAccountList().remove(i);
                        updateTxtFiles();
                    }

                }
            }

        }else{
            System.out.println("No customer with given SSN were found.");
        }


        }

        public void printSpecificCustomer(String SSN){
        if(findCustomer(SSN) != null){
            System.out.println(findCustomer(SSN));
        }else{
            System.out.println("No customer with " + SSN + " were found.");
        }
        }


    public Optional<Customer> findCustomer(String SSN){
        return customerList.stream().filter(customer -> customer.getSSN().equals(SSN)).findFirst();
    }

    private void updateTxtFiles() throws IOException {
        BufferedWriter accountWriter = new BufferedWriter(new FileWriter("C:\\Users\\noahn\\Desktop\\demo\\BankApplication\\src\\com\\company\\Accounts", false));
        BufferedWriter customerWriter = new BufferedWriter(new FileWriter("C:\\Users\\noahn\\Desktop\\demo\\BankApplication\\src\\com\\company\\Customers", false));

        accountWriter.write("");
        customerWriter.write("");

        for(Customer c : customerList){
            for(Account acc : c.getAccountList()){
                accountWriter.write(acc.getBalance() + "," + acc.getAccountNr() + ",");
            }
            accountWriter.newLine();
        }

        for(Customer c : customerList){
            customerWriter.write(c.getName() + "," + c.getSSN());
            customerWriter.newLine();
        }

        customerWriter.close();
        accountWriter.close();



    }






    }
