package model;

import exceptions.ColorException;
import exceptions.NullBoardException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.Objects;

//Represents a chess piece
public abstract class Piece implements Writable {
    protected final String color;
    protected String name;
    protected Square square;

    //EFFECTS: creates a piece with given name and color
    public Piece(String color, String name) throws ColorException {
        if (!color.equals("white") && !color.equals("black")) {
            throw new ColorException();
        }
        this.color = color;
        this.name = name;
        square = null;
    }

    //REQUIRES: this piece to exist on the board
    //EFFECTS: Returns squares that this piece can move to
    abstract List<Square> getSquaresCanMoveTo(Board board) throws NullBoardException;

    //REQUIRES: this piece to exist on the board
    //EFFECTS: Returns squares that this piece can legally move to without putting the same colored king in check
    abstract List<Square> getLegalMoves(Board board) throws NullBoardException;

    @Override
    //EFFECTS: returns this as JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("currentX", square.getXCoordinate());
        json.put("currentY", square.getYCoordinate());
        json.put("color", color);
        return json;
    }

    public String getColor() {return  color;}

    public String getName() {
        return name;
    }

    public Square getSquare(){
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return color.equals(piece.color) && name.equals(piece.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, name);
    }
}
