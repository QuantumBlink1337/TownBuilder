package TownBuilder.UI;
import TownBuilder.*;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.Monuments.Monument;
import TownBuilder.DebugApps.DebugTools;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static javax.swing.border.BevelBorder.RAISED;

public class BoardUI extends JPanel {

    final JPanel mainPanel = new JPanel(new MigLayout("", "[][][]", "[][][]"));
    private final TileButton[][] tileAccessMatrix = new TileButton[4][4];
    private final String playerName;

    private ResourceEnum selectedResourceForTurn;
    private BuildingEnum selectedBuildingForTurn;
    private boolean activeBuildingToggled;

    public static float getResourceTextFont() {
        return switch (UI_Utilities.SCREEN_WIDTH) {
            case 2560 -> 30f;
            default -> 22f;
        };
    }

    public static float getPlayerHeaderTextFont() {
        return switch (UI_Utilities.SCREEN_WIDTH) {
            case 2560 -> 42f;
            default -> 36f;
        };
    }
    public static float getYesNoPromptFont() {
        return switch (UI_Utilities.SCREEN_WIDTH) {
            case 2560 -> 30f;
            default -> 22f;
        };
    }
    public static float getBuildingSelectionPromptFont() {
        return switch (UI_Utilities.SCREEN_WIDTH) {
            case 2560 -> 25f;
            default -> 20f;
        };
    }
    public static float getPlayerViewFont() {
        return switch (UI_Utilities.SCREEN_WIDTH) {
            case 2560 -> 24f;
            default -> 18f;
        };
    }

    public Building getSelectedActiveBuilding() {
        return selectedActiveBuilding;
    }

    public void setSelectedActiveBuilding(Building selectedActiveBuilding) {
        this.selectedActiveBuilding = selectedActiveBuilding;
    }

    private Building selectedActiveBuilding;

    public boolean isActiveBuildingToggled() {
        return activeBuildingToggled;
    }

    public void setActiveBuildingToggled(boolean activeBuildingToggled) {
        this.activeBuildingToggled = activeBuildingToggled;
    }

    public String getActiveMode() {
        return activeMode;
    }

    public void setActiveMode(String activeMode) {
        this.activeMode = activeMode;
    }

    private String activeMode;
    private volatile int[] selectedCoords;

    public boolean isCoordinatesClicked() {
        return coordinatesClicked;
    }
    public void setCoordinatesClicked(boolean b) {
        coordinatesClicked = b;
    }

    private boolean coordinatesClicked = false;

    private boolean isYes;
    private final ArrayList<Building> buildings;
    private final Board board;
    JLabel turnResourceText;
    JLabel secondaryTextLabel = new JLabel();
    JLabel buildingSelectionLabel;

    public JLabel getErrorTextLabel() {
        return errorTextLabel;
    }

    JLabel errorTextLabel;
    JLabel yesOrNoText;
    JLabel resourceSelectionLabel;
    final JPanel boardHeader;
    final JPanel boardMatrixPanel;
    public final JPanel resourceSelectionPanel;
    final JPanel resourcePromptTextPanel;
    JPanel otherBoardsPanel;

    public ActiveBuildingsUI getActiveBuildingPanel() {
        return activeBuildingPanel;
    }

    final ActiveBuildingsUI activeBuildingPanel;
    final ManualUI manualPanel;
    final ScoreUI scorePanel;

    final JPanel YesOrNoPanel;
    final JPanel buildingSelectingPanel;
    JPanel rightInteractionPanel = new JPanel(new MigLayout());
    JPanel gamePanel = new JPanel(new MigLayout());
    JPanel userPromptPanel = new JPanel();
    JPanel leftInteractionPanel = new JPanel(new MigLayout());




