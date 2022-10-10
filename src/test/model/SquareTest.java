package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
}
