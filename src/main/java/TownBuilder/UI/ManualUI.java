package TownBuilder.UI;

import TownBuilder.Manual;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ManualUI extends JPanel {
    final int RULES_CONSTANT = 3;
    //HashMap<String, String> rules = new HashMap<>(RULES_CONSTANT);
    ArrayList<ArrayList<String>> rules = new ArrayList<>(RULES_CONSTANT);
    JPanel mainManualPanel;
    JButton manualRuleAccessButton;
    JButton manualBuildingAccessButton;
    JPanel manualMenuPanel;
    JPanel manualRulesPanel = initializeRulesMenu();


    public ManualUI() {
        add(createManualPanel());
    }



    private JPanel createManualPanel() {
        JPanel mainPanel = new JPanel(new MigLayout());
        Font font = mainPanel.getFont().deriveFont(Font.BOLD, 36f);
        JLabel label = new JLabel("Manual");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);
        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.add(createManualMenuPanel(), "wrap");
        mainPanel.add(manualRulesPanel);
        mainManualPanel = mainPanel;
        return mainPanel;
    }
    private JPanel createManualMenuPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel label = new JLabel("What would you like to read about?");
        label.setFont(panel.getFont().deriveFont(Font.BOLD, 20f));
        panel.add(label, "wrap");
        initializeRuleButton();
        initalizeBuildingButton();
        panel.add(manualRuleAccessButton, "dock center, wrap");
        panel.add(manualBuildingAccessButton, "dock center, wrap");
        manualMenuPanel = panel;
        return panel;
    }
    private void initializeRuleButton() {
        manualRuleAccessButton = new JButton("Rules");
        Font font = this.getFont().deriveFont(36f);
        manualRuleAccessButton.setFont(font);
        manualRuleAccessButton.setPreferredSize(BoardUI.ButtonSize());
        manualRuleAccessButton.addActionListener(e -> {
            mainManualPanel.remove(manualMenuPanel);
//            manualMenuPanel.setVisible(false);
            manualRulesPanel.setVisible(true);
        });
    }
    private void initalizeBuildingButton() {
        manualBuildingAccessButton = new JButton("Buildings");
        Font font = this.getFont().deriveFont(36f);
        manualBuildingAccessButton.setFont(font);
        manualBuildingAccessButton.setPreferredSize(BoardUI.ButtonSize());
        manualBuildingAccessButton.addActionListener(e -> manualMenuPanel.setVisible(false));
    }
    private void initializeRules() {
        rules.add(new ArrayList<>(Arrays.asList("Objective", "The goal of TownBuilder is to construct as many buildings as possible to earn points.\nThe game ends when you have nowhere else to place a resource or building. Planning is key!")));
        rules.add(new ArrayList<>(Arrays.asList("Placement", "Buildings are made by placing resources on your board. Each building has its own unique pattern of what it looks like.\\nThe base pattern (see: display patterns) can be rotated and mirrored.\\nFor example, the Cottage can have 8 different orientations!")));
        rules.add(new ArrayList<>(Arrays.asList("Scoring", "Each building has its own unique rules on how they accumulate points.\\nBut remember - for each resource you leave on the board you LOSE a point! It's up to you to figure out how to build your town for the most points possible.")));

    }
    private JPanel initializeRulesMenu() {
        JPanel panel = new JPanel(new MigLayout());
        initializeRules();
        for (ArrayList<String> rules : rules) {
            JPanel rulePanel = initializeRulePage(rules.get(1));
            JButton button = new JButton(rules.get(0));
            button.setPreferredSize(BoardUI.ButtonSize());
            rulePanel.setVisible(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainManualPanel.remove(manualRulesPanel);
                    mainManualPanel.add(rulePanel);
                }
            });

            panel.add(button, "dock center, wrap");
        }
        JButton exitButton = new JButton("EXIT");
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, 30f));
        exitButton.setPreferredSize(BoardUI.BUTTON_SIZE);
        exitButton.addActionListener(e -> {
            mainManualPanel.remove(manualRulesPanel);
            mainManualPanel.add(manualMenuPanel);

        });
        panel.add(exitButton, "dock center");
        panel.setVisible(false);
        return panel;
    }
    private JPanel initializeRulePage(String ruleText) {
        JPanel panel = new JPanel(new MigLayout());
        JLabel label = new JLabel();
        JButton exitButton = new JButton();
        exitButton.setText("EXIT");
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, 30f));
        exitButton.setPreferredSize(BoardUI.BUTTON_SIZE);
        exitButton.addActionListener(e -> {
            mainManualPanel.remove(panel);
            mainManualPanel.add(manualRulesPanel);
        });
        label.setText(ruleText);
        label.setFont(panel.getFont().deriveFont(20f));
        panel.add(label, "dock center, wrap");
        panel.add(exitButton, "dock center");
        return panel;
    }
}
