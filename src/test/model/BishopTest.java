package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {
    private Board board;
    private Square a1;
    private Square d4;
    private Square h8;

    @BeforeEach
    void setup(){
        board = new Board();
        a1 = board.getSquare(1,1);
        d4 = board.getSquare(4,4);
        h8 = board.getSquare(8,8);
    }

    @Test
    public void testConstructor(){
        Bishop whiteBishop = new Bishop("white");
        Bishop blackBishop = new Bishop("black");

        assertEquals("white", whiteBishop.getColor());
        assertNull(whiteBishop.getSquare());
        assertEquals("bishop", whiteBishop.getName());

        assertEquals("black", blackBishop.getColor());
        assertNull(blackBishop.getSquare());
        assertEquals("bishop", blackBishop.getName());
    }

    @Test
    public void testPossibleMovesFromCorners(){
        a1.setPiece(new Bishop("white"));
        h8.setPiece(new Bishop("white"));
        List<Square> a1PieceSquares = a1.getPiece().squaresCanMoveTo(board);
        List<Square> h8PieceSquares = h8.getPiece().squaresCanMoveTo(board);

        assertEquals(6, a1PieceSquares.size());
        assertTrue(a1PieceSquares.contains(board.getSquare(7,7)));
        assertTrue(a1PieceSquares.contains(board.getSquare(5,5)));
        assertTrue(a1PieceSquares.contains(board.getSquare(2,2)));
        assertFalse(a1PieceSquares.contains(a1));
        assertFalse(a1PieceSquares.contains(h8));

        assertEquals(6, h8PieceSquares.size());
        assertTrue(h8PieceSquares.contains(board.getSquare(7,7)));
        assertTrue(h8PieceSquares.contains(board.getSquare(5,5)));
        assertTrue(h8PieceSquares.contains(board.getSquare(2,2)));
        assertFalse(h8PieceSquares.contains(a1));
        assertFalse(h8PieceSquares.contains(h8));
    }

    @Test
    public void testPossibleCaptures() {
        Square b2 = board.getSquare(2,2);
        Square c5 = board.getSquare(3,5);
        Square g1 = board.getSquare(7,1);
        b2.setPiece(new Bishop("white"));
        c5.setPiece(new Bishop("white"));
        d4.setPiece(new Bishop("black"));
        g1.setPiece(new Bishop("white"));
        h8.setPiece(new Bishop("white"));
        List<Square> d4PieceSquares = d4.getPiece().squaresCanMoveTo(board);

        assertEquals(10, d4PieceSquares.size());
        assertTrue(d4PieceSquares.contains(board.getSquare(3,3)));
        assertTrue(d4PieceSquares.contains(c5));
        assertTrue(d4PieceSquares.contains(g1));
        assertTrue(d4PieceSquares.contains(b2));
        assertFalse(d4PieceSquares.contains(board.getSquare(2,6)));
        assertFalse(d4PieceSquares.contains(a1));
    }

    @Test
    public void testPieceBlocked() {
        Square c3 = board.getSquare(3,3);
        Square c5 = board.getSquare(3,5);
        Square e3 = board.getSquare(5,3);
        Square e5 = board.getSquare(5,5);
        c3.setPiece(new Bishop("black"));
        c5.setPiece(new Bishop("black"));
        d4.setPiece(new Bishop("black"));
        e3.setPiece(new Bishop("black"));
        e5.setPiece(new Bishop("black"));
        List<Square> d4PieceSquares = d4.getPiece().squaresCanMoveTo(board);
        assertEquals(0, d4PieceSquares.size());
    }

    @Test
    public void testLegalMoves() {
        Bishop bishop = new Bishop("white");
        d4.setPiece(bishop);
        board.getSquare(4,5).setPiece(new Rook("black"));
        Square d3 = board.getSquare(4,3);
        d3.setPiece(new King("white"));
        assertEquals(0, bishop.legalMoves(board).size());
        board.movePiece(d3, board.getSquare(3,3));
        assertEquals(10, bishop.legalMoves(board).size());
    }
}
