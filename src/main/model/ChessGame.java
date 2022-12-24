package model;

import exceptions.ColorException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Represents a chess game
public class ChessGame implements Writable {
    private Board board;
    private List<Board> savedBoards;
    private String playerTurn;

    //EFFECTS: creates a chess game
    public ChessGame() {
        board = new Board();
        board.setupBoard();
        playerTurn = "white";
        savedBoards = new ArrayList<>();
    }

    //region PlayingGame
    //REQUIRES:fromSquare piece != null, both squares != null
    //MODIFIES:fromSquare, toSquare, fromSquare piece
    //EFFECTS: return true if legal, else false. Make move if legal, move is legal if:
    //          -piece that is moving is the same color as the player turn
    //          -piece can move to that square
    //      if made move end turn, log this event
    public boolean movePiece(Square fromSquare, Square toSquare) {
        Piece piece = fromSquare.getPiece();
        if (fromSquare.containsPiece() && piece.getColor().equals(playerTurn)) {
            List<Square> fromSquares = new ArrayList<>();
            List<Square> toSquares = new ArrayList<>();
            board.getLegalMoves(fromSquares, toSquares, piece.getColor());
            for (int i = 0; i < fromSquares.size(); i++) {
                if (fromSquare == fromSquares.get(i) && toSquare == toSquares.get(i)) {
                    checkSpecialMoves(fromSquare, toSquare);
                    board.movePiece(fromSquare, toSquare);
                    endTurn();
                    logValidMove(fromSquare, toSquare);
                    return true;
                }
            }
        }
        logInvalidMove(fromSquare, toSquare);
        return false;
    }

    //MODIFIES: this, the pawns of the player whose turn it is
    //EFFECTS:  -removes en passant privileges from pawns of player whose turn it is
    //          -changes player turn from white to black and vice versa
    //          -turns pawns to queen if they made it to end of board
    //          -adds a copy of the board to savedBoards
    private void endTurn() {
        List<Piece> pieces = board.getPieces(playerTurn);
        pieces.removeIf(piece -> (!piece.getName().equals("pawn")));
        for (Piece piece : pieces) {
            Pawn pawn = (Pawn) piece;
            pawn.setCanEnPassantRight(false);
            pawn.setCanEnPassantLeft(false);
            promote(pawn);
        }
        changePlayerTurn();
        Board boardToSave = new Board();
        boardToSave.copyBoard(board);
        this.saveBoard(boardToSave);
    }

