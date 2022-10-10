package model;

//Represents one of the 64 squares on the board
public class Square {
    private final int positionX;
    private final int positionY;
    private Piece piece;

    //REQUIRES: 0<x<9 and 0<y<9
    //EFFECTS:Creates an empty square with given coordinates
    public Square(int x, int y) {
        positionX = x;
        positionY = y;
        piece = null;
    }

    //MODIFIES:this
    //EFFECTS: Sets piece to null
    public void removePiece() {
        piece = null;
    }

    //REQUIRES: piece != null
    //MODIFIES:this, piece, piece's square
    //EFFECTS: -Sets piece to given piece
    //         -Set piece's square to this
    public void setPiece(Piece piece) {
        piece.setSquare(this);
        this.piece = piece;
    }

    public int getXCoordinate() {
        return positionX;
    }

    public int getYCoordinate() {
        return positionY;
    }

    public Piece getPiece() {
        return piece;
    }
}
