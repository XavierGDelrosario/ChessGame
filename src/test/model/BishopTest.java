package model;

import exceptions.ColorException;
import exceptions.NullBoardException;
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
        try {
            Bishop whiteBishop = new Bishop("white");
            Bishop blackBishop = new Bishop("black");
            assertEquals("white", whiteBishop.getColor());
            assertNull(whiteBishop.getSquare());
            assertEquals("bishop", whiteBishop.getName());

            assertEquals("black", blackBishop.getColor());
            assertNull(blackBishop.getSquare());
            assertEquals("bishop", blackBishop.getName());
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
    }

    //region Exceptions
    @Test
    public void testColorException(){
        try {
            new Bishop("Not A Color");
            fail("Expected to throw exception");
        } catch (ColorException e) {
            //pass
        }
    }

    @Test
    public void testNullBoardException(){
        try {
            Piece piece = new Bishop("white");
            piece.getLegalMoves(null);
            fail("Expected to throw exception");
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        } catch (NullBoardException e) {
            //pass
        }
    }
    //endregion

    //region Moves
    @Test
    public void testPossibleMovesFromCorners(){
        try {
            a1.setPiece(new Bishop("white"));
            h8.setPiece(new Bishop("white"));
            List<Square> a1PieceSquares = a1.getPiece().getSquaresCanMoveTo(board);
            List<Square> h8PieceSquares = h8.getPiece().getSquaresCanMoveTo(board);

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
        } catch (ColorException e) {
            fail("Did not expect to catch ColorException");
        } catch (NullBoardException e) {
            fail("Did not expect to catch NullBoardException");
        }
    }

    @Test
    public void testPossibleCaptures() {
        Square b2 = board.getSquare(2,2);
        Square c5 = board.getSquare(3,5);
        Square g1 = board.getSquare(7,1);
        try {
            b2.setPiece(new Bishop("white"));
            c5.setPiece(new Bishop("white"));
            d4.setPiece(new Bishop("black"));
            g1.setPiece(new Bishop("white"));
            h8.setPiece(new Bishop("white"));

            List<Square> d4PieceSquares = d4.getPiece().getSquaresCanMoveTo(board);

            assertEquals(10, d4PieceSquares.size());
            assertTrue(d4PieceSquares.contains(board.getSquare(3,3)));
            assertTrue(d4PieceSquares.contains(c5));
            assertTrue(d4PieceSquares.contains(g1));
            assertTrue(d4PieceSquares.contains(b2));
            assertFalse(d4PieceSquares.contains(board.getSquare(2,6)));
            assertFalse(d4PieceSquares.contains(a1));
        } catch (ColorException e) {
            fail("Did not expect to catch ColorException");
        } catch (NullBoardException e) {
            fail("Did not expect to catch NullBoardException");
        }
    }

    @Test
    public void testPieceBlocked() {
        Square c3 = board.getSquare(3,3);
        Square c5 = board.getSquare(3,5);
        Square e3 = board.getSquare(5,3);
        Square e5 = board.getSquare(5,5);
        try {
            c3.setPiece(new Bishop("black"));
            c5.setPiece(new Bishop("black"));
            d4.setPiece(new Bishop("black"));
            e3.setPiece(new Bishop("black"));
            e5.setPiece(new Bishop("black"));
            List<Square> d4PieceSquares = d4.getPiece().getSquaresCanMoveTo(board);
            assertEquals(0, d4PieceSquares.size());
        } catch (ColorException e) {
            fail("Did not expect to catch ColorException");
        } catch (NullBoardException e) {
            fail("Did not expect to catch NullBoardException");
        }
    }

    @Test
    public void testLegalMoves() {
        try {
            Bishop bishop = new Bishop("white");
            d4.setPiece(bishop);
            board.getSquare(4,5).setPiece(new Rook("black"));
            Square d3 = board.getSquare(4,3);
            d3.setPiece(new King("white"));
            assertEquals(0, bishop.getLegalMoves(board).size());
            board.movePiece(d3, board.getSquare(3,3));
            assertEquals(10, bishop.getLegalMoves(board).size());
        } catch (ColorException e) {
            fail("Did not expect to catch ColorException");
        } catch (NullBoardException e) {
            fail("Did not expect to catch NullBoardException");
        }
    }
    //endregion
}
