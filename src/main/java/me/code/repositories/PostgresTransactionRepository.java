package me.code.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import me.code.models.Transaction;

public class PostgresTransactionRepository implements ITransactionRepository {

    private final DataSource dataSource;

    public PostgresTransactionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Transaction findById(UUID transactionId) throws Exception {
        String sql = """
            SELECT id, amount, description, transaction_date, is_income
            FROM transactions
            WHERE id = ?
        """;

        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, transactionId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                return mapRow(rs);
            }
        }
    }

    @Override
    public List<Transaction> findAll() throws Exception {
        String sql = """
            SELECT id, amount, description, transaction_date, is_income
            FROM transactions
            ORDER BY transaction_date DESC
        """;

        List<Transaction> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                results.add(mapRow(rs));
            }
        }

        return results;
    }

    @Override
    public List<Transaction> findAllIncome() throws Exception {
        return findByIncomeFlag(true);
    }

    @Override
    public List<Transaction> findAllExpenses() throws Exception {
        return findByIncomeFlag(false);
    }

    private List<Transaction> findByIncomeFlag(boolean isIncome) throws Exception {
        String sql = """
            SELECT id, amount, description, transaction_date, is_income
            FROM transactions
            WHERE is_income = ?
        """;

        List<Transaction> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, isIncome);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
        }

        return results;
    }

    @Override
    public List<Transaction> findAllByDate(Integer year, Integer month, Integer day) throws Exception {
        String sql = """
            SELECT id, amount, description, transaction_date, is_income
            FROM transactions
            WHERE (? IS NULL OR EXTRACT(YEAR FROM transaction_date) = ?)
            AND (? IS NULL OR EXTRACT(MONTH FROM transaction_date) = ?)
            AND (? IS NULL OR EXTRACT(DAY FROM transaction_date) = ?)
        """;

        List<Transaction> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, year);
            ps.setObject(2, year);
            ps.setObject(3, month);
            ps.setObject(4, month);
            ps.setObject(5, day);
            ps.setObject(6, day);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
        }

        return results;
    }

    @Override
    public void save(Transaction transaction) throws Exception {
        String sql = """
            INSERT INTO transactions (id, amount, description, transaction_date, is_income)
            VALUES (?, ?, ?, ?, ?)
            ON CONFLICT (id) DO UPDATE SET
                amount = EXCLUDED.amount,
                description = EXCLUDED.description,
                transaction_date = EXCLUDED.transaction_date,
                is_income = EXCLUDED.is_income
        """;

        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, transaction.getId());
            ps.setDouble(2, transaction.getamount());
            ps.setString(3, transaction.getDescription());
            ps.setDate(4, new Date(transaction.getDate().getTime()));
            ps.setBoolean(5, transaction.getIsIncome());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Transaction transaction) throws Exception {
        String sql = "DELETE FROM transactions WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, transaction.getId());
            ps.executeUpdate();
        }
    }

    private Transaction mapRow(ResultSet rs) throws Exception {
        UUID id = rs.getObject("id", UUID.class);
        double amount = rs.getDouble("amount");
        String description = rs.getString("description");
        Date date = rs.getDate("transaction_date");
        Boolean isIncome = rs.getBoolean("is_income");

        return new Transaction(id, amount, description, date, isIncome);
    }
}




/*package me.code.repositories;

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
            double amount = Double.parseDouble(parts[1]);
            String description = parts[2];
            Date transactionDate = new Date(Long.parseLong(parts[3]));
            Boolean isIncome = Boolean.parseBoolean(parts[4]);

            return new Transaction(id, amount, description, transactionDate, isIncome);

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

    @Override
    public List<Transaction> findAllByDate(Integer year, Integer month, Integer day) throws Exception {
        List<Transaction> allTransactions = findAll();

        return allTransactions.stream()
            .filter(t -> {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(t.getDate());

                boolean match = true;
                if (year != null) {
                    match &= cal.get(java.util.Calendar.YEAR) == year;
                }
                if (month != null) {
                    match &= (cal.get(java.util.Calendar.MONTH) + 1) == month;
                }
                if (day != null) {
                    match &= cal.get(java.util.Calendar.DAY_OF_MONTH) == day;
                }
                return match;
            })
        .toList();
}

    @Override
    public void save(Transaction transaction) throws Exception {
        String filename = getFileName(transaction.getId());

        try (FileOutputStream fos = new FileOutputStream(filename)) {
            String line = transaction.getId() + "," + transaction.getamount() + "," +
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

}*/