    public BoardUI(Board board) {
        this.board = board;
        this.playerName = board.getBoardName();

        buildings = board.getScorableBuildings();
        manualPanel = new ManualUI(buildings);
        boardHeader = createBoardHeaderPanel();
        boardMatrixPanel = createBoardMatrix();
        resourceSelectionPanel = createResourceSelectionButtonPanel();
        resourcePromptTextPanel = createResourceTextPanel();
        activeBuildingPanel = new ActiveBuildingsUI(tileAccessMatrix, board, this);
        scorePanel = new ScoreUI(board);
        YesOrNoPanel = createYesNoPrompt();
        buildingSelectingPanel = createBuildingSelectionPanel();
        otherBoardsPanel = createPlayerViewPanel();
        resourcePromptTextPanel.setVisible(false);
        YesOrNoPanel.setVisible(false);
        mainPanel.add(leftInteractionPanel, "dock west, gapright "+ UI_Utilities.convertIntToPercentString(120, true));
        mainPanel.add(gamePanel, "dock center, align center");
        mainPanel.add(rightInteractionPanel, "dock east, gapleft "+ UI_Utilities.convertIntToPercentString(120, true));
        add(mainPanel);


        leftInteractionPanel.add(otherBoardsPanel, "Wrap");


        gamePanel.add(boardHeader, "wrap, align center, span 1 1");
        gamePanel.add(resourcePromptTextPanel, "wrap, align center, w "+UI_Utilities.convertIntToPercentString(1350, true)+"!");
        gamePanel.add(boardMatrixPanel, "wrap, align center, w "+UI_Utilities.convertIntToPercentString(900, true)+"!, h " + UI_Utilities.convertIntToPercentString(900, false)+"!");
        gamePanel.add(userPromptPanel, "align center");

        userPromptPanel.add(resourceSelectionPanel, "dock center");
        userPromptPanel.add(YesOrNoPanel, "dock center");
        userPromptPanel.add(buildingSelectingPanel, "dock center");

        rightInteractionPanel.add(manualPanel, "Wrap, h "+UI_Utilities.convertIntToPercentString(520, false)+"!");
        rightInteractionPanel.add(activeBuildingPanel, "Wrap, h "+UI_Utilities.convertIntToPercentString(300, false)+"!");
        rightInteractionPanel.add(scorePanel, "h "+UI_Utilities.convertIntToPercentString(550, false)+"!");




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
                tilePanel.add(temp);
                temp.addActionListener(e -> {
                     selectedCoords = null;
                     selectedCoords = temp.getCoords();
                     coordinatesClicked = true;
                     synchronized (board.getNotifier()) {
                         board.getNotifier().notify();
                     }

                });
            }
        }
        tilePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        return tilePanel;
    }
    public int[] getSelectedCoords() {
        return selectedCoords;
    }
    public ResourceEnum getUserSelectedResource() {
        return selectedResourceForTurn;
    }
    public void promptResourceSelection(boolean mode) throws IOException {
        DebugTools.logging("[BoardUI] - Triggering Resource Prompt Visibility");
        resourceSelectionPanel.setVisible(mode);
    }
    public void promptYesNoPrompt(String labelText) {
        yesOrNoText.setText(labelText);
        resourcePromptTextPanel.setVisible(false);
        resourceSelectionPanel.setVisible(false);
        YesOrNoPanel.setVisible(true);
    }
    public void promptBuildingSelection(String labelText) {
        buildingSelectionLabel.setText(labelText);
        buildingSelectingPanel.setVisible(true);
//        leftInteractionPanel.setVisible(false);
//        rightInteractionPanel.setVisible(false);
    }
    public void promptFinalScoreView() throws IOException {
        mainPanel.removeAll();
        JLabel header = new JLabel("Final score for " + board.getBoardName() + ":");
        header.setFont(mainPanel.getFont().deriveFont(Font.BOLD, 50f));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(header, "dock center, align center, wrap");
        mainPanel.add(scorePanel.createFinalScoreView());
        mainPanel.updateUI();
    }
    public boolean getUserYesNoAnswer() {
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
    public void resetBoardTiles(boolean doLeaveResourceLocked, boolean doLeaveBuildingLocked) {
        for (TileButton[] tileButtons : tileAccessMatrix) {
            for (TileButton tileButton : tileButtons) {

                tileButton.setEnabled(true);
                tileButton.setVisible(true);
                tileButton.setBorder(mainPanel.getBorder());
                if ((tileButton.getResourceEnum() != ResourceEnum.NONE && doLeaveResourceLocked) ||(tileButton.getBuildingEnum() != BuildingEnum.NONE && doLeaveBuildingLocked)) {
                    tileButton.setEnabled(false);
                }
            }
        }
    }




    private JPanel createBoardHeaderPanel() {
        JPanel panel = new JPanel(new MigLayout());
        Font font = panel.getFont().deriveFont(Font.BOLD, getPlayerHeaderTextFont());
        System.out.println(playerName);
        JLabel playerLabel = new JLabel(playerName+"'s Turn");
        playerLabel.setFont(font);
        playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(playerLabel, "align center");
        return panel;
    }
    private JPanel createResourceTextPanel() {
        JPanel panel = new JPanel(new MigLayout());
        Font font = panel.getFont().deriveFont(Font.BOLD, getResourceTextFont());

        turnResourceText = new JLabel();
        turnResourceText.setHorizontalAlignment(SwingConstants.CENTER);
        turnResourceText.setFont(font);

        secondaryTextLabel = new JLabel();
        secondaryTextLabel.setVisible(false);
        secondaryTextLabel.setFont(font);
        secondaryTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

        errorTextLabel = new JLabel();
        errorTextLabel.setFont(font);
        errorTextLabel.setForeground(Color.RED);
        errorTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(turnResourceText, "wrap, align center, dock center");
        panel.add(secondaryTextLabel, "Wrap, align center");
        panel.add(errorTextLabel, "align center");
        return panel;
    }
    private JPanel createResourceSelectionButtonPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JPanel selectionPanel = new JPanel(new GridLayout(1, 5, 0, 0));
        Font font = panel.getFont().deriveFont(Font.BOLD, 36f);
        resourceSelectionLabel = new JLabel("Select a Resource");
        resourceSelectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resourceSelectionLabel.setFont(font);
        panel.add(resourceSelectionLabel, "dock center, wrap");
        ArrayList<ResourceEnum> resourceArray = new ArrayList<>(Arrays.asList(ResourceEnum.WOOD, ResourceEnum.BRICK, ResourceEnum.WHEAT, ResourceEnum.GLASS, ResourceEnum.STONE));
        for (int i = 0; i < 5; i++) {
            TileButton button = new TileButton(-1, -1);
            button.setBackground(resourceArray.get(i).getColor().getOverallColor());
            button.setResourceEnum(resourceArray.get(i));
            button.addActionListener(e -> {
                setSelectedResourceForTurn(button.getResourceEnum());
                resourceSelectionPanel.setVisible(false);
                synchronized (Utility.getNotifier()) {
                    Utility.getNotifier().notify();
                }
                System.out.println("Pressed resource button");
                mainPanel.updateUI();
            });
            button.setText(resourceArray.get(i).toString());
            button.setFont(panel.getFont().deriveFont(Font.BOLD, 25f));
            //button.setPreferredSize(new Dimension(200,50));
            selectionPanel.add(button, "w :200:, h :50:");
        }
        panel.add(selectionPanel, "align center, span 3");
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.setVisible(false);
        return panel;
    }
    private JPanel createBuildingSelectionPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JPanel selectionPanel = new JPanel(new GridLayout(1, buildings.size()-1, 0, 0));
        Font font = panel.getFont().deriveFont(Font.BOLD, getBuildingSelectionPromptFont());
        buildingSelectionLabel = new JLabel("Select a building to place.");
        buildingSelectionLabel.setFont(font);
        buildingSelectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        for (Building building : buildings) {
            if (!(building instanceof Monument)) {
                TileButton button = new TileButton(-1, -1);
                button.setBackground(building.getType().getColor().getOverallColor());
                button.setBuildingEnum(building.getType());
                button.addActionListener(e -> {
                    selectedBuildingForTurn = building.getType();
                    panel.setVisible(false);
                    leftInteractionPanel.setVisible(true);
                    rightInteractionPanel.setVisible(true);
                    synchronized (Utility.getNotifier()) {
                        Utility.getNotifier().notify();
                    }
                });
                button.setText(building.toString());
                button.setFont(panel.getFont().deriveFont(Font.BOLD, getBuildingSelectionPromptFont()));
                selectionPanel.add(button, "w " + UI_Utilities.convertIntToPercentString(300, true) + ", h " + UI_Utilities.convertIntToPercentString(50, false));
            }
        }
        panel.add(buildingSelectionLabel, "dock center, Wrap");
        panel.add(selectionPanel);
        panel.setVisible(false);
        return panel;
    }

    private JPanel createYesNoPrompt() {
        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.red));
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        yesOrNoText = new JLabel();
        yesOrNoText.setFont(panel.getFont().deriveFont(Font.BOLD, getYesNoPromptFont()));
        yesOrNoText.setHorizontalAlignment(SwingConstants.CENTER);
        Font font = panel.getFont().deriveFont(Font.BOLD, 20f);
        yesButton.setFont(font);
        yesButton.setBackground(new Color(35, 138, 35));
        noButton.setFont(font);
        noButton.setBackground(new Color(183, 19, 19));
        yesButton.addActionListener(e -> {
            isYes = true;
            YesOrNoPanel.setVisible(false);
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        noButton.addActionListener(e -> {
            isYes = false;
            YesOrNoPanel.setVisible(false);
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        panel.add(yesOrNoText, "wrap, align center, span 2 1");
        panel.add(yesButton, "w " +UI_Utilities.convertIntToPercentString(500, true)+ " !, h " + UI_Utilities.convertIntToPercentString(125, false)+"!");
        panel.add(noButton, "w " +UI_Utilities.convertIntToPercentString(500, true)+ " !, h " + UI_Utilities.convertIntToPercentString(125, false)+"!");
        return panel;
    }
    private JPanel createPlayerViewPanel() {
        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(new LineBorder(Color.BLACK, 2));
        JLabel label = new JLabel("Other Players");
        label.setFont(panel.getFont().deriveFont(Font.BOLD, getYesNoPromptFont()));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, "dock center, align center, wrap, w "+ UI_Utilities.INTERACTIVE_PANEL_WIDTH + "!");
        ArrayList<Board> boards = board.getPlayerManager().getBoards();
        for (Board board : boards) {
            if (board != this.board) {
                JButton button = new JButton(board.getBoardName());
                button.setFont(panel.getFont().deriveFont(getPlayerViewFont()));
                button.setHorizontalAlignment(SwingConstants.CENTER);
                Board thisBoard = this.board;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        try {
                            thisBoard.updateBoard(board.getGameResourceBoard(), board.getGameBuildingBoard());
                            resetBoardTiles(true, true);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        mainPanel.updateUI();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        try {
                            thisBoard.updateBoard();
                            resetBoardTiles(true, true);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        mainPanel.updateUI();
                    }
                });
                panel.add(button, "wrap, dock center");
            }
        }
        return panel;
    }
    public void createPlayerView() {
        leftInteractionPanel.remove(otherBoardsPanel);
        leftInteractionPanel.add(createPlayerViewPanel(), "wrap, cell 0 0");
    }

    public void setSelectedResourceForTurn(ResourceEnum resource) {
        System.out.println("Set Selected Resource Text Label");

        selectedResourceForTurn = resource;
        turnResourceText.setText("Your resource for this turn is "+ selectedResourceForTurn+ ". Where would you like to place it?");
        //secondaryTextLabel.setVisible(false);
        resourcePromptTextPanel.setVisible(true);
    }
    public void setResourceSelectionLabel(String string) {
        resourceSelectionLabel.setText(string);
    }
    public void setResourceSelectionLabel() {
        resourceSelectionLabel.setText("Select a resource.");
    }

    public void setPrimaryTextLabel(String string) {
        //System.out.println("Set Primary Text Label");
        turnResourceText.setText(string);
        turnResourceText.setVisible(true);
        resourcePromptTextPanel.setVisible(true);
    }
    public void setSecondaryTextLabel(String string) {
        //System.out.println("Set Secondary Text Label");
        secondaryTextLabel.setText(string);
        secondaryTextLabel.setVisible(true);
        resourcePromptTextPanel.setVisible(true);
    }
    public void setSecondaryTextLabel(String string, Color color) {
        //System.out.println("Set Secondary Text Label with Color");
        secondaryTextLabel.setText(string);
        secondaryTextLabel.setForeground(color);
        secondaryTextLabel.setVisible(true);
        resourcePromptTextPanel.setVisible(true);
        resourcePromptTextPanel.updateUI();
    }


    public BuildingEnum getSelectedBuildingForTurn() {
        return selectedBuildingForTurn;
    }
}
