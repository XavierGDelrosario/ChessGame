package model;

import exceptions.ColorException;

import java.util.ArrayList;
import java.util.List;

//Represents rook piece in chess
public class Rook extends Queen {
    private boolean hasMoved;

    //EFFECTS: creates a rook with given color,  throws ColorException if not white or black
    public Rook(String color) throws ColorException {
        super(color);
        this.name = "rook";
        hasMoved = false;
    }

    @Override
    //REQUIRES:board != null, this piece exists on the board
    //EFFECTS:returns all squares horizontally and vertically of this piece's square if square is:
    //        -not occupied by piece of the same color
    //        -square is on the board. 0<x<9, 0<y<9
    //        -square is not after a square with a piece
    public List<Square> getSquaresCanMoveTo(Board board) {
        List<Square> squares = new ArrayList<>();
        super.checkDirection(board, squares, 1, 0);
        super.checkDirection(board, squares, -1, 0);
        super.checkDirection(board, squares, 0, 1);
        super.checkDirection(board, squares, 0, -1);
        return squares;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    //EFFECTS: sets has moved to true
    public void setHasMovedTrue() {
        hasMoved = true;
    }
}
