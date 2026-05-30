package core.board;


import utils.Position;

public class Board {
    private final int row;
    private final int col;
    private Cell [][] grid = null;

    public Board(int row, int col) {
        this.row = row;
        this.col = col;

        grid = new Cell[row][col];
        setBlankGrid();

    }

    private void setBlankGrid(){
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                grid[i][j] = new Cell();
            }
        }
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Position placePiece(int selectedCol, Piece piece){
        //for each col - start from bottom and place new piece on next empty cell
        for(int currentRow = row-1; currentRow >= 0; currentRow--){
            Cell currentCell = grid[currentRow][selectedCol];
            if(currentCell.getPiece() == Piece.EMPTY) {
                currentCell.setPiece(piece);
                return new Position(currentRow,selectedCol);
            }
        }
        return null;
    }
    public boolean isConnectedFour(Position lastPositionedCell){
        Piece [] xAxis = new Piece[7];
        Piece [] yAxis = new Piece[7];
        Piece [] sameSignDiagonal = new Piece[7];
        Piece [] diffSignDiagonal = new Piece[7];
        Piece currentPiece = null;
        int maxOffsetBound = 3;

        for(int offsetRow = -maxOffsetBound; offsetRow <= maxOffsetBound; offsetRow++){
            for(int offsetCol = -maxOffsetBound; offsetCol <= maxOffsetBound; offsetCol++){
                int currentRow = lastPositionedCell.getRow() + offsetRow;
                int currentCol = lastPositionedCell.getCol() + offsetCol;

                if(!isValidCellPosition(currentRow, currentCol))
                    continue;

                //conditions to match and look for
                //Y-Axis
                if(offsetCol == 0){
                    currentPiece = getCell(currentRow, currentCol).getPiece();
                    yAxis[offsetRow+maxOffsetBound] = currentPiece;
                }

                //X-Axis
                if(offsetRow == 0){
                    currentPiece = getCell(currentRow, currentCol).getPiece();
                    xAxis[offsetCol+maxOffsetBound] = currentPiece;
                }

                //Diff-Sign-Diagonal-Line
                if((offsetRow == 0 || offsetRow != offsetCol)
                        && Math.abs(offsetRow) == Math.abs(offsetCol)) {
                    currentPiece = getCell(currentRow, currentCol).getPiece();
                    diffSignDiagonal[offsetRow+maxOffsetBound] = currentPiece;
                }

                //Same-Sign-Diagonal-Line
                if(offsetRow == offsetCol) {
                    currentPiece = getCell(currentRow, currentCol).getPiece();
                    sameSignDiagonal[offsetRow+maxOffsetBound] = currentPiece;
                }
            }
        }

        //verifying consecutive connect in any array
        return isConsecutiveConnect(xAxis, maxOffsetBound)
                || isConsecutiveConnect(yAxis, maxOffsetBound)
                || isConsecutiveConnect(sameSignDiagonal, maxOffsetBound)
                || isConsecutiveConnect(diffSignDiagonal, maxOffsetBound);
    }
    public boolean hasAvailableCell(){
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(grid[i][j].getPiece() == Piece.EMPTY){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidCellPosition(int row, int col){
        return row >= 0 && row < this.row
                && col >= 0 && col < this.col;

    }

    private boolean isConsecutiveConnect(Piece [] line, int offset){
        Piece targetPiece = line[offset];
        Piece currentPiece = null;
        int matchCount = 1;
        int negSideCursor = offset-1;
        int posSideCursor = offset+1;

        while (negSideCursor >= 0){
            currentPiece = line[negSideCursor];
            if(currentPiece == targetPiece)
                matchCount++;
            else
                break;
            negSideCursor--;
        }

        while (posSideCursor < line.length){
            currentPiece = line[posSideCursor];
            if(currentPiece == targetPiece)
                matchCount++;
            else
                break;
            posSideCursor++;
        }

        return matchCount >= 4; //CRITICAL
    }

}
