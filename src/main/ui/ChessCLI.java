package ui;

import exceptions.InputException;
import model.Board;
import model.ChessGame;
import model.Square;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


// Note: code loading and saving mostly based off of JSONSerializationDemo
//Represents the console user interface
public class ChessCLI {
    private static final String JSON_STORE = "./data/chessGame.txt";
    private final Scanner scanner;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private ChessGame chessGame;
    private ChessGame loadedChessGame;

    //EFFECTS: starts a new chess game in console
    public ChessCLI() {
        chessGame = new ChessGame();
        scanner = new Scanner(System.in);
        loadedChessGame = null;
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        runApplication();
    }

    //EFFECTS: performs corresponding methods depending on user input, prints error if user gives a command that
    // does not exist
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
                System.err.println("Have not loaded a chess game yet");
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

    //EFFECTS: runs application and asks for user input to perform any of the following functions:
    //      - save and load game
    //      - start a new game
    //      - view played moves
    //      - quit application
    //      - showing commands
    private void runApplication() {
        while (true) {
            System.out.println("Enter command or c to view commands");
            String command = scanner.nextLine();
            if (command.equals("q")) {
                break;
            } else if (command.equals("c")) {
                this.displayCommands();
            } else {
                processCommand(command);
            }
        }
    }

    //MODIFIES: chessGame
    //EFFECTS: runs a playable two-player chess game, gets input from player to perform command or play a move,
    //         -prints an error if move is not legal
    private void runGame() {
        while (true) {
            displayBoard(chessGame.getBoard());
            if (!chessGame.getGameOverString().equals(" ")) {
                System.out.println("Game ended by " + chessGame.getGameOverString());
                break;
            }
            System.out.println("Enter square coordinate with piece to move or command");
            String fromSquareInput = scanner.nextLine();
            while (fromSquareInput.length() == 1) {
                processCommand(fromSquareInput);
                System.out.println("Enter square coordinate with piece to move or command");
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

    //EFFECTS: displays board at given turn from user input,
    //         if user inputs q ask for a command,
    //         if user gives input that is not a number in range or not q print an error, then ask for a command again
    private void loadPreviousBoard(ChessGame chessGame) {
        while (true) {
            System.out.println("Enter the number of the move played or q");
            System.out.println(chessGame.getSavedSize() + " moves have been played");
            String input = scanner.nextLine();
            if (input.equals("q")) {
                runApplication();
            }
            int index = 0;
            try {
                index = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println("Input not an integer or q");
                loadPreviousBoard(chessGame);
            }
            if (index != 0 && index - 1 < chessGame.getSavedSize() && index - 1 >= 0) {
                displayBoard(chessGame.getSavedMove(index - 1));
            } else {
                System.err.println("That number of moves has not been played yet, " + chessGame.getSavedSize()
                        + " moves have been played");
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
    //EFFECTS: displays chess board with pieces in that console, player who is playing has their piece starting
    // locations displayed at the bottom of the board
    private void displayBoard(Board board) {
        List<Square> squares = board.getSquares();
        if (chessGame.getPlayerTurn().equals("black")) {
            for (int i = 0; i < 8; i++) {
                System.out.println("|" + squares.get(i + 56).getPieceInitial() + "|"
                        + squares.get(i + 48).getPieceInitial() + "|"
                        + squares.get(i + 40).getPieceInitial() + "|" + squares.get(i + 32).getPieceInitial() + "|"
                        + squares.get(i + 24).getPieceInitial() + "|" + squares.get(i + 16).getPieceInitial() + "|"
                        + squares.get(i + 8).getPieceInitial() + "|" + squares.get(i).getPieceInitial() + "|");
            }
        } else {
            for (int i = 63; i > 55; i--) {
                System.out.println("|" + squares.get(i - 56).getPieceInitial() + "|"
                        + squares.get(i - 48).getPieceInitial() + "|"
                        + squares.get(i - 40).getPieceInitial() + "|" + squares.get(i - 32).getPieceInitial()
                        + "|" + squares.get(i - 24).getPieceInitial() + "|" + squares.get(i - 16).getPieceInitial()
                        + "|" + squares.get(i - 8).getPieceInitial() + "|" + squares.get(i).getPieceInitial() + "|");
            }
        }
    }

    //region GetSquareInput
    //EFFECTS: returns the square represented by input, throws InputException if given a square that does not exist then
    // ask for new input
    private Square getInputSquare(String input) {
        Square inputSquare = null;
        try {
            int inputSquareX = this.getInputSquareX(input);
            int inputSquareY = this.getInputSquareY(input);
            inputSquare = chessGame.getSquare(inputSquareX, inputSquareY);
        } catch (InputException e) {
            System.err.println("Invalid square, please enter a valid square");
            String newInput = scanner.nextLine();
            getInputSquare(newInput);
        }
        return inputSquare;
    }

    //REQUIRES: input represents a square that exists on the board
    //EFFECTS: returns x coordinate of given square
    private int getInputSquareX(String input) throws InputException {
        int squareX = 0;
        if (input.length() == 2) {
            if (input.startsWith("a") || input.startsWith("1")) {
                return  1;
            } else if (input.startsWith("b") || input.startsWith("2")) {
                return  2;
            } else if (input.startsWith("c") || input.startsWith("3")) {
                return  3;
            } else if (input.startsWith("d") || input.startsWith("4")) {
                return  4;
            } else if (input.startsWith("e") || input.startsWith("5")) {
                return  5;
            } else if (input.startsWith("f") || input.startsWith("6")) {
                return  6;
            } else if (input.startsWith("g") || input.startsWith("7")) {
                return  7;
            } else if (input.startsWith("h") || input.startsWith("8")) {
                return  8;
            }
        }
        throw new InputException();
    }

    //REQUIRES: input represents a square that exists on the board
    //EFFECTS: returns y coordinate of given square
    private int getInputSquareY(String input) throws InputException {
        if (input.length() == 2) {
            if (input.endsWith("a") || input.endsWith("1")) {
                return 1;
            } else if (input.endsWith("b") || input.endsWith("2")) {
                return  2;
            } else if (input.endsWith("c") || input.endsWith("3")) {
                return  3;
            } else if (input.endsWith("d") || input.endsWith("4")) {
                return  4;
            } else if (input.endsWith("e") || input.endsWith("5")) {
                return  5;
            } else if (input.endsWith("f") || input.endsWith("6")) {
                return  6;
            } else if (input.endsWith("g") || input.endsWith("7")) {
                return  7;
            } else if (input.endsWith("h") || input.endsWith("8")) {
                return  8;
            }
        }
        throw new InputException();
    }

    //endregion

    // EFFECTS: saves the chessGame to file, throws FileNotFoundException if unable to find file to write to
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
    // EFFECTS: loads chessGame from file, throws IOException if unable to read file
    private void loadChessGame() {
        try {
            loadedChessGame = jsonReader.read();
            System.out.println("Loaded Chess Game from: " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
