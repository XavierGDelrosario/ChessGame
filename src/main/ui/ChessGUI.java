package ui;

import model.ChessGame;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

//Represents manager for which graphics to display
public class ChessGUI {
    private static final String JSON_STORE = "./data/chessGame.txt";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private JFrame currentFrame;
    private ChessGame chessGame;
    private ChessGame loadedChessGame;

    //EFFECTS: creates chess application graphics
    public ChessGUI() {
        chessGame = new ChessGame();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        currentFrame = new ApplicationFrame(new CommandsPanel(this));
        displayCurrentGame();
    }

    //EFFECTS: displays current chess board with command panel
    public void displayCurrentGame() {
        JFrame newFrame = new ApplicationFrame(new CommandsPanel(this));
        currentFrame.dispose();
        currentFrame = newFrame;
    }

    //EFFECTS: displays current chess board with review panel
    public void displayPreviousMoves() {
        JFrame newFrame = new ApplicationFrame(new ReviewPanel(this, chessGame.getSavedBoards()));
        currentFrame.dispose();
        currentFrame = newFrame;
    }

    //EFFECTS: displays loaded chess board with review panel
    public void displayLoadedPreviousMoves() {
        JFrame newFrame = new ApplicationFrame(new ReviewPanel(this, loadedChessGame.getSavedBoards()));
        currentFrame.dispose();
        currentFrame = newFrame;
    }

    //EFFECTS: displays on given label whether successfully loaded game or not
    public void loadGame(Label label) {
        try {
            loadedChessGame = jsonReader.read();
            label.setText("Loaded Chess Game from: " + JSON_STORE);
        } catch (IOException e) {
            label.setText("Unable to read file from: " + JSON_STORE);
        }
    }

    //EFFECTS: displays on given label whether successfully saved game or not
    public void saveGame(Label label) {
        try {
            jsonWriter.open();
            jsonWriter.write(chessGame);
            jsonWriter.close();
            label.setText("Saved Chess Game to:" + JSON_STORE);
        } catch (FileNotFoundException e) {
            label.setText("Unable to write to file: " + JSON_STORE);
        }
    }

    //EFFECTS: sets current chess game to new chess game
    public void setChessGame() {
        chessGame = new ChessGame();
    }
}
