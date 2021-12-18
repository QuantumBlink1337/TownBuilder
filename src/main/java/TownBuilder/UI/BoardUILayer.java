package TownBuilder.UI;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class BoardUILayer extends JFrame {


        JPanel mainPanel = new JPanel(new MigLayout("","[center][right][left][c]","[top][center][b]"));
        private final JPanel turnPanel = createTurnPanel();
        private final JPanel resourceLabelPanel = createResourceLabelPanel();

        TileButton[][] tileAccessMatrix = new TileButton[4][4];
        public BoardUILayer() {
            setSize(1920, 1080);
            buildBoardTiles();
            mainPanel.add(turnPanel, "dock north");
            mainPanel.add(resourceLabelPanel, "dock south");
            add(mainPanel);

        }
        private void buildBoardTiles() {
            JPanel tilePanel = new JPanel(new GridLayout(4, 4, 2, 0));
            for (int r = 0; r < tileAccessMatrix.length; r++) {
                for (int c = 0; c < tileAccessMatrix[r].length; c++) {
                    TileButton temp = new TileButton(r, c);
                    tileAccessMatrix[r][c] = temp;
                    temp.addActionListener(temp);
                    temp.setPreferredSize(new Dimension(75, 75));
                    tilePanel.add(temp);
                }
            }
            mainPanel.add(tilePanel, "dock center");
        }
        public int[] listenForTilePress() {
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
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        Font font = panel.getFont().deriveFont(Font.BOLD, 36f);

        JLabel label = new JLabel("Select a Resource");
        label.setFont(font);
        panel.add(label);

        return panel;
    }
        private JPanel createTurnPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        Font font = panel.getFont().deriveFont(Font.BOLD, 36f);

        JLabel playerLabel = new JLabel("Player's Turn");
        playerLabel.setFont(font);
        panel.add(playerLabel);

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
