package me.code.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final UUID id;
    private double amount;
    private String description;
    private Date transactionDate;
    private Boolean isIncome;

    public Transaction(double amount, String description, Date transactionDate, Boolean isIncome) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.isIncome = isIncome;

    }

    public Transaction(UUID id, double amount, String description, Date transactionDate, Boolean isIncome) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.isIncome = isIncome;
    }


    public UUID getId() {
        return id;
    }

    public double getamount() {
        return amount;
    }

    public void setamount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    } 

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return transactionDate;
    }

    public void setDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Boolean getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(Boolean isIncome) {
        this.isIncome = isIncome;
    }

    @Override
    public String toString() {
        return "Transaction: \n id: " + id +
               ", \n amount: " + amount +
               ", \n description: '" + description + '\'' +
               ", \n date: " + DATE_FORMAT.format(this.transactionDate) +
               ", \n isIncome: " + isIncome;
    }


}
