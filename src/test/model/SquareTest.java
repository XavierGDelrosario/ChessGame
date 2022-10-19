package model;

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
        Bishop bishop = new Bishop("black");

        assertNull(a1.getPiece());
        a1.setPiece(bishop);
        assertEquals(bishop, a1.getPiece());
    }

    @Test
    public void testSetPieces(){
        Rook rook = new Rook("black");
        Queen queen = new Queen("white");

        a1.setPiece(rook);
        assertEquals(rook, a1.getPiece());
        assertEquals(a1, rook.getSquare());

        a1.setPiece(queen);
        assertEquals(queen, a1.getPiece());
        assertEquals(a1, queen.getSquare());
    }

    @Test
    public void testRemovePiece() {
        Piece rook = new Rook("black");
        a1.setPiece(rook);
        assertEquals(rook, a1.getPiece());
        a1.removePiece();
        assertNull(a1.getPiece());
    }

    @Test
    public void testIcons() {
        Board board = new Board();
        board.setupBoard();
        assertTrue(board.getSquare(1,1).getIcon().equals("r"));
        assertTrue(board.getSquare(2,1).getIcon().equals("n"));
        assertTrue(board.getSquare(3,1).getIcon().equals("b"));
        assertTrue(board.getSquare(4,1).getIcon().equals("q"));
        assertTrue(board.getSquare(5,1).getIcon().equals("k"));
        assertTrue(board.getSquare(1,2).getIcon().equals("p"));
        assertTrue(board.getSquare(1,8).getIcon().equals("R"));
        assertTrue(board.getSquare(2,8).getIcon().equals("N"));
        assertTrue(board.getSquare(3,8).getIcon().equals("B"));
        assertTrue(board.getSquare(4,8).getIcon().equals("Q"));
        assertTrue(board.getSquare(5,8).getIcon().equals("K"));
        assertTrue(board.getSquare(1,7).getIcon().equals("P"));
        assertTrue(board.getSquare(1,5).getIcon().equals(" "));
    }

    @Test
    public void testDoesNotHavePiece() {
        assertFalse(a1.containsPiece());
    }

    @Test
    public void testHasPiece() {
        a1.setPiece(new Queen("white"));
        assertTrue(a1.containsPiece());
    }

}
