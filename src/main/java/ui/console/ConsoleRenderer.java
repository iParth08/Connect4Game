package ui.console;

import core.board.Board;
import core.board.Cell;
import core.board.Piece;
import utils.Constants;

public class ConsoleRenderer {
    public void printWelcomeScreen(){
        System.out.println("=============================================");
        System.out.println("------------------ " + Constants.GAME_NAME + " ---------------");
        System.out.println("=============================================");
        System.out.println(">>>>>>>>>>> Welcome to the Arena <<<<<<<<<<<<");
        System.out.println();
    }

    public void printMenu(){
        System.out.println("=============================================");
        System.out.println("------------------ Menu Board ---------------");
        System.out.println(" R for Restart");
        System.out.println(" Q for Quit");
        System.out.println(" H for Help");
        System.out.println(" 0-6 for Drop");
        System.out.println("=============================================");

        System.out.println();
    }

    public void printMessage(String message){
        System.out.println(message);
    }

    public void printGameBoard(Board board){
        printColumnHeader(board);
        for(int row=0; row < board.getRow(); row++){
            printRow(board, row);
            System.out.println();
        }
    }
    private void printRow(Board board, int row){
        for(int col=0; col < board.getCol(); col++){
            Cell  cell = board.getCell(row, col);
            printCell(cell);
        }
    }

    private void printCell(Cell cell){
        Piece piece = cell.getPiece();
        switch(piece){
            case EMPTY:
                System.out.print("[   ]");
                break;

            case X:
                System.out.print("[ X ]");
                break;

            case O:
                System.out.print("[ O ]");
                break;
        }
    }

    private void printColumnHeader(Board board){
        for (int col=0; col < board.getCol(); col++){
            System.out.printf("{ %d }", col);
        }
        System.out.print("\n\n");
    }
}
