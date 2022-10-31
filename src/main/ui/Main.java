package ui;

import javax.swing.*;

public class Main extends JFrame {

    //EFFECTS: starts chess application
    public static void main(String[] args) {
        new ChessGUI();
        /*
        try {
            new ChessConsoleUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
         */
    }
}
