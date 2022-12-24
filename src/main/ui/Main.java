package ui;

import javax.swing.*;


public class Main extends JFrame {

    //EFFECTS: starts chess application
    public static void main(String[] args) {
        new ChessGUI();
        new ChessCLI();
    }
}
