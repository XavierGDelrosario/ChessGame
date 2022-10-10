package model;

import java.util.List;

public interface Piece {
    List<Square> squaresCanMoveTo(Board board);

    List<Square> legalMoves(Board board);

    String getColor();

    String getName();

    Square getSquare();

    void setSquare(Square square);
}
