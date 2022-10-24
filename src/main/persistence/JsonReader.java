package persistence;

import exceptions.ColorException;
import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Note: some code based off of JSONReader from JSONSerializationDemo
// Represents a reader that reads chess game from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads chess game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ChessGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseChessGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses Chess game from JSON object and returns it
    private ChessGame parseChessGame(JSONObject jsonObject) {
        ChessGame chessGame = new ChessGame();
        addBoards(chessGame, jsonObject);
        return chessGame;
    }

    // MODIFIES: chessGame
    // EFFECTS: parses boards from JSON object and adds them to savedBoards
    private void addBoards(ChessGame chessGame, JSONObject jsonObject) {
        JSONArray boardsJsonArray = jsonObject.getJSONArray("boards");
        for (Object board : boardsJsonArray) {
            Board boardToSave = new Board();
            JSONObject boardJson = (JSONObject) board;
            JSONArray piecesJsonArray = boardJson.getJSONArray("pieces");
            for (Object piece : piecesJsonArray) {
                JSONObject jsonPiece = (JSONObject) piece;
                addPieces(boardToSave, jsonPiece);
            }
            chessGame.saveBoard(boardToSave);
        }
    }

    // MODIFIES: board
    // EFFECTS: parses pieces from JSON object and adds it to board
    private void addPieces(Board board, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int x = jsonObject.getInt("currentX");
        int y = jsonObject.getInt("currentY");
        String color = jsonObject.getString("color");
        Square square = board.getSquare(x, y);
        try {
            if (name.equals("king")) {
                square.setPiece(new King(color));
            } else if (name.equals("queen")) {
                square.setPiece(new Queen(color));
            } else if (name.equals("knight")) {
                square.setPiece(new Knight(color));
            } else if (name.equals("bishop")) {
                square.setPiece(new Bishop(color));
            } else if (name.equals("rook")) {
                square.setPiece(new Rook(color));
            }  else {
                square.setPiece(new Pawn(color));
            }
        } catch (ColorException e) {
            System.err.println("Tried to create a piece that is not white or black");
        }

    }
}
