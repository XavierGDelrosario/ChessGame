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
        this.name = "rook";
        hasMoved = false;
    }

    @Override
    //EFFECTS: returns this as JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("currentX", square.getXCoordinate());
        json.put("currentY", square.getYCoordinate());
        json.put("color", color);
        json.put("moved", hasMoved);
        return json;
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

    //EFFECTS: sets has moved to true
    public void setHasMoved(boolean b) {
        hasMoved = b;
    }
}
