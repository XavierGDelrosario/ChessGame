package model;

import exceptions.ColorException;
import exceptions.NullBoardException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Represents rook piece in chess
public class Rook extends Queen {
    private boolean hasMoved;

    //EFFECTS: creates a rook with given color,  throws ColorException if not white or black
    public Rook(String color) throws ColorException {
        super(color);
        name = "rook";
        hasMoved = false;
    }

    @Override
    //REQUIRES:this piece exists on the board
    //EFFECTS: returns all the squares this piece can move to and not put king with the same color in check
    //         -throws caught NullBoardException
    public List<Square> getLegalMoves(Board board) throws NullBoardException {
        List<Square> movesToCheck = this.getSquaresCanMoveTo(board);
        List<Square> legalMoves = new ArrayList<>();
        for (Square square : movesToCheck) {
            if (board.checkIsLegalMove(this.square, square)) {
                legalMoves.add(square);
            }
        }
        return legalMoves;
    }

    @Override
    //REQUIRES: this piece exists on the board
    //EFFECTS:returns all squares horizontally and vertically of this piece's square if square is:
    //        -not occupied by piece of the same color
    //        -square is on the board. 0<x<9, 0<y<9
    //        -square is not after a square with a piece
    //        -throw NullBoardException if board == null
    public List<Square> getSquaresCanMoveTo(Board board) throws NullBoardException {
        if (board == null) {
            throw new NullBoardException();
        }
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

    //MODIFIES: this
    //EFFECTS: sets hasMoved to true
    public void setHasMoved(boolean b) {
        hasMoved = b;
    }
}
