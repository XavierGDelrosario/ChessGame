package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChessGameTest {
    ChessGame chessGame;
    Board board;
    @BeforeEach
    public void setup() {
        chessGame = new ChessGame();
        board = chessGame.getBoard();
    }
    @Test
    public void testConstructor() {
        assertEquals("white", chessGame.getPlayerTurn());
        assertEquals("rook", chessGame.getBoard().getSquare(1,1).getPiece().getName());
    }

    @Test
    public void testConsecutiveTwoTurns() {
        chessGame.movePiece(chessGame.getSquare(4,2), chessGame.getSquare(4,4));
        boolean performedMove = chessGame.movePiece(chessGame.getSquare(3,2), chessGame.getSquare(3,3));
        assertFalse(performedMove);
        assertNull(board.getSquare(3,3).getPiece());
    }

    @Test
    public void testCastlingShort() {
        chessGame.movePiece(chessGame.getSquare(4,2), chessGame.getSquare(4,4));
        chessGame.movePiece(chessGame.getSquare(5,7), chessGame.getSquare(5,5));
        chessGame.movePiece(chessGame.getSquare(5,2), chessGame.getSquare(5,3));
        chessGame.movePiece(chessGame.getSquare(6,8), chessGame.getSquare(5,7));
        chessGame.movePiece(chessGame.getSquare(6,1), chessGame.getSquare(4,3));
        chessGame.movePiece(chessGame.getSquare(7,8), chessGame.getSquare(6,6));
        chessGame.movePiece(chessGame.getSquare(7,1), chessGame.getSquare(6,3));
        chessGame.movePiece(chessGame.getSquare(5,8), chessGame.getSquare(7,8));
        chessGame.movePiece(chessGame.getSquare(5,1), chessGame.getSquare(7,1));
        chessGame.movePiece(chessGame.getSquare(7,8), chessGame.getSquare(8,8));
        chessGame.movePiece(chessGame.getSquare(7,1), chessGame.getSquare(8,1));
        assertEquals("rook", chessGame.getSquare(6,8).getPiece().getName());
        assertEquals("rook", chessGame.getSquare(6,1).getPiece().getName());
    }

    @Test
    public void testCastlingLong() {
        chessGame.movePiece(chessGame.getSquare(4,2), chessGame.getSquare(4,4));
        chessGame.movePiece(chessGame.getSquare(4,7), chessGame.getSquare(4,5));
        chessGame.movePiece(chessGame.getSquare(3,1), chessGame.getSquare(5,3));
        chessGame.movePiece(chessGame.getSquare(4,8), chessGame.getSquare(4,6));
        chessGame.movePiece(chessGame.getSquare(4,1), chessGame.getSquare(4,2));
        chessGame.movePiece(chessGame.getSquare(3,8), chessGame.getSquare(5,6));
        chessGame.movePiece(chessGame.getSquare(2,1), chessGame.getSquare(3,3));
        chessGame.movePiece(chessGame.getSquare(2,8), chessGame.getSquare(3,6));
        chessGame.movePiece(chessGame.getSquare(1,2), chessGame.getSquare(1,3));
        chessGame.movePiece(chessGame.getSquare(5,8), chessGame.getSquare(3,8));
        chessGame.movePiece(chessGame.getSquare(5,1), chessGame.getSquare(3,1));
        chessGame.movePiece(chessGame.getSquare(3,8), chessGame.getSquare(2,8));
        chessGame.movePiece(chessGame.getSquare(3,1), chessGame.getSquare(2,1));
        assertEquals("rook", chessGame.getSquare(4,8).getPiece().getName());
        assertEquals("rook", chessGame.getSquare(4,1).getPiece().getName());
    }

    @Test
    public void testEnPassantLeft() {
        chessGame.movePiece(chessGame.getSquare(4,2), chessGame.getSquare(4,4));
        chessGame.movePiece(chessGame.getSquare(5,7), chessGame.getSquare(5,5));
        chessGame.movePiece(chessGame.getSquare(4,4), chessGame.getSquare(4,5));
        chessGame.movePiece(chessGame.getSquare(5,5), chessGame.getSquare(5,4));
        chessGame.movePiece(chessGame.getSquare(6,2), chessGame.getSquare(6,4));
        chessGame.movePiece(chessGame.getSquare(3,7), chessGame.getSquare(3,5));
        chessGame.movePiece(chessGame.getSquare(4,5), chessGame.getSquare(3,6));
        assertNull(chessGame.getSquare(3,5).getPiece());
        boolean madeMove = chessGame.movePiece(chessGame.getSquare(5,4), chessGame.getSquare(6,3));
        assertFalse(madeMove);
    }

    @Test
    public void testEnPassantRight() {
        chessGame.movePiece(chessGame.getSquare(5,7), chessGame.getSquare(5,5));
        chessGame.movePiece(chessGame.getSquare(4,4), chessGame.getSquare(4,5));
        chessGame.movePiece(chessGame.getSquare(5,5), chessGame.getSquare(5,4));
        chessGame.movePiece(chessGame.getSquare(6,2), chessGame.getSquare(6,4));
        chessGame.movePiece(chessGame.getSquare(5,4), chessGame.getSquare(6,3));
        assertNull(chessGame.getSquare(3,4).getPiece());

        chessGame.movePiece(chessGame.getSquare(1,2), chessGame.getSquare(1,3));
        chessGame.movePiece(chessGame.getSquare(3,7), chessGame.getSquare(3,5));
        chessGame.movePiece(chessGame.getSquare(1,3), chessGame.getSquare(1,4));
        boolean madeMove = chessGame.movePiece(chessGame.getSquare(4,5), chessGame.getSquare(3,6));
        assertFalse(madeMove);
    }

    @Test
    public void testPawnMoving() {
        chessGame.movePiece(chessGame.getSquare(4,2), chessGame.getSquare(4,4));
        chessGame.movePiece(chessGame.getSquare(5,7), chessGame.getSquare(5,5));
        Pawn d4Pawn = (Pawn) chessGame.getSquare(4,4).getPiece();
        Pawn e5Pawn = (Pawn) chessGame.getSquare(5,5).getPiece();
        assertTrue(d4Pawn.getHasMoved());
        assertTrue(e5Pawn.getHasMoved());
    }

    @Test
    public void testRookMoving() {
        chessGame.movePiece(chessGame.getSquare(4,2), chessGame.getSquare(4,4));
        chessGame.movePiece(chessGame.getSquare(8,7), chessGame.getSquare(8,6));
        chessGame.movePiece(chessGame.getSquare(1,2), chessGame.getSquare(1,3));
        chessGame.movePiece(chessGame.getSquare(8,8), chessGame.getSquare(8,7));
        chessGame.movePiece(chessGame.getSquare(1,1), chessGame.getSquare(1,2));
        Rook a2Rook = (Rook) chessGame.getSquare(1,2).getPiece();
        Rook h7Rook = (Rook) chessGame.getSquare(8,7).getPiece();
        assertTrue(a2Rook.getHasMoved());
        assertTrue(h7Rook.getHasMoved());
    }

    @Test
    public void testEndTurn() {
        chessGame.movePiece(chessGame.getSquare(4,2), chessGame.getSquare(4,4));
        chessGame.movePiece(chessGame.getSquare(4,7), chessGame.getSquare(4,6));
        chessGame.movePiece(chessGame.getSquare(4,4), chessGame.getSquare(4,5));
        chessGame.movePiece(chessGame.getSquare(5,7), chessGame.getSquare(5,5));
        chessGame.movePiece(chessGame.getSquare(3,2), chessGame.getSquare(3,4));
        Pawn pawn = (Pawn) chessGame.getSquare(4,5).getPiece();
        assertEquals(5, chessGame.getSavedBoards().size());
        assertTrue(chessGame.getPlayerTurn().equals("black"));
        assertFalse(pawn.getCanEnPassantRight());
        Square a1 =  chessGame.getBoard().getSquare(1,1);
        Square a8 =  chessGame.getBoard().getSquare(1,8);
        a1.setPiece(new Pawn("black"));
        a8.setPiece(new Pawn("white"));
        chessGame.movePiece(chessGame.getSquare(8,7), chessGame.getSquare(8,6));
        assertTrue(a1.getPiece().getName().equals("queen"));
        assertTrue(a1.getPiece().getColor().equals("black"));
        chessGame.movePiece(chessGame.getSquare(8,2), chessGame.getSquare(8,4));
        assertTrue(a8.getPiece().getName().equals("queen"));
        assertTrue(a8.getPiece().getColor().equals("white"));

    }

    @Test
    public void testCheckmate() {
        board.clear();
        board.getSquare(1,1).setPiece(new King("white"));
        board.getSquare(1,2).setPiece(new Queen("black"));
        board.getSquare(2,1).setPiece(new Queen("black"));
        assertTrue(chessGame.checkIsGameOver().equals("checkmate"));
    }

    @Test
    public void testStalemate() {
        board.clear();
        King king = new King("white");
        king.setHasMovedTrue();
        board.getSquare(1,1).setPiece(king);
        board.getSquare(2,3).setPiece(new Queen("black"));
        assertTrue(chessGame.checkIsGameOver().equals("draw by stalemate"));
    }

    @Test
    public void testInsufficientMaterial() {
        board.clear();
        King kingWhite = new King("white");
        King kingBlack = new King("black");
        kingWhite.setHasMovedTrue();
        kingBlack.setHasMovedTrue();
        board.getSquare(1,1).setPiece(kingWhite);
        board.getSquare(1,2).setPiece(new Knight("white"));
        board.getSquare(1,3).setPiece(kingBlack);
        board.getSquare(1,4).setPiece(new Bishop("black"));
        assertTrue(chessGame.checkIsGameOver().equals("draw by insufficient material"));
    }

    @Test
    public void testOnePlayerInsufficientMaterial() {
        board.clear();
        King kingWhite = new King("white");
        King kingBlack = new King("black");
        kingWhite.setHasMovedTrue();
        kingBlack.setHasMovedTrue();
        board.getSquare(1,1).setPiece(kingWhite);
        board.getSquare(1,2).setPiece(new Queen("white"));
        board.getSquare(1,3).setPiece(kingBlack);
        board.getSquare(1,4).setPiece(new Bishop("black"));
        assertTrue(chessGame.checkIsGameOver().equals(" "));
    }

    @Test
    public void testDrawByRepetition() {
        Square e1 = board.getSquare(5,1);
        Square e2 = board.getSquare(5,2);
        Square e7 = board.getSquare(5,7);
        Square e8 = board.getSquare(5,8);
        chessGame.movePiece(e2, board.getSquare(5,4));
        chessGame.movePiece(e7, board.getSquare(5,5));
        assertTrue(chessGame.checkIsGameOver().equals(" "));
        chessGame.movePiece(e1, e2);
        chessGame.movePiece(e8, e7);
        assertTrue(chessGame.checkIsGameOver().equals(" "));
        chessGame.movePiece(e2, e1);
        chessGame.movePiece(e7, e8);
        assertTrue(chessGame.checkIsGameOver().equals(" "));
        chessGame.movePiece(e1, e2);
        chessGame.movePiece(e8, e7);
        assertTrue(chessGame.checkIsGameOver().equals(" "));
        chessGame.movePiece(e2, e1);
        assertTrue(chessGame.checkIsGameOver().equals(" "));
        chessGame.movePiece(e7, e8);
        assertTrue(chessGame.checkIsGameOver().equals("draw by repetition"));
        chessGame.movePiece(e2, e1);
        assertTrue(chessGame.checkIsGameOver().equals("draw by repetition"));
    }
}
