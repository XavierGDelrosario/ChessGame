package model;

import exceptions.ColorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SquareTest {
    private Square a1;

    @BeforeEach
    void setup() {
        a1 = new Square(1, 1);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, a1.getXCoordinate());
        assertEquals(1, a1.getYCoordinate());
        assertNull(a1.getPiece());
    }

    @Test
    public void testSetPiece() {
        assertNull(a1.getPiece());
        try {
            Bishop bishop = new Bishop("black");
            a1.setPiece(bishop);
            assertEquals(bishop, a1.getPiece());
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
    }

    @Test
    public void testSetPieces(){
        try {
            Rook rook = new Rook("black");
            Queen queen = new Queen("white");

            a1.setPiece(rook);
            assertEquals(rook, a1.getPiece());
            assertEquals(a1, rook.getSquare());

            a1.setPiece(queen);
            assertEquals(queen, a1.getPiece());
            assertEquals(a1, queen.getSquare());
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
    }

    @Test
    public void testRemovePiece() {
        try {
            Piece rook = new Rook("black");
            a1.setPiece(rook);
            assertEquals(rook, a1.getPiece());
            a1.removePiece();
            assertNull(a1.getPiece());
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
    }

    @Test
    public void testIcons() {
        Board board = new Board();
        board.setupBoard();
        assertTrue(board.getSquare(1,1).getPieceInitial().equals("r"));
        assertTrue(board.getSquare(2,1).getPieceInitial().equals("n"));
        assertTrue(board.getSquare(3,1).getPieceInitial().equals("b"));
        assertTrue(board.getSquare(4,1).getPieceInitial().equals("q"));
        assertTrue(board.getSquare(5,1).getPieceInitial().equals("k"));
        assertTrue(board.getSquare(1,2).getPieceInitial().equals("p"));
        assertTrue(board.getSquare(1,8).getPieceInitial().equals("R"));
        assertTrue(board.getSquare(2,8).getPieceInitial().equals("N"));
        assertTrue(board.getSquare(3,8).getPieceInitial().equals("B"));
        assertTrue(board.getSquare(4,8).getPieceInitial().equals("Q"));
        assertTrue(board.getSquare(5,8).getPieceInitial().equals("K"));
        assertTrue(board.getSquare(1,7).getPieceInitial().equals("P"));
        assertTrue(board.getSquare(1,5).getPieceInitial().equals(" "));
    }

    @Test
    public void testDoesNotHavePiece() {
        assertFalse(a1.containsPiece());
    }

    @Test
    public void testHasPiece() {
        try {
            a1.setPiece(new Queen("white"));
        } catch (ColorException e) {
            fail("Did not expect to catch exception");
        }
        assertTrue(a1.containsPiece());
    }

}
