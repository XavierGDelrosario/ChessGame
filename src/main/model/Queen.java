package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Represents queen piece in chess
public class Queen implements Piece {
    protected final String color;
    protected String name;
    protected Square square;

    //REQUIRES:color = "white" or "black", square != null
    //EFFECTS: creates a queen with given color
    public Queen(String color) {
        this.color = color;
        square = null;
        name = "queen";
    }

    @Override
    //REQUIRES: board != null, this piece exists on the board
    //EFFECTS:returns all squares horizontally, vertically, and diagonally of this piece's square if square is:
    //        -not occupied by piece of the same color
    //        -square is on the board, 0<x<9, 0<y<9
    //        -square is not after a square with a piece
    public List<Square> getSquaresCanMoveTo(Board board) {
        List<Square> squares = new ArrayList<>();
        checkDirection(board, squares, 1, 0);
        checkDirection(board, squares, -1,0);
        checkDirection(board, squares, 0, 1);
        checkDirection(board, squares, 0,-1);
        checkDirection(board, squares, 1, 1);
        checkDirection(board, squares, 1, -1);
        checkDirection(board, squares, -1, 1);
        checkDirection(board, squares, -1, -1);
        return squares;
    }

    @Override
    //REQUIRES: board != null, this piece exists on the board
    //EFFECTS: returns all the squares this piece can move to and not put king with the same color in check
    public List<Square> getLegalMoves(Board board) {
        List<Square> movesToCheck = this.getSquaresCanMoveTo(board);
        List<Square> legalMoves = new ArrayList<>();
        for (Square square : movesToCheck) {
            if (board.checkIsLegalMove(this.square, square)) {
                legalMoves.add(square);
            }
        }
        return legalMoves;
    }

    //REQUIRES:directionX = 1 (right) or -1 (left) or 0 not moving horizontally
    //         directionY = 1 (up) or -1 (down) or 0 not moving vertically , board != null
    //EFFECTS: adds all squares to list that are diagonal to this in a certain direction if square is:
    //        -not occupied by piece of the same color
    //        -square is on the board. 0<x<9, 0<y<9
    //        -square is not after a square with a piece of either color
    protected void checkDirection(Board board, List<Square> squares, int directionX, int directionY) {
        for (int i = 1; i < 8; i++) {
            int newX = this.square.getXCoordinate() + i * directionX;
            int newY = this.square.getYCoordinate() + i * directionY;
            Square square = board.getSquare(newX, newY);
            if (square != null) {
                if (square.getPiece() == null) {
                    squares.add(square);
                } else if (!square.getPiece().getColor().equals(this.color)) {
                    squares.add(square);
                    break;
                } else {
                    break;
                }
            }
        }
    }

    @Override
    //EFFECTS: returns this as JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("currentX", square.getXCoordinate());
        json.put("currentY", square.getYCoordinate());
        json.put("color", color);
        return json;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Square getSquare() {
        return square;
    }

    @Override
    public void setSquare(Square square) {
        this.square = square;
    }


}
