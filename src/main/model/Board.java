package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Represents a chess board with 64 squares.
public class Board implements Writable {
    private final List<Square> squares;

    //EFFECTS:Creates a 8x8 board and list of squares with the correct coordinates
    public Board() {
        squares = new ArrayList<>();
        for (int i = 1; i < 65; i++) {
            if (i % 8 == 0) {
                squares.add(new Square(i / 8, 8));
            } else {
                squares.add(new Square(i / 8 + 1, i % 8));
            }
        }
    }

    //region Setup
    //MODIFIES:squares that will have pieces, and the pieces themselves
    //EFFECTS: places pieces on their starting square based on chess rules
    public void setupBoard() {
        setupWhitePieces();
        setupBlackPieces();
    }

    //MODIFIES:squares that will have pieces, and the pieces themselves
    //EFFECTS: places white pieces on their starting square based on chess rules
    private void setupWhitePieces() {
        this.getSquare(1, 1).setPiece(new Rook("white"));
        this.getSquare(2, 1).setPiece(new Knight("white"));
        this.getSquare(3, 1).setPiece(new Bishop("white"));
        this.getSquare(4, 1).setPiece(new Queen("white"));
        this.getSquare(5, 1).setPiece(new King("white"));
        this.getSquare(6, 1).setPiece(new Bishop("white"));
        this.getSquare(7, 1).setPiece(new Knight("white"));
        this.getSquare(8, 1).setPiece(new Rook("white"));
        for (int i = 1; i < 9; i++) {
            Square square = this.getSquare(i, 2);
            square.setPiece(new Pawn("white"));
        }
    }

    //MODIFIES:squares that will have pieces, and the pieces themselves
    //EFFECTS: places black pieces on their starting square based on chess rules
    private void setupBlackPieces() {
        this.getSquare(1, 8).setPiece(new Rook("black"));
        this.getSquare(2, 8).setPiece(new Knight("black"));
        this.getSquare(3, 8).setPiece(new Bishop("black"));
        this.getSquare(4, 8).setPiece(new Queen("black"));
        this.getSquare(5, 8).setPiece(new King("black"));
        this.getSquare(6, 8).setPiece(new Bishop("black"));
        this.getSquare(7, 8).setPiece(new Knight("black"));
        this.getSquare(8, 8).setPiece(new Rook("black"));
        for (int i = 1; i < 9; i++) {
            Square square = this.getSquare(i, 7);
            square.setPiece(new Pawn("black"));
        }
    }
    //endregion

    //REQUIRES: fromSquare and toSquare exist on the board
    //EFFECTS: tests given move, if the king with the same color as the piece moving is not in check return true,
    // else return false
    public boolean checkIsLegalMove(Square fromSquare, Square toSquare) {
        int fromSquareIndex = squares.indexOf(fromSquare);
        int toSquareIndex = squares.indexOf(toSquare);

        Board testBoard = new Board();
        testBoard.copyBoard(this);
        Square testFromSquare = testBoard.getSquares().get(fromSquareIndex);
        Square testToSquare = testBoard.getSquares().get(toSquareIndex);
        testBoard.movePiece(testFromSquare, testToSquare);
        return !testBoard.isKingInCheck(fromSquare.getPiece().getColor());
    }

