package model;

import exceptions.ColorException;

//Represents a piece that can move differently depending on if it has moved or not
public abstract class SpecialMovesPiece implements Piece {
    protected boolean hasMoved;
    protected final String color;
    protected String name;
    protected Square square;

    //EFFECTS: creates a piece with given color and has not moved,  throws ColorException if not white or black
    public SpecialMovesPiece(String color) throws ColorException {
        if (!color.equals("white") && !color.equals("black")) {
            throw new ColorException();
        }
        this.color = color;
        square = null;
        hasMoved = false;
    }

    protected boolean getHasMoved() {
        return hasMoved;
    }

    //MODIFIES: this
    //EFFECTS: sets hasMoved to true
    public void setHasMoved(boolean b) {
        hasMoved = b;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Square getSquare() {
        return square;
    }

    @Override
    public void setSquare(Square square) {
        this.square = square;
    }

}

