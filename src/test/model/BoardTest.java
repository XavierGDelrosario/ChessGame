package model;

import exceptions.ColorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private List<Square> squares;
    private Square a1;
    private Square a8;
    private Square b3;
    private Square c6;
    private Square h1;
    private Square h8;
    @BeforeEach
    void setup() {
        board = new Board();
        squares = board.getSquares();
        a1 = squares.get(0);
        a8 = squares.get(7);
        b3 = squares.get(10);
        c6 = squares.get(21);
        h1 = squares.get(56);
        h8 = squares.get(63);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, squares.get(0).getXCoordinate());
        assertEquals(1, squares.get(0).getYCoordinate());

        assertEquals(1, squares.get(7).getXCoordinate());
        assertEquals(8, squares.get(7).getYCoordinate());

        assertEquals(2, squares.get(8).getXCoordinate());
        assertEquals(1, squares.get(8).getYCoordinate());

        assertEquals(2, squares.get(10).getXCoordinate());
        assertEquals(3, squares.get(10).getYCoordinate());

        assertEquals(2, squares.get(15).getXCoordinate());
        assertEquals(8, squares.get(15).getYCoordinate());
        assertEquals(3, squares.get(16).getXCoordinate());
        assertEquals(1, squares.get(16).getYCoordinate());

        assertEquals(3, squares.get(21).getXCoordinate());
        assertEquals(6, squares.get(21).getYCoordinate());

        assertEquals(4, squares.get(24).getXCoordinate());
        assertEquals(1, squares.get(24).getYCoordinate());

        assertEquals(5, squares.get(32).getXCoordinate());
        assertEquals(1, squares.get(32).getYCoordinate());

        assertEquals(6, squares.get(40).getXCoordinate());
        assertEquals(1, squares.get(40).getYCoordinate());

        assertEquals(7, squares.get(48).getXCoordinate());
        assertEquals(1, squares.get(48).getYCoordinate());

        assertEquals(8, squares.get(56).getXCoordinate());
        assertEquals(1, squares.get(56).getYCoordinate());

        assertEquals(8, squares.get(63).getXCoordinate());
        assertEquals(8, squares.get(63).getYCoordinate());
    }

    @Test
    public void testGetSquares(){
        assertEquals(a1, board.getSquare(1,1));
        assertEquals(a8, board.getSquare(1,8));
        assertEquals(b3, board.getSquare(2,3));
        assertEquals(c6, board.getSquare(3,6));
        assertEquals(h1, board.getSquare(8,1));
        assertEquals(h8, board.getSquare(8,8));
        assertNull(board.getSquare(0, 0));
        assertNull(board.getSquare(9, 9));
    }

    @Test
    public void testSetupWhitePieces(){
        board.setupBoard();
        Piece a1Piece = board.getSquare(1,1).getPiece();
        Piece b1Piece = board.getSquare(2,1).getPiece();
        Piece c1Piece = board.getSquare(3,1).getPiece();
        Piece a2Piece = board.getSquare(1,2).getPiece();
        Piece d2Piece = board.getSquare(2,2).getPiece();
        Piece h2Piece = board.getSquare(3,2).getPiece();

        assertEquals("rook", a1Piece.getName());
        assertEquals("knight", b1Piece.getName());
        assertEquals("bishop", c1Piece.getName());
        assertEquals("white", a1Piece.getColor());
        assertEquals("white", b1Piece.getColor());
        assertEquals("white", c1Piece.getColor());
        assertEquals("pawn", a2Piece.getName());
        assertEquals("pawn", d2Piece.getName());
        assertEquals("pawn", h2Piece.getName());
        assertEquals("white", a2Piece.getColor());
        assertEquals("white", d2Piece.getColor());
        assertEquals("white", h2Piece.getColor());
    }

    @Test
    public void testSetupBlackPieces() {
        board.setupBoard();
        Piece a8Piece = board.getSquare(1,8).getPiece();
        Piece b8Piece = board.getSquare(2,8).getPiece();
        Piece c8Piece = board.getSquare(3,8).getPiece();
        Piece a7Piece = board.getSquare(1,7).getPiece();
        Piece d7Piece = board.getSquare(4,7).getPiece();
        Piece h7Piece = board.getSquare(8,7).getPiece();

        assertEquals("rook", a8Piece.getName());
        assertEquals("knight", b8Piece.getName());
        assertEquals("bishop", c8Piece.getName());
        assertEquals("black", a8Piece.getColor());
        assertEquals("black", b8Piece.getColor());
        assertEquals("black", c8Piece.getColor());
        assertEquals("pawn", a7Piece.getName());
        assertEquals("pawn", d7Piece.getName());
        assertEquals("pawn", h7Piece.getName());
        assertEquals("black", a7Piece.getColor());
        assertEquals("black", d7Piece.getColor());
        assertEquals("black", h7Piece.getColor());
    }

    @Test
    public void testMovingMultipleTimes() {
        try {
            Queen queen = new Queen("white");
            Knight knight = new Knight("black");
            a8.setPiece(queen);
            b3.setPiece(knight);
            assertNull(a1.getPiece());
            board.movePiece(b3, a1);
            assertNull(b3.getPiece());
            assertEquals(knight,a1.getPiece());
            board.movePiece(a8, a1);
            assertNull(a8.getPiece());
            assertEquals(queen, a1.getPiece());
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
    }

    @Test
    public void testGetPieces() {
        board.setupBoard();
        assertEquals(16, board.getPieces("white").size());
        assertEquals(16, board.getPieces("black").size());
        board.getSquare(1,1).removePiece();
        board.getSquare(1,2).removePiece();
        try {
            board.getSquare(1,1).setPiece(new Rook("black"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        assertEquals(14, board.getPieces("white").size());
        assertEquals(17, board.getPieces("black").size());
    }

    @Test
    public void testNotInCheck() {
        try {
            a1.setPiece(new King("white"));
            a8.setPiece(new King("black"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        assertFalse(board.isKingInCheck("white"));
        assertFalse(board.isKingInCheck("black"));
    }

    @Test
    public void testInCheckFromQueen() {
        try {
            a1.setPiece(new King("white"));
            a8.setPiece(new King("black"));
            h1.setPiece(new Queen("black"));
            h8.setPiece(new Queen("white"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        assertTrue(board.isKingInCheck("white"));
        assertTrue(board.isKingInCheck("black"));
        h1.removePiece();
        assertFalse(board.isKingInCheck("white"));
        assertTrue(board.isKingInCheck("black"));
        h8.removePiece();
        assertFalse(board.isKingInCheck("white"));
        assertFalse(board.isKingInCheck("black"));
    }

    @Test
    public void testInCheckFromRook() {
        try {
            a1.setPiece(new King("white"));
            a8.setPiece(new King("black"));
            h1.setPiece(new Rook("black"));
            h8.setPiece(new Rook("white"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        assertTrue(board.isKingInCheck("white"));
        assertTrue(board.isKingInCheck("black"));
        h1.removePiece();
        assertFalse(board.isKingInCheck("white"));
        assertTrue(board.isKingInCheck("black"));
        h8.removePiece();
        assertFalse(board.isKingInCheck("white"));
        assertFalse(board.isKingInCheck("black"));
    }

    @Test
    public void testInCheckFromBishop() {
        try {
            a1.setPiece(new King("white"));
            a8.setPiece(new King("black"));
            h1.setPiece(new Bishop("white"));
            h8.setPiece(new Bishop("black"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        assertTrue(board.isKingInCheck("white"));
        assertTrue(board.isKingInCheck("black"));
        h8.removePiece();
        assertFalse(board.isKingInCheck("white"));
        assertTrue(board.isKingInCheck("black"));
        h1.removePiece();
        assertFalse(board.isKingInCheck("white"));
        assertFalse(board.isKingInCheck("black"));
    }

    @Test
    public void testInCheckFromKnight() {

        Square b3 = board.getSquare(2,3);
        Square b6 = board.getSquare(2,6);
        try {
            a1.setPiece(new King("white"));
            a8.setPiece(new King("black"));
            b6.setPiece(new Knight("white"));
            b3.setPiece(new Knight("black"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        assertTrue(board.isKingInCheck("white"));
        assertTrue(board.isKingInCheck("black"));
        b3.removePiece();
        assertFalse(board.isKingInCheck("white"));
        assertTrue(board.isKingInCheck("black"));
        b6.removePiece();
        assertFalse(board.isKingInCheck("white"));
        assertFalse(board.isKingInCheck("black"));
    }

    @Test
    public void testInCheckFromPawn() {
        Square b2 = board.getSquare(2,2);
        Square b7 = board.getSquare(2,7);
        try {
            a1.setPiece(new King("white"));
            a8.setPiece(new King("black"));
            b7.setPiece(new Pawn("white"));
            b2.setPiece(new Pawn("black"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        assertTrue(board.isKingInCheck("white"));
        assertTrue(board.isKingInCheck("black"));
        b2.removePiece();
        assertFalse(board.isKingInCheck("white"));
        assertTrue(board.isKingInCheck("black"));
        b7.removePiece();
        assertFalse(board.isKingInCheck("white"));
        assertFalse(board.isKingInCheck("black"));
    }

    @Test
    public void testCopyBoard() {
        board.setupBoard();
        Board copiedBoard = new Board();
        copiedBoard.copyBoard(board);

        assertEquals(16, copiedBoard.getPieces("white").size());
        assertEquals(16, copiedBoard.getPieces("black").size());
        assertEquals("rook", copiedBoard.getSquare(1,1).getPiece().getName());
        assertEquals("knight", copiedBoard.getSquare(2,8).getPiece().getName());
        assertEquals("bishop", copiedBoard.getSquare(3,1).getPiece().getName());
        assertEquals("queen", copiedBoard.getSquare(4,8).getPiece().getName());
        assertEquals("king", copiedBoard.getSquare(5,1).getPiece().getName());
        assertEquals("pawn", copiedBoard.getSquare(6,7).getPiece().getName());
    }

    @Test
    public void testCheckLegalMove() {
        Square b1 = board.getSquare(2,1);
        try {
            a1.setPiece(new King("white"));
            a8.setPiece(new Queen("black"));
            h1.setPiece(new Queen("black"));
            b1.setPiece(new Queen("white"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }

        assertTrue(board.checkIsLegalMove(a1, board.getSquare(2,2)));
        assertFalse(board.checkIsLegalMove(a1, board.getSquare(1,2)));
        assertFalse(board.checkIsLegalMove(b1, board.getSquare(1,2)));
        assertFalse(board.checkIsLegalMove(b1, board.getSquare(8,1)));
    }

    @Test
    public void testGetLegalMoves() {
        Square a1 =board.getSquare(1,1);
        Square b1 = board.getSquare(2,1);
        Square h1 = board.getSquare(8,1);
        try {
            King king = new King("white");
            king.setHasMoved(true);
            board.getSquare(2,2).setPiece(new Pawn("white"));
            a1.setPiece(king);
            b1.setPiece(new Rook("white"));
            h1.setPiece(new Rook("black"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        List<Square> fromSquares = new ArrayList<>();
        List<Square> toSquares = new ArrayList<>();
        board.getLegalMoves(fromSquares, toSquares, "white");

        assertEquals(9, fromSquares.size());
        assertEquals(9, toSquares.size());
        assertEquals(a1, fromSquares.get(0));
        assertEquals(b1, fromSquares.get(3));
        assertEquals(b1, fromSquares.get(6));
        assertTrue(toSquares.contains(board.getSquare(8,1)));
    }

    @Test
    public void testClear() {
        board.setupBoard();
        board.clear();
        assertEquals(0, board.getPieces("white").size());
        assertEquals(0, board.getPieces("black").size());
    }

    @Test
    public void testSameBoard() {
        board.setupBoard();
        Board newBoard = new Board();
        assertFalse(board.isIdentical(newBoard));
        newBoard.setupBoard();
        assertTrue(board.isIdentical(newBoard));
    }
}