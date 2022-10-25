package model;

import exceptions.ColorException;
import org.json.JSONObject;
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

        try {
            b3.setPiece(new Knight("black"));
            b5.setPiece(new Knight("black"));
            c2.setPiece(new Knight("black"));
            c6.setPiece(new Knight("black"));
            e2.setPiece(new Knight("black"));
            e6.setPiece(new Knight("black"));
            f3.setPiece(new Knight("black"));
            f5.setPiece(new Knight("black"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
    }

    @Test
    public void testConstructor() {
        try {
            Knight whiteKnight = new Knight("white");
            Knight blackKnight = new Knight("black");

            assertEquals("white", whiteKnight.getColor());
            assertNull(whiteKnight.getSquare());
            assertEquals("knight", whiteKnight.getName());

            assertEquals("black", blackKnight.getColor());
            assertNull(blackKnight.getSquare());
            assertEquals("knight", blackKnight.getName());
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
    }

    @Test
    public void testException(){
        try {
            new Knight("Not A Color");
            fail("Expected to throw exception");
        } catch (ColorException e) {
            //pass
        }
    }

    @Test
    public void testMovesFromCorners() {
        Square a1 = board.getSquare(1,1);
        Square h8 = board.getSquare(8,8);
        try {
            a1.setPiece(new Knight("white"));
            h8.setPiece(new Knight("black"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        List<Square> a1PieceSquares = a1.getPiece().getSquaresCanMoveTo(board);
        List<Square> h8PieceSquares = h8.getPiece().getSquaresCanMoveTo(board);
        assertEquals(2, a1PieceSquares.size());
        assertEquals(2, h8PieceSquares.size());
    }

    @Test
    public void testPossibleCaptures() {
        try {
            d4.setPiece(new Knight("white"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        List<Square> d4PieceSquares = d4.getPiece().getSquaresCanMoveTo(board);
        assertEquals(8, d4PieceSquares.size());
    }

    @Test
    public void testPieceBlocked() {
        try {
            d4.setPiece(new Knight("black"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        List<Square> d4PieceSquares = d4.getPiece().getSquaresCanMoveTo(board);
        assertEquals(0, d4PieceSquares.size());
    }

    @Test
    public void testLegalMoves() {
        try {
            Knight knight = new Knight("white");
            board.getSquare(4, 4).setPiece(knight);
            board.getSquare(4, 5).setPiece(new Rook("black"));
            Square d3 = board.getSquare(4, 3);
            d3.setPiece(new King("white"));
            assertEquals(0, knight.getLegalMoves(board).size());
            board.movePiece(d3, board.getSquare(2, 2));
            assertEquals(8, knight.getLegalMoves(board).size());
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
    }

    @Test
    public void testWritingKnightInfo() {
        try {
            Knight knight = new Knight("white");
            board.getSquare(1, 1).setPiece(knight);
            JSONObject jsonQueen = knight.toJson();
            assertEquals("knight", jsonQueen.getString("name"));
            assertEquals("white", jsonQueen.getString("color"));
            assertEquals(1, jsonQueen.getInt("currentX"));
            assertEquals(1, jsonQueen.getInt("currentY"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }

    }
}
