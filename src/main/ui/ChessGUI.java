package ui;

import exceptions.NullBoardException;
import model.Board;
import model.ChessGame;
import model.Square;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

//Represents manager for which graphics to display
public class ChessGUI {
    private static final String JSON_STORE = "./data/chessGame.txt";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static ApplicationFrame currentFrame;
    private ChessGame chessGame;
    private ChessGame loadedChessGame;
    private Label userNotification;
    private boolean isPlaying;
    private Square fromSquare;
    private Square toSquare;

    private static ChessGUI chessGUI;

    //EFFECTS: creates chess application graphics
    private ChessGUI() {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        chessGame = new ChessGame();
        loadedChessGame = null;
        userNotification = null;
        fromSquare = null;
        toSquare = null;
        isPlaying = true;
    }

    private static void initialize() {
        currentFrame = new ApplicationFrame(new CommandsPanel());
    }

    public static ChessGUI getInstance() {
        if (chessGUI == null) {
            chessGUI = new ChessGUI();
            initialize();
        }
        return chessGUI;
    }

    //region DisplayingFrames
    //MODIFIES:this, userNotification
    //EFFECTS: displays current chess board with command panel and closes previous frame
    public void displayCurrentGame() {
        isPlaying = true;
        ApplicationFrame newFrame = new ApplicationFrame(new CommandsPanel());
        currentFrame.dispose();
        currentFrame = newFrame;
    }

    //MODIFIES:this, userNotification
    //EFFECTS: displays playable version of loaded game
    public void displayLoadedGame() {
        if (loadedChessGame != null) {
            chessGame = loadedChessGame;
            if (chessGame.getSavedSize() % 2 == 1) {
                chessGame.changePlayerTurn();
            }
            int lastIndex = chessGame.getSavedSize() - 1;
            Board newCurrentBoard = chessGame.getSavedMove(lastIndex);
            chessGame.setBoard(newCurrentBoard);
            updateBoard(newCurrentBoard);
            displayCurrentGame();
        } else {
            userNotification.setText("ERROR: User has not loaded a game yet");
        }
    }


    //MODIFIES:this, userNotification
    //EFFECTS: displays current chess board with review panel and closes previous frame
    public void displayPreviousMoves() {
        try {
            ApplicationFrame newFrame = new ApplicationFrame(new ReviewPanel(chessGame));
            isPlaying = false;
            currentFrame.dispose();
            currentFrame = newFrame;
        } catch (NullBoardException e) {
            userNotification.setText("ERROR: User has not played a move yet, no moves to view");
        }
    }

    //MODIFIES:this, userNotification
    //EFFECTS: displays loaded chess board with review panel
    public void displayLoadedPreviousMoves() {
        try {
            ApplicationFrame newFrame = new ApplicationFrame(new ReviewPanel(loadedChessGame));
            isPlaying = false;
            currentFrame.dispose();
            currentFrame = newFrame;
        } catch (NullPointerException e) {
            userNotification.setText("ERROR: User has not loaded a game yet");
        } catch (NullBoardException e) {
            userNotification.setText("ERROR: User has loaded a game that has not made any moves");
        }

    }
    //endregion

    //REQUIRES: fromSquare and toSquare != null
    //MODIFIES: this, gamePanel, userNotification
    //EFFECTS: tries to make a move on board, reset the stored move inputs, and display the following messages
    //          -invalid move
    //          -which player turn it is
    //          -how the game ended
    public void makeMove() {
        if (isPlaying && !chessGame.checkIsGameOver()) {
            if (!chessGame.movePiece(fromSquare, toSquare)) {
                userNotification.setText("Invalid Move: Player " + chessGame.getPlayerTurn() + " to move");
            } else {
                updateBoard(chessGame.getBoard());
                userNotification.setText("Player " + chessGame.getPlayerTurn() + " to move");
            }
            fromSquare = null;
            toSquare = null;

            String gameOverString = chessGame.getGameOverString();
            if (!gameOverString.equals(" ")) {
                userNotification.setText("Game ended by " + gameOverString);
            }
        } else {
            userNotification.setText("ERROR: Game ended");
        }
    }

    public void updateBoard(Board board) {
        currentFrame.getGamePanel().drawBoard(board);
    }

    //MODIFIES: this
    //EFFECTS: displays on label whether successfully loaded game or not
    public void loadGame() {
        try {
            loadedChessGame = jsonReader.read();
            userNotification.setText("Loaded Chess Game from: " + JSON_STORE);
        } catch (IOException e) {
            userNotification.setText("ERROR: Unable to read file from: " + JSON_STORE);
        }
    }

    //EFFECTS: displays on label whether successfully saved game or not
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(chessGame);
            jsonWriter.close();
            userNotification.setText("Saved Chess Game to:" + JSON_STORE);
        } catch (FileNotFoundException e) {
            userNotification.setText("ERROR: Unable to write to file: " + JSON_STORE);
        }
    }

    //REQUIRES:square != null
    //MODIFIES: this
    //EFFECTS:if fromSquare is null, set it to given square, else set toSquare to given square.
    public void setInputSquares(Square square) {
        if (fromSquare == null) {
            fromSquare = square;
        } else if (fromSquare != square) {
            toSquare = square;
            makeMove();
        }
    }

    //EFFECTS: sets current chess game to new chess game
    public void setChessGame() {
        chessGame = new ChessGame();
    }

    public ChessGame getChessGame() {
        return chessGame;
    }

    public void setUserNotification(Label userNotification) {
        this.userNotification = userNotification;
    }
}
