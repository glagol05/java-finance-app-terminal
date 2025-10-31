package me.code.commands;

import java.util.Scanner;
import java.util.stream.Stream;

import me.code.models.Transaction;
import me.code.services.ITransactionService;

public class FindAllTransactions extends Command {

    public FindAllTransactions(ITransactionService transactionService, Scanner scanner) {
        super("Display all transactions", transactionService, scanner);
    }

    @Override
    public void execute() {
        System.out.println("All transactions: ");

        try {
            try(Stream<Transaction> transactionList = transactionService.getTransactions()) {
                transactionList.forEach(System.out::println);
            }
            
        } catch (Exception e) {
            System.err.println("Error getting all transactions: " + e.getMessage());
        }
    }
    
}
