package me.code.commands;

import java.util.Date;
import java.util.Scanner;

import me.code.models.Transaction;
import me.code.services.ITransactionService;

public class CreateTransaction extends  Command {

    public CreateTransaction(ITransactionService transactionService, Scanner scanner) {
        super("Create transaction", transactionService, scanner);
    }
    
    @Override
    public void execute() {

        double amount = 0;
        String description = "N/A";
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

        while (true) { 
            System.out.println("Enter date (yyyy-mm-dd) format. Press enter for todays date");
            System.out.print(">");
            String inputDate = scanner.nextLine();

            try {
                currentDate = Transaction.DATE_FORMAT.parse(inputDate);
                break;
            } catch (Exception e) {
                System.err.println("Incorrect date, try again");
            }
        }
        

        System.out.println("Is it income?: ");
        System.out.print("> ");
        if(scanner.nextLine().equalsIgnoreCase("yes")) {
            isIncome = true;
        } else {
            isIncome = false;
        }

        try {
            if(amount == 0 && description.equals("")) {
                System.out.println("You didnt specify amount or add an description. Try again.");
            } else {
                Transaction transaction = new Transaction(amount, description, currentDate, isIncome);
                transactionService.createTransaction(transaction);    
            }        
        } catch (Exception e) {
            System.err.println("Failed creating a transaction: " + e.getMessage());
        }
    }
}
