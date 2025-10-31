package me.code.commands;

import java.util.Scanner;

import me.code.services.ITransactionService;

public class GetTotalIncome extends Command {

    public GetTotalIncome(ITransactionService transactionService, Scanner scanner) {
        super("Get total income", transactionService, scanner);
    }

    @Override
    public void execute() {
        try {
            Double totalIncome = transactionService.getTotalIncome();
            System.out.println("Total expense: " + totalIncome);
        } catch (Exception e) {
            System.err.println("Error displaying total expense: " + e.getMessage());
        }
    }
    
}
