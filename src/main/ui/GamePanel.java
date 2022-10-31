package ui;

import model.Board;
import model.ChessGame;
import model.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

//Represents panel that displays chess board
public class GamePanel extends JPanel implements ActionListener {
    private static final Color darkSquare = new Color(68, 68, 68);
    private static final Color lightSquare = new Color(226, 213, 186);
    private List<SquareUI> squares;

    //EFFECTS: constructs a graphical display of chess board
    public GamePanel() {
        squares = new ArrayList<>();
        setLayout(new GridLayout(8, 8, 0, 0));
        setBorder(BorderFactory.createBevelBorder(1,Color.black,Color.BLACK));
        setVisible(true);

        Board testBoard = new Board();
        testBoard.setupBoard();
        drawBoard(testBoard);
    }

    //EFFECTS: creates and adds each square to squares, display square in panel
    public void drawBoard(Board board) {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                SquareUI square = new SquareUI(board.getSquares().get(i + j * 8));
                squares.add(square);
                add(square);
                square.setActionCommand("selected");
                square.addActionListener(this);
                square.setOpaque(true);
                square.setBorderPainted(false);
                if ((i + j) % 2 == 0) {
                    square.setBackground(darkSquare);
                } else {
                    square.setBackground(lightSquare);
                }
            }
        }
    }

    @Override
    //EFFECTS: store value of square selected, if two squares have been selected try to make move
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("selected")) {
            //stub
        }
    }
}
