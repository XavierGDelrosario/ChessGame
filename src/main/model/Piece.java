package model;

import persistence.Writable;

import java.util.List;

public interface Piece extends Writable {
    List<Square> getSquaresCanMoveTo(Board board);

    List<Square> getLegalMoves(Board board);

    String getColor();

    String getName();

    Square getSquare();

    void setSquare(Square square);
}
