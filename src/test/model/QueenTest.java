package model;
import exceptions.ColorException;
import exceptions.NullBoardException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {
    private Board board;
    private Square d4;

    @BeforeEach
    void setup(){
        board = new Board();
        d4 = board.getSquare(4,4);
    }

    @Test
    public void testConstructor() {
        try {
            Queen whiteQueen = new Queen("white");
            Queen blackQueen = new Queen("black");

            assertEquals("white", whiteQueen.getColor());
            assertNull(whiteQueen.getSquare());
            assertEquals("queen", whiteQueen.getName());

            assertEquals("black", blackQueen.getColor());
            assertNull(blackQueen.getSquare());
            assertEquals("queen", blackQueen.getName());
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
    }

    //region Exceptions
    @Test
    public void testColorException(){
        try {
            new Queen("Not A Color");
            fail("Expected to throw exception");
        } catch (ColorException e) {
            //pass
        }
    }

    @Test
    public void testNullBoardException(){
        try {
            Piece piece = new Queen("white");
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
    public void testMovesFromCorner() {
        Square a1 = board.getSquare(1,1);
        Square h8 = board.getSquare(8,8);
        try {
            a1.setPiece(new Queen("white"));
            h8.setPiece(new Queen("white"));

            List<Square> a1PieceSquares = a1.getPiece().getSquaresCanMoveTo(board);
            List<Square> h8PieceSquares = h8.getPiece().getSquaresCanMoveTo(board);
            assertEquals(20, a1PieceSquares.size());
            assertEquals(20, h8PieceSquares.size());
        } catch (ColorException e) {
        fail("Did not expect to catch ColorException");
        } catch (NullBoardException e) {
            fail("Did not expect to catch NullBoardException");
        }
    }

    @Test
    public void testPossibleCapturesDiagonally() {
        Square b2 = board.getSquare(2,2);
        Square c5 = board.getSquare(3,5);
        Square g1 = board.getSquare(7,1);
        Square h8 = board.getSquare(8,8);
        try {
            b2.setPiece(new Queen("white"));
            c5.setPiece(new Queen("white"));
            d4.setPiece(new Queen("black"));
            g1.setPiece(new Queen("white"));
            h8.setPiece(new Queen("white"));

            List<Square> d4PieceSquares = d4.getPiece().getSquaresCanMoveTo(board);

            assertEquals(24, d4PieceSquares.size());
            assertTrue(d4PieceSquares.contains(board.getSquare(3,3)));
            assertTrue(d4PieceSquares.contains(c5));
            assertTrue(d4PieceSquares.contains(g1));
            assertTrue(d4PieceSquares.contains(b2));
            assertFalse(d4PieceSquares.contains(board.getSquare(2,6)));
            assertFalse(d4PieceSquares.contains(board.getSquare(1,1)));
        } catch (ColorException e) {
            fail("Did not expect to catch ColorException");
        } catch (NullBoardException e) {
            fail("Did not expect to catch NullBoardException");
        }
    }

    @Test
    public void testCapturesHorizontallyAndVertically() {
        Square b4 = board.getSquare(2,4);
        Square d3 = board.getSquare(4,3);
        Square d8 = board.getSquare(4,8);
        Square g4 = board.getSquare(7,4);
        try {
            b4.setPiece(new Queen("black"));
            d3.setPiece(new Queen("black"));
            d8.setPiece(new Queen("black"));
            g4.setPiece(new Queen("black"));
            d4.setPiece(new Queen("white"));

            List<Square> d4PieceSquares = d4.getPiece().getSquaresCanMoveTo(board);
            assertEquals(23, d4PieceSquares.size());
        } catch (ColorException e) {
            fail("Did not expect to catch ColorException");
        } catch (NullBoardException e) {
            fail("Did not expect to catch NullBoardException");
        }
    }

    @Test
    public void testPieceBlocked() {
        Square c3 = board.getSquare(3,3);
        Square c4 = board.getSquare(3,4);
        Square c5 = board.getSquare(3,5);
        Square d3 = board.getSquare(4,3);
        Square d5 = board.getSquare(4,5);
        Square e3 = board.getSquare(5,3);
        Square e4 = board.getSquare(5,4);
        Square e5 = board.getSquare(5,5);

        try {
            c3.setPiece(new Queen("black"));
            c4.setPiece(new Queen("black"));
            c5.setPiece(new Queen("black"));
            d3.setPiece(new Queen("black"));
            d5.setPiece(new Queen("black"));
            e3.setPiece(new Queen("black"));
            e4.setPiece(new Queen("black"));
            e5.setPiece(new Queen("black"));
            d4.setPiece(new Queen("black"));

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
            Queen queen = new Queen("white");
            d4.setPiece(queen);
            board.getSquare(4, 5).setPiece(new Rook("black"));
            Square d3 = board.getSquare(4, 3);
            d3.setPiece(new King("white"));
            assertEquals(1, queen.getLegalMoves(board).size());
            board.movePiece(d3, board.getSquare(3, 3));
            assertEquals(21, queen.getLegalMoves(board).size());
        } catch (ColorException e) {
                fail("Did not expect to catch ColorException");
        } catch (NullBoardException e) {
            fail("Did not expect to catch NullBoardException");
        }
    }
    //endregion

    @Test
    public void testWritingQueenInfo() {
        try {
            Queen queen = new Queen("white");
            board.getSquare(1, 1).setPiece(queen);
            JSONObject jsonQueen = queen.toJson();
            assertEquals("queen", jsonQueen.getString("name"));
            assertEquals("white", jsonQueen.getString("color"));
            assertEquals(1, jsonQueen.getInt("currentX"));
            assertEquals(1, jsonQueen.getInt("currentY"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }

    }
}
