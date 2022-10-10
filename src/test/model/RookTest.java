package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RookTest {
    private Board board;
    private Square d4;
    @BeforeEach
    void setup(){
        board = new Board();
        d4 = board.getSquare(4,4);
    }

    @Test
    public void testConstructor(){
        Rook whiteRook = new Rook("white");
        Rook blackRook = new Rook("black");

        assertEquals("white", whiteRook.getColor());
        assertNull(whiteRook.getSquare());
        assertEquals("rook", whiteRook.getName());
        assertFalse(whiteRook.getHasMoved());

        assertEquals("black", blackRook.getColor());
        assertNull(blackRook.getSquare());
        assertEquals("rook", blackRook .getName());
        assertFalse(blackRook.getHasMoved());
    }

    @Test
    public void testMovesFromCorner() {
        Square a1 = board.getSquare(1,1);
        Square h8 = board.getSquare(8,8);
        a1.setPiece(new Rook("white"));
        h8.setPiece(new Rook("white"));
        List<Square> a1PieceSquares = a1.getPiece().squaresCanMoveTo(board);
        List<Square> h8PieceSquares = h8.getPiece().squaresCanMoveTo(board);

        assertEquals(14, a1PieceSquares.size());
        assertEquals(14, h8PieceSquares.size());
        assertTrue(a1PieceSquares.contains(board.getSquare(1,8)));
        assertTrue(h8PieceSquares.contains(board.getSquare(1,8)));
        assertTrue(a1PieceSquares.contains(board.getSquare(8,1)));
        assertTrue(h8PieceSquares.contains(board.getSquare(8,1)));
    }

    @Test
    public void testPossibleCaptures() {
        Square b4 = board.getSquare(2,4);
        Square d3 = board.getSquare(4,3);
        Square d8 = board.getSquare(4,8);
        Square g4 = board.getSquare(7,4);
        b4.setPiece(new Rook("black"));
        d3.setPiece(new Rook("black"));
        d8.setPiece(new Rook("black"));
        g4.setPiece(new Rook("black"));
        d4.setPiece(new Rook("white"));
        List<Square> d4PieceSquares = d4.getPiece().squaresCanMoveTo(board);
        assertEquals(10, d4PieceSquares.size());
    }

    @Test
    public void testPieceBlocked() {
        Square c4 = board.getSquare(3,4);
        Square d3 = board.getSquare(4,3);
        Square d5 = board.getSquare(4,5);
        Square e4 = board.getSquare(5,4);
        c4.setPiece(new Rook("black"));
        d3.setPiece(new Rook("black"));
        d5.setPiece(new Rook("black"));
        e4.setPiece(new Rook("black"));
        d4.setPiece(new Rook("black"));
        List<Square> d4PieceSquares = d4.getPiece().squaresCanMoveTo(board);
        assertEquals(0, d4PieceSquares.size());
    }

    @Test
    public void testChangeHasMoved() {
        d4.setPiece(new Rook("white"));
        Rook rook = (Rook) d4.getPiece();
        assertFalse(rook.getHasMoved());
        rook.setHasMovedTrue();
        assertTrue(rook.getHasMoved());
    }

    @Test
    public void testLegalMoves() {
        Rook rook = new Rook("white");
        d4.setPiece(rook);
        board.getSquare(4,5).setPiece(new Rook("black"));
        Square d3 = board.getSquare(4,3);
        d3.setPiece(new King("white"));
        assertEquals(1, rook.legalMoves(board).size());
        board.movePiece(d3, board.getSquare(3,3));
        assertEquals(11, rook.legalMoves(board).size());
    }
}
