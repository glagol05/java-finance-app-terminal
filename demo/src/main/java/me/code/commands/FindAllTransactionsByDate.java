package me.code.commands;

import java.util.Scanner;

import me.code.services.ITransactionService;

public class FindAllTransactionsByDate extends Command {

    public FindAllTransactionsByDate(ITransactionService transactionService, Scanner scanner) {
        super("Display transactions during a time period", transactionService, scanner);
    }
    
    @Override
        public void execute() {
            try {
                System.out.println("Enter year (or leave blank to ignore): ");
                System.out.print(">");
                String yearInput = scanner.nextLine();
                Integer year = yearInput.isBlank() ? null : Integer.parseInt(yearInput);

                System.out.println("Enter month (1-12, or leave blank to ignore): ");
                System.out.print(">");
                String monthInput = scanner.nextLine();
                Integer month = monthInput.isBlank() ? null : Integer.parseInt(monthInput);

                System.out.println("Enter day (1-31, or leave blank to ignore): ");
                System.out.print(">");
                String dayInput = scanner.nextLine();
                Integer day = dayInput.isBlank() ? null : Integer.parseInt(dayInput);

                var transactions = transactionService.getTransactionsByDate(year, month, day).toList();

                if (transactions.isEmpty()) {
                    System.out.println("No transactions found for the given period.");
                } else {
                    System.out.println("Transactions found:");
                    transactions.forEach(System.out::println);
                }

            } catch (Exception e) {
                System.err.println("Error retrieving transactions by date: " + e.getMessage());
            }
        }
}

