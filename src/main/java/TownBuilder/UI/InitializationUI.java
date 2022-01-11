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
    private BuildingEnum buildingSelection;
    private int playerCount;


    private String selection;
    public InitializationUI() {
        System.out.println("Initialize UI");
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



    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new MigLayout("" +
                "","[][]20[]", "[][]500[]"));
        JLabel titleLabel = new JLabel("TownBuilder");
        titleLabel.setFont(panel.getFont().deriveFont(Font.BOLD, 50f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel promptLabel = new JLabel("What type of game would you like?");
        promptLabel.setFont(panel.getFont().deriveFont(Font.BOLD, 50f));
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font buttonFont = panel.getFont().deriveFont(Font.BOLD, 30f);
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
        panel.add(titleLabel, "wrap, , align center");
        panel.add(promptLabel, " align center, wrap");
        panel.add(defaultButton, "split 3, dock center, align center, w 500!, h 100!");
        panel.add(customButton, "dock center, align center, w 500!, h 100!");
        panel.add(randomButton, "dock center, align center, w 500!, h 100!");

        return panel;
    }
    private JPanel createPlayerSelectionPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel headerLabel = new JLabel("How many players?");
        headerLabel.setFont(panel.getFont().deriveFont(Font.BOLD, 50f));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Integer[] integers = new Integer[]{1,2,3,4,5,6};
        JComboBox<Integer> playerSelection = new JComboBox<>(integers);
        playerSelection.setSelectedIndex(0);
        playerSelection.setFont(panel.getFont().deriveFont(50f));
        playerSelection.addActionListener(e -> {
            //noinspection ConstantConditions
            playerCount = (int) playerSelection.getSelectedItem();
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        panel.add(headerLabel, "align center, wrap");
        panel.add(playerSelection, "align center");
        //panel.add(playerSelection, "align center");
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
        buildingSelectionLabel.setFont(panel.getFont().deriveFont(Font.BOLD, 50f));
        buildingSelectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        HashMap<ColorEnum, ArrayList<Building>> masterBuildingList = BuildingFactory.getBuildingMasterList();
        ColorEnum[] colors = GameInitializer.colors;
        for (int i = 0; i < masterBuildingList.size(); i++) {
            JPanel mainBuildingViewPanel = new JPanel(new MigLayout());
            ArrayList<Building> buildings = masterBuildingList.get(colors[i]);
            for (Building building : buildings) {
                mainBuildingViewPanel.add(initializeIndividualBuildingView(building), "split " + buildings.size() + "" );
            }
            coloredBuildingViews.add(mainBuildingViewPanel);
        }
        panel.add(buildingSelectionLabel, "wrap");
        return panel;

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
        JScrollPane jScrollPane = new JScrollPane(textArea);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        exitButton.setText("Choose this building");
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, 30f));
        exitButton.setPreferredSize(BoardUI.BUTTON_SIZE);
        exitButton.addActionListener(e -> {
            buildingSelection = building.getType();
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        panel.setPreferredSize(new Dimension(380, 200));
        panel.add(buildingLabel, "dock center, wrap");
        panel.add(explanationLabel, "dock center, wrap");
        panel.add(jScrollPane, "dock center, wrap, h 175!");
        panel.add(matrixLabel, "dock center, wrap");
        panel.add(initializeBuildingMatrix(building), "dock center, wrap");
        panel.add(exitButton, "dock center");
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

}
