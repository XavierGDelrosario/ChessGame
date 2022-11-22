package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;

//Represents frame that contains panels for chess application
public class ApplicationFrame extends JFrame {
    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 1200;
    private GamePanel gamePanel;

    //EFFECTS:constructs frame with game panel and given control panel
    public ApplicationFrame(JPanel inputPanel, ChessGUI gui) {
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(110, 87, 7));

        GridBagConstraints constraints = new GridBagConstraints();
        getContentPane().setLayout(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        gamePanel = new GamePanel(gui);
        add(gamePanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0.0;
        add(inputPanel, constraints);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        setCloseFunctions();
    }

    //Note: borrowed from @1566 piazza
    //EFFECTS: Sets the method to run when application is closed
    private void setCloseFunctions() {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                displayEventLog();
                System.exit(0);
            }
        };
        this.addWindowListener(exitListener);
    }

    public void displayEventLog() {
        Iterator<Event> iterator = EventLog.getInstance().iterator();
        while (iterator.hasNext()) {
            Event event = iterator.next();
            System.out.println(event.toString());
        }
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
