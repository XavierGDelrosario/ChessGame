package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Represents the king piece in chess
public class King extends SpecialMovesPiece {

    //REQUIRES:color = "white" or "black"
    //EFFECTS: creates a king with given color
    public King(String color) {
        super(color);
        this.name = "king";
    }

    @Override
    //REQUIRES: board != null
    //EFFECTS: returns all squares adjacent to king if square is:
    //        -not occupied by piece of the same color
    //        -on the board. 0<x<9, 0<y<9
    public List<Square> getSquaresCanMoveTo(Board board) {
        List<Square> squares = new ArrayList<>();
        List<Square> squaresToCheck = new ArrayList<>();
        int currentX = this.getSquare().getXCoordinate();
        int currentY = this.getSquare().getYCoordinate();

        squaresToCheck.add(board.getSquare(currentX + 1, currentY + 1));
        squaresToCheck.add(board.getSquare(currentX + 1, currentY));
        squaresToCheck.add(board.getSquare(currentX + 1, currentY - 1));
        squaresToCheck.add(board.getSquare(currentX, currentY + 1));
        squaresToCheck.add(board.getSquare(currentX, currentY - 1));
        squaresToCheck.add(board.getSquare(currentX - 1, currentY + 1));
        squaresToCheck.add(board.getSquare(currentX - 1, currentY));
        squaresToCheck.add(board.getSquare(currentX - 1, currentY - 1));
        for (Square square : squaresToCheck) {
            if (square != null) {
                if (!square.containsPiece() || !square.getPiece().getColor().equals(this.getColor())) {
                    squares.add(square);
                }
            }
        }
        return squares;
    }

    @Override
    //REQUIRES: board != null, this piece exists on the boar
    //EFFECTS: returns all the squares that:
    //         -this piece can move to and not be in check
    //         -this piece can castle(move) to the square 2 squares to the right or left direction if:
    //               -this has not moved
    //               -rook in that direction has not moved
    //               -first square in the direction is a legal move
    //               -king is not in check
    //               -squares between rook and king are empty
    public List<Square> getLegalMoves(Board board) {
        List<Square> movesToCheck = this.getSquaresCanMoveTo(board);
        List<Square> legalMoves = new ArrayList<>();
        for (Square square : movesToCheck) {
            if (board.checkIsLegalMove(this.square, square)) {
                legalMoves.add(square);
            }
        }
        if (!hasMoved && !board.isKingInCheck(color)) {
            checkCanCastle(board, legalMoves);
        }
        return legalMoves;
    }

    //REQUIRES: direction = "left" or "right", board != null, king has not moved and not in check
    //EFFECTS: adds squares that are 2 squares to the right or left direction from this piece if the following
    //conditions are met:
    //               -rook in that direction has not moved
    //               -first square in the direction is a legal move
    //               -squares between rook and kin are empty
    private void checkCanCastle(Board board, List<Square> legalMoves) {
        int currentX = square.getXCoordinate();
        int currentY = square.getYCoordinate();
        List<Square> squaresToCheckRight = new ArrayList<>();
        List<Square> squaresToCheckLeft = new ArrayList<>();

        squaresToCheckRight.add(board.getSquare(currentX + 1, currentY));
        Square castlingRightSquare = board.getSquare(currentX + 2, currentY);
        squaresToCheckRight.add(castlingRightSquare);
        squaresToCheckRight.add(board.getSquare(currentX + 3, currentY));
        if (checkCastlingConditions(board, squaresToCheckRight)) {
            legalMoves.add(castlingRightSquare);
        }
        squaresToCheckLeft.add(board.getSquare(currentX - 1, currentY));
        Square castlingLeftSquare = board.getSquare(currentX - 2, currentY);
        squaresToCheckLeft.add(castlingLeftSquare);
        squaresToCheckLeft.add(board.getSquare(currentX - 3, currentY));
        squaresToCheckLeft.add(board.getSquare(currentX - 4, currentY));

        if (checkCastlingConditions(board, squaresToCheckLeft)) {
            legalMoves.add(castlingLeftSquare);
        }
    }

    //REQUIRES: board != null, king has not moved and king is not in check
    //EFFECTS: returns true if:
    //               -rook in that direction has not moved
    //               -first square in the direction is a legal move
    //               -squares between rook and king are empty
    private boolean checkCastlingConditions(Board board, List<Square> squaresToCheck) {
        Square rookSquare = squaresToCheck.get(squaresToCheck.size() - 1);
        boolean rookHasMoved = true;
        if (rookSquare.containsPiece() && rookSquare.getPiece().getName().equals("rook")) {
            Rook rook = (Rook) rookSquare.getPiece();
            rookHasMoved = rook.getHasMoved();
        }
        for (int i = 0; i < 2; i++) {
            Square square = squaresToCheck.get(i);
            if (square.containsPiece() || !board.checkIsLegalMove(this.square, square)) {
                return false;
            }
        }
        if (squaresToCheck.size() == 4) {
            if (squaresToCheck.get(2).containsPiece()) {
                return false;
            }
        }
        return !rookHasMoved;
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
}
