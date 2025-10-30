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
        Transaction transaction = transactionRepository.findById(id);
        return transaction;
    }

    @Override
    public Stream<Transaction> getTransactions() throws Exception {
        Stream<Transaction> transactionList = transactionRepository.findAll().stream();
        return transactionList;
    }

    @Override
    public Stream<Transaction> getIncome() throws Exception {
        Stream<Transaction> incomeList = transactionRepository.findAllIncome().stream();
        return incomeList;
    }

    @Override
    public Stream<Transaction> getExpenses() throws Exception {
        Stream<Transaction> expenseList = transactionRepository.findAllExpenses().stream();
        return expenseList;
    }

    @Override
    public double getBalance() throws Exception {
        Double totalIncome = transactionRepository.findAll().stream()
            .filter(Transaction::getIsIncome)
            .mapToDouble(Transaction::getAmmount)
            .sum();

        Double totalExpense = transactionRepository.findAll().stream()
            .filter(t -> !t.getIsIncome())
            .mapToDouble(Transaction::getAmmount)
            .sum();

        return totalIncome - totalExpense;
    }

    @Override
    public Stream<Transaction> searchTransaction() throws Exception {
        return transactionRepository.findAll().stream();
    }
    
}
