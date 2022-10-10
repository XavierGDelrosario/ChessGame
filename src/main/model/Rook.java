package model;

import java.util.ArrayList;
import java.util.List;

//Represents rook piece in chess
public class Rook extends Queen {
    private boolean hasMoved;

    //REQUIRES:color = "white" or "black"
    //EFFECTS: creates a rook that has not moved, with given color and with name "rook", not on board
    public Rook(String color) {
        super(color);
        this.name = "rook";
        hasMoved = false;
    }

    @Override
    //REQUIRES:board != null, this piece exists on the board
    //EFFECTS:returns all squares horizontally and vertically of rook if square is:
    //        -not occupied by piece of the same color
    //        -square is on the board. 0<x<9, 0<y<9
    //        -square is not after a square with a piece
    public List<Square> squaresCanMoveTo(Board board) {
        List<Square> squares = new ArrayList<>();
        super.checkHorizontally(board, squares, 1);
        super.checkHorizontally(board, squares, -1);
        super.checkVertically(board, squares, 1);
        super.checkVertically(board, squares, -1);
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
