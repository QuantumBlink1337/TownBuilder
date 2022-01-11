package TownBuilder.UI;
import TownBuilder.*;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.Monuments.Monument;
import TownBuilder.DebugApps.DebugTools;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static javax.swing.border.BevelBorder.RAISED;

public class BoardUI extends JPanel {

    final JPanel mainPanel = new JPanel(new MigLayout("", "[][][]", "[][][]"));
    private TileButton[][] tileAccessMatrix = new TileButton[4][4];
    private final String playerName;

    private ResourceEnum selectedResourceForTurn;
    private BuildingEnum selectedBuildingForTurn;
    private boolean activeBuildingToggled;

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
    static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
    static final Dimension BUTTON_SIZE = new Dimension(50, 50);
    static final int INTERACTIVE_PANEL_WIDTH = (int) (SCREEN_WIDTH * (380.0/SCREEN_WIDTH));

    public static Dimension ButtonSize() {
        return BUTTON_SIZE;
    }


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
        mainPanel.add(leftInteractionPanel, "dock west");
        //mainPanel.add(gamePanel, ", gapleft "+(int) (SCREEN_WIDTH*(600.0/SCREEN_WIDTH))+", gapright "+(int) (SCREEN_WIDTH*(200.0/SCREEN_WIDTH)));
        mainPanel.add(gamePanel, "dock center");
        mainPanel.add(rightInteractionPanel, "dock east");
        add(mainPanel);


        leftInteractionPanel.add(otherBoardsPanel, "Wrap");
        leftInteractionPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));


        gamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gamePanel.add(boardHeader, "wrap, align center, span 1 1");
        gamePanel.add(resourcePromptTextPanel, "wrap, align center,");
        gamePanel.add(boardMatrixPanel, " wrap, align center , w "+(int)(SCREEN_WIDTH*(900.0/SCREEN_WIDTH))+"!, h " + (int)(SCREEN_HEIGHT*(900.0/SCREEN_HEIGHT)) + "!");
        gamePanel.add(userPromptPanel, "align center");
        userPromptPanel.add(resourceSelectionPanel, "dock center");
        userPromptPanel.add(YesOrNoPanel, "dock center");
        userPromptPanel.add(buildingSelectingPanel, "dock center");
        rightInteractionPanel.add(manualPanel, "Wrap, h "+(int)(SCREEN_HEIGHT * (550.0/SCREEN_HEIGHT))+"!");
        rightInteractionPanel.add(activeBuildingPanel, "Wrap, h "+(int)(SCREEN_HEIGHT * (300.0/SCREEN_HEIGHT))+"!");
        rightInteractionPanel.add(scorePanel, "h "+(int)(SCREEN_HEIGHT * (550.0/SCREEN_HEIGHT))+"!");
        rightInteractionPanel.setBorder(BorderFactory.createLineBorder(Color.pink));




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
        panel.add(playerLabel, "align center");
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        return panel;
    }
    private JPanel createResourceTextPanel() {
        JPanel panel = new JPanel(new MigLayout());
        Font font = panel.getFont().deriveFont(Font.BOLD, 30f);

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

        panel.add(turnResourceText, "wrap, align center");
        panel.add(secondaryTextLabel, "Wrap, align center");
        panel.add(errorTextLabel, "align center");
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
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
        Font font = panel.getFont().deriveFont(Font.BOLD, 25f);
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
                    synchronized (Utility.getNotifier()) {
                        Utility.getNotifier().notify();
                    }
                });
                button.setText(building.toString());
                button.setFont(panel.getFont().deriveFont(Font.BOLD, 25f));
                button.setPreferredSize(new Dimension(300,50));
                selectionPanel.add(button);
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
        panel.add(yesButton);
        panel.add(noButton);
        return panel;
    }
    private JPanel createPlayerViewPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel label = new JLabel("Other Players");
        label.setFont(panel.getFont().deriveFont(Font.BOLD, 30f));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, "dock center, align center, wrap, w "+BoardUI.INTERACTIVE_PANEL_WIDTH);
        ArrayList<Board> boards = board.getPlayerManager().getBoards();
        for (Board board : boards) {
            if (board != this.board) {
                JButton button = new JButton(board.getBoardName());
                button.setFont(panel.getFont().deriveFont(24f));
                button.setHorizontalAlignment(SwingConstants.CENTER);
                TileButton[][] copiedMatrix = tileAccessMatrix;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        System.out.println("Mouse in.");
                        tileAccessMatrix = board.getBoardUI().getTileAccessMatrix();
                        try {
                            board.updateBoard();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        mainPanel.updateUI();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        tileAccessMatrix = copiedMatrix;
                        try {
                            board.updateBoard();
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
    public void setResourceSelectionLabel(String string, Color color) {
        resourceSelectionLabel.setText(string);
        resourceSelectionLabel.setForeground(color);
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
