package com.example;

import java.util.Scanner;

import me.code.commands.CreateTransaction;
import me.code.commands.DeleteTransaction;
import me.code.commands.FindAllExpense;
import me.code.commands.FindAllIncome;
import me.code.commands.FindAllTransactions;
import me.code.commands.FindAllTransactionsByDate;
import me.code.commands.FindTransactionById;
import me.code.commands.GetBalance;
import me.code.commands.GetTotalExpense;
import me.code.commands.GetTotalIncome;
import me.code.commands.UpdateTransaction;
import me.code.repositories.FileTransactionRepository;
import me.code.services.DefaultTransactionService;
import me.code.services.ICommandService;
import me.code.services.ITransactionService;
import me.code.services.TerminalCommandService;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        FileTransactionRepository fileRepo = new FileTransactionRepository();
        ITransactionService transactionService = new DefaultTransactionService(fileRepo);

        ICommandService commandService = new TerminalCommandService();

        commandService.registerCommand(new CreateTransaction(transactionService, scanner));
        commandService.registerCommand(new DeleteTransaction(transactionService, scanner));
        commandService.registerCommand(new FindTransactionById(transactionService, scanner));
        commandService.registerCommand(new FindAllTransactions(transactionService, scanner));
        commandService.registerCommand(new FindAllIncome(transactionService, scanner));
        commandService.registerCommand(new FindAllExpense(transactionService, scanner));
        commandService.registerCommand(new GetBalance(transactionService, scanner));
        commandService.registerCommand(new GetTotalIncome(transactionService, scanner));
        commandService.registerCommand(new GetTotalExpense(transactionService, scanner));
        commandService.registerCommand(new FindAllTransactionsByDate(transactionService, scanner));
        commandService.registerCommand(new UpdateTransaction(transactionService, scanner));
        
        if (commandService instanceof TerminalCommandService service) {
            service.start();
        }
    }
}