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

    //EFFECTS: returns the symbol of a piece. Symbol is first letter of the piece name (except knight which
    // is changed to n). Capitalize if piece is black. Return " " if no piece.
    public String getIcon() {
        String icon = " ";
        if (piece != null) {
            if (piece.getName().equals("king")) {
                icon = "k";
            } else if (piece.getName().equals("queen")) {
                icon = "q";
            } else if (piece.getName().equals("bishop")) {
                icon = "b";
            } else if (piece.getName().equals("knight")) {
                icon = "n";
            } else if (piece.getName().equals("rook")) {
                icon = "r";
            } else {
                icon = "p";
            }
            if (piece.getColor().equals("black")) {
                return icon.toUpperCase();
            }
            return icon;
        }
        return icon;

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
