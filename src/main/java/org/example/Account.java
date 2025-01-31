package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account implements AccountService {
    private List<Transaction> transactions = new ArrayList<>();

    @Override
    public void deposit(int amount) {
        transactions.add(new Transaction("deposit", amount, LocalDate.now()));
    }

    @Override
    public void withdraw(int amount) {
        transactions.add(new Transaction("withdrawal", amount, LocalDate.now()));
    }

    @Override
    public void printStatement() {
        int balance = 0;
        System.out.println("date || credit || debit || balance");
        for (Transaction transaction : transactions) {
            String date = transaction.getDate().toString();
            int amount = transaction.getAmount();
            String type = transaction.getType();

            if (type.equals("deposit")) {
                balance += amount;
                System.out.printf("%s || %d || || %d\n", date, amount, balance);
            } else if (type.equals("withdrawal")) {
                balance -= amount;
                System.out.printf("%s || || -%d || %d\n", date, amount, balance);
            }
        }
    }

}
