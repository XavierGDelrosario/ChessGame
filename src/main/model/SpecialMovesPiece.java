package model;

//Represents a piece that can move differently depending on if it has moved or not
public abstract class SpecialMovesPiece implements Piece {
    protected boolean hasMoved;
    protected final String color;
    protected String name;
    protected Square square;

    //REQUIRES: color = "white" or "black"
    public SpecialMovesPiece(String color) {
        this.color = color;
        name = "";
        square = null;
        hasMoved = false;
    }

    protected boolean getHasMoved() {
        return hasMoved;
    }

    //MODIFIES: this
    //EFFECTS: sets hasMoved to true
    protected void setHasMovedTrue() {
        hasMoved = true;
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

