package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents panel that contains command buttons and a label
public class CommandsPanel extends JPanel implements ActionListener {
    private static final String TAB_SPACING = "          ";
    private final JPanel panel;
    private final ChessGUI gui;

    //EFFECTS: constructs panel containing command buttons and label
    public CommandsPanel(ChessGUI gui) {
        this.gui = gui;
        setLayout(new GridLayout(2,3));

        Label label = new Label(TAB_SPACING + "Player " + gui.getChessGame().getPlayerTurn() + "  to move");
        panel = new JPanel();
        panel.setLayout(new GridLayout(2,3));
        panel.setBackground(new Color(255, 239, 195));

        add(label);
        gui.setUserNotification(label);
        add(panel);

        createButton("View current game moves", "viewCurrent");
        createButton("Save game", "save");
        createButton("Start new game", "start");
        createButton("View loaded game moves", "viewLoaded");
        createButton("Load game", "load");

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: create a button in panel
    private void createButton(String buttonText, String actionCommand) {
        JButton btn = new JButton(buttonText);
        btn.setActionCommand(actionCommand);
        btn.addActionListener(this);
        panel.add(btn);
    }

    @Override
    //MODIFIES: this, gui
    //EFFECTS: performs the following actions depending on which button is pressed:
    //         -start a new chess game
    //         -save current game
    //         -load previously saved game
    //         -view moves played of current or loaded game
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("start")) {
            gui.setChessGame();
            gui.displayCurrentGame();
        } else if (e.getActionCommand().equals("viewCurrent")) {
            gui.displayPreviousMoves();
        } else if (e.getActionCommand().equals("save")) {
            gui.saveGame();
        }  else if (e.getActionCommand().equals("viewLoaded")) {
            gui.displayLoadedPreviousMoves();
        } else if (e.getActionCommand().equals("load")) {
            gui.loadGame();
        }
    }
}
