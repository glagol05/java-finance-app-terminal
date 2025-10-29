package com.example;

import java.util.Scanner;
import java.util.UUID;

import me.code.commands.CreateTransaction;
import me.code.commands.DeleteTransaction;
import me.code.commands.FindAllTransactions;
import me.code.commands.FindTransactionById;
import me.code.repositories.FileTransactionRepository;
import me.code.services.DefaultTransactionService;
import me.code.services.ICommandService;
import me.code.services.ITransactionService;
import me.code.services.TerminalCommandService;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");

        Scanner scanner = new Scanner(System.in);

        UUID id = UUID.fromString("f1b8f679-07ea-46f0-a138-47572402c8c2");

        //filerepo.save(transaction);
        //System.out.println(filerepo.findById(id));

        // Setup layers manually (basic dependency injection)
        FileTransactionRepository fileRepo = new FileTransactionRepository();
        ITransactionService transactionService = new DefaultTransactionService(fileRepo);

        // Create command and run
        ICommandService commandService = new TerminalCommandService();

        commandService.registerCommand(new CreateTransaction(transactionService, scanner));
        commandService.registerCommand(new DeleteTransaction(transactionService, scanner));
        commandService.registerCommand(new FindTransactionById(transactionService, scanner));
        commandService.registerCommand(new FindAllTransactions(transactionService, scanner));
        
        if (commandService instanceof TerminalCommandService service) {
            service.start();
        }
    }
}