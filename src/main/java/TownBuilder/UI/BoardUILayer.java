package TownBuilder.UI;
import javax.swing.*;
import java.awt.*;

public class BoardUILayer extends JFrame {


        JPanel masterUIPanel = new JPanel(new BorderLayout());
        GridBagLayout tileLayout = new GridBagLayout();
        GridBagConstraints tileLayoutConstraints = new GridBagConstraints();
        JPanel tilePanel = new JPanel(tileLayout);
        JPanel infoPanel = new JPanel();
        JPanel selectionPanel = new JPanel(tileLayout);
        private final JLabel errorLabel = new JLabel();
        TileButton[][] tileAccessMatrix = new TileButton[4][4];
        public BoardUILayer() {
            setSize(1920, 1080);
            buildBoardTiles();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
            infoPanel.add(errorLabel);
            //masterUIPanel.add(infoPanel, BorderLayout.SOUTH);
            this.add(tilePanel, BorderLayout.CENTER);

        }
        private void buildBoardTiles() {
            for (int r = 0; r < tileAccessMatrix.length; r++) {
                for (int c = 0; c < tileAccessMatrix[r].length; c++) {
                    TileButton temp = new TileButton(r, c);
                    tileAccessMatrix[r][c] = temp;
                    tileLayoutConstraints.ipadx = 72;
                    tileLayoutConstraints.ipady = 72;
                    tileLayoutConstraints.gridx = r;
                    tileLayoutConstraints.gridy = c;
                    temp.addActionListener(temp);
                    tilePanel.add(temp, tileLayoutConstraints);
                }
            }
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
        public void triggerResourcePlacementPrompt() {
            final JLabel resourceTextLabel = new JLabel("What resource would you like?");
            tileLayoutConstraints.ipadx = 0;
            tileLayoutConstraints.ipady = 400;
            tileLayoutConstraints.gridx = 50;
            tileLayoutConstraints.gridy = 50;
            selectionPanel.add(resourceTextLabel, tileLayoutConstraints);
            this.add(selectionPanel, BorderLayout.SOUTH);

        }
        public void failedResourcePlacement(int error) {
            switch (error) {
                case 1 -> errorLabel.setText("A resource is already on that tile!");
                case 2 -> errorLabel.setText("A building is already on that tile!");
                default -> errorLabel.setText("Failed resource placement");
            }
        }
        public void clearErrorLabel() {
            errorLabel.setText("");
        }

}
