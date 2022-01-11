package TownBuilder;

import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.UI.InitializationUI;
import com.diogonunes.jcolor.Attribute;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GameInitializer {


    private final ArrayList<Building> buildingsForGame = new ArrayList<>();
    HashMap<ColorEnum, ArrayList<Building>> buildingMasterList;

    public InitializationUI getInitializationUI() {
        return initializationUI;
    }

    private final InitializationUI initializationUI;
    public static final ColorEnum[] colors = new ColorEnum[]{ColorEnum.BLUE, ColorEnum.RED, ColorEnum.GRAY, ColorEnum.ORANGE, ColorEnum.GREEN, ColorEnum.YELLOW, ColorEnum.WHITE};


    public GameInitializer() throws IOException {
        BuildingFactory.setbuildingMasterList();
        buildingMasterList = BuildingFactory.getBuildingMasterList();
        initializationUI = new InitializationUI();
        Driver.getGameFrame().add(initializationUI);
        Driver.initFrame();
    }


    public ArrayList<Building> getBuildingsForGame() {
        return buildingsForGame;
    }
    public void buildingSelection() throws IOException {
        synchronized (Utility.getNotifier()) {
            try {
                Utility.getNotifier().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        switch (initializationUI.getSelection()) {
            case "default" -> defaultGame();
            case "custom" -> customGame();
            case "random" -> randomGame();
        }
    }
        private void defaultGame() throws IOException {
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.COTTAGE, buildingsForGame, -1, -1, false, null));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.FARM, buildingsForGame, -1, -1, false, null));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.WELL, buildingsForGame, -1, -1, false, null));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.CHAPEL, buildingsForGame, -1, -1, false, null));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.TAVERN, buildingsForGame, -1, -1, false, null));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.THEATER, buildingsForGame, -1, -1, false, null));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.WAREHOUSE, buildingsForGame, -1, -1, false, null));
        }
        private void customGame() throws IOException {
            initializationUI.getMainMenuPanel().setVisible(false);
            initializationUI.getCustomSelectionPanel().setVisible(true);
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.COTTAGE, buildingsForGame, -1, -1, false, null));
            for (int i = 0; i < initializationUI.getColoredBuildingViews().size(); i++) {
                JPanel colorView = initializationUI.getColoredBuildingViews().get(i);
                initializationUI.getCustomSelectionPanel().add(colorView, "dock center");
                colorView.setVisible(true);
                System.out.println("Started notifier");
               synchronized (Utility.getNotifier()) {
                   try {
                       Utility.getNotifier().wait();
                   }
                   catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               System.out.println("Stopped notifier");
               buildingsForGame.add(BuildingFactory.getBuilding(initializationUI.getBuildingSelection(), buildingsForGame, -1, -1, false, null));
                initializationUI.getCustomSelectionPanel().remove(colorView);
               initializationUI.updateUI();
            }
            initializationUI.getCustomSelectionPanel().setVisible(false);
        }
        private void randomGame() throws IOException {
            BuildingFactory.setbuildingMasterList();
            for (ColorEnum color : colors) {
                ArrayList<Building> buildings = buildingMasterList.get(color);
                if (buildings.size() > 1) {
                    int randomIndex = (int) (Math.random() * buildings.size());
                    Building building = buildings.get(randomIndex);
                    buildingsForGame.add(BuildingFactory.getBuilding(building.getType(), buildingsForGame, -1, -1, false, null));
                } else {
                    buildingsForGame.add(BuildingFactory.getBuilding(buildings.get(0).getType(), buildingsForGame, -1, -1, false, null));
                }
            }
        }
}
