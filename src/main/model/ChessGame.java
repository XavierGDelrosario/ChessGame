package model;

//Represents a chess game
public class ChessGame {
    private Board board;

    //EFFECTS: creates a chess game
    public ChessGame() {
        board = new Board();
        board.setupBoard();
    }

    //TODO
    //REQUIRES:fromSquare piece != null
    //MODIFIES:fromSquare, toSquare, fromSquare piece
    //EFFECTS:removes piece reference from one square and adds that piece to the other square.
    // The piece's square reference is the to Square.
    public void movePiece(Square fromSquare, Square toSquare) {
        Piece piece = fromSquare.getPiece();
        fromSquare.removePiece();
        toSquare.setPiece(piece);
    }

    //TODO
    public void endTurn() {
        
    }

    //TODO
    public boolean checkGameOver() {
        return false;//stub
    }

    //TODO
    public boolean isCheckmate() {
        return false; //stub
    }

    //TODO
    public boolean isStalemate() {
        return false; //stub
    }

    //TODO
    public boolean isInsufficientMaterial() {
        return false;//stub
    }

    //TODO
    public boolean isRepetition() {
        return false;//stub
    }


}
