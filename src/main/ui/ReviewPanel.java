package ui;

import exceptions.NullBoardException;
import model.ChessGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents panel that allows user to input commands to view previously played moves
public class ReviewPanel extends JPanel implements ActionListener {
    private static final Color backgroundColor = new Color(255, 239, 195);
    private final JTextField field;
    private final Label userNotification;
    private final ChessGame chessGame;


    //EFFECTS: constructs panel with user interface to allow user to enter a played move
    public ReviewPanel(ChessGame chessGame) throws NullBoardException {
        this.chessGame = chessGame;
        if (chessGame.getSavedSize() == 0) {
            throw new NullBoardException();
        }
        setLayout(new GridBagLayout());
        setBackground(backgroundColor);

        userNotification = new Label(chessGame.getSavedSize() + " moves played");
        Label label = new Label("Enter move number");
        ChessGUI.getInstance().setUserNotification(userNotification);
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
            ChessGUI.getInstance().displayCurrentGame();
        } else if (e.getActionCommand().equals("enter")) {
            try {
                int index = Integer.parseInt(field.getText());
                ChessGUI.getInstance().updateBoard(chessGame.getSavedMove(index - 1));
                userNotification.setText(chessGame.getSavedSize() + " moves played");
            } catch (NumberFormatException exception) {
                userNotification.setText("Invalid user input:" + chessGame.getSavedSize() + " moves played");
            } catch (IndexOutOfBoundsException exception) {
                userNotification.setText("Invalid number of move:" + chessGame.getSavedSize()
                        + " moves played");
            }

        }
    }
}
