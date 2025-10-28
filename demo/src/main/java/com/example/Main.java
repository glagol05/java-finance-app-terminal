package com.example;

import java.util.UUID;

import me.code.commands.CreateTransaction;
import me.code.repositories.FileTransactionRepository;
import me.code.services.DefaultTransactionService;
import me.code.services.ITransactionService;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");

        UUID id = UUID.fromString("f1b8f679-07ea-46f0-a138-47572402c8c2");

        //filerepo.save(transaction);
        //System.out.println(filerepo.findById(id));

        // Setup layers manually (basic dependency injection)
        FileTransactionRepository fileRepo = new FileTransactionRepository();
        ITransactionService transactionService = new DefaultTransactionService(fileRepo);

        // Create command and run
        CreateTransaction createTransaction = new CreateTransaction(transactionService);
        createTransaction.execute();
        
    }
}