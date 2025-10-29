package me.code.commands;

import java.util.Scanner;
import java.util.stream.Stream;

import me.code.models.Transaction;
import me.code.services.ITransactionService;

public class FindAllIncome extends Command {

    public FindAllIncome(ITransactionService transactionService, Scanner scanner) {
        super("Display all income", transactionService, scanner);
    }

    @Override
    public void execute() {

        try {
            try(Stream<Transaction> incomeList = transactionService.getIncome()) {
                var income = incomeList.toList();

                if(income.isEmpty()) {
                    System.out.println("No income found");
                } else {
                    System.out.println("All income: ");
                    income.forEach(System.out::println);
                }
            }
        } catch (Exception e) {
            System.err.println("Error displaying all income: " + e.getMessage());
        }
    }
}
