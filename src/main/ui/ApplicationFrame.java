package ui;

import javax.swing.*;
import java.awt.*;

//Represents frame that contains panels for chess application
public class ApplicationFrame extends JFrame {
    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 1200;
    private GamePanel gamePanel;

    //EFFECTS:constructs frame with game panel and given control panel
    public ApplicationFrame(JPanel inputPanel) {
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(110, 87, 7));

        GridBagConstraints constraints = new GridBagConstraints();
        getContentPane().setLayout(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        gamePanel = new GamePanel();
        add(gamePanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0.0;
        add(inputPanel, constraints);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
