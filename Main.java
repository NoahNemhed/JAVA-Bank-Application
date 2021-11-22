package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static void runApp() throws IOException {
        Bank bank = new Bank("Handelsbanken");
        bank.loadCustomers();

        Scanner sc = new Scanner(System.in);
        System.out.println("Press (0) to print menu \n" +
                "Press (1) to print customers. \n" +
                "Press (2) to print specific customer. \n" +
                "Press (3) to deposit. \n" +
                "Press (4) to withdraw. \n" +
                "Press (5) to add account. \n" +
                "Press (6) to remove account. \n" +
                "Press (7) to add new customer. \n" +
                "Press (8) to delete a customer. \n" +
                "Press (9) to change customer name. \n" +
                "Press (10) to quit." );
        boolean quit = false;
        String SSN;
        double amount;
        while(!quit){


            switch (sc.nextInt()){
                case 0:
                    System.out.println("Press (0) to print menu \n" +
                            "Press (1) to print customers. \n" +
                            "Press (2) to print specific customer. \n" +
                            "Press (3) to deposit. \n" +
                            "Press (4) to withdraw. \n" +
                            "Press (5) to add account. \n" +
                            "Press (6) to remove account. \n" +
                            "Press (7) to add new customer. \n" +
                            "Press (8) to delete a customer. \n" +
                            "Press (9) to change customer name. \n" +
                            "Press (10) to quit." );
                    break;
                case 1:
                    bank.printCustomersAndAccounts();
                    break;
                case 2:
                    System.out.println("Enter SSN");
                    SSN = sc.next();
                    bank.printSpecificCustomer(SSN);
                    break;
                case 3:
                    System.out.println("Enter amount");
                    amount = sc.nextInt();
                    System.out.println("Enter SSN");
                    SSN = sc.next();
                    bank.deposit(amount, SSN);
                    break;
                case 4:
                    System.out.println("Enter amount");
                    amount = sc.nextInt();
                    System.out.println("Enter SSN");
                    SSN = sc.next();
                    bank.withdraw(amount, SSN);
                    break;
                case 5:
                    System.out.println("Enter SSN");
                    SSN = sc.next();
                    bank.addAccounts(SSN);
                    break;
                case 6:
                    System.out.println("Enter SSN");
                    SSN = sc.next();
                    try{
                        bank.deleteAccount(SSN);
                    }catch (IndexOutOfBoundsException e){

                    }

                    break;
                case 7:
                    try{
                        System.out.println("Enter SSN");
                        SSN = sc.next();
                        System.out.println("Enter name");
                        String name = sc.next();
                        bank.addNewCustomer(SSN, name);
                    }catch(InputMismatchException e){

                    }
                    break;
                case 8:
                    System.out.println("Enter SSN");
                    SSN = sc.next();
                    try{
                        bank.removeCustomer(SSN);
                    }catch(ConcurrentModificationException e){

                     }

                    break;
                case 9:
                    System.out.println("Enter SSN");
                    SSN = sc.next();
                    System.out.println("Enter Name you want to change.");
                    String name = sc.next();
                    bank.changeCustomerName(SSN, name);
                    break;

                case 10:
                    System.out.println("Quitting....");
                    quit = true;
            }
        }


    }


    public static void main(String[] args) throws IOException {
        runApp();

    }
}
