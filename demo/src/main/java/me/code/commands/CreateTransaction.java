package me.code.commands;

import java.util.Date;

import me.code.models.Transaction;
import me.code.services.ITransactionService;

public class CreateTransaction extends  Command {

    public CreateTransaction(ITransactionService transactionService, java.util.Scanner scanner) {
        super(500, "Create new shit", transactionService, scanner);
    }
    
    @Override
    public void execute() {

        double amount = 0;
        String description = "";
        Date currentDate = new Date();
        Boolean isIncome = true;

        System.out.println("Do you wish to create a file?");
        System.out.print("> ");

        String answer = scanner.nextLine();

        if (!answer.equalsIgnoreCase("yes")) {
            System.out.println("Aborted.");
            return;
        }

        System.out.println("Enter amount: ");
        System.out.print("> ");
        amount = Double.parseDouble(scanner.nextLine());

        System.out.println("Describe the transaction: ");
        System.out.print("> "); 
        description = scanner.nextLine();

        System.out.println("Is it income?: ");
        System.out.print("> ");
        if(scanner.nextLine().equalsIgnoreCase("yes")) {
            isIncome = true;
        } else {
            isIncome = false;
        }

        try {
            Transaction transaction = new Transaction(amount, description, currentDate, isIncome);
            transactionService.createTransaction(transaction);            
        } catch (Exception e) {
            System.err.println("Failed creating a transaction: " + e.getMessage());
        }
    }
}
