package model;

import java.util.ArrayList;
import java.util.List;

//Represents a chess game
public class ChessGame {
    private final Board board;
    private List<Board> savedBoards;
    private String playerTurn;

    //EFFECTS: creates a chess game
    public ChessGame() {
        board = new Board();
        board.setupBoard();
        playerTurn = "white";
        savedBoards = new ArrayList<>();
    }

    //REQUIRES:fromSquare piece != null, both squares != null
    //MODIFIES:fromSquare, toSquare, fromSquare piece
    //EFFECTS: return true if legal, else false. Make move if legal, move is legal if:
    //          -piece that is moving is the same color as the player turn
    //          -piece can move to that square
    public boolean movePiece(Square fromSquare, Square toSquare) {
        Piece piece = fromSquare.getPiece();
        if (piece != null && piece.getColor().equals(playerTurn)) {
            List<Square> fromSquares = new ArrayList<>();
            List<Square> toSquares = new ArrayList<>();
            board.getLegalMoves(fromSquares, toSquares, piece.getColor());
            for (int i = 0; i < fromSquares.size(); i++) {
                if (fromSquare == fromSquares.get(i) && toSquare == toSquares.get(i)) {
                    checkSpecialMoves(fromSquare, toSquare);
                    board.movePiece(fromSquare, toSquare);
                    endTurn();
                    return true;
                }
            }
        }
        return false;
    }

    //region SpecialMoves
    //REQUIRES: both squares != null, fromSquare piece != null
    //MODIFIES:piece on fromSquare
    //EFFECTS: performs extra methods if a special move:
    //         -if fromSquare piece is pawn, rook, or king, set has moved to true
    //         -if pawn moved twice in first move, give en passant privileges to adjacent pawns
    //         -if pawn performs en passant, remove the corresponding pawn from the board
    //         -if king castles, move rook to other side of it
    private void checkSpecialMoves(Square fromSquare, Square toSquare) {
        Piece piece = fromSquare.getPiece();
        if (piece.getName().equals("king")) {
            King king = (King) piece;
            king.setHasMovedTrue();
            checkHasCastled(fromSquare, toSquare);
        } else if (piece.getName().equals("rook")) {
            Rook rook = (Rook) piece;
            rook.setHasMovedTrue();
        } else if (piece.getName().equals("pawn")) {
            Pawn pawn = (Pawn) piece;
            pawn.setHasMovedTrue();
            checkEnPassant(fromSquare, toSquare);
            checkPawnMovedTwice(fromSquare, toSquare);
        }
    }

    //REQUIRES: both squares != null
    //MODIFIES: toSquare, square of rook, rook piece
    //EFFECTS: if given move is castling, move rook to the other side of king
    private void checkHasCastled(Square fromSquare, Square toSquare) {
        int fromSquareX = fromSquare.getXCoordinate();
        int fromSquareY = fromSquare.getYCoordinate();
        int toSquareX = toSquare.getXCoordinate();
        if ((toSquareX - fromSquareX) == 2) {
            Square rookFromSquare = board.getSquare(fromSquareX + 3, fromSquareY);
            Square rookToSquare = board.getSquare(fromSquareX + 1, fromSquareY);
            board.movePiece(rookFromSquare, rookToSquare);
        } else if ((toSquareX - fromSquareX) == -2) {
            Square rookFromSquare = board.getSquare(fromSquareX - 4, fromSquareY);
            Square rookToSquare = board.getSquare(fromSquareX - 1, fromSquareY);
            board.movePiece(rookFromSquare, rookToSquare);
        }
    }

    //REQUIRES: both squares != null, fromSquare has a pawn
    //MODIFIES: square of pawn to capture
    //EFFECTS: if move en passant, remove pawn above or below the moving pawns new position
    private void checkEnPassant(Square fromSquare, Square toSquare) {
        Pawn pawn = (Pawn) fromSquare.getPiece();
        int fromSquareX = fromSquare.getXCoordinate();
        int fromSquareY = fromSquare.getYCoordinate();
        int toSquareX = toSquare.getXCoordinate();
        if ((toSquareX - fromSquareX) == 1 && pawn.getCanEnPassantRight()) {
            board.getSquare(fromSquareX + 1, fromSquareY).removePiece();
        } else if ((toSquareX - fromSquareX) == -1 && pawn.getCanEnPassantLeft()) {
            board.getSquare(fromSquareX - 1, fromSquareY).removePiece();
        }
    }

    //REQUIRES: both squares != null, fromSquare has a pawn
    //MODIFIES: square, pawn, pawns that moving pawn lands adjacent to
    //EFFECTS: if pawn moves forward two squares, set pawns of opposite color adjacent to toSquare to be able to
    //perform en passant
    private void checkPawnMovedTwice(Square fromSquare, Square toSquare) {
        Pawn pawn = (Pawn) fromSquare.getPiece();
        int fromSquareY = fromSquare.getYCoordinate();
        int toSquareY = toSquare.getYCoordinate();
        int toSquareX = toSquare.getXCoordinate();
        if ((toSquareY - fromSquareY) == 2 || (toSquareY - fromSquareY) == -2) {
            if (board.getSquare(toSquareX + 1, toSquareY) != null) {
                Piece adjacentPieceRight = board.getSquare(toSquareX + 1, toSquareY).getPiece();
                if (adjacentPieceRight != null && adjacentPieceRight.getName().equals("pawn")
                        && !adjacentPieceRight.getColor().equals(pawn.getColor())) {
                    Pawn adjacentRightPawn = (Pawn) adjacentPieceRight;
                    adjacentRightPawn.setCanEnPassantLeft(true);
                }
            }
            if (board.getSquare(toSquareX - 1, toSquareY) != null) {
                Piece adjacentPieceLeft = board.getSquare(toSquareX - 1, toSquareY).getPiece();
                if (adjacentPieceLeft != null && adjacentPieceLeft.getName().equals("pawn")
                        && !adjacentPieceLeft.getColor().equals(pawn.getColor())) {
                    Pawn adjacentLeftPawn = (Pawn) adjacentPieceLeft;
                    adjacentLeftPawn.setCanEnPassantRight(true);
                }
            }
        }
    }

    //endregion

    //EFFECTS: returns square with given coordinates
    public Square getSquare(int x, int y) {
        return board.getSquare(x, y);
    }

    //MODIFIES: this, the pawns of the player whose turn it is
    //EFFECTS:  -removes en passant privileges from pawns of player whose turn it is
    //          -changes player turn from white to black and vice versa
    //          -adds a copy of the board to savedBoards
    public void endTurn() {
        List<Piece> pieces = board.getPieces(playerTurn);
        pieces.removeIf(piece -> (!piece.getName().equals("pawn")));
        for (Piece piece : pieces) {
            Pawn pawn = (Pawn) piece;
            pawn.setCanEnPassantRight(false);
            pawn.setCanEnPassantLeft(false);
        }
        changePlayerTurn();
        Board boardToSave = new Board();
        boardToSave.copyBoard(board);
        savedBoards.add(boardToSave);
    }

    //MODIFIES: this
    //EFFECTS: changes player turn from "white" to "black" and vice versa
    public void changePlayerTurn() {
        if (playerTurn.equals("white")) {
            playerTurn = "black";
        } else {
            playerTurn = "white";
        }
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public Board getBoard() {
        return board;
    }

    public List<Board> getSavedBoards() {
        return savedBoards;
    }
}
