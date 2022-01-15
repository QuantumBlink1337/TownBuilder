package TownBuilder.UI;

import TownBuilder.Board;
import TownBuilder.Buildings.*;
import TownBuilder.ResourceEnum;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ActiveBuildingsUI extends JPanel {
    ArrayList<Building> buildings;

    String choice;
    Board board;
    TileButton[][] tileAccessMatrix;

    JPanel mainActiveBuildingPanel;
    JPanel mainPanel;
    final BuildingEnum gameActiveBuilding;
    final BoardUI boardUI;

    public ActiveBuildingsUI(TileButton[][] tB, Board b, BoardUI boardUIPanel) {
        boardUI = boardUIPanel;
        board = b;
        buildings = board.getScorableBuildings();
        gameActiveBuilding = buildings.get(buildings.size()-2).getType();
        tileAccessMatrix = tB;
        generateMainActiveBuildingsPanel();
        setBorder(new LineBorder(Color.BLACK, 2));
        add(mainPanel);

    }
    private void generateMainActiveBuildingsPanel() {
        mainPanel = new JPanel(new MigLayout());
        mainActiveBuildingPanel = new JPanel(new MigLayout());
        JLabel label = new JLabel("Active Buildings");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        Font headerFont = getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(36f));
        label.setFont(headerFont);
        mainPanel.add(label, "align center, wrap, w "+ UI_Utilities.INTERACTIVE_PANEL_WIDTH+"!");
        mainPanel.add(mainActiveBuildingPanel, "dock center, Wrap");
    }

    public void updateActiveBuildings() {
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
                for (TileButton activeTileButton : activeTileButtons) {
                    JButton button = new JButton(gameActiveBuilding.toString());
                    Font smallFont = mainPanel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(25f));
                    button.setFont(smallFont);
                    JPanel individualView;
                    switch (gameActiveBuilding) {
                        case WAREHOUSE -> individualView = generateIndividualWarehouseView(activeTileButton.getCoords());
                        case FACTORY -> individualView = generateIndividualFactoryView(activeTileButton.getCoords());
                        case BANK -> individualView = generateIndividualBankView(activeTileButton.getCoords());
                        default -> individualView = new JPanel();
                    }
                    button.addActionListener(e -> {
                        mainPanel.remove(mainActiveBuildingPanel);
                        mainPanel.add(individualView, "dock center, w " + UI_Utilities.INTERACTIVE_PANEL_WIDTH+"!");
                        activeTileButton.setBackground(activeTileButton.getBuildingEnum().getColor().getOverallColor());
                        updateUI();
                    });
                    button.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            activeTileButton.setBackground(new Color(220, 105, 105, 255));
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            activeTileButton.setBackground(activeTileButton.getBuildingEnum().getColor().getOverallColor());
                        }
                    });
                    mainActiveBuildingPanel.add(button, "wrap, dock center, h " + UI_Utilities.convertIntToPercentString(35, false)+"!");
                }
            }
    }
    private JPanel generateIndividualWarehouseView(int[] coords) {
        JPanel panel = new JPanel(new MigLayout("", "[][][]", "[]0[][]"));
        JButton exitButton = new JButton("EXIT");
        JLabel inventoryLabel = new JLabel("Inventory");
        inventoryLabel.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(30f)));
        inventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
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
        Font font = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(25f));
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
        JPanel inventoryPanel = new JPanel(new MigLayout());
        inventoryPanel.add(inventoryLabel, "wrap, dock center, align center");
        inventoryPanel.add(inventoryView, "dock center, align center");
        panel.add(inventoryPanel, "dock center, align center, wrap");
        String sizeControl = "w "+UI_Utilities.convertIntToPercentString(180, true)+"!, h "+ UI_Utilities.convertIntToPercentString(40, false)+"!";
        panel.add(swapButton, "split 2, align center, "+sizeControl);
        swapButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(placeButton, sizeControl+",wrap");
        placeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        exitButton.addActionListener(e -> {
            mainPanel.remove(panel);
            mainPanel.add(mainActiveBuildingPanel, "dock center");
            updateUI();
        });
        panel.add(exitButton, "dock center, h " + UI_Utilities.convertIntToPercentString(40, false) + "!");
        return panel;
    }
    private JPanel generateIndividualFactoryView(int[] coords) {
        JPanel panel = new JPanel(new MigLayout());
        JButton exitButton = new JButton("EXIT");
        JLabel inventoryLabel = new JLabel("Chosen Resource");
        inventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inventoryLabel.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(30f)));
        JPanel inventoryView = new JPanel(new MigLayout());
        Factory building = (Factory) board.getGameBuildingBoard()[coords[0]][coords[1]];
        ResourceEnum selectedFactoryResource = building.getFactorizedResource();
        JLabel factoryResourceLabel = new JLabel(selectedFactoryResource.toString());
        factoryResourceLabel.setBackground(selectedFactoryResource.getColor().getOverallColor());
        factoryResourceLabel.setOpaque(true);
        factoryResourceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        factoryResourceLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        inventoryView.add(inventoryLabel, "dock center, wrap");
        inventoryView.add(factoryResourceLabel, "dock center");
        panel.add(inventoryView, "wrap, dock center");
        JButton swapButton = new JButton("Swap");
        Font font = panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(25f));
        exitButton.setFont(font);
        swapButton.setFont(font);
        swapButton.addActionListener(e -> {
            boardUI.setActiveBuildingToggled(true);
            boardUI.setActiveMode("exchange");
            boardUI.setSelectedActiveBuilding(building);
            boardUI.setCoordinatesClicked(false);
            synchronized (board.getNotifier()) {
                board.getNotifier().notify();
            }
        });
        panel.add(swapButton, "wrap, dock center");
        exitButton.addActionListener(e -> {
            mainPanel.remove(panel);
            mainPanel.add(mainActiveBuildingPanel, "dock center");
            updateUI();
        });
        panel.add(exitButton, "dock center, h " + UI_Utilities.convertIntToPercentString(40, false) + "!");
        return panel;
    }
    private JPanel generateIndividualBankView(int[] coords) {
        JPanel panel = new JPanel(new MigLayout());
        JButton exitButton = new JButton("EXIT");
        exitButton.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(25f)));
        JLabel inventoryLabel = new JLabel("Chosen Resource");
        inventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inventoryLabel.setFont(panel.getFont().deriveFont(Font.BOLD, UI_Utilities.convertFontSize(30f)));
        JPanel inventoryView = new JPanel(new MigLayout());
        Bank building = (Bank) board.getGameBuildingBoard()[coords[0]][coords[1]];
        ResourceEnum selectedFactoryResource = building.getLockedResource();
        JLabel factoryResourceLabel = new JLabel(selectedFactoryResource.toString());
        factoryResourceLabel.setBackground(selectedFactoryResource.getColor().getOverallColor());
        factoryResourceLabel.setOpaque(true);
        factoryResourceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inventoryView.add(inventoryLabel, "dock center, wrap");
        inventoryView.add(factoryResourceLabel, "dock center");
        panel.add(inventoryView, "wrap, dock center");
        exitButton.addActionListener(e -> {
            mainPanel.remove(panel);
            mainPanel.add(mainActiveBuildingPanel, "dock center");
            updateUI();
        });
        panel.add(exitButton, "dock center, h " + UI_Utilities.convertIntToPercentString(40, false) + "!");
        return panel;
    }
}
