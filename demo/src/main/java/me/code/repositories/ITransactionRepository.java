package me.code.repositories;

import java.util.List;
import java.util.UUID;

import me.code.models.Transaction;

public interface ITransactionRepository{

    Transaction findById(UUID TransactionId) throws Exception;

    List<Transaction> findAll() throws Exception;

    List<Transaction> findAllIncome() throws Exception;

    List<Transaction> findAllExpenses() throws Exception;

    void save(Transaction transaction) throws Exception;

    void delete(Transaction transaction) throws Exception;
    
}