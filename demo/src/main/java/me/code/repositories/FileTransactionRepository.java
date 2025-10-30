package me.code.repositories;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            UUID id = UUID.fromString(parts[0]);
            double ammount = Double.parseDouble(parts[1]);
            String description = parts[2];
            Date transactionDate = new Date(Long.parseLong(parts[3]));
            Boolean isIncome = Boolean.parseBoolean(parts[4]);

            return new Transaction(id, ammount, description, transactionDate, isIncome);

        }
        
    }

    @Override
    public List<Transaction> findAll() throws Exception {
        ArrayList<Transaction> transactions = new ArrayList<>();

        Path folder = Paths.get(".");
        try(var files = Files.list(folder)) {
            files
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(EXTENSION))
                .forEach(path -> {
                    try {
                        
                        String filename = path.getFileName().toString();
                        String UUIDpart = filename.replace(EXTENSION, "");

                        UUID id = UUID.fromString(UUIDpart);
                        Transaction transaction = findById(id);

                        if(transaction != null) {
                            transactions.add(transaction);
                        }
                    } catch (Exception e) {
                        System.err.println("Error loading all files: " + e.getMessage());
                    }
                });
        }

        return transactions;

    }

    @Override
    public List<Transaction> findAllIncome() throws Exception {
        ArrayList<Transaction> incomeList = new ArrayList<>();

        Path folder = Paths.get(".");
        try(var files = Files.list(folder)) {
            files
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(EXTENSION))
                .forEach(path -> {
                        try(BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {

                        String line = reader.readLine();

                        String filename = path.getFileName().toString();
                        String UUIDpart = filename.replace(EXTENSION, "");

                        UUID id = UUID.fromString(UUIDpart);
                        Transaction income = findById(id);

                        if(line != null && line.trim().endsWith("true")) {
                            incomeList.add(income);
                        }
                    } catch (Exception e) {
                        System.err.println("Error loading all files: " + e.getMessage());
                    }
                });
        }
        return incomeList;
    }

    @Override
    public List<Transaction> findAllExpenses() throws Exception {
        ArrayList<Transaction> expenseList = new ArrayList<>();

        Path folder = Paths.get(".");
        try(var files = Files.list(folder)) {
            files
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(EXTENSION))
                .forEach(path -> {
                    try(BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {

                        String line = reader.readLine();

                        String filename = path.getFileName().toString();
                        String UUIDpart = filename.replace(EXTENSION, "");

                        UUID id = UUID.fromString(UUIDpart);
                        Transaction expense = findById(id);

                        if(line != null && line.trim().endsWith("false")) {
                            expenseList.add(expense);
                        }
                    } catch (Exception e) {
                        System.err.println("Error loading all files: " + e.getMessage());
                    }
                });
        }

        return expenseList;
    }

    /*@Override
    public void findBalance() throws Exception {

    }*/

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
        String filename = getFileName(transaction.getId());

        Path path = Paths.get(filename);
        Files.delete(path);
    }

    private static String getFileName(UUID transactionId) {
        return transactionId.toString() + EXTENSION;
    }

}