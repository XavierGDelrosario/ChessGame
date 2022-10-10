package model;

import java.util.List;

public interface Piece {
    List<Square> getSquaresCanMoveTo(Board board);

    List<Square> getLegalMoves(Board board);

    String getColor();

    String getName();

    Square getSquare();

    void setSquare(Square square);
}
