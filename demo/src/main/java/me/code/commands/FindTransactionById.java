package me.code.commands;

import java.util.Scanner;
import java.util.UUID;

import me.code.models.Transaction;
import me.code.services.ITransactionService;

public class FindTransactionById extends Command {

    public FindTransactionById(ITransactionService transactionService, Scanner scanner) {
        super("Find Transaction by ID", transactionService, scanner);
    }
    
    @Override
    public void execute() {
        System.out.println("What transaction would you like to display?");
        System.out.print(">");

        try {
            UUID answer = UUID.fromString(scanner.nextLine());
            Transaction transaction = transactionService.getTransactionById(answer);

            if(transaction != null) {
                System.out.println(transaction.toString());
            }

        } catch (Exception e) {
            System.err.println("Error finding transaction: " + e.getMessage());
        }

    }
}
