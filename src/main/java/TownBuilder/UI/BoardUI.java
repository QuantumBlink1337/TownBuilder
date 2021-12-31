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
    JLabel turnResourceText;
    final JPanel turnPanel = createTurnPanel();
    final JPanel resourceLabelPanel = createResourceLabelPanel();
    final JPanel resourceButtonPanel = createResourceButtonPanel();
    final JPanel resourceSelectionPanel = createResourceSelectionButtonPanel();
    final JPanel resourcePromptTextPanel = createResourceTextPanel();
    final ManualUI manualPanel;
    static final Dimension BUTTON_SIZE = new Dimension(50, 50);

    public static Dimension ButtonSize() {
        return BUTTON_SIZE;

    }

    private final int PLAYER_SELECTION_ROW_CONSTANT = 7;

    public BoardUI(ArrayList<Building> buildingsForGame) {
        setSize(1920, 1080);

        manualPanel = new ManualUI(buildingsForGame);

        mainPanel.add(resourceButtonPanel, "dock center, w 800!,h 800!");
        mainPanel.add(manualPanel, "dock east, gapright 30");
        mainPanel.add(turnPanel, "dock north");
        mainPanel.add(resourcePromptTextPanel, "dock north");
        mainPanel.add(resourceSelectionPanel, " w 1000!, h 100!, dock south, align center");
        mainPanel.add(resourceLabelPanel, "dock south");

        add(mainPanel);

    }
    public TileButton[][] getTileAccessMatrix() {
        return tileAccessMatrix;
    }
    private JPanel createResourceButtonPanel() {
        JPanel tilePanel = new JPanel(new GridLayout(4, 4, 2, 0));
        for (int r = 0; r < tileAccessMatrix.length; r++) {
            for (int c = 0; c < tileAccessMatrix[r].length; c++) {
                TileButton temp = new TileButton(r, c);
                tileAccessMatrix[r][c] = temp;
                temp.addActionListener(temp);
                temp.setPreferredSize(BUTTON_SIZE);
                tilePanel.add(temp);
            }
        }
        return tilePanel;
    }
    public int[] ClickedButtonCoordinates() {
        TileButton button = null;
        while (button == null) {
            for (TileButton[] tileButtons : tileAccessMatrix) {
                for (TileButton tileButton : tileButtons) {
                    if (tileButton.isClicked()) {
                        button = tileButton;
                    }
                }
            }
        }
        button.setClicked(false);
        return new int[]{button.getRow(), button.getCol()};
    }
    private JPanel createResourceLabelPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        //panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Font font = panel.getFont().deriveFont(Font.BOLD, 36f);
        JLabel label = new JLabel("Select a Resource");
        label.setFont(font);
        panel.add(label);
        return panel;
    }
    private JPanel createTurnPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Font font = panel.getFont().deriveFont(Font.BOLD, 42f);
        JLabel playerLabel = new JLabel("Player's Turn");
        playerLabel.setFont(font);
        panel.add(playerLabel);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        return panel;
    }
    private JPanel createResourceTextPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        Font font = panel.getFont().deriveFont(Font.BOLD, 30f);
        turnResourceText = new JLabel("Your resource for this turn is Glass.");
        turnResourceText.setFont(font);
        panel.add(turnResourceText);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        return panel;
    }
    private JPanel createResourceSelectionButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 20, 0));
        ArrayList<ResourceEnum> resourceArray = new ArrayList<>(Arrays.asList(ResourceEnum.WOOD, ResourceEnum.BRICK, ResourceEnum.WHEAT, ResourceEnum.GLASS, ResourceEnum.STONE));
        for (int i = 0; i < 5; i++) {
            TileButton button = new TileButton(5, -1);
            button.addActionListener(button);
            button.setText(resourceArray.get(i).toString());
            button.setPreferredSize(new Dimension(200,50));
            panel.add(button);
        }
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        return panel;
    }


//        public void failedResourcePlacement(int error) {
//            selectionPanel.setVisible(false);
//            switch (error) {
//                case 1 -> errorLabel.setText("A resource is already on that tile!");
//                case 2 -> errorLabel.setText("A building is already on that tile!");
//                default -> errorLabel.setText("Failed resource placement");
//            }
//        }
//        public void clearErrorLabel() {
//            errorLabel.setText("");
//        }

}
