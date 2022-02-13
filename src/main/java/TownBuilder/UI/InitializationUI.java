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


public class InitializationUI extends JPanel {

    private final JPanel mainMenuPanel;
    private final JPanel playerSelectionPanel;
    private final JPanel customSelectionPanel;
    private final ArrayList<JPanel> coloredBuildingViews = new ArrayList<>();

    public boolean isResourceSelectionCheat() {
        return resourceSelectionCheat;
    }

    public boolean isBuildingSelectionCheat() {
        return buildingSelectionCheat;
    }

    public boolean isMonumentSelectionCheat() {
        return monumentSelectionCheat;
    }

    private boolean resourceSelectionCheat = false;
    private boolean buildingSelectionCheat = false;
    private boolean monumentSelectionCheat = false;
    private String chosenBoardName;
    private BuildingEnum buildingSelection;
    private int playerCount;
    private String selection;
    /*
        Creates and adds the initially needed panels to the object.
     */
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
    /*
        Generates the main building selection panel.
     */
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new MigLayout("" +
                "","[][]"+UI_Utilities.convertIntToPercentString(20, true)+"[]", "[][]"+UI_Utilities.convertIntToPercentString(500, false)+"[]"));
        JLabel titleLabel = new JLabel("TownBuilder");
        Font headerFont = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(60f));
        titleLabel.setFont(headerFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel promptLabel = new JLabel("What type of game would you like?");
        promptLabel.setFont(headerFont);
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font buttonFont = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(30f));
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
    /*
        Generates the panel to allow a user to input how many players they'd like.
     */
    private JPanel createPlayerSelectionPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel headerLabel = new JLabel("How many players?");
        headerLabel.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(60f)));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Integer[] integers = new Integer[]{1,2,3,4,5,6};
        JComboBox<Integer> playerSelection = new JComboBox<>(integers);
        playerSelection.setSelectedIndex(0);
        playerSelection.setFont(panel.getFont().deriveFont(UI_Utilities.convertFontSize(60f)));
        playerSelection.addActionListener(e -> {
            // Notifies PlayerManager.
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
        buildingSelectionLabel.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(60f)));
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
        buildingLabel.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(30f)));
        buildingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        matrixLabel.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(25f)));
        matrixLabel.setHorizontalAlignment(SwingConstants.CENTER);
        explanationLabel.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(25f)));
        explanationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(panel.getFont().deriveFont(UI_Utilities.convertFontSize(24f)));
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane jScrollPane = new JScrollPane(textArea);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        exitButton.setText(string);
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD,UI_Utilities.convertFontSize(25f)));
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
        headerLabel.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(50f)));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JTextArea nameTextArea = new JTextArea();
        JButton button = new JButton("Enter");
        button.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(20f)));
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
        nameTextArea.setFont(panel.getFont().deriveFont(UI_Utilities.convertFontSize(40f)));
        nameTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(headerLabel, "dock center, wrap");
        panel.add(nameTextArea, "dock center, align center, wrap, gaptop " + UI_Utilities.convertIntToPercentString(400, false));
        panel.add(button, "dock center, align center, w "+UI_Utilities.convertIntToPercentString(850, true)+"!, h " + UI_Utilities.convertIntToPercentString(150, false) + "!");

        return panel;
    }
    public JPanel createCheatsPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel headerLabel = new JLabel("Cheat/Debug Menu");
        headerLabel.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(50f)));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton resourceButton = new JButton("Resource Selection");
        resourceButton.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(20f)));
        resourceButton.setHorizontalAlignment(SwingConstants.CENTER);
        resourceButton.addActionListener(e -> resourceSelectionCheat = true);
        JButton buildingButton = new JButton("Building Selection");
        buildingButton.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(20f)));
        buildingButton.setHorizontalAlignment(SwingConstants.CENTER);
        buildingButton.addActionListener(e -> buildingSelectionCheat = true);
        JButton monumentSelectionButton = new JButton("Monument Selection");
        monumentSelectionButton.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(20f)));
        monumentSelectionButton.setHorizontalAlignment(SwingConstants.CENTER);
        monumentSelectionButton.addActionListener(e -> monumentSelectionCheat = true);
        JButton exitButton = new JButton("EXIT");
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(20f)));
        exitButton.setHorizontalAlignment(SwingConstants.CENTER);
        exitButton.addActionListener(e -> {
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        String sizeControl = "w " + UI_Utilities.convertIntToPercentString(500, true) + "!, h " + UI_Utilities.convertIntToPercentString(100, false) + "!";
        panel.add(headerLabel, "wrap, , align center");
        panel.add(resourceButton, "split 3, dock center, align center, " + sizeControl);
        panel.add(monumentSelectionButton, "dock center, align center," + sizeControl);
        panel.add(buildingButton, "dock center, align center, wrap, " + sizeControl);
        panel.add(exitButton, "align center, dock center, " + sizeControl);
        return panel;
    }
}
