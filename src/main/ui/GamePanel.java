package ui;

import model.Board;
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
    private final ChessGUI gui;

    //EFFECTS: constructs a graphical display of chess board
    public GamePanel(ChessGUI gui) {
        this.gui = gui;
        squares = new ArrayList<>();
        setLayout(new GridLayout(8, 8, 0, 0));
        setBorder(BorderFactory.createBevelBorder(1,Color.black,Color.BLACK));
        setVisible(true);

        drawBoard(gui.getChessGame().getBoard());
    }

    //EFFECTS: creates and adds each squareUI from given board to squares, display square in panel
    public void drawBoard(Board board) {
        for (SquareUI squareUI :squares) {
            remove(squareUI);
        }
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
    //EFFECTS: gets square associated with pressed button and passes it to gui
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("selected")) {
            SquareUI squareUI = (SquareUI) e.getSource();
            Square square = squareUI.getSquare();
            gui.setInputSquares(square);
        }
    }
}
