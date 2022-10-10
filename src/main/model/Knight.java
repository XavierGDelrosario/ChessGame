package model;

import java.util.ArrayList;
import java.util.List;

//Represents a knight piece in chess
public class Knight implements Piece {
    private final String color;
    private final String name;
    private Square square;

    //REQUIRES:color = "white" or "black"
    //EFFECTS: creates a knight with given color and with name "knight", not on the board
    public Knight(String color) {
        this.color = color;
        square = null;
        this.name = "knight";
    }

    @Override
    //REQUIRES:board != null, this piece exists on the board
    //EFFECTS: returns squares 2 units in one direction and 1 unit in the other if square is:
    //        -not occupied by a piece of same color
    //        -on the board. 0<x<9, 0<y<9
    public List<Square> getSquaresCanMoveTo(Board board) {
        List<Square> squares = new ArrayList<>();
        List<Square> squaresToCheck = new ArrayList<>();
        int currentX = this.getSquare().getXCoordinate();
        int currentY = this.getSquare().getYCoordinate();

        squaresToCheck.add(board.getSquare(currentX + 2, currentY + 1));
        squaresToCheck.add(board.getSquare(currentX + 2, currentY - 1));
        squaresToCheck.add(board.getSquare(currentX - 2, currentY + 1));
        squaresToCheck.add(board.getSquare(currentX - 2, currentY - 1));
        squaresToCheck.add(board.getSquare(currentX + 1, currentY + 2));
        squaresToCheck.add(board.getSquare(currentX - 1, currentY + 2));
        squaresToCheck.add(board.getSquare(currentX + 1, currentY - 2));
        squaresToCheck.add(board.getSquare(currentX - 1, currentY - 2));

        for (Square square : squaresToCheck) {
            if (square != null) {
                if (square.getPiece() == null || !square.getPiece().getColor().equals(this.getColor())) {
                    squares.add(square);
                }
            }
        }
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
