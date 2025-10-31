package me.code.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import me.code.commands.Command;

public class TerminalCommandService implements ICommandService {

    private final List<Command> commands = new ArrayList<>();

    public void start() {
        System.out.println("---Finance machine---");
        System.out.println("Choose one of following commands: ");

        for(Command command : commands) {
            System.out.println(command);
        }

        System.out.println("exit - Exit the application");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command: ");
            String commandInput = scanner.nextLine();

            if (commandInput.equalsIgnoreCase("exit")) {
                return;
            }

            try {
                executeCommand(commandInput);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }
    
    @Override
    public void registerCommand(Command command) {
        this.commands.add(command);
    }

    @Override
    public void executeCommand(String commandInput) {
        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(commandInput)) {
                command.execute();
                return;
            }
        }

        System.out.println("The command does not exist, try again!");
    }
}