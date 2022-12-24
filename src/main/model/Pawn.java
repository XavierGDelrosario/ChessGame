package model;

import exceptions.ColorException;
import exceptions.NullBoardException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Represents the pawn piece in chess
public class Pawn extends SpecialMovesPiece {
    private boolean canEnPassantLeft;
    private boolean canEnPassantRight;

    //EFFECTS: creates a pawn with given color,  throws ColorException if not white or black
    public Pawn(String color) throws ColorException {
        super(color);
        this.name = "pawn";
        canEnPassantRight = false;
        canEnPassantLeft = false;
    }

    @Override
    //REQUIRES:this square y position != 1 or 8, board != null, this piece exists on the board
    //EFFECTS: returns squares if square is:
    //       - 1 square up if white, down if black, if that square does not have a piece of either color
    //       - 1 square diagonally(up if white, down if black) and square is occupied by piece of different color or
    //         can perform en passant in that direction
    //       - 2 squares (up if white, down if black) if pawn has not moved and not occupied by a piece
    //        -throw NullBoardException if board == null
    public List<Square> getSquaresCanMoveTo(Board board) {
        List<Square> squares = new ArrayList<>();
        checkForward(board, squares);
        checkDiagonally(board, squares);
        return squares;
    }

    @Override
    //REQUIRES: this piece exists on the board
    //EFFECTS: returns all the squares this piece can move to and not put king with the same color in check
    //        -throws caught NullBoardException

    public List<Square> getLegalMoves(Board board) throws NullBoardException {
        if (board == null) {
            throw new NullBoardException();
        }
        List<Square> movesToCheck = this.getSquaresCanMoveTo(board);
        List<Square> legalMoves = new ArrayList<>();
        for (Square square : movesToCheck) {
            if (board.checkIsLegalMove(this.square, square)) {
                legalMoves.add(square);
            }
        }
        return legalMoves;
    }

    //REQUIRES:board != null, this piece exists on the board
    //EFFECTS: adds squares to list if square is:
    //       - 1 square up if white, down if black, if that square does not have a piece of either color
    //       - 2 squares up if white or 2 down if black if the following conditions are met:
    //         -pawn has not moved
    //         -can move to previous square
    //         -square does not have a piece of either color
    private void checkForward(Board board, List<Square> squares) {
        int currentX = this.getSquare().getXCoordinate();
        int currentY = this.getSquare().getYCoordinate();
        if (this.color.equals("white")) {
            Square upwardSquare = board.getSquare(currentX, currentY + 1);
            if (upwardSquare != null && !upwardSquare.containsPiece()) {
                squares.add(upwardSquare);
                Square nextUpwardSquare = board.getSquare(currentX, currentY + 2);
                if (nextUpwardSquare != null && !this.hasMoved && !nextUpwardSquare.containsPiece()) {
                    squares.add(nextUpwardSquare);
                }
            }
        } else {
            Square downwardSquare = board.getSquare(currentX, currentY - 1);
            if (downwardSquare != null && !downwardSquare.containsPiece()) {
                squares.add(downwardSquare);
                Square nextDownwardSquare = board.getSquare(currentX, currentY - 2);
                if (nextDownwardSquare != null && !this.hasMoved && !nextDownwardSquare.containsPiece()) {
                    squares.add(nextDownwardSquare);
                }
            }
        }
    }

    //REQUIRES: board != null
    //EFFECTS: adds squares to list if square is:
    //          -diagonally up if white, down if black
    //          -occupied with a piece of different color than this
    //          -on the board
    //           or if pawn can perform En passant in that direction
    private void checkDiagonally(Board board, List<Square> squares) {
        int currentX = this.getSquare().getXCoordinate();
        int currentY = this.getSquare().getYCoordinate();
        if (this.color.equals("white")) {
            Square upwardRightSquare = board.getSquare(currentX + 1, currentY + 1);
            Square upwardLeftSquare = board.getSquare(currentX - 1, currentY + 1);
            checkCanCapture(squares, upwardRightSquare, upwardLeftSquare);
        } else {
            Square downwardRightSquare = board.getSquare(currentX + 1, currentY - 1);
            Square downwardLeftSquare = board.getSquare(currentX - 1, currentY - 1);
            checkCanCapture(squares, downwardRightSquare, downwardLeftSquare);
        }
    }

    //EFFECTS: adds squares to list if one of the following conditions are met:
    //         -given square is occupied by a piece of different color from this
    //         -this can perform En Passant on the square, when an opposing pawn moves two squares in its first move
    //          and lands adjacent to this pawn, this can capture as if it only moved one square. Has this privilege
    //          for only one turn
    private void checkCanCapture(List<Square> squares, Square rightSquare, Square leftSquare) {
        if ((rightSquare != null && rightSquare.containsPiece()
                && !rightSquare.getPiece().getColor().equals(this.color))
                || (canEnPassantRight && rightSquare != null && !rightSquare.containsPiece())) {
            squares.add(rightSquare);
        }
        if ((leftSquare != null && leftSquare.containsPiece())
                && (!leftSquare.getPiece().getColor().equals(this.color))
                || (canEnPassantLeft && rightSquare != null && !leftSquare.containsPiece())) {
            squares.add(leftSquare);
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
        json.put("moved", hasMoved);
        json.put("enPassantRight", canEnPassantRight);
        json.put("enPassantLeft", canEnPassantLeft);
        return json;
    }

    public boolean getCanEnPassantLeft() {
        return canEnPassantLeft;
    }

    public boolean getCanEnPassantRight() {
        return canEnPassantRight;
    }

    public void setCanEnPassantLeft(boolean b) {
        canEnPassantLeft = b;
    }

    public void setCanEnPassantRight(boolean b) {
        canEnPassantRight = b;
    }

}
