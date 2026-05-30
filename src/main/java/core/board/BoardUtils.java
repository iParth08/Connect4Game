package core.board;

public class BoardUtils {
    public static boolean isValidColumn(int maxCol, int col){
        return col >= 0 && col <= maxCol;
    }


}
