package TownBuilder.UI;

import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.ColorEnum;
import TownBuilder.GameInitializer;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static TownBuilder.UI.ManualUI.getManualBuildingButtonFont;

public class InitializationUI extends JPanel {

    private final JPanel mainMenuPanel;
    private final JPanel playerSelectionPanel;
    private final JPanel customSelectionPanel;
    private final ArrayList<JPanel> coloredBuildingViews = new ArrayList<>();
    private String chosenBoardName;
    private BuildingEnum buildingSelection;
    private int playerCount;
    private String selection;

    public InitializationUI() {
        mainMenuPanel = createMainMenuPanel();
        playerSelectionPanel = createPlayerSelectionPanel();
        customSelectionPanel = createBuildingSelectionPanel();
        customSelectionPanel.setVisible(false);
        add(mainMenuPanel);
        add(customSelectionPanel);

    }
    public String getSelection() {
        return selection;
    }
    public int getPlayerCount() {
        return playerCount;
    }
    public BuildingEnum getBuildingSelection() {
        return buildingSelection;
    }
    public ArrayList<JPanel> getColoredBuildingViews() {
        return coloredBuildingViews;
    }
    public JPanel getCustomSelectionPanel() {
        return customSelectionPanel;
    }
    public JPanel getMainMenuPanel() {
        return mainMenuPanel;
    }
    public JPanel getPlayerSelectionPanel() {
        return playerSelectionPanel;
    }
    public String getChosenBoardName() {
        return chosenBoardName;
    }
    private static float getHeaderFont() {
        return switch (UI_Utilities.SCREEN_WIDTH) {
            case 2560 -> 60f;
            default -> 50f;
        };
    }
    private static float getButtonFont() {
        return switch (UI_Utilities.SCREEN_WIDTH) {
            case 2560 -> 30f;
            default -> 24f;
        };
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new MigLayout("" +
                "","[][]20[]", "[][]500[]"));
        JLabel titleLabel = new JLabel("TownBuilder");
        titleLabel.setFont(panel.getFont().deriveFont(Font.BOLD, getHeaderFont()));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel promptLabel = new JLabel("What type of game would you like?");
        promptLabel.setFont(panel.getFont().deriveFont(Font.BOLD, getHeaderFont()));
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font buttonFont = panel.getFont().deriveFont(Font.BOLD, getButtonFont());
        JButton defaultButton = new JButton("Default");
        defaultButton.setHorizontalAlignment(SwingConstants.CENTER);
        defaultButton.setFont(buttonFont);
        defaultButton.setToolTipText("A classic game with the normal buildings. Recommended for new players.");
        defaultButton.addActionListener(e -> {
            selection = "default";
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        JButton customButton = new JButton("Custom");
        customButton.setHorizontalAlignment(SwingConstants.CENTER);
        customButton.setFont(buttonFont);
        customButton.setToolTipText("Decide what buildings you'll use for your game.");
        customButton.addActionListener(e -> {
            selection = "custom";
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        JButton randomButton = new JButton("Random");
        randomButton.setHorizontalAlignment(SwingConstants.CENTER);
        randomButton.setFont(buttonFont);
        randomButton.setToolTipText("Let Math.random() decide your buildings.");
        randomButton.addActionListener(e -> {
            selection = "random";
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        String sizeControl = "w " + UI_Utilities.convertIntToPercentString(500, true) + "!, h " + UI_Utilities.convertIntToPercentString(100, false) + "!";
        panel.add(titleLabel, "wrap, , align center");
        panel.add(promptLabel, " align center, wrap");
        panel.add(defaultButton, "split 3, dock center, align center, " + sizeControl);
        panel.add(customButton, "dock center, align center," + sizeControl);
        panel.add(randomButton, "dock center, align center, " + sizeControl);

        return panel;
    }
    private JPanel createPlayerSelectionPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel headerLabel = new JLabel("How many players?");
        headerLabel.setFont(panel.getFont().deriveFont(Font.BOLD, getHeaderFont()));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Integer[] integers = new Integer[]{1,2,3,4,5,6};
        JComboBox<Integer> playerSelection = new JComboBox<>(integers);
        playerSelection.setSelectedIndex(0);
        playerSelection.setFont(panel.getFont().deriveFont(getHeaderFont()));
        playerSelection.addActionListener(e -> {
            //noinspection ConstantConditions
            playerCount = (int) playerSelection.getSelectedItem();
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        panel.add(headerLabel, "align center, wrap");
        panel.add(playerSelection, "align center, gaptop " + UI_Utilities.convertIntToPercentString(400, false));
        return panel;
    }
    public void promptPlayerSelection() {
        remove(mainMenuPanel);
        add(playerSelectionPanel);
        updateUI();
    }
    private JPanel createBuildingSelectionPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel buildingSelectionLabel = new JLabel("Select your buildings!");
        buildingSelectionLabel.setFont(panel.getFont().deriveFont(Font.BOLD, getHeaderFont()));
        buildingSelectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        HashMap<ColorEnum, ArrayList<Building>> masterBuildingList = BuildingFactory.getBuildingMasterList();
        ColorEnum[] colors = GameInitializer.colors;
        for (int i = 0; i < masterBuildingList.size(); i++) {
            if (masterBuildingList.get(colors[i]).size() > 1) {
                JPanel mainBuildingViewPanel = new JPanel(new MigLayout());
                ArrayList<Building> buildings = masterBuildingList.get(colors[i]);
                for (Building building : buildings) {
                    mainBuildingViewPanel.add(initializeIndividualBuildingView(building, "Choose this building"), "split " + buildings.size() + ", w " +
                            UI_Utilities.convertIntToPercentString(500, true) + "!, h " + UI_Utilities.convertIntToPercentString(600, false) + "!");
                }
                coloredBuildingViews.add(mainBuildingViewPanel);
            }
        }
        panel.add(buildingSelectionLabel, "wrap, dock center");
        return panel;

    }
    private JPanel initializeIndividualBuildingView(Building building, String string) {
        JPanel panel = new JPanel(new MigLayout());
        JButton exitButton = new JButton();
        JTextArea textArea = new JTextArea(building.getManualEntry());
        JLabel explanationLabel = new JLabel("Here's what it does:");
        JLabel matrixLabel = new JLabel("Here's what it looks like:");
        JLabel buildingLabel = new JLabel(building.toString());
        buildingLabel.setFont(panel.getFont().deriveFont(Font.BOLD, ManualUI.getManualIndividualBuildingHeaderFont()));
        buildingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        matrixLabel.setFont(panel.getFont().deriveFont(Font.BOLD, ManualUI.getManualBuildingButtonFont()));
        matrixLabel.setHorizontalAlignment(SwingConstants.CENTER);
        explanationLabel.setFont(panel.getFont().deriveFont(Font.BOLD, ManualUI.getManualBuildingButtonFont()));
        explanationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(panel.getFont().deriveFont(ManualUI.getBuildingManualEntryTextFont()));
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane jScrollPane = new JScrollPane(textArea);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        exitButton.setText(string);
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD,ManualUI.getManualBuildingButtonFont()));
        exitButton.addActionListener(e -> {
            buildingSelection = building.getType();
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        panel.add(buildingLabel, "dock center, wrap");
        panel.add(explanationLabel, "dock center, wrap");
        panel.add(jScrollPane, "dock center, wrap, h " + UI_Utilities.convertIntToPercentString(160, false) + "!");
        panel.add(matrixLabel, "dock center, wrap");
        panel.add(initializeBuildingMatrix(building), "dock center, wrap");
        panel.add(exitButton, "dock center, h " + UI_Utilities.convertIntToPercentString(40, false) + "!");
        return panel;
    }


    private JPanel initializeBuildingMatrix(Building building) {
        ResourceEnum[][] buildingPattern = building.getBuildingPatternsList().get(0);
        JPanel tilePanel = new JPanel(new GridLayout(buildingPattern.length, buildingPattern[buildingPattern.length-1].length, 2, 0));
        for (ResourceEnum[] resourceEnums : buildingPattern) {
            for (ResourceEnum resourceEnum : resourceEnums) {
                JButton temp = new JButton(resourceEnum.toString());
                temp.setBackground(resourceEnum.getColor().getOverallColor());
                if (temp.getText().equals("NONE")) {
                    temp.setVisible(false);
                }
                tilePanel.add(temp);
            }
        }
        return tilePanel;
    }
    public JPanel generatePlayerNameSelectionPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel headerLabel = new JLabel("What's your name?");
        headerLabel.setFont(panel.getFont().deriveFont(Font.BOLD, 50f));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JTextArea nameTextArea = new JTextArea();
        JButton button = new JButton("Enter");
        button.setFont(panel.getFont().deriveFont(Font.BOLD, 15f));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.addActionListener(e -> {
            if (nameTextArea.getText().length() < 20) {
                chosenBoardName = nameTextArea.getText();
                synchronized (Utility.getNotifier()) {
                    Utility.getNotifier().notify();
                }
            }
        });
        nameTextArea.setEditable(true);
        nameTextArea.setLineWrap(true);
        nameTextArea.setWrapStyleWord(true);
        nameTextArea.setFont(panel.getFont().deriveFont(20f));
        nameTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(headerLabel, "dock center, wrap");
        panel.add(nameTextArea, "dock center, align center, wrap, gaptop " + UI_Utilities.convertIntToPercentString(400, false));
        panel.add(button, "dock center, align center");

        return panel;
    }

}
