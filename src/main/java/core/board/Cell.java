package core.board;

public class Cell {
    Piece piece;

    public Cell(){
        piece = Piece.EMPTY;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
