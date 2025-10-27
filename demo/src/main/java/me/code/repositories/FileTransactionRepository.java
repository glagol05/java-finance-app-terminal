package me.code.repositories;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.code.models.Transaction;

public class FileTransactionRepository implements ITransactionRepository {
 
    private static final String EXTENSION = ".txt";

    @Override
    public Transaction findById(UUID transactionId) throws Exception {
        String filename = getFileName(transactionId);

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String line = reader.readLine();
            if (line == null) {
                return null;
            }
            
            String[] parts = line.split(",");
            double ammount = Double.parseDouble(parts[1]);
            String description = parts[2];
            Date transactionDate = new Date(Long.parseLong(parts[3]));
            Boolean isIncome = Boolean.parseBoolean(parts[4]);

            return new Transaction(ammount, description, transactionDate, isIncome);

        }
        
    }

    @Override
    public List<Transaction> findAll() throws Exception {

        ArrayList<Transaction> transactions = new ArrayList<>();
        return transactions;

    }

    @Override
    public void save(Transaction transaction) throws Exception {
        String filename = getFileName(transaction.getId());

        try (FileOutputStream fos = new FileOutputStream(filename)) {
            String line = transaction.getId() + "," + transaction.getAmmount() + "," +
                        transaction.getDescription() + "," +
                        transaction.getDate().getTime() + "," + transaction.getIsIncome() + "\n";
            fos.write(line.getBytes());
        }


        catch(IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    @Override
    public void delete(Transaction transaction) throws Exception {

    }

    private static String getFileName(UUID transactionId) {
        return transactionId.toString() + EXTENSION;
    }

}