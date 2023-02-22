package model;

import java.util.Objects;

//Represents one of the 64 squares on the board
public class Square {
    private final int positionX;
    private final int positionY;
    private Piece piece;
    private String name;

    //REQUIRES: 0<x<9 and 0<y<9
    //EFFECTS:Creates an empty square with given coordinates
    public Square(int x, int y) {
        positionX = x;
        positionY = y;
        piece = null;
        initializeName();
    }

    //MODIFIES:this
    //EFFECTS: Sets piece to null
    public void removePiece() {
        piece = null;
    }


    //EFFECTS: sets name to chess notation, 1,1 = a1, 4,1= d1, 8,1 = h1 etc
    private void initializeName() {
        if (positionX == 1) {
            name = "a" + positionY;
        } else if (positionX == 2) {
            name = "b" + positionY;
        } else if (positionX == 3) {
            name = "c" + positionY;
        } else if (positionX == 4) {
            name = "d" + positionY;
        } else if (positionX == 5) {
            name = "e" + positionY;
        } else if (positionX == 6) {
            name = "f" + positionY;
        } else if (positionX == 7) {
            name = "g" + positionY;
        } else if (positionX == 8) {
            name = "h" + positionY;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return positionX == square.positionX && positionY == square.positionY && Objects.equals(piece, square.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionX, positionY, piece);
    }

    //EFFECTS: returns the icon of a piece. Icon is first letter of the piece name (except knight which
    // is changed to n). Capitalize if piece is black. Return " " if no piece.
    public String getPieceInitial() {
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
    //MODIFIES:this, piece
    //EFFECTS: -Sets piece to given piece
    //         -Set piece's square to this
    public void setPiece(Piece piece) {
        piece.setSquare(this);
        this.piece = piece;
    }

    //EFFECTS: returns true if square has a piece, else false
    public boolean containsPiece() {
        return piece != null;
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

    public String getName() {
        return name;
    }


}
