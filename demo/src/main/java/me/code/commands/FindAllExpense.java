package me.code.commands;

import java.util.Scanner;
import java.util.stream.Stream;

import me.code.models.Transaction;
import me.code.services.ITransactionService;

public class FindAllExpense extends Command {

    public FindAllExpense(ITransactionService transactionService, Scanner scanner) {
        super("Display all expenses", transactionService, scanner);
    }

    @Override
    public void execute() {

        try {
            try(Stream<Transaction> expenseList = transactionService.getExpenses()) {
                var expenses = expenseList.toList();

                if(expenses.isEmpty()) {
                    System.out.println("No expenses found");
                } else {
                    System.out.println("All expenses: ");
                    expenseList.forEach(System.out::println);
                }
            }
        } catch (Exception e) {
            System.err.println("Error displaying all expenses: " + e.getMessage());
        }

    }
    
}