    //MODIFIES:square with pawn
    //EFFECTS: if pawn is has y = 8 replace it with a white queen, if y = 1 replace it with a black queen
    private void promote(Pawn pawn) {
        int currentY = pawn.getSquare().getYCoordinate();
        try {
            if (currentY == 1) {
                pawn.getSquare().setPiece(new Queen("black"));
            } else if (currentY == 8) {
                pawn.getSquare().setPiece(new Queen("white"));
            }
        } catch (ColorException e) {
            System.err.println("Tried to create a piece that is not white or black");
        }

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
    //endregion

    //EFFECTS: Logs an event that the player made an invalid move
    private void logInvalidMove(Square fromSquare, Square toSquare) {
        EventLog.getInstance().logEvent(new Event("Player " + playerTurn + " attempted move "
                + fromSquare.getName() + " to " + toSquare.getName()));
    }

    //EFFECTS: Logs an event that the player made a move
    private void logValidMove(Square fromSquare, Square toSquare) {
        EventLog.getInstance().logEvent(new Event("Player " + playerTurn + " made move "
                + fromSquare.getName() + " to " + toSquare.getName()));
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
            king.setHasMoved(true);
            checkHasCastled(fromSquare, toSquare);
        } else if (piece.getName().equals("rook")) {
            Rook rook = (Rook) piece;
            rook.setHasMoved(true);
        } else if (piece.getName().equals("pawn")) {
            Pawn pawn = (Pawn) piece;
            pawn.setHasMoved(true);
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

    //region CheckIsOver
    //EFFECTS: returns true if game has ended else false
    public boolean checkIsGameOver() {
        return isInsufficientMaterial() || isStalemate() || isDrawByRepetition() || isCheckMate();
    }

    //EFFECTS: returns " " if game has not ended or if the game is over
    //          -checkmate
    //          -draw by repetition
    //          -draw by insufficient material
    //          -draw by stalemate
    public String getGameOverString() {
        String result = " ";
        if (isCheckMate()) {
            result = "checkmate";
        } else if (isStalemate()) {
            result = "draw by stalemate";
        } else if (isDrawByRepetition()) {
            result = "draw by repetition";
        } else if (isInsufficientMaterial()) {
            result = "draw by insufficient material";
        } else {
            return result;
        }
        EventLog.getInstance().logEvent(new Event("Game ended by " + result));
        return result;
    }

    //EFFECTS: returns true if player currently playing has their king in check and has no legal moves, else false
    private boolean isCheckMate() {
        List<Square> fromSquares = new ArrayList<>();
        List<Square> toSquares = new ArrayList<>();
        board.getLegalMoves(fromSquares, toSquares, playerTurn);
        return (board.isKingInCheck(playerTurn) && fromSquares.size() == 0);
    }

    //EFFECTS: returns true if player currently playing has their king not in check and has no legal moves else false
    private boolean isStalemate() {
        List<Square> fromSquares = new ArrayList<>();
        List<Square> toSquares = new ArrayList<>();
        board.getLegalMoves(fromSquares, toSquares, playerTurn);
        return (!board.isKingInCheck(playerTurn) && fromSquares.size() == 0);
    }

    //REQUIRES: both players have a king
    //EFFECTS: returns true if both players don't have enough pieces to checkmate the other player, combinations are:
    //         -king
    //         -king and 1 knight
    //         -king and 1 bishop
    //       else return false
    private boolean isInsufficientMaterial() {
        List<Piece> whitePieces = board.getPieces("white");
        List<Piece> blackPieces = board.getPieces("black");
        boolean whiteInsufficientMaterial = false;
        boolean blackInsufficientMaterial = false;
        if (whitePieces.size() <= 2 && blackPieces.size() <= 2) {
            for (int i = 0; i < 2; i++) {
                if (whitePieces.get(i).getName().equals("knight")
                        || whitePieces.get(i).getName().equals("bishop")) {
                    whiteInsufficientMaterial = true;
                }
                if (blackPieces.get(i).getName().equals("knight")
                        || blackPieces.get(i).getName().equals("bishop")) {
                    blackInsufficientMaterial = true;
                }
            }
        }
        return whiteInsufficientMaterial && blackInsufficientMaterial;
    }

    //EFFECTS: return true if that last 3 turns with the same player turn have the same board position, else false
    private boolean isDrawByRepetition() {

        if (savedBoards.size() >= 10) {
            Board currentBoard = savedBoards.get(savedBoards.size() - 1);
            Board previousBoard = savedBoards.get(savedBoards.size() - 5);
            Board secondPreviousBoard = savedBoards.get(savedBoards.size() - 9);
            return currentBoard.isIdentical(previousBoard) && currentBoard.isIdentical(secondPreviousBoard);
        }
        return false;
    }
    //endregion

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("boards", boardsToJson());
        return json;
    }

    // EFFECTS: returns saved boards as a JSON array
    private JSONArray boardsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Board board : savedBoards) {
            jsonArray.put(board.toJson());
        }
        return jsonArray;
    }

    //REQUIRES: board != null
    //MODIFIES: this
    //EFFECTS: adds board to saved board
    public void saveBoard(Board board) {
        savedBoards.add(board);
    }

    public void setBoard(Board board) {
        this.board = board;
        EventLog.getInstance().logEvent(new Event("User started a new game"));
    }

    public Board getBoard() {
        return board;
    }

    public List<Board> getSavedBoards() {
        return savedBoards;
    }

    public int getSavedBoardsSize() {
        return getSavedBoards().size();
    }

    public Board getSavedBoard(int index) {
        EventLog.getInstance().logEvent(new Event("User displaying move " + (index + 1)));
        return savedBoards.get(index);
    }

    //EFFECTS: returns square with given coordinates
    public Square getSquare(int x, int y) {
        return board.getSquare(x, y);
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

}
