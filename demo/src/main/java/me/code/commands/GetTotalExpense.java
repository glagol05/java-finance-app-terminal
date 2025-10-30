package me.code.commands;

import java.util.Scanner;

import me.code.services.ITransactionService;

public class GetTotalExpense extends Command {

    public GetTotalExpense(ITransactionService transactionService, Scanner scanner) {
        super("Display total expense", transactionService, scanner);
    }

    @Override
    public void execute() {
        try {
            Double totalExpense = transactionService.getTotalExpense();
            System.out.println("Total expense: " + totalExpense);
        } catch (Exception e) {
            System.err.println("Error displaying total expense: " + e.getMessage());
        }
    }
    
}
