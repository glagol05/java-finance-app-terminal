package me.code.models;

import java.util.Date;
import java.util.UUID;

public class Transaction {
    
    private final UUID id;
    private double ammount;
    private String description;
    private Date transactionDate;
    private Boolean isIncome;

    public Transaction(double ammount, String description, Date transactionDate, Boolean isIncome) {
        this.id = UUID.randomUUID();
        this.ammount = ammount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.isIncome = isIncome;

    }

    public Transaction(UUID id, double ammount, String description, Date transactionDate, Boolean isIncome) {
        this.id = id;
        this.ammount = ammount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.isIncome = isIncome;
    }


    public UUID getId() {
        return id;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
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



}