    //REQUIRES:color = "white" or "black", a king piece with given color exists on the board
    //EFFECTS: returns true if given color's king can be captured by an opponent piece, else false
    public boolean isKingInCheck(String color) {
        List<Piece> pieces;
        Square kingSquare = null;

        for (Square square : squares) {
            Piece piece = square.getPiece();
            if (square.containsPiece() && piece.getName().equals("king") && piece.getColor().equals(color)) {
                kingSquare = square;
                break;
            }
        }
        if (color.equals("white")) {
            pieces = this.getPieces("black");
        } else {
            pieces = this.getPieces("white");
        }
        for (Piece piece : pieces) {
            List<Square> squares = piece.getSquaresCanMoveTo(this);
            if (squares.contains(kingSquare)) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES:fromSquare piece != null, both squares != null
    //MODIFIES:fromSquare, toSquare, fromSquare piece
    //EFFECTS: moves piece from one square to another
    public void movePiece(Square fromSquare, Square toSquare) {
        Piece piece = fromSquare.getPiece();
        fromSquare.removePiece();
        toSquare.setPiece(piece);
    }

    //REQUIRES: board != null
    //MODIFIES:this
    //EFFECTS:copies all piece locations of given board.
    public void copyBoard(Board boardToCopy) {
        for (int i = 0; i < boardToCopy.squares.size(); i++) {
            Piece pieceToCopy = boardToCopy.getSquares().get(i).getPiece();
            if (pieceToCopy != null) {
                if (pieceToCopy.getName().equals("king")) {
                    this.squares.get(i).setPiece(new King(pieceToCopy.getColor()));
                } else if (pieceToCopy.getName().equals("knight")) {
                    this.squares.get(i).setPiece(new Knight(pieceToCopy.getColor()));
                } else if (pieceToCopy.getName().equals("bishop")) {
                    this.squares.get(i).setPiece(new Bishop(pieceToCopy.getColor()));
                } else if (pieceToCopy.getName().equals("queen")) {
                    this.squares.get(i).setPiece(new Queen(pieceToCopy.getColor()));
                } else if (pieceToCopy.getName().equals("rook")) {
                    this.squares.get(i).setPiece(new Rook(pieceToCopy.getColor()));
                } else {
                    this.squares.get(i).setPiece(new Pawn(pieceToCopy.getColor()));
                }
            }
        }
    }

    //REQUIRES: board != null
    //EFFECTS: returns true if both board and this have the same pieces in the same places
    public boolean isIdentical(Board board) {
        for (int i = 0; i < squares.size(); i++) {
            Piece thisPiece = squares.get(i).getPiece();
            Piece otherPiece = board.getSquares().get(i).getPiece();
            if (thisPiece != null && otherPiece != null) {
                if (!thisPiece.getName().equals(otherPiece.getName())
                        || !thisPiece.getColor().equals(otherPiece.getColor())) {
                    return false;
                }
            } else if (thisPiece != null || otherPiece != null) {
                return false;
            }
        }
        return true;
    }

    //MODIFIES: squares with pieces
    //EFFECTS: removes all pieces from the board
    public void clear() {
        for (Square square : squares) {
            square.removePiece();
        }
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("pieces", piecesToJson());
        return json;
    }

    // EFFECTS: returns pieces as a JSON array
    private JSONArray piecesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Piece piece : this.getPieces("white")) {
            jsonArray.put(piece.toJson());
        }
        for (Piece piece : this.getPieces("black")) {
            jsonArray.put(piece.toJson());
        }
        return jsonArray;
    }

    //MODIFIES: fromSquares, toSquares
    //EFFECTS: adds legal moves to given lists, each fromSquare has the same index as the corresponding toSquare
    public void getLegalMoves(List<Square> fromSquares, List<Square> toSquares, String color) {
        List<Piece> pieces = getPieces(color);
        for (Piece piece : pieces) {
            List<Square> squares = piece.getLegalMoves(this);
            for (Square square : squares) {
                fromSquares.add(piece.getSquare());
                toSquares.add(square);
            }
        }
    }

    //REQUIRES: color = "white" or "black"
    //EFFECTS: returns all pieces of given color from board in no guaranteed order
    public List<Piece> getPieces(String color) {
        List<Piece> pieces = new ArrayList<>();
        for (Square square : squares) {
            Piece piece = square.getPiece();
            if (piece != null && piece.getColor().equals(color)) {
                pieces.add(piece);
            }
        }
        return pieces;
    }

    //EFFECTS:returns square with given coordinates from board, if square is not in board return null
    public Square getSquare(int x, int y) {
        for (Square square : squares) {
            if (square.getXCoordinate() == x && square.getYCoordinate() == y) {
                return square;
            }
        }
        return null;
    }

    public List<Square> getSquares() {
        return squares;
    }
}
