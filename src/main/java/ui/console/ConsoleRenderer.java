package ui.console;

import core.board.Board;
import core.board.Cell;
import core.board.Piece;
import core.engine.GameState;
import utils.Constants;
import utils.MessageType;

public class ConsoleRenderer {
    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
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

    public void printMessage(MessageType messageType, Piece piece){
        switch (messageType){
            case BYE ->
                    System.out.println("Mr. " + piece.getLabel() + ", Okay, See ya!");
            case ASK_RESTART->
                    System.out.println("Mr. " + piece.getLabel() + ", wanna have another game? Y or N");
            case NEXT_TURN ->
                    System.out.println("Its your turn. Mr. " +  piece.getLabel());
            case RESTART ->
                    System.out.println("Mr. " + piece.getLabel() + ", here we go again. Its new game.");
            case ERROR ->
                System.out.println("Game Manager sighs! Invalid command.\nPress H for HELP");
        }

    }

    public void logColumnFullErr(int column){
        System.out.println("No room to place piece in the chosen column : [ " +column+" ]");
        System.out.println("Make another choice.");
    }

    public void logPlayerColumnChoice(Piece piece, int column){
        System.out.println("Chosen column: " + column + " by Player : " + piece.getLabel());
    }

    public void postGameStateMessage(GameState gameState, Piece piece){
        switch (gameState){
            case WON:
                System.out.println("Game won by Mr. " + piece.getLabel());
                break;
            case DRAW:
                    System.out.println("Lets call it a draw.");
                    break;
            case EXIT:
                    System.out.println("Its sad, you decided to go, Mr. " + piece.getLabel());
                    System.out.println("How about meeting again");
                break;
        }
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
