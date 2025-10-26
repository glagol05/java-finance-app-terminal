package me.code.repositories;

import java.util.List;
import java.util.UUID;
import me.code.models.Transaction;

public class FileTransactionRepository implements ITransactionRepository {
 
    private static final String EXTENSION = ".txt";

    @Override
    public Transaction findById(UUID transactionId) throws Exception {
        String filename = getFileName(transactionId);

        
    }

    @Override
    public List<Transaction> findAll() throws Exception {

    }

    @Override
    public void save(Transaction transaction) throws Exception {
        String filename = getFileName(transaction.getId());

    }

    @Override
    public void delete(Transaction transaction) throws Exception {

    }

    private static String getFileName(UUID transactionId) {
        return transactionId.toString() + EXTENSION;
    }

}