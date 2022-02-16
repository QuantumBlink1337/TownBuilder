package TownBuilder.UI;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Utility;
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
        JPanel panel = new JPanel(new MigLayout("", "0[]0"));
        JLabel mainLabel = new JLabel("Scorer");
        mainLabel.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(36f)));
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton scorerButton = new JButton("Score Board");
        Font buttonFont = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(30f));
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
        panel.add(mainLabel, "align center, w "+ UI_Utilities.convertIntToPercentString(434, true)+", wrap");
        panel.add(scorerButton, "dock center");
        return panel;
    }
    private JPanel createScoreView() throws IOException {
        int score = board.getScorer().scoring();
        HashMap<BuildingEnum, Integer> scores = board.getScorer().getScores();
        JPanel panel = new JPanel(new MigLayout());
        JLabel scoreLabel = new JLabel();
        JButton exitButton = new JButton("EXIT");
        Font labelFont = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(30f));
        scoreLabel.setText("Total Score: " + score);
        scoreLabel.setFont(labelFont);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(scoreLabel, "align center, w "+ UI_Utilities.INTERACTIVE_PANEL_WIDTH+", wrap");
        ArrayList<Building> scorableBuildings = board.getScorableBuildings();
        for (Building scorableBuilding : scorableBuildings) {
            JButton label = new JButton(scorableBuilding + ": " + scores.get(scorableBuilding.getType()));
            label.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(24f)));
            label.setBackground(scorableBuilding.getType().getColor().getOverallColor());
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(label, "Wrap, dock center, h " + UI_Utilities.convertIntToPercentString(35, false) + "!");
        }
        StringBuilder penaltyString = new StringBuilder();
        int penalty = board.getScorer().getResourcePenalty();
        if (penalty > 0) {
            penaltyString.append("-");
        }
        penaltyString.append(penalty);
        JLabel penaltyLabel = new JLabel("Resource Penalty: " + penaltyString);
        penaltyLabel.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(24f)));
        penaltyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(penaltyLabel, "Wrap, dock center, h " + UI_Utilities.convertIntToPercentString(35, false) + "!");
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(24f)));
        exitButton.setHorizontalAlignment(SwingConstants.CENTER);
        exitButton.addActionListener(e -> {
            remove(panel);
            add(mainScorerPanel);
            updateUI();
        });
        panel.add(exitButton, "dock center");
        return panel;
    }
    public JPanel createFinalScoreView() throws IOException {
        int score = board.getScorer().scoring();
        HashMap<BuildingEnum, Integer> scores = board.getScorer().getScores();
        JPanel panel = new JPanel(new MigLayout());
        JLabel scoreLabel = new JLabel();
        JButton exitButton = new JButton("OK");
        Font labelFont = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(40f));
        scoreLabel.setText("Total Score: " + score + " Penalty: " + board.getScorer().getResourcePenalty());
        scoreLabel.setFont(labelFont);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //panel.add(scoreLabel, "align center, wrap");
        panel.add(scoreLabel, "align center, w "+ UI_Utilities.INTERACTIVE_PANEL_WIDTH+", wrap");
        ArrayList<Building> scorableBuildings = board.getScorableBuildings();
        for (Building scorableBuilding : scorableBuildings) {
            JButton label = new JButton(scorableBuilding + ": " + scores.get(scorableBuilding.getType()));
            label.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(30f)));
            label.setBackground(scorableBuilding.getType().getColor().getOverallColor());
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(label, "Wrap, dock center, h " + UI_Utilities.convertIntToPercentString(35, false) + "!");
        }
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(24f)));
        exitButton.setHorizontalAlignment(SwingConstants.CENTER);
        exitButton.addActionListener(e -> {
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        panel.add(exitButton, "dock center");
        return panel;
    }
}
