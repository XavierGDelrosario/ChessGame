package model;

import exceptions.ColorException;

import java.util.ArrayList;
import java.util.List;

//Represents bishop piece in chess
public class Bishop extends Queen {

    //EFFECTS: creates a bishop with given color, throws color exception if not black or white
    public Bishop(String color) throws ColorException {
        super(color);
        this.name = "bishop";
    }

    @Override
    //REQUIRES: board != null, this piece exists on the board
    //EFFECTS: returns all squares diagonal to this piece's square if square is:
    //        -not occupied by piece of the same color
    //        -square is on the board. 0<x<9, 0<y<9
    //        -square is not after a square with a piece of either color
    public List<Square> getSquaresCanMoveTo(Board board) {
        List<Square> squares = new ArrayList<>();
        super.checkDirection(board, squares, 1, 1);
        super.checkDirection(board, squares, 1, -1);
        super.checkDirection(board, squares, -1, 1);
        super.checkDirection(board, squares, -1, -1);
        return squares;
    }
}
