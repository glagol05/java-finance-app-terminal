package me.code.commands;

public abstract class Command {

    protected final double ammount;
    protected final String description;

    public Command(double ammount, String description) {
        this.ammount = ammount;
        this.description = description;
    }

    public abstract void execute();

    public double getAmmount() {
        return ammount;
    }

    public String getDescription() {
        return description;
    }
    
}
