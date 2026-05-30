package ui.console;

import core.board.BoardUtils;

import java.util.Scanner;

public class ConsoleInputHandler {
    private final Scanner input;

    public ConsoleInputHandler(){
        input = new Scanner(System.in);
    }

    public int getPlayerColumnChoice(int col){
        System.out.print("Please choose your column: ");
        int columnChoice = input.nextInt();
        while (!BoardUtils.isValidColumn(col, columnChoice)){
            System.out.println("Invalid column. Please try again");
            System.out.print("Please choose your column: ");
            columnChoice = input.nextInt();
        }
        return columnChoice;
    }

    public char getUserCommand(String user) {
        System.out.printf("Mr. %s, your command: ", user);
        String userCommand = input.nextLine().trim();
        //validate int or char
        while(userCommand.isEmpty() || !validateCommandRange(userCommand.toUpperCase().charAt(0))){
            System.out.println("Invalid command. Please try again");
            System.out.printf("Mr. %s, your command: ", user);
            userCommand = input.nextLine().trim();
        };

        return userCommand.toUpperCase().charAt(0);

    }

    private boolean validateCommandRange(char command){
        return (command >= 'A' && command <= 'Z') || (command >= '0' && command <= '9');
    }
}
