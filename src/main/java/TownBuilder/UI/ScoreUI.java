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
    public static float getScorerHeaderFont() {
        return switch (UI_Utilities.SCREEN_WIDTH) {
            case 2560 -> 36f;
            default -> 28f;
        };
    }
    public static float getScorerMainButtonFont() {
        return switch (UI_Utilities.SCREEN_WIDTH) {
            case 2560 -> 30f;
            default -> 25;
        };
    }
    public static float getScorerSecondaryButtonFont() {
        return switch (UI_Utilities.SCREEN_WIDTH) {
            case 2560 -> 24f;
            default -> 18f;
        };
    }
    public static float getScorerFinalLabelFont() {
        return switch (UI_Utilities.SCREEN_WIDTH) {
            case 2560 -> 40f;
            default -> 30f;
        };
    }
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
        mainLabel.setFont(panel.getFont().deriveFont(Font.BOLD, getScorerHeaderFont()));
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton scorerButton = new JButton("Score Board");
        Font buttonFont = panel.getFont().deriveFont(Font.BOLD, getScorerMainButtonFont());
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
        panel.add(mainLabel, "align center, w "+ UI_Utilities.INTERACTIVE_PANEL_WIDTH+", wrap");
        panel.add(scorerButton, "dock center");
        return panel;
    }
    private JPanel createScoreView() throws IOException {
        int score = board.getScorer().scoring(true);
        HashMap<BuildingEnum, Integer> scores = board.getScorer().getScores();
        JPanel panel = new JPanel(new MigLayout());
        JLabel scoreLabel = new JLabel();
        JButton exitButton = new JButton("EXIT");
        Font labelFont = panel.getFont().deriveFont(Font.BOLD, getScorerMainButtonFont());
        scoreLabel.setText("Total Score: " + score + " Penalty: " + board.getScorer().getResourcePenalty());
        scoreLabel.setFont(labelFont);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //panel.add(scoreLabel, "align center, wrap");
        panel.add(scoreLabel, "align center, w "+ UI_Utilities.INTERACTIVE_PANEL_WIDTH+", wrap");
        ArrayList<Building> scorableBuildings = board.getScorableBuildings();
        for (Building scorableBuilding : scorableBuildings) {
            JButton label = new JButton(scorableBuilding + ": " + scores.get(scorableBuilding.getType()));
            label.setFont(panel.getFont().deriveFont(Font.BOLD, getScorerSecondaryButtonFont()));
            label.setBackground(scorableBuilding.getType().getColor().getOverallColor());
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(label, "Wrap, dock center, h " + UI_Utilities.convertIntToPercentString(35, false) + "!");
        }
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, getScorerSecondaryButtonFont()));
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
        int score = board.getScorer().scoring(true);
        HashMap<BuildingEnum, Integer> scores = board.getScorer().getScores();
        JPanel panel = new JPanel(new MigLayout());
        JLabel scoreLabel = new JLabel();
        JButton exitButton = new JButton("OK");
        Font labelFont = panel.getFont().deriveFont(Font.BOLD, getScorerFinalLabelFont());
        scoreLabel.setText("Total Score: " + score + " Penalty: " + board.getScorer().getResourcePenalty());
        scoreLabel.setFont(labelFont);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //panel.add(scoreLabel, "align center, wrap");
        panel.add(scoreLabel, "align center, w "+ UI_Utilities.INTERACTIVE_PANEL_WIDTH+", wrap");
        ArrayList<Building> scorableBuildings = board.getScorableBuildings();
        for (Building scorableBuilding : scorableBuildings) {
            JButton label = new JButton(scorableBuilding + ": " + scores.get(scorableBuilding.getType()));
            label.setFont(panel.getFont().deriveFont(Font.BOLD, getScorerMainButtonFont()));
            label.setBackground(scorableBuilding.getType().getColor().getOverallColor());
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(label, "Wrap, dock center, h " + UI_Utilities.convertIntToPercentString(35, false) + "!");
        }
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, getScorerSecondaryButtonFont()));
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
