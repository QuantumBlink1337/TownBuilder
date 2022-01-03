package TownBuilder.UI;
import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Placeable;
import TownBuilder.Resource;
import TownBuilder.ResourceEnum;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static javax.swing.border.BevelBorder.RAISED;

public class BoardUI extends JFrame {

    final JPanel mainPanel = new JPanel(new MigLayout("","[center][right][left][c]","[top][center][b]"));
    private final TileButton[][] tileAccessMatrix = new TileButton[4][4];
    private final String playerName;
    private final Board board;
    public ResourceEnum getSelectedResourceForTurn() {
        return selectedResourceForTurn;
    }

    private volatile ResourceEnum selectedResourceForTurn;
    private volatile BuildingEnum selectedBuildingForTurn;
    private volatile int[] selectedCoords;
    
    private boolean isYes;
    private volatile boolean clicked = false;
    private final ArrayList<Building> buildings;
    JLabel turnResourceText;
    JLabel secondaryTextLabel = new JLabel();
    JLabel yesOrNoText;
    final JPanel boardHeader;
    final JPanel boardMatrixPanel;
    final JPanel resourceSelectionPanel;
    final JPanel resourcePromptTextPanel;

    public ActiveBuildingsUI getActiveBuildingPanel() {
        return activeBuildingPanel;
    }

    ActiveBuildingsUI activeBuildingPanel;
    final JPanel YesOrNoPanel;
    final JPanel buildingSelectingPanel;

    public ManualUI getManualPanel() {
        return manualPanel;
    }

    final ManualUI manualPanel;


    JPanel interactionPanel = new JPanel(new MigLayout());
    JPanel gamePanel = new JPanel(new MigLayout());
    JPanel userPromptPanel = new JPanel();
    static final Dimension BUTTON_SIZE = new Dimension(50, 50);

    public static Dimension ButtonSize() {
        return BUTTON_SIZE;
    }


    public BoardUI(Board board) {
        this.board = board;
        this.playerName = board.getBoardName();
        setSize(2560, 1440);
        //setSize(1920, 1080);
        buildings = board.getScorableBuildings();
        manualPanel = new ManualUI(buildings);
        boardHeader = createBoardHeaderPanel();
        boardMatrixPanel = createBoardMatrix();
        resourceSelectionPanel = createResourceSelectionButtonPanel();
        resourcePromptTextPanel = createResourceTextPanel();
        activeBuildingPanel = new ActiveBuildingsUI(tileAccessMatrix, board);
        YesOrNoPanel = createYesNoPrompt();
        buildingSelectingPanel = createBuildingSelectionPanel();
        resourcePromptTextPanel.setVisible(false);
        YesOrNoPanel.setVisible(false);
        yesOrNoText.setText("TEST");





        gamePanel.add(boardHeader, "wrap");
        gamePanel.add(resourcePromptTextPanel, "wrap, align center");
        gamePanel.add(boardMatrixPanel, "wrap, align center, w 900!, h 900!");
        gamePanel.add(userPromptPanel, "align center");
        userPromptPanel.add(resourceSelectionPanel);
        userPromptPanel.add(YesOrNoPanel);


        interactionPanel.add(manualPanel, "Wrap, h 550!");
        interactionPanel.add(activeBuildingPanel, "h 550!");
        mainPanel.add(gamePanel);
        mainPanel.add(interactionPanel);
        add(mainPanel);









//        mainPanel.add(boardMatrixPanel, "dock center, w 800!,h 800!");
//        mainPanel.add(manualPanel, "dock east, ");
//        mainPanel.add(boardHeader, "dock north");
//        mainPanel.add(resourcePromptTextPanel, "dock north");
//        mainPanel.add(resourceSelectionPanel, " w 1000!, h 100!, dock south, align center");


        gamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }
    public TileButton[][] getTileAccessMatrix() {
        return tileAccessMatrix;
    }

    private JPanel createBoardMatrix() {
        JPanel tilePanel = new JPanel(new GridLayout(4, 4, 2, 0));
        for (int r = 0; r < tileAccessMatrix.length; r++) {
            for (int c = 0; c < tileAccessMatrix[r].length; c++) {
                TileButton temp = new TileButton(r, c);
                tileAccessMatrix[r][c] = temp;
                temp.setPreferredSize(BUTTON_SIZE);
                tilePanel.add(temp);
                temp.addActionListener(e -> {
                        selectedCoords = temp.getCoords();
                });
            }
        }
        tilePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        return tilePanel;
    }
    public int[] promptUserInputOfBoard() {
        selectedCoords = null;
        while (selectedCoords == null) {
            Thread.onSpinWait();
        }
        return selectedCoords;
    }
    public ResourceEnum promptUserResourceSelection() {
        selectedResourceForTurn = null;
        resourceSelectionPanel.setVisible(true);
        resourcePromptTextPanel.setVisible(false);
        while (selectedResourceForTurn == null) {
            Thread.onSpinWait();
        }

        resourceSelectionPanel.setVisible(false);
        return selectedResourceForTurn;
    }
    public boolean promptYesNoPrompt(String labelText) {
        yesOrNoText.setText(labelText);
        resourcePromptTextPanel.setVisible(false);
        resourceSelectionPanel.setVisible(false);
        YesOrNoPanel.setVisible(true);
        clicked = false;
        while (!clicked) {
            Thread.onSpinWait();
        }
        YesOrNoPanel.setVisible(false);
        return isYes;
    }
    public void highlightBoardTiles(ArrayList<Resource> resources) {
        ArrayList<TileButton> foundButtons = new ArrayList<>();
        for (TileButton[] tileButtons : tileAccessMatrix) {
            for (TileButton tileButton : tileButtons) {
                for (Placeable placeable : resources) {
                    tileButton.setEnabled(false);
                    if (tileButton.getRow() == placeable.getRow() && tileButton.getCol() == placeable.getCol()) {
                        foundButtons.add(tileButton);
                    }
                }
            }
            for (TileButton tileButton : foundButtons) {
                tileButton.setBorder(new BevelBorder(RAISED, Color.black, Color.black));
                tileButton.setEnabled(true);
            }
        }
    }
    public void resetBoardTiles() {
        for (TileButton[] tileButtons : tileAccessMatrix) {
            for (TileButton tileButton : tileButtons) {
                tileButton.setEnabled(true);
                tileButton.setVisible(true);
                tileButton.setBorder(mainPanel.getBorder());
            }
        }
    }




