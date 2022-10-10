package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {
    private Board board;
    private Square d4;

    @BeforeEach
    void setup() {
        board = new Board();
        d4 = board.getSquare(4,4);
        Square b3 = board.getSquare(2,3);
        Square b5 = board.getSquare(2,5);
        Square c2 = board.getSquare(3,2);
        Square c6 = board.getSquare(3,6);
        Square e2 = board.getSquare(5,2);
        Square e6 = board.getSquare(5,6);
        Square f3 = board.getSquare(6,3);
        Square f5 = board.getSquare(6,5);

        b3.setPiece(new Knight("black"));
        b5.setPiece(new Knight("black"));
        c2.setPiece(new Knight("black"));
        c6.setPiece(new Knight("black"));
        e2.setPiece(new Knight("black"));
        e6.setPiece(new Knight("black"));
        f3.setPiece(new Knight("black"));
        f5.setPiece(new Knight("black"));
    }

    @Test
    public void testConstructor() {
        Knight whiteKnight = new Knight("white");
        Knight blackKnight = new Knight("black");

        assertEquals("white", whiteKnight.getColor());
        assertNull(whiteKnight.getSquare());
        assertEquals("knight", whiteKnight.getName());

        assertEquals("black", blackKnight.getColor());
        assertNull(blackKnight.getSquare());
        assertEquals("knight", blackKnight.getName());
    }

    @Test
    public void testMovesFromCorners() {
        Square a1 = board.getSquare(1,1);
        Square h8 = board.getSquare(8,8);
        a1.setPiece(new Knight("white"));
        h8.setPiece(new Knight("black"));
        List<Square> a1PieceSquares = a1.getPiece().squaresCanMoveTo(board);
        List<Square> h8PieceSquares = h8.getPiece().squaresCanMoveTo(board);
        assertEquals(2, a1PieceSquares.size());
        assertEquals(2, h8PieceSquares.size());
    }

    @Test
    public void testPossibleCaptures() {
        d4.setPiece(new Knight("white"));
        List<Square> d4PieceSquares = d4.getPiece().squaresCanMoveTo(board);
        assertEquals(8, d4PieceSquares.size());
    }

    @Test
    public void testPieceBlocked() {
        d4.setPiece(new Knight("black"));
        List<Square> d4PieceSquares = d4.getPiece().squaresCanMoveTo(board);
        assertEquals(0, d4PieceSquares.size());
    }

    @Test
    public void testLegalMoves() {
        Knight knight = new Knight("white");
        board.getSquare(4,4).setPiece(knight);
        board.getSquare(4,5).setPiece(new Rook("black"));
        Square d3 = board.getSquare(4,3);
        d3.setPiece(new King("white"));
        assertEquals(0, knight.legalMoves(board).size());
        board.movePiece(d3, board.getSquare(2,2));
        assertEquals(8, knight.legalMoves(board).size());
    }
}
