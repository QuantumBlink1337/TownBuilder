package TownBuilder;

import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import com.diogonunes.jcolor.Attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GameInitializer {


    private final ArrayList<Building> buildingsForGame = new ArrayList<>();

    public GameInitializer() {}


    public ArrayList<Building> getBuildingsForGame() {
        return buildingsForGame;
    }
    public void buildingSelection() {
        Scanner sc = new Scanner(System.in);

        HashMap<ColorEnum, ArrayList<Building>> buildingMasterList = BuildingFactory.getBuildingMasterList();
        String userInput;
        boolean isUserInputValid = false;
        boolean isCustomGame = false;
        boolean isRandomGame = false;
        ColorEnum[] colors = new ColorEnum[]{ColorEnum.BLUE, ColorEnum.RED, ColorEnum.GRAY, ColorEnum.ORANGE, ColorEnum.GREEN, ColorEnum.YELLOW, ColorEnum.WHITE};
        do {
            System.out.println("Welcome to TownBuilder. Would you like to play a " + Utility.generateColorizedString("default", Attribute.CYAN_TEXT()) + " game (recommended for new players), " +
                    "a " + Utility.generateColorizedString("custom", Attribute.MAGENTA_TEXT()) + " game, or a " + Utility.generateColorizedString("random", Attribute.BRIGHT_YELLOW_TEXT()) + " game?");
            userInput = sc.nextLine().toLowerCase();
            switch (userInput) {
                case "default", "d" -> isUserInputValid = true;
                case "custom", "c" -> {
                    isUserInputValid = true;
                    isCustomGame = true;
                }
                case "random", "r" -> {
                    isRandomGame = true;
                    isUserInputValid = true;
                }
                default -> System.out.println("Invalid input. Please try again with 'custom' or 'default'.");
            }
        }
        while (!isUserInputValid);
        if (isCustomGame) {
            BuildingFactory.setbuildingMasterList();
            for (ColorEnum color : colors) {
                ArrayList<Building> coloredBuildings = buildingMasterList.get(color);
                isUserInputValid = false;
                if (coloredBuildings.size() > 1) {
                    do {
                        System.out.println("What " + color + " building would you like for your game?");
                        System.out.println("Available choices include:");
                        Utility.printBuildingsInList(coloredBuildings);
                        userInput = sc.nextLine().toLowerCase();
                        for (Building coloredBuilding : coloredBuildings) {
                            if (userInput.equals(coloredBuilding.toString().toLowerCase())) {
                                buildingsForGame.add(BuildingFactory.getBuilding(coloredBuilding.getType(), buildingsForGame, -1, -1, false));
                                isUserInputValid = true;
                            }
                        }
                    }
                    while (!isUserInputValid);
                } else {
                    buildingsForGame.add(coloredBuildings.get(0));
                }

            }
        } else if (isRandomGame) {
            BuildingFactory.setbuildingMasterList();
            for (ColorEnum color : colors) {
                ArrayList<Building> buildings = buildingMasterList.get(color);
                if (buildings.size() > 1) {
                    int randomIndex = (int) (Math.random() * buildings.size());
                    Building building = buildings.get(randomIndex);
                    buildingsForGame.add(BuildingFactory.getBuilding(building.getType(), buildingsForGame, -1, -1, false));
                } else {
                    buildingsForGame.add(BuildingFactory.getBuilding(buildings.get(0).getType(), buildingsForGame, -1, -1, false));
                }

            }
        } else {
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.COTTAGE, buildingsForGame, -1, -1, false));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.FARM, buildingsForGame, -1, -1, false));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.WELL, buildingsForGame, -1, -1, false));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.CHAPEL, buildingsForGame, -1, -1, false));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.TAVERN, buildingsForGame, -1, -1, false));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.THEATER, buildingsForGame, -1, -1, false));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.WAREHOUSE, buildingsForGame, -1, -1, false));
        }
    }
}