    private JPanel createBoardHeaderPanel() {
        JPanel panel = new JPanel(new MigLayout());
        Font font = panel.getFont().deriveFont(Font.BOLD, 42f);
        System.out.println(playerName);
        JLabel playerLabel = new JLabel(playerName+"'s Turn");
        playerLabel.setFont(font);
        playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerLabel.setPreferredSize(new Dimension(2060, 50));
        panel.add(playerLabel, "align center");
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        return panel;
    }
    private JPanel createResourceTextPanel() {
        JPanel panel = new JPanel(new MigLayout());
        Font font = panel.getFont().deriveFont(Font.BOLD, 30f);
        turnResourceText = new JLabel("Your resource for this turn is "+ selectedResourceForTurn+ ".");
        JLabel label = new JLabel("Where would you like to place it?");
        secondaryTextLabel = new JLabel();
        secondaryTextLabel.setVisible(false);
        secondaryTextLabel.setFont(font);
        label.setFont(font);
        turnResourceText.setFont(font);
        panel.add(turnResourceText, "wrap");
        panel.add(label, "wrap");
        panel.add(secondaryTextLabel);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        return panel;
    }
    private JPanel createResourceSelectionButtonPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JPanel selectionPanel = new JPanel(new GridLayout(1, 5, 0, 0));
        Font font = panel.getFont().deriveFont(Font.BOLD, 36f);
        JLabel label = new JLabel("Select a Resource");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(font);
        panel.add(label, "dock center, wrap");
        ArrayList<ResourceEnum> resourceArray = new ArrayList<>(Arrays.asList(ResourceEnum.WOOD, ResourceEnum.BRICK, ResourceEnum.WHEAT, ResourceEnum.GLASS, ResourceEnum.STONE));
        for (int i = 0; i < 5; i++) {
            TileButton button = new TileButton(-1, -1);
            button.setBackground(resourceArray.get(i).getColor().getOverallColor());
            button.setResourceEnum(resourceArray.get(i));
            button.addActionListener(e -> {
                setSelectedResourceForTurn(button.getResourceEnum());
                mainPanel.remove(panel);
                resourcePromptTextPanel.setVisible(true);
                mainPanel.updateUI();
            });
            button.setText(resourceArray.get(i).toString());
            button.setFont(panel.getFont().deriveFont(Font.BOLD, 25f));
            button.setPreferredSize(new Dimension(200,50));
            selectionPanel.add(button);
        }
        panel.add(selectionPanel);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.setVisible(false);
        return panel;
    }
    private JPanel createBuildingSelectionPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JPanel selectionPanel = new JPanel(new GridLayout(1, buildings.size(), 0, 0));
        Font font = panel.getFont().deriveFont(Font.BOLD, 25f);
        JLabel label = new JLabel("Select a building to place.");
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        for (Building building : buildings) {
            TileButton button = new TileButton(-1, -1);
            button.setBackground(building.getType().getColor().getOverallColor());
            button.setBuildingEnum(building.getType());
            button.addActionListener(e -> selectedBuildingForTurn = building.getType());
            button.setText(building.toString());
            button.setFont(panel.getFont().deriveFont(Font.BOLD, 25f));
            button.setPreferredSize(new Dimension(300,50));
            selectionPanel.add(button);
        }
        panel.add(label, "Wrap");
        panel.add(selectionPanel);
        return panel;
    }

    private JPanel createYesNoPrompt() {
        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.red));
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        yesOrNoText = new JLabel();
        yesOrNoText.setFont(panel.getFont().deriveFont(Font.BOLD, 30f));
        yesOrNoText.setHorizontalAlignment(SwingConstants.CENTER);
        Dimension dimension = new Dimension(500, 125);
        Font font = panel.getFont().deriveFont(Font.BOLD, 20f);
        yesButton.setFont(font);
        yesButton.setBackground(new Color(35, 138, 35));
        yesButton.setPreferredSize(dimension);
        noButton.setFont(font);
        noButton.setBackground(new Color(183, 19, 19));
        noButton.setPreferredSize(dimension);
        yesButton.addActionListener(e -> {
            isYes = true;
            clicked = true;
        });
        noButton.addActionListener(e -> {
            isYes = false;
            clicked = true;
        });
        panel.add(yesOrNoText, "wrap, align center, span 2 1");
        panel.add(yesButton);
        panel.add(noButton);



        return panel;

    }

    public void setSelectedResourceForTurn(ResourceEnum resource) {
        selectedResourceForTurn = resource;
        turnResourceText.setText("Your resource for this turn is "+ selectedResourceForTurn+ ".");
        secondaryTextLabel.setVisible(false);
        resourcePromptTextPanel.setVisible(true);
    }
    public void setPrimaryTextLabel(String string) {
        turnResourceText.setText(string);
        resourcePromptTextPanel.setVisible(true);
        System.out.println("Prompt");
    }
    public void setSecondaryTextLabel(String string) {
        secondaryTextLabel.setText(string);
        secondaryTextLabel.setVisible(true);
    }




}
