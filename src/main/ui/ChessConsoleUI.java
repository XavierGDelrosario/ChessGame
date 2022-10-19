package ui;

import model.Board;
import model.ChessGame;
import model.Square;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//Represents the console user interface
public class ChessConsoleUI {
    private static final String JSON_STORE = "./data/chessGame.txt";
    private final Scanner scanner;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private ChessGame chessGame;
    private ChessGame loadedChessGame;

    //EFFECTS: starts a new chess game in console
    public ChessConsoleUI() throws FileNotFoundException {
        chessGame = new ChessGame();
        scanner = new Scanner(System.in);
        loadedChessGame = null;
        System.out.println("Enter c to show list of commands");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        runApplication();
    }

    //EFFECTS: performs corresponding methods depending on user input
    private void processCommand(String command) {
        if (command.equals("p")) {
            chessGame = new ChessGame();
            runGame();
        } else if (command.equals("r")) {
            runGame();
        } else if (command.equals("h")) {
            loadPreviousBoard(chessGame);
        } else if (command.equals("v")) {
            if (loadedChessGame != null) {
                loadPreviousBoard(loadedChessGame);
            } else {
                System.out.println("Have not loaded a chess game yet");
            }
        } else if (command.equals("s")) {
            saveChessGame();
        } else if (command.equals("l")) {
            loadChessGame();
        } else if (command.equals("q")) {
            runApplication();
        } else {
            System.err.println("Invalid command");
        }
    }

    //EFFECTS: runs application and asks for user input to perform methods
    private void runApplication() {
        while (true) {
            System.out.println("Enter command or c to view commands or q to quit");
            String command = scanner.nextLine();
            if (command.equals("q")) {
                break;
            } else if (command.equals("c")) {
                this.displayCommands();
            }
            processCommand(command);
        }
    }

    //MODIFIES: chessGame
    //EFFECTS: runs a playable two-player chess game, gets input from player to perform the following functions:
    //         -ending the application
    //         -loading previous moves
    //         -playing a move
    private void runGame() {
        while (true) {
            displayBoard(chessGame.getBoard());
            if (!chessGame.checkIsGameOver().equals(" ")) {
                System.out.println("Game ended by " + chessGame.checkIsGameOver());
                break;
            }
            System.out.println("Enter square coordinate with piece to move or command");
            String fromSquareInput = scanner.nextLine();
            while (fromSquareInput.length() == 1) {
                processCommand(fromSquareInput);
                System.out.println("Enter square coordinate with piece to move");
                fromSquareInput = scanner.nextLine();
            }
            Square fromSquare = getInputSquare(fromSquareInput);
            System.out.println("Enter square coordinate to move to");
            String toSquareInput = scanner.nextLine();
            Square toSquare = getInputSquare(toSquareInput);
            if (!chessGame.movePiece(fromSquare, toSquare)) {
                System.err.println("Invalid move");
            }
        }
    }

    //EFFECTS: displays board at given turn from user input. If user inputs "return" go back and run game
    private void loadPreviousBoard(ChessGame chessGame) {
        while (true) {
            System.out.println("Enter the index number of the move played or q");
            String input = scanner.nextLine();
            if (input.equals("q")) {
                runApplication();
            }
            int index = 0;
            try {
                index = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                return;
            }
            if (index != 0 && index - 1 < chessGame.getSavedBoards().size() && index - 1 >= 0) {
                displayBoard(chessGame.getSavedBoards().get(index - 1));
            } else {
                System.err.println("That number of moves has not been played yet");
                System.err.println(chessGame.getSavedBoards().size() + " moves have been played");
            }
        }
    }

    //EFFECTS: console prints out commands
    private void displayCommands() {
        System.out.println("Enter c to show list of commands");
        System.out.println("Enter p to start a new chess game");
        System.out.println("Enter r to return to current chess game");
        System.out.println("Enter h to view history of moves of current game");
        System.out.println("Enter v to view history of moves of loaded game");
        System.out.println("Enter s to save chess game");
        System.out.println("Enter l to load chess game");
        System.out.println("Enter q to quit application");
    }

