package ui;

import model.Board;
import model.ChessGame;
import model.Square;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class Main extends JFrame {

    public static class ChessGameConsoleUI {
        private final ChessGame chessGame;
        private final Scanner scanner;

        //EFFECTS: starts a new chess game in console
        public ChessGameConsoleUI() {
            chessGame = new ChessGame();
            scanner = new Scanner(System.in);
            System.out.println("Additional Functions: enter quit to end application or load to go back and look at a "
                    + "previous position before starting to enter a move");
            runGame();
        }

        //MODIFIES: chessGame
        //EFFECTS: runs a playable two-player chess game, gets input from player to perform the following functions:
        //         -ending the application
        //         -loading previous moves
        //         -playing a move
        private void runGame() {
            while (true) {
                displayBoard(chessGame.getBoard());
                System.out.println("Enter square coordinate with the piece to move");
                String fromSquareInput = scanner.nextLine();
                if (fromSquareInput.equals("quit")) {
                    break;
                } else if (fromSquareInput.equals("load")) {
                    fromSquareInput =  loadPreviousBoard();
                }
                Square fromSquare = getInputSquare(fromSquareInput);
                System.out.println("Enter square coordinate to move to");
                String toSquareInput = scanner.nextLine();
                Square toSquare = getInputSquare(toSquareInput);
                if (!chessGame.movePiece(fromSquare, toSquare)) {
                    System.out.println("Invalid move");
                }
            }
        }

        //EFFECTS: displays board at given turn from user input. If user inputs "return" go back and run game
        public String loadPreviousBoard() {
            System.out.println("Enter return to go back to the game");
            while (true) {
                System.out.println("Enter the number of the move played");
                String input = scanner.nextLine();
                if (input.equals("return")) {
                    displayBoard(chessGame.getBoard());
                    System.out.println("Enter square coordinate with the piece to move");
                    return scanner.nextLine();
                }
                int index = Integer.parseInt(input);
                if (index != 0 && index - 1 < chessGame.getSavedBoards().size() && index - 1 >= 0) {
                    displayBoard(chessGame.getSavedBoards().get(index - 1));
                } else {
                    System.out.println("That number of moves has not been played yet");
                }
            }
        }

        //EFFECTS: returns the square represented by input
        public Square getInputSquare(String input) {
            int inputSquareX = this.getInputSquareX(input);
            int inputSquareY = this.getInputSquareY(input);
            Square inputSquare = chessGame.getSquare(inputSquareX, inputSquareY);
            if (inputSquare == null) {
                System.out.println("Invalid square, please enter a valid square");
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

        //REQUIRES: board != null
        //EFFECTS: displays chess board with pieces, changes orientation depending on player turn
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
    }


    public static void main(String[] args) {
        new ChessGameConsoleUI();
    }
}
