package me.code.services;

import java.util.UUID;
import java.util.stream.Stream;

import me.code.models.Transaction;
import me.code.repositories.ITransactionRepository;

public class DefaultTransactionService implements ITransactionService {

    private final ITransactionRepository transactionRepository;

    public DefaultTransactionService(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void createTransaction(Transaction transaction) throws Exception {
        transactionRepository.save(transaction);
    }

    @Override
    public Transaction deleteTransactionById(UUID id) throws Exception {
        Transaction transaction = transactionRepository.findById(id);
        transactionRepository.delete(transaction);
        return transaction;
    }

    @Override
    public Transaction updateTransactionById(UUID id) throws Exception {
        Transaction transaction = new Transaction(0, null, null, null);
        return transaction;
    }

    @Override
    public Transaction getTransactionById(UUID id) throws Exception {
        Transaction transaction = new Transaction(0, null, null, null);
        return transaction;
    }

    @Override
    public Stream<Transaction> getTransactions() throws Exception {
        return transactionRepository.findAll().stream();
    }

    @Override
    public Stream<Transaction> searchTransaction() throws Exception {
        return transactionRepository.findAll().stream();
    }
    
}
