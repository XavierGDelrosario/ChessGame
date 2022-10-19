package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

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
        JSONArray jsonArray = jsonObject.getJSONArray("boards");
        Board board = new Board();
        for (Object json : jsonArray) {
            JSONObject piece = (JSONObject) json;
            addPieces(board, piece);
        }
        chessGame.saveBoard(board);
    }

    // MODIFIES: board
    // EFFECTS: parses pieces from JSON object and adds it to board
    private void addPieces(Board board, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int x = Integer.parseInt(jsonObject.getString("currentX"));
        int y = Integer.parseInt(jsonObject.getString("currentX"));
        String color = jsonObject.getString("color");
        Square square = board.getSquare(x, y);
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
    }
}
