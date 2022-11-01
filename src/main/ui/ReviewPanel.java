package ui;

import exceptions.NullBoardException;
import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

//Represents panel that allows user to input commands to view previously played moves
public class ReviewPanel extends JPanel implements ActionListener {
    private static final Color backgroundColor = new Color(255, 239, 195);
    private final List<Board> savedBoards;
    private final JTextField field;
    private final Label userNotification;
    private final ChessGUI gui;


    //EFFECTS: constructs panel with user interface to allow user to enter a played move
    public ReviewPanel(ChessGUI gui, List<Board> savedBoards) throws NullBoardException {
        this.savedBoards = savedBoards;
        if (savedBoards.size() == 0) {
            throw new NullBoardException();
        }
        this.gui = gui;
        setLayout(new GridBagLayout());
        setBackground(backgroundColor);

        userNotification = new Label(savedBoards.size() + " moves played");
        Label label = new Label("Enter move number");
        gui.setUserNotification(userNotification);
        field = new JTextField(5);

        JButton btnReturn = new JButton("Return to game");
        btnReturn.setActionCommand("return");
        btnReturn.addActionListener(this);
        JButton btnEnter = new JButton("Display move");
        btnEnter.setActionCommand("enter");
        btnEnter.addActionListener(this);

        addComponent(1,1, 8, userNotification);
        addComponent(1,2, 1, label);
        addComponent(2,2, 1, field);
        addComponent(3,2, 1, btnEnter);
        addComponent(2,4, 1, btnReturn);

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: adds component to panel with given coordinates in gridbaglayout
    private void addComponent(int x, int y, int width, Component component) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(component, constraints);
    }

    //MODIFIES: this, gamePanel
    //EFFECTS: performs the following actions depending on which button is pressed:
    //         -display board the user inputs
    //         -returning to current gam
    //         -if given a non integer display error
    //         -if given a move that has not been played, display error
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("return")) {
            gui.displayCurrentGame();
        } else if (e.getActionCommand().equals("enter")) {
            try {
                int index = Integer.parseInt(field.getText());
                gui.updateBoard(savedBoards.get(index - 1));
                userNotification.setText(savedBoards.size() + " moves played");
            } catch (NumberFormatException exception) {
                userNotification.setText("Invalid user input:" + savedBoards.size() + " moves played");
            } catch (IndexOutOfBoundsException exception) {
                userNotification.setText("Invalid number of move:" + savedBoards.size()
                        + " moves played");
            }

        }
    }
}
