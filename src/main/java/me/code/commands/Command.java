package me.code.commands;

import java.util.Scanner;

import me.code.services.ITransactionService;

public abstract class Command {

    protected final String description;
    protected final ITransactionService transactionService;
    protected final Scanner scanner;

    public Command(String description, ITransactionService transactionService, Scanner scanner) {
        this.description = description;
        this.transactionService = transactionService;
        this.scanner = scanner;
    }

    public abstract void execute();

    public String getName() {
        return description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
    
}
