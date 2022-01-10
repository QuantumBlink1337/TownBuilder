package TownBuilder;

import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.UI.InitializationUI;
import com.diogonunes.jcolor.Attribute;

import javax.swing.*;
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
    private final ColorEnum[] colors = new ColorEnum[]{ColorEnum.BLUE, ColorEnum.RED, ColorEnum.GRAY, ColorEnum.ORANGE, ColorEnum.GREEN, ColorEnum.YELLOW, ColorEnum.WHITE};


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


//        if (isCustomGame) {
//
//            for (ColorEnum color : colors) {
//                ArrayList<Building> coloredBuildings = buildingMasterList.get(color);
//                isUserInputValid = false;
//                if (coloredBuildings.size() > 1) {
//                    do {
//                        System.out.println("What " + color + " building would you like for your game?");
//                        System.out.println("Available choices include:");
//                        Utility.printBuildingsInList(coloredBuildings);
//                        userInput = sc.nextLine().toLowerCase();
//                        for (Building coloredBuilding : coloredBuildings) {
//                            if (userInput.equals(coloredBuilding.toString().toLowerCase())) {
//                                buildingsForGame.add(BuildingFactory.getBuilding(coloredBuilding.getType(), buildingsForGame, -1, -1, false, null));
//                                isUserInputValid = true;
//                            }
//                        }
//                    }
//                    while (!isUserInputValid);
//                } else {
//                    buildingsForGame.add(coloredBuildings.get(0));
//                }
//
//            }
//        } else if (isRandomGame) {
////            BuildingFactory.setbuildingMasterList();
////            for (ColorEnum color : colors) {
////                ArrayList<Building> buildings = buildingMasterList.get(color);
////                if (buildings.size() > 1) {
////                    int randomIndex = (int) (Math.random() * buildings.size());
////                    Building building = buildings.get(randomIndex);
////                    buildingsForGame.add(BuildingFactory.getBuilding(building.getType(), buildingsForGame, -1, -1, false, null));
////                } else {
////                    buildingsForGame.add(BuildingFactory.getBuilding(buildings.get(0).getType(), buildingsForGame, -1, -1, false, null));
////                }
////            }
//        }
//        else {
////            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.COTTAGE, buildingsForGame, -1, -1, false, null));
////            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.FARM, buildingsForGame, -1, -1, false, null));
////            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.WELL, buildingsForGame, -1, -1, false, null));
////            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.CHAPEL, buildingsForGame, -1, -1, false, null));
////            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.TAVERN, buildingsForGame, -1, -1, false, null));
////            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.THEATER, buildingsForGame, -1, -1, false, null));
////            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.WAREHOUSE, buildingsForGame, -1, -1, false, null));
//        }
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
        private void customGame() {

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
