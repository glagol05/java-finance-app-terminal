package me.code.commands;

import java.util.Scanner;

import me.code.services.ITransactionService;

public class GetBalance extends Command {

    public GetBalance(ITransactionService transactionService, Scanner scanner) {
        super("Get balance", transactionService, scanner);
    }

    @Override
    public void execute() {
        System.out.println("Your balance: ");
    }
    
}
