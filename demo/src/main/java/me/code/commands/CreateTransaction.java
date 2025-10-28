package me.code.commands;

import java.util.Date;
import java.util.Scanner;

import me.code.models.Transaction;
import me.code.services.ITransactionService;

public class CreateTransaction extends  Command {

    public CreateTransaction(ITransactionService transactionService) {
        super(500, "Create new shit", transactionService);
    }
    
    @Override
    public void execute() {
        System.err.println("Do you wish to create a file?");
        System.out.println(">");

        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();

        if(answer.equalsIgnoreCase("yes")) {
            try {
                Date currentDate = new Date();
                Transaction transaction = new Transaction(5000, "Test file!", currentDate, true);
                transactionService.createTransaction(transaction);
            } catch (Exception ex) {
            }
        }
    }
}
