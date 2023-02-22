package persistence;


import model.Board;
import model.ChessGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.txt");
        try {
            ChessGame chessGame = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNewChessGame() {
        JsonReader reader = new JsonReader("./data/testChessGame.txt");
        try {
            ChessGame chessGame = reader.read();
            assertEquals(0, chessGame.getSavedSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderTwoMovesPlayed() {
        JsonReader reader = new JsonReader("./data/testTwoMovesPlayed.txt");
        try {
            ChessGame chessGame = reader.read();
            Board board1 = chessGame.getSavedMove(0);
            Board board2 = chessGame.getSavedMove(1);
            assertEquals(2, chessGame.getSavedSize());
            assertEquals("pawn", board1.getSquare(5,4).getPiece().getName());
            assertFalse(board1.getSquare(5,2).containsPiece());
            assertTrue(board1.getSquare(5,7).containsPiece());
            assertEquals("pawn", board2.getSquare(5,5).getPiece().getName());
            assertFalse(board2.getSquare(5,7).containsPiece());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
