package me.code.commands;

import java.util.Scanner;
import java.util.UUID;

import me.code.services.ITransactionService;

public class DeleteTransaction extends Command {
    

    public DeleteTransaction(ITransactionService transactionService, Scanner scanner) {
        super("Delete transaction", transactionService, scanner);
    }

    public void execute() {
        System.out.println("What file do you want to delete?");
        System.out.print(">");

        String answer = scanner.nextLine();
        UUID id = UUID.fromString(answer);

        try {
            transactionService.deleteTransactionById(id);
            System.out.println("Transaction was successfully deleted");
        } catch (Exception e) {
            System.err.println("Error deleting transaction: " + e.getMessage());
        }
    }
}
