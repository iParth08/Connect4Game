package core.engine;

import core.board.Board;
import core.board.Piece;
import core.player.UserCommand;
import ui.console.ConsoleInputHandler;
import ui.console.ConsoleRenderer;
import utils.Constants;
import utils.MessageType;
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
        boolean keepPlaying = true;
        while(keepPlaying){
            initGameState();
            consoleRenderer.clearConsole();
            consoleRenderer.printWelcomeScreen();
            consoleRenderer.printMenu();
            consoleRenderer.printGameBoard(board);
            gameLoop();
            consoleRenderer.postGameStateMessage(gameState, currentPiece);
            if(gameState != GameState.RESTART)
                keepPlaying = handlePostGameChoice();
            else
                consoleRenderer.printMessage(MessageType.RESTART, currentPiece);
        }

    }

    private boolean handlePostGameChoice(){
        consoleRenderer.printMessage(MessageType.ASK_RESTART, currentPiece);
        char userInput = consoleInputHandler.getUserCommand(currentPiece.getLabel());
        if(userInput == 'Y'){
            return true;
        }else {
            consoleRenderer.printMessage(MessageType.BYE, currentPiece);
            return false;
        }
    }

    private void initGameState(){
        board = new Board(Constants.ROWS, Constants.COLUMNS);
        currentPiece = Piece.O;
        gameState = GameState.RUNNING;
    }

    private void gameLoop(){
        while(gameState == GameState.RUNNING){
            consoleRenderer.printMessage(MessageType.NEXT_TURN, currentPiece);
            char userInput = consoleInputHandler.getUserCommand(currentPiece.getLabel());
            UserCommand command = interpretCommand(userInput);
            if(command != UserCommand.DROP){
                executeSpecialCommand(command);
            }
            else{
                Position placedPosition = dropInColumn(command);
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
                    currentPiece = currentPiece.togglePiece();
                }
            }
        }
    }



    private void executeSpecialCommand(UserCommand command){
        switch (command){
            case RESTART->
                gameState = GameState.RESTART;
            case QUIT ->
                gameState = GameState.EXIT;
            case HELP->
                consoleRenderer.printMenu();
            default->
                consoleRenderer.printMessage(MessageType.ERROR, null);
        }
    }

    private Position dropInColumn(UserCommand command){
        int chosenCol = command.getValue(); //validated beforehand
        consoleRenderer.logPlayerColumnChoice(currentPiece, chosenCol);
        Position placedPosition = board.placePiece(chosenCol, currentPiece);
        if(null == placedPosition){
            consoleRenderer.logColumnFullErr(chosenCol);
        }

        return placedPosition;
    }

    private UserCommand interpretCommand(char command){
        if(command == 'R'){
            return UserCommand.RESTART;
        }
        else if(command == 'Q'){
            return UserCommand.QUIT;
        }
        else if(command == 'H'){
            return UserCommand.HELP;
        }
        else if(command >= '0' && command <= '6'){
            UserCommand dropCommand = UserCommand.DROP;
            dropCommand.setValue(command - '0');
            return dropCommand;
        }else{
            return UserCommand.INVALID;
        }
    }
}
