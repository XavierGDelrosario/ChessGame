package model;

import exceptions.NullBoardException;
import persistence.Writable;

import java.util.List;

public interface Piece extends Writable {
    List<Square> getSquaresCanMoveTo(Board board) throws NullBoardException;

    List<Square> getLegalMoves(Board board) throws NullBoardException;

    String getColor();

    String getName();

    Square getSquare();

    void setSquare(Square square);
}
