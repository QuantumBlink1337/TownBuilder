package TownBuilder.UI;
import javax.swing.*;
import java.awt.*;

public class BoardUILayer extends JFrame {


        JPanel masterUIPanel = new JPanel(new BorderLayout());
        GridBagLayout tileLayout = new GridBagLayout();
        GridBagConstraints tileLayoutConstraints = new GridBagConstraints();
        JPanel tilePanel = new JPanel(tileLayout);
        JPanel infoPanel = new JPanel();
        private final JLabel errorLabel = new JLabel();
        TileButton[][] tileAccessMatrix = new TileButton[4][4];
        public BoardUILayer() {
            setSize(1000, 1000);
            buildBoardTiles();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
            infoPanel.add(errorLabel);
            masterUIPanel.add(tilePanel,BorderLayout.CENTER);
            masterUIPanel.add(infoPanel, BorderLayout.SOUTH);
            this.add(masterUIPanel);

        }
        private void buildBoardTiles() {
            for (int r = 0; r < tileAccessMatrix.length; r++) {
                for (int c = 0; c < tileAccessMatrix[r].length; c++) {
                    TileButton temp = new TileButton(r, c);
                    tileAccessMatrix[r][c] = temp;
                    tileLayoutConstraints.ipadx = 100;
                    tileLayoutConstraints.ipady = 100;
                    tileLayoutConstraints.gridx = r;
                    tileLayoutConstraints.gridy = c;
                    temp.addActionListener(temp);
                    tilePanel.add(temp, tileLayoutConstraints);
                }
            }
        }
        public TileButton listenForResourcePlacement(String string) {
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
            return button;
        }
        public void failedResourcePlacement(int error) {
            switch (error) {
                case 1:
                    errorLabel.setText("A resource is already on that tile!");
                    break;
                case 2:
                    errorLabel.setText("A building is already on that tile!");
                    break;
                default:
                    errorLabel.setText("Failed resource placement");
                    break;

            }
        }
        public void clearErrorLabel() {
            errorLabel.setText("");
        }

}
