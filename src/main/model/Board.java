package model;

import exceptions.ColorException;
import exceptions.NullBoardException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Represents a chess board with 64 squares.
public class Board {
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

    //REQUIRES: board != null
    //MODIFIES:this
    //EFFECTS:copies all piece locations of given board.
    public void Board(Board board) {
        try {
            for (int i = 0; i < board.squares.size(); i++) {
                Piece pieceToCopy = board.getSquares().get(i).getPiece();
                if (pieceToCopy != null) {
                    if (pieceToCopy.getName().equals("king")) {
                        this.squares.get(i).setPiece(copyPiece(pieceToCopy));
                    } else if (pieceToCopy.getName().equals("knight")) {
                        this.squares.get(i).setPiece(new Knight(pieceToCopy.getColor()));
                    } else if (pieceToCopy.getName().equals("bishop")) {
                        this.squares.get(i).setPiece(new Bishop(pieceToCopy.getColor()));
                    } else if (pieceToCopy.getName().equals("queen")) {
                        this.squares.get(i).setPiece(new Queen(pieceToCopy.getColor()));
                    } else if (pieceToCopy.getName().equals("rook")) {
                        this.squares.get(i).setPiece(copyPiece(pieceToCopy));
                    } else {
                        this.squares.get(i).setPiece(copyPiece(pieceToCopy));
                    }
                }
            }
        } catch (ColorException e) {
            System.err.println("Tried to create a piece that is not white or black");
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
        try {
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
        } catch (ColorException e) {
            System.err.println("Tried to create a piece that is not white or black");
        }

    }

    //MODIFIES:squares that will have pieces, and the pieces themselves
    //EFFECTS: places black pieces on their starting square based on chess rules
    private void setupBlackPieces() {
        try {
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
        } catch (ColorException e) {
            System.err.println("Tried to create a piece that is not white or black");
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
        testBoard.Board(this);
        Square testFromSquare = testBoard.getSquares().get(fromSquareIndex);
        Square testToSquare = testBoard.getSquares().get(toSquareIndex);
        testBoard.movePiece(testFromSquare, testToSquare);
        return !testBoard.isKingInCheck(fromSquare.getPiece().getColor());
    }

    //MODIFIES: fromSquares, toSquares
    //EFFECTS: adds legal moves to given lists, each fromSquare has the same index as the corresponding toSquare
    public void getPlayerLegalMove(List<Square> fromSquares, List<Square> toSquares, String color) {
        List<Piece> pieces = getPieces(color);
        List<Square> squares = null;
        for (Piece piece : pieces) {
            squares = getLegalMoves(piece);
            for (Square square : squares) {
                fromSquares.add(piece.getSquare());
                toSquares.add(square);
            }
        }
    }

    public List<Square> getLegalMoves(Piece piece) {
        try {
            return piece.getLegalMoves(this);
        } catch (NullBoardException e) {
            System.err.println("Passed null board");
        }
        return null;
    }

    //REQUIRES:color = "white" or "black", a king piece with given color exists on the board
    //EFFECTS: returns true if given color's king can be captured by an opponent piece, else false
    public boolean isKingInCheck(String color) {
        List<Piece> pieces;
        Square kingSquare = getKingSquare(color);
        if (color.equals("white")) {
            pieces = this.getPieces("black");
        } else {
            pieces = this.getPieces("white");
        }
        for (Piece piece : pieces) {
            try {
                List<Square> squares = piece.getSquaresCanMoveTo(this);
                if (squares.contains(kingSquare)) {
                    return true;
                }
            } catch (NullBoardException e) {
                System.err.println("Passed in board is null");
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

    //REQUIRES:piece = king, rook, or pawn
    //EFFECTS: returns copy of piece with extra fields to copy: king, rook, pawn keep track if they have moved, pawn
    // keeps track of left and right en passant
    private Piece copyPiece(Piece piece) {
        try {
            if (piece.getName().equals("pawn")) {
                Pawn pawn = new Pawn(piece.getColor());
                Pawn pawnToCopy = (Pawn) piece;
                pawn.setCanEnPassantLeft(pawnToCopy.getCanEnPassantLeft());
                pawn.setCanEnPassantRight(pawnToCopy.getCanEnPassantRight());
                pawn.setHasMoved(pawnToCopy.getHasMoved());
                return pawn;
            } else if (piece.getName().equals("rook")) {
                Rook rook = new Rook(piece.getColor());
                Rook rookToCopy = (Rook) piece;
                rook.setHasMoved(rookToCopy.getHasMoved());
                return rook;
            } else {
                King king = new King(piece.getColor());
                King kingToCopy = (King) piece;
                king.setHasMoved(kingToCopy.getHasMoved());
                return king;
            }
        } catch (ColorException e) {
            System.err.println("Tried to create piece that is not white or black");
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return squares.equals(board.squares);
    }

    @Override
    public int hashCode() {
        return Objects.hash(squares);
    }

    //MODIFIES: squares with pieces
    //EFFECTS: removes all pieces from the board
    public void clear() {
        for (Square square : squares) {
            square.removePiece();
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

    //REQUIRES:color = "white" or "black"
    //EFFECTS: returns square that contains king of given color or null if king does not exist
    private Square getKingSquare(String color) {
        for (Square square : squares) {
            Piece piece = square.getPiece();
            if (square.containsPiece() && piece.getName().equals("king") && piece.getColor().equals(color)) {
                return square;
            }
        }
        return null;
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

    public Square getSquare(String s) {
        for (Square square : squares) {
            if (square.getName().equals(s)) {
                return square;
            }
        }
        return null;
    }

    public List<Square> getSquares() {
        return squares;
    }
}
