package me.code.commands;

import java.util.Scanner;

import me.code.services.ITransactionService;

public abstract class Command {

    protected final double ammount;
    protected final String description;
    protected final ITransactionService transactionService;
    protected final Scanner scanner;

    public Command(double ammount, String description, ITransactionService transactionService, Scanner scanner) {
        this.ammount = ammount;
        this.description = description;
        this.transactionService = transactionService;
        this.scanner = scanner;
    }

    public abstract void execute();

    public double getAmmount() {
        return ammount;
    }

    public String getDescription() {
        return description;
    }
    
}
