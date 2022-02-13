package TownBuilder.UI;

import TownBuilder.Buildings.Building;
import TownBuilder.ResourceEnum;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
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
        setBorder(new LineBorder(Color.BLACK, 2));
    }

    private JPanel createMainManualPanel() {
        JPanel mainPanel = new JPanel(new MigLayout());
        Font font = mainPanel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(36f));
        JLabel label = new JLabel("Manual");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);
        mainPanel.add(label, "w "+ UI_Utilities.INTERACTIVE_PANEL_WIDTH+"! , wrap");
        manualMenuPanel = createManualMenuPanel();
        manualRulesPanel = initializeRulesMenu();
        initializeBuildingMenu();
        mainPanel.add(manualMenuPanel,  "wrap, w " + UI_Utilities.INTERACTIVE_PANEL_WIDTH + "!");
        mainManualPanel = mainPanel;
        return mainPanel;
    }
    private JPanel createManualMenuPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel label = new JLabel("What would you like to read about?");
        label.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(22f)));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, "dock center, wrap");
        initializeRuleButton();
        initalizeBuildingButton();
        panel.add(manualRuleAccessButton, "dock center, wrap");
        panel.add(manualBuildingAccessButton, "dock center, wrap");
        manualMenuPanel = panel;
        return panel;
    }
    private void initializeRuleButton() {
        manualRuleAccessButton = new JButton("Rules");
        Font font = this.getFont().deriveFont(UI_Utilities.convertFontSize(36f));
        manualRuleAccessButton.setFont(font);
        manualRuleAccessButton.addActionListener(e -> {
            mainManualPanel.remove(manualMenuPanel);
            mainManualPanel.add(manualRulesPanel, "w " + UI_Utilities.INTERACTIVE_PANEL_WIDTH + "!");
            mainManualPanel.updateUI();
        });
    }
    private void initalizeBuildingButton() {
        manualBuildingAccessButton = new JButton("Buildings");
        Font font = this.getFont().deriveFont(UI_Utilities.convertFontSize(36f));
        manualBuildingAccessButton.setFont(font);
        manualBuildingAccessButton.addActionListener(e -> {
            mainManualPanel.remove(manualMenuPanel);
            mainManualPanel.add(manualBuildingSelectionPanel, "w "+ UI_Utilities.INTERACTIVE_PANEL_WIDTH + "!");
            mainManualPanel.updateUI();
        });
    }
    private void initializeBuildingMenu() {
        manualBuildingSelectionPanel = new JPanel(new MigLayout("", "[]0[]0[]", "[]3[]3[]"));
        Font font = manualBuildingSelectionPanel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(25f));
        for (Building building : buildingsForGame) {
            JPanel panel = initializeIndividualBuildingView(building);
            JButton button = new JButton(building.toString());
            button.setBackground(building.getType().getColor().getOverallColor());
            button.setFont(font);
            button.addActionListener(e -> {
                mainManualPanel.remove(manualBuildingSelectionPanel);
                mainManualPanel.add(panel, "w "+ UI_Utilities.INTERACTIVE_PANEL_WIDTH + "!");
                mainManualPanel.updateUI();
            });
            manualBuildingSelectionPanel.add(button, "dock center, wrap, h " + UI_Utilities.convertIntToPercentString(40, false) + "!");
        }
        JButton exitButton = new JButton();
        exitButton.setText("EXIT");
        exitButton.setFont(font);
        exitButton.addActionListener(e -> {
            mainManualPanel.remove(manualBuildingSelectionPanel);
            mainManualPanel.add(manualMenuPanel, "wrap, align center, w " + UI_Utilities.INTERACTIVE_PANEL_WIDTH + "!");
            mainManualPanel.updateUI();
        });

        manualBuildingSelectionPanel.add(exitButton, "dock center");
    }
    private JPanel initializeIndividualBuildingView(Building building) {
        JPanel panel = new JPanel(new MigLayout());
        JButton exitButton = new JButton();
        JTextArea textArea = new JTextArea(building.getManualEntry());
        JLabel explanationLabel = new JLabel("Here's what it does:");
        JLabel matrixLabel = new JLabel("Here's what it looks like:");
        JLabel buildingLabel = new JLabel(building.toString());
        Font font1 = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(25f));
        Font font2 = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(30f));
        Font font3 = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(24f));
        buildingLabel.setFont(font2);
        buildingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        matrixLabel.setFont(font1);
        matrixLabel.setHorizontalAlignment(SwingConstants.CENTER);
        explanationLabel.setFont(font1);
        explanationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(font3);
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane jScrollPane = new JScrollPane(textArea);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        exitButton.setText("EXIT");
        exitButton.setFont(font3);
        exitButton.addActionListener(e -> {
            mainManualPanel.remove(panel);
            mainManualPanel.add(manualBuildingSelectionPanel, "dock center");
            mainManualPanel.updateUI();

        });
        panel.add(buildingLabel, "dock center, wrap");
        panel.add(explanationLabel, "dock center, wrap");
        panel.add(jScrollPane, "dock center, wrap, h " + UI_Utilities.convertIntToPercentString(125, false) + "!");
        panel.add(matrixLabel, "dock center, wrap");
        panel.add(UI_Utilities.initializeBuildingMatrix(building), "dock center, wrap");
        panel.add(exitButton, "dock center, h " + UI_Utilities.convertIntToPercentString(40, false) + "!");
        return panel;
    }


    private void initializeRules() {
        rules.add(new ArrayList<>(Arrays.asList("Objective", "The goal of TownBuilder is to construct as many buildings as possible to earn points. The game ends when you have nowhere else to place a resource or building. Planning is key!")));
        rules.add(new ArrayList<>(Arrays.asList("Placement", "Buildings are made by placing resources on your board. Each building has its own unique pattern of what it looks like. The base pattern (see: display patterns) can be rotated and mirrored. For example, the Cottage can have 8 different orientations!")));
        rules.add(new ArrayList<>(Arrays.asList("Scoring", "Each building has its own unique rules on how they accumulate points. But remember - for each resource you leave on the board you LOSE a point! It's up to you to figure out how to build your town for the most points possible.")));
        rules.add(new ArrayList<>(Arrays.asList("Monuments", "Monuments are types of unique buildings that can have powerful benefits. Each player gets access to one monument - and no one can build it except you. You can only build it once though!")));
        rules.add(new ArrayList<>(Arrays.asList("Active Buildings", "Active buildings have functionality that you can use on the board compared to regular buildings, that just add points to your score. These buildings are the Factory, Warehouse, Bank, and Trading Post. You may up to 4 active buildings.")));

    }
    private JPanel initializeRulesMenu() {
        JPanel panel = new JPanel(new MigLayout());
        initializeRules();
        for (ArrayList<String> rules : rules) {
            JPanel individualRulePanel = initializeRulePage(rules.get(1));
            JButton button = new JButton(rules.get(0));
            Font font = individualRulePanel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(25f));
            button.setFont(font);
            individualRulePanel.setVisible(true);
            button.addActionListener(e -> {
                mainManualPanel.remove(manualRulesPanel);
                mainManualPanel.add(individualRulePanel, "dock center");
                mainManualPanel.updateUI();
            });

            panel.add(button, "dock center, wrap, h "+UI_Utilities.convertIntToPercentString(40, false) + "!" );
        }
        JButton exitButton = new JButton("EXIT");
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(30f)));
        exitButton.addActionListener(e -> {
            mainManualPanel.remove(manualRulesPanel);
            mainManualPanel.add(manualMenuPanel, "wrap, w " + UI_Utilities.INTERACTIVE_PANEL_WIDTH + "!");
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
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(36f)));
        exitButton.addActionListener(e -> {
            mainManualPanel.remove(panel);
            mainManualPanel.add(manualRulesPanel, "w " + UI_Utilities.INTERACTIVE_PANEL_WIDTH + "!");
            mainManualPanel.updateUI();

        });
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(ruleText);
        textArea.setFont(panel.getFont().deriveFont(UI_Utilities.convertFontSize(25f)));
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane jScrollPane = new JScrollPane(textArea);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jScrollPane, "dock center, wrap, h "+UI_Utilities.convertIntToPercentString(275, false) + "!");
        panel.add(exitButton, "dock center");
        return panel;
    }
}
