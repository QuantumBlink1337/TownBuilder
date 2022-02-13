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
    private Building selectedActiveBuilding;
    private String activeMode;
    private volatile int[] selectedCoords;
    private boolean coordinatesClicked = false;
    private boolean isYes;
    private final ArrayList<Building> buildings;
    private final Board board;

    public BuildingEnum getMonumentSelection() {
        return monumentSelection;
    }

    private BuildingEnum monumentSelection;
    JLabel turnResourceText;
    JLabel secondaryTextLabel = new JLabel();
    JLabel buildingSelectionLabel;


    JLabel errorTextLabel;
    JLabel yesOrNoText;
    JLabel resourceSelectionLabel;
    final JPanel boardHeader;
    final JPanel boardMatrixPanel;
    public final JPanel resourceSelectionPanel;
    final JPanel resourcePromptTextPanel;
    JPanel otherBoardsPanel;


    final ActiveBuildingsUI activeBuildingPanel;
    final ManualUI manualPanel;
    final ScoreUI scorePanel;



    final JPanel YesOrNoPanel;
    final JPanel buildingSelectingPanel;
    JPanel rightInteractionPanel = new JPanel(new MigLayout());
    JPanel gamePanel = new JPanel(new MigLayout());
    JPanel userPromptPanel = new JPanel();
    JPanel leftInteractionPanel = new JPanel(new MigLayout());



    /*
        Generates all necessary panels and adds them sequentially first to their own sub panels, then to the BoardUI object itself.
     */
    public BoardUI(Board board) throws IOException {
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

    }
    public void initializeBoard() {

        /*
            Subpanel for "user prompt" actions. E.g. Yes/No, selecting a resource/building, etc.
            Appears in the bottom center of the screen.
         */
        userPromptPanel.add(resourceSelectionPanel, "dock center");
        userPromptPanel.add(YesOrNoPanel, "dock center");
        userPromptPanel.add(buildingSelectingPanel, "dock center");
        /*
            Subpanel for the main board access matrix. Displays the title header, the text for which resource, as well as the main interactable matrix itself.
            Integrates the userPromptPanel into the bottom.
            Appears in the center of the screen.
         */
        gamePanel.add(boardHeader, "wrap, align center, span 1 1");
        gamePanel.add(resourcePromptTextPanel, "wrap, align center, w "+UI_Utilities.convertIntToPercentString(1350, true)+"!");
        gamePanel.add(boardMatrixPanel, "wrap, align center, w "+UI_Utilities.convertIntToPercentString(900, true)+"!, h " + UI_Utilities.convertIntToPercentString(900, false)+"!");
        gamePanel.add(userPromptPanel, "align center");
        /*
            Subpanel for interactive components appearing on the left of the screen. Currently, hosts the screen peeking panel.
         */
        leftInteractionPanel.add(otherBoardsPanel, "Wrap, h " + UI_Utilities.convertIntToPercentString(500, false) + "!");
        /*
            Subpanel for interactive components appearing on the right of the screen. Hosts the Manual, Active Buildings UI, and the Scorer.
         */
        rightInteractionPanel.add(manualPanel, "Wrap, h "+UI_Utilities.convertIntToPercentString(520, false)+"!");
        rightInteractionPanel.add(activeBuildingPanel, "Wrap, h "+UI_Utilities.convertIntToPercentString(300, false)+"!");
        rightInteractionPanel.add(scorePanel, "h "+UI_Utilities.convertIntToPercentString(550, false)+"!");
        /*
            mainPanel combines the three main subpanels and places them in the appropriate area.
         */
        mainPanel.add(leftInteractionPanel, "dock west, gapright "+ UI_Utilities.convertIntToPercentString(120, true));
        mainPanel.add(gamePanel, "dock center, align center");
        mainPanel.add(rightInteractionPanel, "dock east, gapleft "+ UI_Utilities.convertIntToPercentString(120, true));
        add(mainPanel);
    }
    public ActiveBuildingsUI getActiveBuildingPanel() {
        return activeBuildingPanel;
    }
    public TileButton[][] getTileAccessMatrix() {
        return tileAccessMatrix;
    }
    public boolean isActiveBuildingToggled() {
        return activeBuildingToggled;
    }
    public void setActiveBuildingToggled(boolean activeBuildingToggled) {
        this.activeBuildingToggled = activeBuildingToggled;
    }
    public void setSelectedActiveBuilding(Building selectedActiveBuilding) {
        this.selectedActiveBuilding = selectedActiveBuilding;
    }
    public boolean isCoordinatesClicked() {
        return coordinatesClicked;
    }
    public void setCoordinatesClicked(boolean b) {
        coordinatesClicked = b;
    }
    public JLabel getErrorTextLabel() {
        return errorTextLabel;
    }
    public String getActiveMode() {
        return activeMode;
    }
    public void setActiveMode(String activeMode) {
        this.activeMode = activeMode;
    }
    public Building getSelectedActiveBuilding() {
        return selectedActiveBuilding;
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
    /*
        Generates the interactive board.
     */
    private JPanel createBoardMatrix() {
        JPanel tilePanel = new JPanel(new GridLayout(4, 4, 2, 0));
        for (int r = 0; r < tileAccessMatrix.length; r++) {
            for (int c = 0; c < tileAccessMatrix[r].length; c++) {
                TileButton temp = new TileButton(r, c);
                tileAccessMatrix[r][c] = temp;
                tilePanel.add(temp);
                temp.addActionListener(e -> {
                    // Nulls existing coordinates and communicates with playerTurn of Board.
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
    }
    /*
        Generates a view of the Scorer for the final presentation of a player's game.
     */
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
    /*
        Highlights board tiles that match the row/column of a given set of resources.
     */
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
    /*
        Resets the visibility properties of individual board tiles and enables them for intractability.
        Parameters:
            doLeaveResourceLocked: If this is true, then nonempty resources will be locked after reset.
            doLeaveBuildingLocked: If this is true, then nonempty buildings will be locked after reset.
        "Nonempty" refers to a building or resource that does not have a NONE enum.
     */
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
    public void unlockBuildingTiles(BuildingEnum e) {
        for (TileButton[] tileButtons : tileAccessMatrix) {
            for (TileButton tileButton : tileButtons) {
                if (tileButton.buildingEnum != BuildingEnum.NONE && tileButton.buildingEnum != e) {
                    tileButton.setEnabled(true);
                    tileButton.setVisible(true);
                    tileButton.setBorder(mainPanel.getBorder());
                }
            }
        }
    }
    /*
        Generates the panel to display the header of the board.
     */
    private JPanel createBoardHeaderPanel() {
        JPanel panel = new JPanel(new MigLayout());
        Font font = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(42f));
        System.out.println(playerName);
        JLabel playerLabel = new JLabel(playerName+"'s Turn");
        playerLabel.setFont(font);
        playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(playerLabel, "align center");
        return panel;
    }
    /*
        Generates the panel to display the various resource strings a player needs.

     */
    private JPanel createResourceTextPanel() {
        JPanel panel = new JPanel(new MigLayout());
        Font font = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(30f));

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
    /*
        Generates the panel for selecting a resource.
     */
    private JPanel createResourceSelectionButtonPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JPanel selectionPanel = new JPanel(new GridLayout(1, 5, 0, 0));
        Font font = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(36f));
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
                mainPanel.updateUI();
            });
            button.setText(resourceArray.get(i).toString());
            button.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(25f)));
            selectionPanel.add(button, "w "+UI_Utilities.convertIntToPercentString(200, true) + "!, h " + UI_Utilities.convertIntToPercentString(50, false) + "!");
        }
        panel.add(selectionPanel, "align center, span 3");
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.setVisible(false);
        return panel;
    }
    /*
        Generates a panel to select a building. Uses the given master buildings to determine what buttons are shown.
     */
    private JPanel createBuildingSelectionPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JPanel selectionPanel = new JPanel(new GridLayout(1, buildings.size()-1, 0, 0));
        Font font = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(25f));
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
                button.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(25f)));
                selectionPanel.add(button, "w " + UI_Utilities.convertIntToPercentString(300, true) + ", h " + UI_Utilities.convertIntToPercentString(50, false));
            }
        }
        panel.add(buildingSelectionLabel, "dock center, Wrap");
        panel.add(selectionPanel);
        panel.setVisible(false);
        return panel;
    }
    /*
        Generates the panel to allow a user to select Yes/No.
     */
    private JPanel createYesNoPrompt() {
        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.red));
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        yesOrNoText = new JLabel();
        yesOrNoText.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(30f)));
        yesOrNoText.setHorizontalAlignment(SwingConstants.CENTER);
        Font font = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(20f));
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
    /*
        Generates the player view panel. This allows the player in a multiplayer setting to "peek" on another player's board.
     */
    private JPanel createPlayerViewPanel() {
        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(new LineBorder(Color.BLACK, 2));
        JLabel label = new JLabel("Other Players");
        label.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(30f)));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, "dock center, align center, wrap, w "+ UI_Utilities.INTERACTIVE_PANEL_WIDTH + "!");
        ArrayList<Board> boards = board.getPlayerManager().getBoards();
        for (Board board : boards) {
            if (board != this.board) {
                JButton button = new JButton(board.getBoardName());
                button.setFont(panel.getFont().deriveFont(UI_Utilities.convertFontSize(24f)));
                button.setHorizontalAlignment(SwingConstants.CENTER);
                Board thisBoard = this.board;
                // When you hover over the player's button, it will show you their board instead of yours
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
    /*
        Allows the PlayerManager to forcibly remove otherBoardsPanel and generate new ones for each player added.
     */
    public void createPlayerView() {
        leftInteractionPanel.remove(otherBoardsPanel);
        leftInteractionPanel.add(createPlayerViewPanel(), "wrap, cell 0 0");
    }

    public void setSelectedResourceForTurn(ResourceEnum resource) {
        selectedResourceForTurn = resource;
        turnResourceText.setText("Your resource for this turn is "+ selectedResourceForTurn+ ". Where would you like to place it?");
        resourcePromptTextPanel.setVisible(true);
    }
    public void setResourceSelectionLabel(String string) {
        resourceSelectionLabel.setText(string);
    }
    public void setResourceSelectionLabel() {
        resourceSelectionLabel.setText("Select a resource.");
    }

    public void setPrimaryTextLabel(String string) {
        turnResourceText.setText(string);
        turnResourceText.setVisible(true);
        resourcePromptTextPanel.setVisible(true);
    }
    public void setSecondaryTextLabel(String string) {
        secondaryTextLabel.setText(string);
        secondaryTextLabel.setVisible(true);
        resourcePromptTextPanel.setVisible(true);
    }
    public void setSecondaryTextLabel(String string, Color color) {
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
