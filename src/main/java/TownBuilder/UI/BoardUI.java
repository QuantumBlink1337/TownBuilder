package TownBuilder.UI;
import TownBuilder.Buildings.Building;
import TownBuilder.ResourceEnum;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BoardUI extends JFrame {

    final JPanel mainPanel = new JPanel(new MigLayout("","[center][right][left][c]","[top][center][b]"));



    private final TileButton[][] tileAccessMatrix = new TileButton[4][4];
    private final String playerName;
    private String selectedResourceForTurn;
    private volatile int[] selectedCoords;
    
    private boolean inputReceived;
    JLabel turnResourceText;
    final JPanel turnPanel;
    final JPanel boardMatrixPanel;
    final JPanel resourceSelectionPanel;
    final JPanel resourcePromptTextPanel;
    final ManualUI manualPanel;
    static final Dimension BUTTON_SIZE = new Dimension(50, 50);

    public static Dimension ButtonSize() {
        return BUTTON_SIZE;
    }

    private final int PLAYER_SELECTION_ROW_CONSTANT = 7;

    public BoardUI(String playerName, ArrayList<Building> buildingsForGame) {
        this.playerName = playerName;
        setSize(1920, 1080);
        manualPanel = new ManualUI(buildingsForGame);
        turnPanel = createTurnPanel();
        boardMatrixPanel = createBoardMatrix();
        resourceSelectionPanel = createResourceSelectionButtonPanel();
        resourcePromptTextPanel = createResourceTextPanel();
        resourcePromptTextPanel.setVisible(false);
        mainPanel.add(boardMatrixPanel, "dock center, w 800!,h 800!");
        mainPanel.add(manualPanel, "dock east, gapright 30");
        mainPanel.add(turnPanel, "dock north");
        mainPanel.add(resourcePromptTextPanel, "dock north");
        mainPanel.add(resourceSelectionPanel, " w 1000!, h 100!, dock south, align center");
        //mainPanel.add(resourceLabelPanel, "dock south");

        add(mainPanel);

    }
    public TileButton[][] getTileAccessMatrix() {
        return tileAccessMatrix;
    }

    public int[] getSelectedCoords() {
        return selectedCoords;
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
                        inputReceived = true;
                        selectedCoords = temp.getCoords();
                });
            }
        }
        return tilePanel;
    }
    public int[] getUserInputOfBoard() {
        selectedCoords = null;
        while (selectedCoords == null) {
            Thread.onSpinWait();
        }
        return selectedCoords;
    }



    private JPanel createTurnPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Font font = panel.getFont().deriveFont(Font.BOLD, 42f);
        System.out.println(playerName);
        JLabel playerLabel = new JLabel(playerName+"'s Turn");
        playerLabel.setFont(font);
        panel.add(playerLabel);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        return panel;
    }
    private JPanel createResourceTextPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        Font font = panel.getFont().deriveFont(Font.BOLD, 30f);
        turnResourceText = new JLabel("Your resource for this turn is "+ selectedResourceForTurn+ ".");
        JLabel label = new JLabel("Where would you like to place it?");
        label.setFont(font);
        turnResourceText.setFont(font);
        panel.add(turnResourceText, "wrap");
        panel.add(label, "wrap");
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        return panel;
    }
    private JPanel createResourceSelectionButtonPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JPanel selectionPanel = new JPanel(new GridLayout(1, 5, 20, 0));
        Font font = panel.getFont().deriveFont(Font.BOLD, 36f);
        JLabel label = new JLabel("Select a Resource");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(font);
        panel.add(label, "dock center, wrap");
        ArrayList<ResourceEnum> resourceArray = new ArrayList<>(Arrays.asList(ResourceEnum.WOOD, ResourceEnum.BRICK, ResourceEnum.WHEAT, ResourceEnum.GLASS, ResourceEnum.STONE));
        for (int i = 0; i < 5; i++) {
            JButton button = new JButton();
            button.setBackground(resourceArray.get(i).getColor().getOverallColor());
            button.addActionListener(e -> {
                selectedResourceForTurn = button.getText();
                updateResourceTextLabel();
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
        return panel;
    }
    private void updateResourceTextLabel() {
        turnResourceText.setText("Your resource for this turn is "+ selectedResourceForTurn+ ".");
    }
    public void setSelectedResourceForTurn(String string) {
        selectedResourceForTurn = string;
    }




}
