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
    public Transaction updateTransaction(Transaction updatedTransaction) throws Exception {
        UUID id = updatedTransaction.getId();
        Transaction existingTransaction = transactionRepository.findById(id);
        if (existingTransaction == null) {
            throw new Exception("Transaction not found with ID: " + id);
        }

        transactionRepository.delete(existingTransaction);
        transactionRepository.save(updatedTransaction);

        return updatedTransaction;
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
    public Stream<Transaction> getTransactionsByDate(Integer year, Integer month, Integer day) throws Exception {
        return transactionRepository.findAllByDate(year, month, day).stream();
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
    public double getTotalIncome() throws Exception {
        Double totalIncome = transactionRepository.findAll().stream()
            .filter(Transaction::getIsIncome)
            .mapToDouble(Transaction::getamount)
            .sum();

        return totalIncome;
    }

    @Override
    public double getTotalExpense() throws Exception {
        Double totalExpense = transactionRepository.findAll().stream()
            .filter(t -> !t.getIsIncome())
            .mapToDouble(Transaction::getamount)
            .sum();

        return totalExpense;
    }

    @Override
    public double getBalance() throws Exception {
        Double totalIncome = getTotalIncome();
        Double totalExpense = getTotalExpense();

        return totalIncome - totalExpense;
    }   
}