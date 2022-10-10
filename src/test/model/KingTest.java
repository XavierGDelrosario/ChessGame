package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KingTest {
    private Board board1;
    private Board board2;
    private Square d4;
    private Square a1;
    private Square c1;
    private Square e1;
    private Square g1;
    private Square h1;

    @BeforeEach
    void setup(){
        board1 = new Board();

        Square c3 = board1.getSquare(3,3);
        Square c4 = board1.getSquare(3,4);
        Square c5 = board1.getSquare(3,5);
        Square d3 = board1.getSquare(4,3);
        d4 = board1.getSquare(4,4);
        Square d5 = board1.getSquare(4,5);
        Square e3 = board1.getSquare(5,3);
        Square e4 = board1.getSquare(5,4);
        Square e5 = board1.getSquare(5,5);
        c3.setPiece(new King("black"));
        c4.setPiece(new King("black"));
        c5.setPiece(new King("black"));
        d3.setPiece(new King("black"));
        d5.setPiece(new King("black"));
        e3.setPiece(new King("black"));
        e4.setPiece(new King("black"));
        e5.setPiece(new King("black"));

        board2 = new Board();
        a1 = board2.getSquare(1,1);
        c1 = board2.getSquare(3,1);
        e1 = board2.getSquare(5,1);
        g1 = board2.getSquare(7,1);
        h1 = board2.getSquare(8,1);
        a1.setPiece(new Rook("white"));
        e1.setPiece(new King("white"));
        h1.setPiece(new Rook("white"));
    }

    @Test
    public void testConstructor() {
        King whiteKing = new King("white");
        King blackKing = new King("black");

        assertEquals("white", whiteKing.getColor());
        assertNull(whiteKing.getSquare());
        assertEquals("king", whiteKing.getName());
        assertFalse(whiteKing.getHasMoved());

        assertEquals("black", blackKing.getColor());
        assertNull(blackKing.getSquare());
        assertEquals("king", blackKing.getName());
        assertFalse(blackKing.getHasMoved());
    }

    @Test
    public void testMovesFromCorner() {
        Square a1 = board1.getSquare(1,1);
        Square h8 = board1.getSquare(8,8);
        a1.setPiece(new King("white"));
        h8.setPiece(new King("black"));
        List<Square> a1PieceSquares = a1.getPiece().getSquaresCanMoveTo(board1);
        List<Square> h8PieceSquares = h8.getPiece().getSquaresCanMoveTo(board1);
        assertEquals(3, a1PieceSquares.size());
        assertEquals(3, h8PieceSquares.size());
    }

    @Test
    public void testPossibleCaptures() {
        d4.setPiece(new King("white"));
        List<Square> d4PieceSquares = d4.getPiece().getSquaresCanMoveTo(board1);
        assertEquals(8, d4PieceSquares.size());
    }

    @Test
    public void testPieceBlocked() {
        d4.setPiece(new King("black"));
        List<Square> d4PieceSquares = d4.getPiece().getSquaresCanMoveTo(board1);
        assertEquals(0, d4PieceSquares.size());
    }

    @Test
    public void testChangeHasMoved() {
        d4.setPiece(new King("white"));
        King king = (King) d4.getPiece();
        assertFalse(king.getHasMoved());
        king.setHasMovedTrue();
        assertTrue(king.getHasMoved());
    }

    @Test
    public void testCantCastleFromCheck() {
        board2.getSquare(5,8).setPiece(new Rook("black"));
        List<Square> kingLegalMoves = e1.getPiece().getLegalMoves(board2);
        assertEquals(4, kingLegalMoves.size());
        assertFalse(kingLegalMoves.contains(board2.getSquare(7,1)));
        assertFalse(kingLegalMoves.contains(board2.getSquare(3,1)));
    }

    @Test
    public void testCantCastlePastSquareInCheck() {
        board2.getSquare(6,8).setPiece(new Rook("black"));
        List<Square> kingLegalMoves = e1.getPiece().getLegalMoves(board2);
        assertEquals(4, kingLegalMoves.size());
        assertFalse(kingLegalMoves.contains(g1));
        assertTrue(kingLegalMoves.contains(c1));

        board2.getSquare(2,8).setPiece(new Rook ("black"));
        kingLegalMoves = e1.getPiece().getLegalMoves(board2);
        assertTrue(kingLegalMoves.contains(c1));

        board2.getSquare(4,8).setPiece(new Rook ("black"));
        kingLegalMoves = e1.getPiece().getLegalMoves(board2);
        assertFalse(kingLegalMoves.contains(c1));
    }

    @Test
    public void testCantCastleIfRookMoved() {
        Rook rookA1 = (Rook) a1.getPiece();
        Rook rookH1 = (Rook) h1.getPiece();
        rookA1.setHasMovedTrue();
        rookH1.setHasMovedTrue();
        List<Square> kingLegalMoves = e1.getPiece().getLegalMoves(board2);
        assertFalse(kingLegalMoves.contains(g1));
        assertFalse(kingLegalMoves.contains(c1));
    }

    @Test
    public void testCantCastleIfKingMoved() {
        King king = (King) e1.getPiece();
        king.setHasMovedTrue();
        List<Square> kingLegalMoves = e1.getPiece().getLegalMoves(board2);
        assertFalse(kingLegalMoves.contains(g1));
        assertFalse(kingLegalMoves.contains(c1));
    }
    @Test
    public void testCantCastleIfBlocked() {
        Square f1 = board2.getSquare(6,1);
        f1.setPiece(new Bishop("white"));
        List<Square> kingLegalMoves = e1.getPiece().getLegalMoves(board2);
        assertFalse(kingLegalMoves.contains(g1));
        assertTrue(kingLegalMoves.contains(c1));

        f1.removePiece();
        g1.setPiece(new Knight("white"));
        board2.getSquare(2,1).setPiece(new Knight ("white"));
        kingLegalMoves = e1.getPiece().getLegalMoves(board2);
        assertFalse(kingLegalMoves.contains(g1));

        c1.setPiece(new Bishop("white"));
        g1.removePiece();
        kingLegalMoves = e1.getPiece().getLegalMoves(board2);
        assertFalse(kingLegalMoves.contains(c1));
        assertTrue(kingLegalMoves.contains(g1));

        board2.getSquare(2,1).setPiece(new Knight("white"));
        c1.removePiece();
        kingLegalMoves = e1.getPiece().getLegalMoves(board2);
        assertFalse(kingLegalMoves.contains(c1));
        assertTrue(kingLegalMoves.contains(g1));
    }
}
