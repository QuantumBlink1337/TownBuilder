package TownBuilder.UI;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.Monuments.Monument;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ScoreUI extends JPanel {
    JPanel mainScorerPanel;
    private final Board board;
    public ScoreUI (Board b) {
        board = b;
        mainScorerPanel = createMainScorerPanel();
        add(mainScorerPanel);
        setBorder(new LineBorder(Color.BLACK, 2));
    }
    private JPanel createMainScorerPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel mainLabel = new JLabel("Scorer");
        mainLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainLabel.setFont(panel.getFont().deriveFont(Font.BOLD, 36f));
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton scorerButton = new JButton("Score Board");
        Font buttonFont = panel.getFont().deriveFont(Font.BOLD, 30f);
        scorerButton.setFont(buttonFont);
        scorerButton.setHorizontalAlignment(SwingConstants.CENTER);
        scorerButton.addActionListener(e -> {
            remove(mainScorerPanel);
            try {
                add(createScoreView());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            updateUI();
        });
        panel.add(mainLabel, "align center, w "+BoardUI.INTERACTIVE_PANEL_WIDTH+", wrap");
        panel.add(scorerButton, "dock center");
        return panel;
    }
    private JPanel createScoreView() throws IOException {
        int score = board.getScorer().scoring(true);
        HashMap<BuildingEnum, Integer> scores = board.getScorer().getScores();
        JPanel panel = new JPanel(new MigLayout());
        JLabel scoreLabel = new JLabel();
        JButton exitButton = new JButton("EXIT");
        Font labelFont = panel.getFont().deriveFont(Font.BOLD, 30f);
        scoreLabel.setText("Total Score: " + score);
        scoreLabel.setFont(labelFont);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //panel.add(scoreLabel, "align center, wrap");
        panel.add(scoreLabel, "align center, w "+BoardUI.INTERACTIVE_PANEL_WIDTH+", wrap");
        ArrayList<Building> scorableBuildings = board.getScorableBuildings();
        for (Building scorableBuilding : scorableBuildings) {
            JButton label = new JButton("Score contribution of " + scorableBuilding + ": " + scores.get(scorableBuilding.getType()));
            if (scorableBuilding instanceof Monument) {
                label.setText("Score contribution of Monument: " + scores.get(scorableBuilding.getType()));
            }
            label.setFont(panel.getFont().deriveFont(20f));
            label.setBackground(scorableBuilding.getType().getColor().getOverallColor());
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(label, "Wrap, dock center");
        }
        JButton penaltyButton = new JButton("Resource Penalty: -" + board.getScorer().getResourcePenalty());
        penaltyButton.setFont(panel.getFont().deriveFont(20f));
        penaltyButton.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(penaltyButton, "Wrap, dock center");
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, 20f));
        exitButton.setHorizontalAlignment(SwingConstants.CENTER);
        exitButton.addActionListener(e -> {
            remove(panel);
            add(mainScorerPanel);
            updateUI();
        });
        panel.add(exitButton, "dock center");
        return panel;
    }
}
