package ui;

import model.Square;

import javax.swing.*;

//Represents square represented graphically
public class SquareUI extends JButton {
    private Square square;

    //EFFECTS: creates a square with icon of the current piece or nothing
    public SquareUI(Square square) {
        new JButton();
        this.square = square;
        setText(square.getIcon());
    }

    public Square getSquare() {
        return square;
    }

}
