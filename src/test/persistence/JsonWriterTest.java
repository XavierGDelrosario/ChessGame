package persistence;

import model.Board;
import model.ChessGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            ChessGame chessGame = new ChessGame();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNewChessGame() {
        try {
            ChessGame chessGame = new ChessGame();
            JsonWriter writer = new JsonWriter("./data/testChessGame.txt");
            writer.open();
            writer.write(chessGame);
            writer.close();

            JsonReader reader = new JsonReader("./data/testChessGame.txt");
            chessGame = reader.read();
            assertEquals(0, chessGame.getSavedBoards().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ChessGame chessGame = new ChessGame();
            chessGame.movePiece(chessGame.getSquare(5,2), chessGame.getSquare(5,4));
            chessGame.movePiece(chessGame.getSquare(5,7), chessGame.getSquare(5,5));
            JsonWriter writer = new JsonWriter("./data/testTwoMovesPlayed.txt");
            writer.open();
            writer.write(chessGame);
            writer.close();

            JsonReader reader = new JsonReader("./data/testTwoMovesPlayed.txt");
            chessGame = reader.read();
            Board board1 = chessGame.getSavedBoards().get(0);
            Board board2 = chessGame.getSavedBoards().get(1);
            assertEquals(2, chessGame.getSavedBoards().size());
            assertEquals("pawn", board1.getSquare(5,4).getPiece().getName());
            assertFalse(board1.getSquare(5,2).containsPiece());
            assertTrue(board1.getSquare(5,7).containsPiece());
            assertEquals("pawn", board2.getSquare(5,5).getPiece().getName());
            assertFalse(board2.getSquare(5,7).containsPiece());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
