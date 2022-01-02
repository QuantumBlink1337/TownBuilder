package TownBuilder.UI;

import TownBuilder.Buildings.Building;
import TownBuilder.ResourceEnum;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class ManualUI extends JPanel {
    final int RULES_CONSTANT = 3;
    ArrayList<ArrayList<String>> rules = new ArrayList<>(RULES_CONSTANT);
    ArrayList<Building> buildingsForGame;
    JPanel mainManualPanel;
    JButton manualRuleAccessButton;
    JButton manualBuildingAccessButton;
    JPanel manualMenuPanel;
    JPanel manualRulesPanel;

    JPanel manualBuildingSelectionPanel;



    public ManualUI(ArrayList<Building> buildingsForGame) {
        this.buildingsForGame = buildingsForGame;
        add(createMainManualPanel());
    }



    private JPanel createMainManualPanel() {
        JPanel mainPanel = new JPanel(new MigLayout());
        Font font = mainPanel.getFont().deriveFont(Font.BOLD, 36f);
        JLabel label = new JLabel("Manual");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);
        label.setPreferredSize(new Dimension(380, 40));
        label.setMaximumSize(new Dimension(380, 40));
        mainPanel.add(label, "dock north, wrap");
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        manualMenuPanel = createManualMenuPanel();
        manualRulesPanel = initializeRulesMenu();
        initializeBuildingMenu();
        mainPanel.add(manualMenuPanel, " wrap");
        System.out.println(label.getPreferredSize().width);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        mainManualPanel = mainPanel;
        return mainPanel;
    }
    private JPanel createManualMenuPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel label = new JLabel("What would you like to read about?");
        label.setFont(panel.getFont().deriveFont(Font.BOLD, 20f));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, "dock center, wrap");
        initializeRuleButton();
        initalizeBuildingButton();
        panel.add(manualRuleAccessButton, "dock center, wrap");
        panel.add(manualBuildingAccessButton, "dock center, wrap");
        manualMenuPanel = panel;
        //panel.setBorder(BorderFactory.createLineBorder(Color.blue));
        panel.setPreferredSize(new Dimension(380, 40));
        return panel;
    }
    private void initializeRuleButton() {
        manualRuleAccessButton = new JButton("Rules");
        Font font = this.getFont().deriveFont(36f);
        manualRuleAccessButton.setFont(font);
        //manualRuleAccessButton.setPreferredSize(BoardUI.ButtonSize());
        manualRuleAccessButton.addActionListener(e -> {
            mainManualPanel.remove(manualMenuPanel);
            mainManualPanel.add(manualRulesPanel);
            mainManualPanel.updateUI();
        });
    }
    private void initalizeBuildingButton() {
        manualBuildingAccessButton = new JButton("Buildings");
        Font font = this.getFont().deriveFont(36f);
        manualBuildingAccessButton.setFont(font);
        manualBuildingAccessButton.setPreferredSize(BoardUI.ButtonSize());
        manualBuildingAccessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainManualPanel.remove(manualMenuPanel);
                mainManualPanel.add(manualBuildingSelectionPanel);
                mainManualPanel.updateUI();
            }
        });
    }
    private void initializeBuildingMenu() {
        manualBuildingSelectionPanel = new JPanel(new MigLayout());
        JLabel label = new JLabel("Buildings");
        label.setFont(manualBuildingSelectionPanel.getFont().deriveFont(Font.BOLD, 30f));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        manualBuildingSelectionPanel.add(label, "dock center, wrap");
        for (Building building : buildingsForGame) {
            JPanel panel = initializeIndividualBuildingView(building);
            JButton button = new JButton(building.toString());
            button.setBackground(building.getType().getColor().getOverallColor());
            Font font = manualBuildingSelectionPanel.getFont().deriveFont(Font.BOLD, 25f);
            button.setFont(font);
            button.setPreferredSize(new Dimension(364, 10));
            button.addActionListener(e -> {
                mainManualPanel.remove(manualBuildingSelectionPanel);
                mainManualPanel.add(panel);
                mainManualPanel.updateUI();
            });
            manualBuildingSelectionPanel.add(button, "wrap");
        }
        JButton exitButton = new JButton();
        exitButton.setText("EXIT");
        exitButton.setFont(manualBuildingSelectionPanel.getFont().deriveFont(Font.BOLD, 22f));
        exitButton.setPreferredSize(new Dimension(364, 10));
        exitButton.addActionListener(e -> {
            mainManualPanel.remove(manualBuildingSelectionPanel);
            mainManualPanel.add(manualMenuPanel);
            mainManualPanel.updateUI();
        });
        manualBuildingSelectionPanel.add(exitButton);
    }
    private JPanel initializeIndividualBuildingView(Building building) {
        JPanel panel = new JPanel(new MigLayout());
        JButton exitButton = new JButton();
        JTextArea textArea = new JTextArea(building.getManualEntry());
        JLabel explanationLabel = new JLabel("Here's what it does:");
        JLabel matrixLabel = new JLabel("Here's what it looks like:");
        JLabel buildingLabel = new JLabel(building.toString());
        buildingLabel.setFont(panel.getFont().deriveFont(Font.BOLD, 30f));
        buildingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        matrixLabel.setFont(panel.getFont().deriveFont(Font.BOLD, 25f));
        matrixLabel.setHorizontalAlignment(SwingConstants.CENTER);
        explanationLabel.setFont(panel.getFont().deriveFont(Font.BOLD, 25f));
        explanationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(panel.getFont().deriveFont(20f));
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        exitButton.setText("EXIT");
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, 30f));
        exitButton.setPreferredSize(BoardUI.BUTTON_SIZE);
        exitButton.addActionListener(e -> {
            mainManualPanel.remove(panel);
            mainManualPanel.add(manualBuildingSelectionPanel);
            mainManualPanel.updateUI();

        });

        panel.setPreferredSize(new Dimension(380, 200));
        panel.add(buildingLabel, "dock center, wrap");
        panel.add(explanationLabel, "dock center, wrap");
        panel.add(textArea, "dock center, wrap");
        panel.add(matrixLabel, "dock center, wrap");
        panel.add(initializeBuildingMatrix(building), "dock center, wrap");
        panel.add(exitButton, "dock center");
        return panel;

    }
    private JPanel initializeBuildingMatrix(Building building) {
        ResourceEnum[][] buildingPattern = building.getBuildingPatternsList().get(0);
        JPanel tilePanel = new JPanel(new GridLayout(Math.max(2, buildingPattern.length), Math.max(2, buildingPattern[buildingPattern.length-1].length), 2, 0));
        for (ResourceEnum[] resourceEnums : buildingPattern) {
            for (ResourceEnum resourceEnum : resourceEnums) {
                JButton temp = new JButton(resourceEnum.toString());
                temp.setBackground(resourceEnum.getColor().getOverallColor());
                temp.setPreferredSize(new Dimension(150, 150));
                if (temp.getText().equals("NONE")) {
                    temp.setVisible(false);
                }
                tilePanel.add(temp);
            }
        }
            return tilePanel;
    }

    private void initializeRules() {
        rules.add(new ArrayList<>(Arrays.asList("Objective", "The goal of TownBuilder is to construct as many buildings as possible to earn points. The game ends when you have nowhere else to place a resource or building. Planning is key!")));
        rules.add(new ArrayList<>(Arrays.asList("Placement", "Buildings are made by placing resources on your board. Each building has its own unique pattern of what it looks like. The base pattern (see: display patterns) can be rotated and mirrored. For example, the Cottage can have 8 different orientations!")));
        rules.add(new ArrayList<>(Arrays.asList("Scoring", "Each building has its own unique rules on how they accumulate points. But remember - for each resource you leave on the board you LOSE a point! It's up to you to figure out how to build your town for the most points possible.")));

    }
    private JPanel initializeRulesMenu() {
        JPanel panel = new JPanel(new MigLayout());
        //panel.setBorder(BorderFactory.createLineBorder(Color.blue));
        initializeRules();
        for (ArrayList<String> rules : rules) {
            JPanel individualRulePanel = initializeRulePage(rules.get(1));
            //individualRulePanel.setBorder(BorderFactory.createLineBorder(Color.blue));
            JButton button = new JButton(rules.get(0));
            Font font = individualRulePanel.getFont().deriveFont(25f);
            button.setPreferredSize(new Dimension(364, 10));
            button.setFont(font);
            individualRulePanel.setVisible(true);
            button.addActionListener(e -> {
                mainManualPanel.remove(manualRulesPanel);
                mainManualPanel.add(individualRulePanel);
                mainManualPanel.updateUI();
            });

            panel.add(button, ", wrap");
        }
        JButton exitButton = new JButton("EXIT");
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, 25f));
        exitButton.addActionListener(e -> {
            mainManualPanel.remove(manualRulesPanel);
            mainManualPanel.add(manualMenuPanel);
            mainManualPanel.updateUI();
        });
        panel.add(exitButton, "dock center");
        return panel;
    }
    private JPanel initializeRulePage(String ruleText) {
        JPanel panel = new JPanel(new MigLayout());
        JTextArea textArea = new JTextArea();
        JButton exitButton = new JButton();
        exitButton.setText("EXIT");
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, 30f));
        exitButton.setPreferredSize(BoardUI.BUTTON_SIZE);
        exitButton.addActionListener(e -> {
            mainManualPanel.remove(panel);
            mainManualPanel.add(manualRulesPanel);
            mainManualPanel.updateUI();

        });
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(ruleText);
        textArea.setFont(panel.getFont().deriveFont(20f));
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.setPreferredSize(new Dimension(380, 200));
        panel.add(textArea, "dock center, wrap");
        panel.add(exitButton, "dock center");
        return panel;
    }
}
