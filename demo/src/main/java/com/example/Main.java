package com.example;

import java.util.Date;
import java.util.UUID;

import me.code.models.Transaction;
import me.code.repositories.FileTransactionRepository;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");

        UUID id = UUID.fromString("f1b8f679-07ea-46f0-a138-47572402c8c2");


        FileTransactionRepository filerepo = new FileTransactionRepository();

        Date currentDate = new Date();
        Transaction transaction = new Transaction(5, "Cool", currentDate, true);

        //filerepo.save(transaction);
        System.out.println(filerepo.findById(id));
    }
}