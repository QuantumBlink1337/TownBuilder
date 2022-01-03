package TownBuilder.UI;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.Warehouse;
import TownBuilder.ResourceEnum;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ActiveBuildingsUI extends JPanel {
    ArrayList<Building> buildings;
    int count = 0;

    public String getChoice() {
        return choice;
    }

    String choice;
    Board board;
    TileButton[][] tileAccessMatrix;
    JPanel activeBuildings;

    public JPanel getMainActiveBuildingPanel() {
        return mainActiveBuildingPanel;
    }

    JPanel mainActiveBuildingPanel;
    JPanel mainPanel;
    final BuildingEnum gameActiveBuilding;

    public ActiveBuildingsUI(TileButton[][] tB, Board b) {
        mainPanel = new JPanel(new MigLayout());
        board = b;
        buildings = board.getScorableBuildings();
        gameActiveBuilding = buildings.get(buildings.size()-2).getType();
        System.out.println(gameActiveBuilding);
        tileAccessMatrix = tB;
        mainActiveBuildingPanel = createActiveBuildingPanel();

        JLabel label = new JLabel("Active Buildings");
        label.setPreferredSize(new Dimension(380, 40));
        label.setMaximumSize(new Dimension(380, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        Font headerFont = getFont().deriveFont(Font.BOLD, 30f);
        label.setFont(headerFont);
        label.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        setBorder(BorderFactory.createLineBorder(Color.green));
        mainPanel.add(label, "dock north, wrap, cell  1 0");
        mainPanel.add(mainActiveBuildingPanel, "Wrap");
        add(mainPanel);

    }
    private JPanel createActiveBuildingPanel() {
        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.blue));
        updateActiveBuildings(panel);
        panel.add(activeBuildings);
        panel.setBorder(BorderFactory.createLineBorder(Color.red));
        return panel;
    }
    public void updateActiveBuildings(JPanel mainPanel) {
        ArrayList<TileButton> activeTileButtons = new ArrayList<>();

        for (TileButton[] tileButtons : tileAccessMatrix) {
            for (TileButton tileButton : tileButtons) {
                //System.out.println("Checking button at " + Arrays.toString(tileButton.getCoords()));
                if (tileButton.buildingEnum == gameActiveBuilding && !tileButton.isActiveBuilding()) {
                    activeTileButtons.add(tileButton);
                    tileButton.setActiveBuilding(true);
                    System.out.println("Fire!" + Arrays.toString(tileButton.getCoords()));
                }
            }
        }
            activeBuildings = new JPanel(new MigLayout());
            if (activeTileButtons.size() > 0) {
                activeBuildings.setBorder(BorderFactory.createLineBorder(Color.blue));
                for (TileButton activeTileButton : activeTileButtons) {
                    System.out.println("Fire two");
                    JButton button = new JButton(gameActiveBuilding.toString());
                    Font smallFont = mainPanel.getFont().deriveFont(Font.BOLD, 25f);
                    button.setFont(smallFont);
                    JPanel individualView = generateIndividualActiveBuildingView(activeTileButton.getCoords());
                    button.addActionListener(e -> {
                        mainActiveBuildingPanel.remove(activeBuildings);
                        mainActiveBuildingPanel.add(individualView);
                        updateUI();
                    });
                    activeBuildings.add(button, "Wrap");
                }
                mainPanel.add(activeBuildings);
            }
    }
    private JPanel generateIndividualActiveBuildingView(int[] coords) {
        JPanel panel = new JPanel(new MigLayout());
        JButton exitButton = new JButton("EXIT");
        if (gameActiveBuilding == BuildingEnum.WAREHOUSE) {
            JLabel inventory = new JLabel("Inventory");
            inventory.setFont(panel.getFont().deriveFont(Font.BOLD, 30f));
            JPanel inventoryView = new JPanel(new GridLayout(1, 3, 0, 0));
            Warehouse building = (Warehouse) board.getGameBuildingBoard()[coords[0]][coords[1]];
            for (ResourceEnum resourceEnum : building.getStoredResources()) {
                JButton button = new JButton(resourceEnum.toString());
                button.setPreferredSize(new Dimension(25, 25));
                button.setBackground(resourceEnum.getColor().getOverallColor());
                inventoryView.add(button);
            }
            JButton swapButton = new JButton("Swap");
            JButton placeButton = new JButton("Place");
            Font font = panel.getFont().deriveFont(Font.BOLD, 25f);
            swapButton.setFont(font);
            placeButton.setFont(font);
            swapButton.addActionListener(e -> choice = "swap");
            placeButton.addActionListener(e -> choice = "place");
            panel.add(inventory, "wrap, align center");
            panel.add(inventoryView, "wrap, align center");
            panel.add(swapButton);
            panel.add(placeButton);
        }
        exitButton.addActionListener(e -> {
            mainActiveBuildingPanel.remove(panel);
            mainActiveBuildingPanel.add(activeBuildings);
            updateUI();
        });
        panel.add(exitButton);
        return panel;
    }
}
