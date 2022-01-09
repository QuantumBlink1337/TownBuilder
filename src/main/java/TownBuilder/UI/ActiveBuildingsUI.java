package TownBuilder.UI;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.Warehouse;
import TownBuilder.ResourceEnum;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class ActiveBuildingsUI extends JPanel {
    ArrayList<Building> buildings;

    public String getChoice() {
        return choice;
    }

    String choice;
    Board board;
    TileButton[][] tileAccessMatrix;

    public JPanel getMainActiveBuildingPanel() {
        return mainActiveBuildingPanel;
    }

    JPanel mainActiveBuildingPanel;
    JPanel mainPanel;
    final BuildingEnum gameActiveBuilding;
    final BoardUI boardUI;

    public ActiveBuildingsUI(TileButton[][] tB, Board b, BoardUI boardUIPanel) {
        boardUI = boardUIPanel;
        mainPanel = new JPanel(new MigLayout());
        board = b;
        buildings = board.getScorableBuildings();
        gameActiveBuilding = buildings.get(buildings.size()-2).getType();
        System.out.println(gameActiveBuilding);
        tileAccessMatrix = tB;
        mainActiveBuildingPanel = new JPanel(new MigLayout());
        JLabel label = new JLabel("Active Buildings");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        Font headerFont = getFont().deriveFont(Font.BOLD, 30f);
        label.setFont(headerFont);
        label.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        setBorder(BorderFactory.createLineBorder(Color.RED));
        mainPanel.add(label, "align center, wrap, w :380:380, h :40:40");
        mainPanel.add(mainActiveBuildingPanel, "dock center, Wrap");
        add(mainPanel);

    }

    public void updateActiveBuildings() throws IOException {
        mainActiveBuildingPanel.removeAll();
        ArrayList<TileButton> activeTileButtons = new ArrayList<>();
        for (TileButton[] tileButtons : tileAccessMatrix) {
            for (TileButton tileButton : tileButtons) {
                if (tileButton.buildingEnum == gameActiveBuilding) {
                    activeTileButtons.add(tileButton);
                    tileButton.setActiveBuilding(true);
                }
            }
        }
            if (activeTileButtons.size() > 0) {
                mainActiveBuildingPanel.setBorder(BorderFactory.createLineBorder(new Color(66, 29, 117)));
                for (TileButton activeTileButton : activeTileButtons) {
                    JButton button = new JButton(gameActiveBuilding.toString());
                    Font smallFont = mainPanel.getFont().deriveFont(Font.BOLD, 25f);
                    button.setFont(smallFont);
                    JPanel individualView = generateIndividualActiveBuildingView(activeTileButton.getCoords());
                    button.addActionListener(e -> {
                        mainPanel.remove(mainActiveBuildingPanel);
                        mainPanel.add(individualView, "dock center");
                        activeTileButton.setBackground(activeTileButton.getBuildingEnum().getColor().getOverallColor());

                        updateUI();
                    });
                    button.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            activeTileButton.setBackground(new Color(210, 124, 124, 255));
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            activeTileButton.setBackground(activeTileButton.getBuildingEnum().getColor().getOverallColor());
                        }
                    });
                    mainActiveBuildingPanel.add(button, "wrap, dock center");
                }
            }
    }
    private JPanel generateIndividualActiveBuildingView(int[] coords) throws IOException {
        JPanel panel = new JPanel(new MigLayout());
        JButton exitButton = new JButton("EXIT");
        if (gameActiveBuilding == BuildingEnum.WAREHOUSE) {
            JLabel inventoryLabel = new JLabel("Inventory");
            inventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
            inventoryLabel.setFont(panel.getFont().deriveFont(Font.BOLD, 30f));
            JPanel inventoryView = new JPanel(new GridLayout(1, 3, 0, 0));
            Warehouse building = (Warehouse) board.getGameBuildingBoard()[coords[0]][coords[1]];
            for (ResourceEnum resourceEnum : building.getStoredResources()) {
                JLabel button = new JLabel(resourceEnum.toString());
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                button.setOpaque(true);
                button.setBackground(resourceEnum.getColor().getOverallColor());
                inventoryView.add(button);
            }
            JButton swapButton = new JButton("Swap");

            JButton placeButton = new JButton("Place");
            Font font = panel.getFont().deriveFont(Font.BOLD, 25f);
            swapButton.setFont(font);
            placeButton.setFont(font);

            swapButton.addActionListener(e -> {
                boardUI.setActiveBuildingToggled(true);
                boardUI.setActiveMode("swap");
                boardUI.setSelectedActiveBuilding(building);
                boardUI.setCoordinatesClicked(false);
                synchronized (board.getNotifier()) {
                    board.getNotifier().notify();
                }
            });
            placeButton.addActionListener(e -> {
                boardUI.setActiveBuildingToggled(true);
                boardUI.setActiveMode("place");
                boardUI.setSelectedActiveBuilding(building);
                boardUI.setCoordinatesClicked(false);
                synchronized (board.getNotifier()) {
                    board.getNotifier().notify();
                }
            });
            exitButton.setFont(font);
            swapButton.addActionListener(e -> choice = "swap");
            placeButton.addActionListener(e -> choice = "place");
            panel.setBorder(BorderFactory.createLineBorder(Color.red));
            JPanel inventoryPanel = new JPanel(new MigLayout());
            inventoryPanel.add(inventoryLabel, "wrap, dock center, align center");
            inventoryPanel.add(inventoryView, "dock center, align center");
            inventoryLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            inventoryView.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            panel.add(inventoryPanel, "dock center, align center, wrap");
            String sizeControl = "w 180!, h 40!";
            panel.add(swapButton, "split 2, align center, "+sizeControl);
            swapButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.add(placeButton, sizeControl+",wrap");
            placeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        exitButton.addActionListener(e -> {
            mainPanel.remove(panel);
            mainPanel.add(mainActiveBuildingPanel, "dock center");
            updateUI();
        });
        panel.add(exitButton, "growx, h 40!");
        return panel;
    }
}
