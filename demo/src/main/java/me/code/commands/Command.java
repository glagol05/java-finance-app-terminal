package me.code.commands;

import me.code.services.ITransactionService;

public abstract class Command {

    protected final double ammount;
    protected final String description;
    protected final ITransactionService transactionService;

    public Command(double ammount, String description, ITransactionService transactionService) {
        this.ammount = ammount;
        this.description = description;
        this.transactionService = transactionService;
    }

    public abstract void execute();

    public double getAmmount() {
        return ammount;
    }

    public String getDescription() {
        return description;
    }
    
}
