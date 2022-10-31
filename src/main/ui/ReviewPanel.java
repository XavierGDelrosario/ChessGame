package ui;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

//Represents panel that allows user to input commands to view previously played moves
public class ReviewPanel extends JPanel implements ActionListener {
    private List<Board> savedBoards;
    private JTextField field;
    private Label label;
    private Label userPrompt;
    private ChessGUI gui;


    //EFFECTS: constructs panel with user interface to allow user to enter a played move
    public ReviewPanel(ChessGUI gui, List<Board> savedBoards) {
        this.savedBoards = savedBoards;
        this.gui = gui;
        setLayout(new GridBagLayout());
        setBackground(new Color(255, 239, 195));

        userPrompt = new Label("moves have been played");
        label = new Label("Enter move number");
        field = new JTextField(5);

        JButton btnReturn = new JButton("Return to game");
        btnReturn.setActionCommand("return");
        btnReturn.addActionListener(this);

        JButton btnEnter = new JButton("Display move");
        btnEnter.setActionCommand("enter");
        btnEnter.addActionListener(this);

        addComponent(1,1, userPrompt);
        addComponent(1,2, label);
        addComponent(2,2, field);
        addComponent(3,2, btnEnter);
        addComponent(2,4, btnReturn);

        setVisible(true);
    }

    //EFFECTS: adds component to panel with given coordinates in gridbaglayout
    private void addComponent(int x, int y, Component component) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        add(component, constraints);
    }

    //EFFECTS: performs the following actions depending on which button is pressed:
    //         -returning to current game
    //         -displaying given move
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("return")) {
            gui.displayCurrentGame();
        } else if (e.getActionCommand().equals("enter")) {
            //stub
        }
    }
}
