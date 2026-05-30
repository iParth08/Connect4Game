package core.board;

public enum Piece {
    X("RED"),
    O("WHITE"),
    EMPTY("");

    private final String label;
    Piece(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    public Piece togglePiece() {
        return this == X ? O : X;
    }
}
