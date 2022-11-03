package model;

import exceptions.ColorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals("black", chessGame.getPlayerTurn());
        assertFalse(pawn.getCanEnPassantRight());
        Square a1 =  chessGame.getBoard().getSquare(1,1);
        Square a8 =  chessGame.getBoard().getSquare(1,8);
        try {
            a1.setPiece(new Pawn("black"));
            a8.setPiece(new Pawn("white"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }

        chessGame.movePiece(chessGame.getSquare(8,7), chessGame.getSquare(8,6));
        assertEquals("queen", a1.getPiece().getName());
        assertEquals("black", a1.getPiece().getColor());
        chessGame.movePiece(chessGame.getSquare(8,2), chessGame.getSquare(8,4));
        assertEquals("queen", a8.getPiece().getName());
        assertEquals("white", a8.getPiece().getColor());

    }

    @Test
    public void testCheckmate() {
        board.clear();
        try {
            board.getSquare(1,1).setPiece(new King("white"));
            board.getSquare(1,2).setPiece(new Queen("black"));
            board.getSquare(2,1).setPiece(new Queen("black"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        assertEquals("checkmate", chessGame.checkIsGameOver());
    }

    @Test
    public void testStalemate() {
        board.clear();
        try {
            King king = new King("white");
            king.setHasMoved(true);
            board.getSquare(1,1).setPiece(king);
            board.getSquare(2,3).setPiece(new Queen("black"));

        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        assertEquals("draw by stalemate", chessGame.checkIsGameOver());
    }

    @Test
    public void testInsufficientMaterial() {
        board.clear();
        try {
            King kingWhite = new King("white");
            King kingBlack = new King("black");
            kingWhite.setHasMoved(true);
            kingBlack.setHasMoved(true);
            board.getSquare(1,1).setPiece(kingWhite);
            board.getSquare(1,2).setPiece(new Knight("white"));
            board.getSquare(1,3).setPiece(kingBlack);
            board.getSquare(1,4).setPiece(new Bishop("black"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }

        assertEquals("draw by insufficient material", chessGame.checkIsGameOver());
    }

    @Test
    public void testOnePlayerInsufficientMaterial() {
        board.clear();
        try {
            King kingWhite = new King("white");
            King kingBlack = new King("black");
            kingWhite.setHasMoved(true);
            kingBlack.setHasMoved(true);
            board.getSquare(1,1).setPiece(kingWhite);
            board.getSquare(1,2).setPiece(new Queen("white"));
            board.getSquare(1,3).setPiece(kingBlack);
            board.getSquare(1,4).setPiece(new Bishop("black"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }

        assertEquals(" ", chessGame.checkIsGameOver());
    }

    @Test
    public void testDrawByRepetition() {
        Square e1 = board.getSquare(5,1);
        Square e2 = board.getSquare(5,2);
        Square e7 = board.getSquare(5,7);
        Square e8 = board.getSquare(5,8);
        chessGame.movePiece(e2, board.getSquare(5,4));
        chessGame.movePiece(e7, board.getSquare(5,5));
        assertEquals(" ", chessGame.checkIsGameOver());
        chessGame.movePiece(e1, e2);
        chessGame.movePiece(e8, e7);
        assertEquals(" ", chessGame.checkIsGameOver());
        chessGame.movePiece(e2, e1);
        chessGame.movePiece(e7, e8);
        assertEquals(" ", chessGame.checkIsGameOver());
        chessGame.movePiece(e1, e2);
        chessGame.movePiece(e8, e7);
        assertEquals(" ", chessGame.checkIsGameOver());
        chessGame.movePiece(e2, e1);
        assertEquals(" ", chessGame.checkIsGameOver());
        chessGame.movePiece(e7, e8);
        assertEquals("draw by repetition", chessGame.checkIsGameOver());
        chessGame.movePiece(e2, e1);
        assertEquals("draw by repetition", chessGame.checkIsGameOver());
    }

}
