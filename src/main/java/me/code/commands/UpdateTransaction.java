package me.code.commands;

import java.util.Scanner;
import java.util.UUID;

import me.code.models.Transaction;
import me.code.services.ITransactionService;

public class UpdateTransaction extends Command {

    public UpdateTransaction(ITransactionService transactionService, Scanner scanner) {
        super("Update transaction", transactionService, scanner);
    }

    @Override
    public void execute() {
        System.out.println("What transaction would you like to update?");
        System.out.print(">");

        try {
            UUID id = UUID.fromString(scanner.nextLine());
            Transaction existingTransaction = transactionService.getTransactionById(id);

            if(existingTransaction == null) {
                System.out.println("Transaction not found");
                return;
            }

            System.out.println("Current amount is (" + existingTransaction.getamount() + "): ");
            String amountInput = scanner.nextLine();
            double newAmount = amountInput.isBlank() ? existingTransaction.getamount() : Double.parseDouble(amountInput);

            System.out.print("New description (" + existingTransaction.getDescription() + "): ");
            String descInput = scanner.nextLine();
            String newDescription = descInput.isBlank() ? existingTransaction.getDescription() : descInput;

            System.out.print("New date (yyyy-MM-dd) (" + Transaction.DATE_FORMAT.format(existingTransaction.getDate()) + "): ");
            String dateInput = scanner.nextLine();
            java.util.Date newDate;

            if (dateInput.isBlank()) {
                newDate = existingTransaction.getDate();
            } else {
                newDate = Transaction.DATE_FORMAT.parse(dateInput);
            }

            System.out.print("Is this income? (" + existingTransaction.getIsIncome() + "): ");
            String isIncomeInput = scanner.nextLine();
            boolean newIsIncome = isIncomeInput.isBlank() ? existingTransaction.getIsIncome() : Boolean.parseBoolean(isIncomeInput);

            Transaction updated = new Transaction(
                existingTransaction.getId(),
                newAmount,
                newDescription,
                newDate,
                newIsIncome
            );

            transactionService.updateTransaction(updated);
            System.out.println("Transaction updated successfully.");

        } catch (Exception e) {
            System.err.println("Error finding transaction: " + e.getMessage());
        }
    }
    
}
