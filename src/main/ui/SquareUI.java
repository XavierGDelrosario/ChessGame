package ui;

import model.Piece;
import model.Square;

import javax.swing.*;

//Represents square represented graphically
public class SquareUI extends JButton {
    private final Square square;

    //EFFECTS: creates a graphical representation of square
    public SquareUI(Square square) {
        new JButton();
        this.square = square;
        setPieceIcon();
    }

    //NOTE: images gathered from https://commons.wikimedia.org/wiki/Category:PNG_chess_pieces/Standard_transparent
    //MODIFIES: this
    //EFFECTS: displays image of the piece on this
    public void setPieceIcon() {
        Piece piece = square.getPiece();
        if (piece != null) {
            String name = piece.getName();
            String color = piece.getColor();
            if (name.equals("king")) {
                setIcon(new ImageIcon("./data/images/" + color + "King.png"));
            } else if (name.equals("queen")) {
                setIcon(new ImageIcon("./data/images/" + color + "Queen.png"));
            } else if (name.equals("knight")) {
                setIcon(new ImageIcon("./data/images/" + color + "Knight.png"));
            } else if (name.equals("bishop")) {
                setIcon(new ImageIcon("./data/images/" + color + "Bishop.png"));
            } else if (name.equals("rook")) {
                setIcon(new ImageIcon("./data/images/" + color + "Rook.png"));
            } else if (name.equals("pawn")) {
                setIcon(new ImageIcon("./data/images/" + color + "Pawn.png"));
            }
        }
    }

    public Square getSquare() {
        return square;
    }
}
