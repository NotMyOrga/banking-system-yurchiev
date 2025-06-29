package bank;

import main.Person;

public class BankAccount {
    private double balance;
    private final Person owner;

    public BankAccount(Person owner, double initialDeposit) {
        this.owner = owner;
        this.balance = initialDeposit;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        // TODO: Add amount to balance
    }

    public boolean withdraw(double amount) {
        // TODO: Subtract if enough balance; return true/false
        return false;
    }
}
