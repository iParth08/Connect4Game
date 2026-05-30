package core.engine;

import core.board.Board;
import core.board.Piece;
import ui.console.ConsoleInputHandler;
import ui.console.ConsoleRenderer;
import utils.Constants;
import utils.Position;

public class GameManager  {
    private GameState gameState;
    private Piece currentPiece;
    private final ConsoleRenderer consoleRenderer;
    private final ConsoleInputHandler consoleInputHandler;
    private Board board;

    public GameManager(){
        consoleRenderer = new ConsoleRenderer();
        consoleInputHandler = new ConsoleInputHandler();
    }

    public void startGame(){
        initGameState();
        consoleRenderer.printWelcomeScreen();
        consoleRenderer.printMenu();
        consoleRenderer.printGameBoard(board);
        gameLoop();
        gameStateMessage();
    }

    private void initGameState(){
        board = new Board(Constants.ROWS, Constants.COLUMNS);
        currentPiece = Piece.O;
        gameState = GameState.RUNNING;
    }

    private void gameLoop(){
        while(gameState == GameState.RUNNING){
            Position placedPosition = null;
            String command = null;
            consoleRenderer.printMessage("Its your turn. Mr. " +  currentPiece.getLabel());
            char userInput = consoleInputHandler.getUserCommand(currentPiece.getLabel());
            command = interpretCommand(userInput);
            if(!command.contains("DROP_")){
                executeSpecialCommand(command);
            }
            else{
                placedPosition = dropInColumn(command);
                if(placedPosition == null) continue;

                consoleRenderer.printGameBoard(board);
                //check win-loss
                if(board.isConnectedFour(placedPosition)){
                    gameState = GameState.WON;
                }
                else if (!board.hasAvailableCell()){ //CRITICAL
                    gameState = GameState.DRAW;
                }
                if(gameState == GameState.RUNNING){
                    switchPiece();
                }
            }
        }
    }

    private void gameStateMessage(){
        switch (gameState){
            case WON:
                consoleRenderer.printMessage("Game won by Mr. " + currentPiece.getLabel());
                break;

            case DRAW:
                consoleRenderer.printMessage("lets call it a draw");
                break;

            case EXIT:
                consoleRenderer.printMessage("Its sad, you decided to go, Mr. " + currentPiece.getLabel());
                consoleRenderer.printMessage("How about meeting again");
                break;
        }
    }

    private void switchPiece(){
        switch (currentPiece){
            case O:
                currentPiece = Piece.X;
                break;
            case X:
                currentPiece = Piece.O;
                break;
            default:
                currentPiece = Piece.EMPTY;
        }
    }

    private void executeSpecialCommand(String command){
        switch (command){
            case "RESTART":
                startGame();
                break;
            case "QUIT":
                gameState = GameState.EXIT;
                break;
            case "HELP":
                consoleRenderer.printMenu();
                break;
            default:
                consoleRenderer.printMessage("Game Manager sighs! Invalid command.");
                consoleRenderer.printMessage("Press H for HELP");
        }
    }

    private Position dropInColumn(String command){
        int chosenCol = Integer.parseInt(command.split("_")[1]); //validated beforehand
        System.out.println("Chosen column: " + chosenCol + " by Player : " + currentPiece.getLabel());
        Position placedPosition = board.placePiece(chosenCol, currentPiece);
        if(null == placedPosition){
            consoleRenderer.printMessage("No room to place piece in column " + chosenCol);
            consoleRenderer.printMessage("Make another choice.");
        }

        return placedPosition;
    }

    private String interpretCommand(char command){
        if(command == 'R'){
            return "RESTART";
        }
        else if(command == 'Q'){
            return "QUIT";
        }
        else if(command == 'H'){
            return "HELP";
        }
        else if(command >= '0' && command <= '6'){
            return "DROP_" + command;
        }else{
            return "INVALID";
        }
    }
}
