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
        chessGame.movePiece(chessGame.getSquare(4,2), chessGame.getSquare(4,4));
    }
    @Test
    public void testConstructor() {
        chessGame.changePlayerTurn();
        assertEquals("white", chessGame.getPlayerTurn());
        assertEquals("rook", chessGame.getBoard().getSquare(1,1).getPiece().getName());
    }

    @Test
    public void testPlayerTurn() {
        chessGame.changePlayerTurn();
        assertEquals("white", chessGame.getPlayerTurn());
        chessGame.changePlayerTurn();
        assertEquals("black", chessGame.getPlayerTurn());
        chessGame.changePlayerTurn();
        assertEquals("white", chessGame.getPlayerTurn());
    }

    @Test
    public void testConsecutiveTwoTurns() {
        boolean performedMove = chessGame.movePiece(chessGame.getSquare(3,2), chessGame.getSquare(3,3));
        assertFalse(performedMove);
        assertNull(board.getSquare(3,3).getPiece());
    }
    @Test
    public void testCastling() {
        chessGame.movePiece(chessGame.getSquare(5,7), chessGame.getSquare(5,5));
        chessGame.movePiece(chessGame.getSquare(3,1), chessGame.getSquare(5,3));
        chessGame.movePiece(chessGame.getSquare(6,8), chessGame.getSquare(5,7));
        chessGame.movePiece(chessGame.getSquare(4,1), chessGame.getSquare(4,2));
        chessGame.movePiece(chessGame.getSquare(7,8), chessGame.getSquare(6,6));
        chessGame.movePiece(chessGame.getSquare(2,1), chessGame.getSquare(3,3));
        chessGame.movePiece(chessGame.getSquare(5,8), chessGame.getSquare(7,8));
        chessGame.movePiece(chessGame.getSquare(5,1), chessGame.getSquare(3,1));
        assertEquals("rook", chessGame.getSquare(6,8).getPiece().getName());
        assertEquals("rook", chessGame.getSquare(4,1).getPiece().getName());
    }

    @Test
    public void testEnPassant() {
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
    public void testPawnMoving() {
        chessGame.movePiece(chessGame.getSquare(5,7), chessGame.getSquare(5,5));
        Pawn d4Pawn = (Pawn) chessGame.getSquare(4,4).getPiece();
        Pawn e5Pawn = (Pawn) chessGame.getSquare(5,5).getPiece();
        assertTrue(d4Pawn.getHasMoved());
        assertTrue(e5Pawn.getHasMoved());
    }

    @Test
    public void testEndTurn() {
        assertEquals(1, chessGame.getSavedBoards().size());
        assertTrue(chessGame.getPlayerTurn().equals("black"));
    }
}
