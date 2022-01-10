package TownBuilder.UI;

import TownBuilder.Board;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreUI extends JPanel {
    private final Board board;
    public ScoreUI (Board b) {
        board = b;
        JPanel mainScorerPanel = createMainScorerPanel();
        add(mainScorerPanel);
    }
    private JPanel createMainScorerPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel mainLabel = new JLabel("Scorer");
        JButton scorerButton = new JButton("Score Board");
        Font buttonFont = panel.getFont().deriveFont(Font.BOLD, 30f);
        scorerButton.setFont(buttonFont);
        scorerButton.setHorizontalAlignment(SwingConstants.CENTER);
        scorerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        panel.add(mainLabel, "dock center, wrap");
        panel.add(scorerButton, "dock center");
        return panel;
    }

}
