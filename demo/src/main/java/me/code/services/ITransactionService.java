package me.code.services;

import java.util.UUID;
import java.util.stream.Stream;

import me.code.models.Transaction;

public interface ITransactionService {
    
    void createTransaction(Transaction transaction) throws Exception;

    Transaction deleteTransactionById(UUID id) throws Exception;

    Transaction updateTransactionById(UUID id) throws Exception;

    Transaction getTransactionById(UUID id) throws Exception;

    Stream<Transaction> getTransactions() throws Exception;

    Stream<Transaction> getIncome() throws Exception;

    Stream<Transaction> getExpenses() throws Exception;

    double getTotalIncome() throws Exception;

    double getTotalExpense() throws Exception;

    double getBalance() throws Exception;

}