    //REQUIRES: board != null
    //EFFECTS: displays chess board with pieces in that console, changes orientation depending on player turn
    private void displayBoard(Board board) {
        List<Square> squares = board.getSquares();
        if (chessGame.getPlayerTurn().equals("black")) {
            for (int i = 0; i < 8; i++) {
                System.out.println("|" + squares.get(i + 56).getIcon() + "|" + squares.get(i + 48).getIcon() + "|"
                        + squares.get(i + 40).getIcon() + "|" + squares.get(i + 32).getIcon() + "|"
                        + squares.get(i + 24).getIcon() + "|" + squares.get(i + 16).getIcon() + "|"
                        + squares.get(i + 8).getIcon() + "|" + squares.get(i).getIcon() + "|");
            }
        } else {
            for (int i = 63; i > 55; i--) {
                System.out.println("|" + squares.get(i - 56).getIcon() + "|" + squares.get(i - 48).getIcon() + "|"
                        + squares.get(i - 40).getIcon() + "|" + squares.get(i - 32).getIcon()
                        + "|" + squares.get(i - 24).getIcon() + "|" + squares.get(i - 16).getIcon()
                        + "|" + squares.get(i - 8).getIcon() + "|" + squares.get(i).getIcon() + "|");
            }
        }
    }

    //region GetSquareInput
    //EFFECTS: returns the square represented by input
    private Square getInputSquare(String input) {
        int inputSquareX = this.getInputSquareX(input);
        int inputSquareY = this.getInputSquareY(input);
        Square inputSquare = chessGame.getSquare(inputSquareX, inputSquareY);
        if (inputSquare == null) {
            System.err.println("Invalid square, please enter a valid square");
            String newInput = scanner.nextLine();
            inputSquare = this.getInputSquare(newInput);
        }
        return inputSquare;
    }

    //REQUIRES: input represents a square that exists on the board
    //EFFECTS: returns y coordinate of given square
    private int getInputSquareY(String input) {
        int squareY = 0;
        if (input.length() == 2) {
            if (input.endsWith("a") || input.endsWith("1")) {
                squareY = 1;
            } else if (input.endsWith("b") || input.endsWith("2")) {
                squareY = 2;
            } else if (input.endsWith("c") || input.endsWith("3")) {
                squareY = 3;
            } else if (input.endsWith("d") || input.endsWith("4")) {
                squareY = 4;
            } else if (input.endsWith("e") || input.endsWith("5")) {
                squareY = 5;
            } else if (input.endsWith("f") || input.endsWith("6")) {
                squareY = 6;
            } else if (input.endsWith("g") || input.endsWith("7")) {
                squareY = 7;
            } else if (input.endsWith("h") || input.endsWith("8")) {
                squareY = 8;
            }
        }
        return squareY;
    }

    //REQUIRES: input represents a square that exists on the board
    //EFFECTS: returns x coordinate of given square
    private int getInputSquareX(String input) {
        int squareX = 0;
        if (input.length() == 2) {
            if (input.startsWith("a") || input.startsWith("1")) {
                squareX = 1;
            } else if (input.startsWith("b") || input.startsWith("2")) {
                squareX = 2;
            } else if (input.startsWith("c") || input.startsWith("3")) {
                squareX = 3;
            } else if (input.startsWith("d") || input.startsWith("4")) {
                squareX = 4;
            } else if (input.startsWith("e") || input.startsWith("5")) {
                squareX = 5;
            } else if (input.startsWith("f") || input.startsWith("6")) {
                squareX = 6;
            } else if (input.startsWith("g") || input.startsWith("7")) {
                squareX = 7;
            } else if (input.startsWith("h") || input.startsWith("8")) {
                squareX = 8;
            }
        }
        return squareX;
    }
    //endregion

    // EFFECTS: saves the workroom to file
    private void saveChessGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(chessGame);
            jsonWriter.close();
            System.out.println("Saved Chess Game to:" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads chessGame from file
    private void loadChessGame() {
        try {
            loadedChessGame = jsonReader.read();
            System.out.println("Loaded Chess Game from: " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
