package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {
    private Board board;
    private  Square b2;
    private Square b7;
    private Square d2;
    private  Square d7;
    private Square f2;
    private Square f7;
    @BeforeEach
    void setup(){
        board = new Board();
        b2 = board.getSquare(2,2);
        b7 = board.getSquare(2,7);
        d2 = board.getSquare(4,2);
        d7 = board.getSquare(4, 7);
        f2 = board.getSquare(6,2);
        f7 = board.getSquare(6,7);
        b2.setPiece(new Pawn("white"));
        b7.setPiece(new Pawn("black"));
        d2.setPiece(new Pawn("white"));
        d7.setPiece(new Pawn("black"));
        f2.setPiece(new Pawn("white"));
        f7.setPiece(new Pawn("black"));
    }

    @Test
    public void testConstructor(){
        Pawn whitePawn = new Pawn("white");
        Pawn blackPawn = new Pawn("black");

        assertEquals("white", whitePawn.getColor());
        assertNull(whitePawn.getSquare());
        assertEquals("pawn", whitePawn.getName());
        assertFalse(whitePawn.getHasMoved());

        assertEquals("black", blackPawn.getColor());
        assertNull(blackPawn.getSquare());
        assertEquals("pawn", blackPawn.getName());
        assertFalse(whitePawn.getHasMoved());
    }

    @Test
    public void testPossibleCaptures() {
        Square b3 = board.getSquare(2,3);
        Square b6 = board.getSquare(2,6);
        Square d4 = board.getSquare(4,4);
        Square d5 = board.getSquare(4,5);
        b3.setPiece(new Pawn("black"));
        d4.setPiece(new Pawn("black"));
        b6.setPiece(new Pawn("white"));
        d5.setPiece(new Pawn("white"));
        List<Square> b2PieceSquares = b2.getPiece().squaresCanMoveTo(board);
        List<Square> d2PieceSquares = d2.getPiece().squaresCanMoveTo(board);
        List<Square> b7PieceSquares = b7.getPiece().squaresCanMoveTo(board);
        List<Square> d7PieceSquares = d7.getPiece().squaresCanMoveTo(board);
        List<Square> f2PieceSquares = f2.getPiece().squaresCanMoveTo(board);
        List<Square> f7PieceSquares = f7.getPiece().squaresCanMoveTo(board);

        assertEquals(0, b2PieceSquares.size());
        assertEquals(1, d2PieceSquares.size());
        assertEquals(0, b7PieceSquares.size());
        assertEquals(1, d7PieceSquares.size());
        assertEquals(2, f2PieceSquares.size());
        assertEquals(2, f7PieceSquares.size());
    }

    @Test
    public void testPieceBlockedByOpponent() {
        Square b3 = board.getSquare(2,3);
        Square b6 = board.getSquare(2,6);
        Square d4 = board.getSquare(4,4);
        Square d5 = board.getSquare(4,5);
        b3.setPiece(new Pawn("black"));
        d4.setPiece(new Pawn("black"));
        b6.setPiece(new Pawn("white"));
        d5.setPiece(new Pawn("white"));
        List<Square> b2PieceSquares = b2.getPiece().squaresCanMoveTo(board);
        List<Square> d2PieceSquares = d2.getPiece().squaresCanMoveTo(board);
        List<Square> b7PieceSquares =b7.getPiece().squaresCanMoveTo(board);
        List<Square> d7PieceSquares = d7.getPiece().squaresCanMoveTo(board);
        List<Square> f2PieceSquares = f2.getPiece().squaresCanMoveTo(board);
        List<Square> f7PieceSquares = f7.getPiece().squaresCanMoveTo(board);

        assertEquals(0, b2PieceSquares.size());
        assertEquals(1, d2PieceSquares.size());
        assertEquals(0, b7PieceSquares.size());
        assertEquals(1, d7PieceSquares.size());
        assertEquals(2, f2PieceSquares.size());
        assertEquals(2, f7PieceSquares.size());
    }

    @Test
    public void testBlockedBySameColor() {
        Square b3 = board.getSquare(2,3);
        Square b6 = board.getSquare(2,6);
        Square d4 = board.getSquare(4,4);
        Square d5 = board.getSquare(4,5);
        b3.setPiece(new Pawn("white"));
        d4.setPiece(new Pawn("white"));
        b6.setPiece(new Pawn("black"));
        d5.setPiece(new Pawn("black"));
        List<Square> b2PieceSquares = b2.getPiece().squaresCanMoveTo(board);
        List<Square> d2PieceSquares = d2.getPiece().squaresCanMoveTo(board);
        List<Square> b7PieceSquares =b7.getPiece().squaresCanMoveTo(board);
        List<Square> d7PieceSquares = d7.getPiece().squaresCanMoveTo(board);
        List<Square> f2PieceSquares = f2.getPiece().squaresCanMoveTo(board);
        List<Square> f7PieceSquares = f7.getPiece().squaresCanMoveTo(board);

        assertEquals(0, b2PieceSquares.size());
        assertEquals(1, d2PieceSquares.size());
        assertEquals(0, b7PieceSquares.size());
        assertEquals(1, d7PieceSquares.size());
        assertEquals(2, f2PieceSquares.size());
        assertEquals(2, f7PieceSquares.size());
    }

    @Test
    public void testChangeHasMoved() {
        Pawn pawn = (Pawn) b2.getPiece();
        assertFalse(pawn.getHasMoved());
        pawn.setHasMovedTrue();
        assertTrue(pawn.getHasMoved());
        assertEquals(1, pawn.squaresCanMoveTo(board).size());
    }

    @Test
    public void testEnPassant() {
        Pawn c4Pawn = new Pawn("black");
        board.getSquare(3,4).setPiece(c4Pawn);
        board.movePiece(b2,board.getSquare(2,4));
        c4Pawn.setCanEnPassantLeft(true);
        c4Pawn.setHasMovedTrue();
        assertEquals(2, c4Pawn.squaresCanMoveTo(board).size());
        c4Pawn.setCanEnPassantRight(true);
        assertEquals(3, c4Pawn.squaresCanMoveTo(board).size());
        c4Pawn.setCanEnPassantRight(false);
        c4Pawn.setCanEnPassantLeft(false);
        assertEquals(1, c4Pawn.squaresCanMoveTo(board).size());

    }

    @Test
    public void testLegalMoves() {
        Pawn pawn = new Pawn("white");
        board.getSquare(4,4).setPiece(pawn);
        board.getSquare(4,5).setPiece(new Rook("black"));
        board.getSquare(5,5).setPiece(new Rook("black"));
        Square d3 = board.getSquare(4,3);
        d3.setPiece(new King("white"));
        assertEquals(0, pawn.legalMoves(board).size());
        board.movePiece(d3, board.getSquare(3,3));
        assertEquals(1, pawn.legalMoves(board).size());
    }
}
