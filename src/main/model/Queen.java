package model;

import java.util.ArrayList;
import java.util.List;

//Represents queen piece in chess
public class Queen implements Piece {
    protected final String color;
    protected String name;
    protected Square square;

    //REQUIRES:color = "white" or "black", square != null
    //EFFECTS: creates a queen with given color and with name "queen", not on board
    public Queen(String color) {
        this.color = color;
        square = null;
        this.name = "queen";
    }

    @Override
    //REQUIRES: board != null, this piece exists on the board
    //EFFECTS:returns all squares horizontally and vertically of rook if square is:
    //        -not occupied by piece of the same color
    //        -square is on the board, 0<x<9, 0<y<9
    //        -square is not after a square with a piece
    public List<Square> getSquaresCanMoveTo(Board board) {
        List<Square> squares = new ArrayList<>();
        checkHorizontally(board, squares, 1);
        checkHorizontally(board, squares, -1);
        checkVertically(board, squares, 1);
        checkVertically(board, squares, -1);
        checkDiagonally(board, squares, 1, 1);
        checkDiagonally(board, squares, 1, -1);
        checkDiagonally(board, squares, -1, 1);
        checkDiagonally(board, squares, -1, -1);
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

    //REQUIRES: directionX = 1 (right) or -1 (left), board != null
    //EFFECTS: adds all squares to list that are horizontally of piece if square is:
    //        -not occupied by piece of the same color
    //        -square is on the board. 0<x<9, 0<y<9
    //        -square is not after a square with a piece
    protected void checkHorizontally(Board board, List<Square> squares, int directionX) {
        for (int i = 1; i < 8; i++) {
            int newX = this.square.getXCoordinate() + i * directionX;
            Square square = board.getSquare(newX, this.square.getYCoordinate());
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

    //REQUIRES: directionY = 1 (up) or -1 (down), board != null
    //EFFECTS: adds all squares to list that are vertically of piece if square is:
    //        -not occupied by piece of the same color
    //        -square is on the board. 0<x<9, 0<y<9
    //        -square is not after a square with a piece
    protected void checkVertically(Board board, List<Square> squares, int directionY) {
        for (int i = 1; i < 8; i++) {
            int newY = this.square.getYCoordinate() + i * directionY;
            Square square = board.getSquare(this.square.getXCoordinate(), newY);
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

    //REQUIRES:directionX = 1 (right) or -1 (left), directionY = 1 (up) or -1 (down), board != null
    //EFFECTS: adds all squares to list that are diagonal to this in a certain direction if square is:
    //        -not occupied by piece of the same color
    //        -square is on the board. 0<x<9, 0<y<9
    //        -square is not after a square with a piece of either color
    protected void checkDiagonally(Board board, List<Square> squares, int directionX, int directionY) {
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
