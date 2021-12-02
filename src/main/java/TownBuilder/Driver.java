package TownBuilder;

import TownBuilder.Buildings.*;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.DebugApps.GameManager;
import com.diogonunes.jcolor.Attribute;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {

    public static void main (String[] args) throws IOException, URISyntaxException {
        DebugTools.initFile();
        ArrayList<Building> buildingsForGame = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        colorPrintingDetermination();
        buildingSelection(buildingsForGame);
        int playerCount;
        ArrayList<Board> boardArrayList = new ArrayList<>();

        do {
            // gets input from user on how many players they want
            try {
                System.out.println("How many players would you like? You can have up to 6.");
                playerCount = sc.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("That's not a number!");
                playerCount = 0;
                sc.next();
                }
        }
        while (playerCount <= 0 || playerCount > 6); // prompts until program gets integer 0 < x <= 6
        for (int i= 0; i < playerCount; i++) {
            // generates a new Board object for each player
            if (playerCount <= 1) {
                Board temp = new Board(buildingsForGame, true);
                boardArrayList.add(temp);
            }
            else {
                Board temp = new Board(buildingsForGame, false);
                boardArrayList.add(temp);
            }

        }
        Manual.tutorial();
        boardArrayList.get(0).getManual().displayBuildings();
        GameManager gameManager = new GameManager(boardArrayList, buildingsForGame);
        // if there's only one player, use default singleplayer code
        do {
            gameManager.manageTurn();
        }
        while(!gameManager.gameActive());
        System.out.println("All players have finished TownBuilder. Thanks for playing! -Matt");
    }
    public static void buildingSelection(ArrayList<Building> buildingsForGame) {
        Scanner sc = new Scanner(System.in);

        HashMap<ColorEnum, ArrayList<Building>> buildingMasterList = BuildingFactory.getBuildingMasterList();
        String userInput;
        boolean isUserInputValid = false;
        boolean isCustomGame = false;
        boolean isRandomGame = false;
        ColorEnum[] colors = new ColorEnum[]{ColorEnum.BLUE, ColorEnum.RED, ColorEnum.GRAY, ColorEnum.ORANGE, ColorEnum.GREEN, ColorEnum.YELLOW, ColorEnum.WHITE};
        do {
            System.out.println("Welcome to TownBuilder. Would you like to play a "+Utility.generateColorizedString("default", Attribute.CYAN_TEXT())+" game (recommended for new players), " +
                    "a "+Utility.generateColorizedString("custom", Attribute.MAGENTA_TEXT()) +" game, or a "+ Utility.generateColorizedString("random", Attribute.BRIGHT_YELLOW_TEXT()) +" game?");
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
        }
        else if (isRandomGame) {
            BuildingFactory.setbuildingMasterList();
            for (ColorEnum color : colors) {
                ArrayList<Building> buildings = buildingMasterList.get(color);
                if (buildings.size() > 1) {
                    int randomIndex = (int) (Math.random() * buildings.size());
                    Building building = buildings.get(randomIndex);
                    buildingsForGame.add(BuildingFactory.getBuilding(building.getType(), buildingsForGame, -1, -1, false));
                }
                else {
                    buildingsForGame.add(BuildingFactory.getBuilding(buildings.get(0).getType(), buildingsForGame, -1, -1, false));
                }

            }
        }
        else {
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.COTTAGE, buildingsForGame, -1, -1, false));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.FARM, buildingsForGame, -1, -1, false));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.WELL, buildingsForGame, -1, -1, false));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.CHAPEL, buildingsForGame, -1, -1, false));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.TAVERN, buildingsForGame, -1, -1, false));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.THEATER, buildingsForGame, -1, -1, false));
            buildingsForGame.add(BuildingFactory.getBuilding(BuildingEnum.WAREHOUSE, buildingsForGame, -1, -1, false));
        }

    }
    private static void colorPrintingDetermination() {
        System.out.println("Are you playing on a Windows terminal? (Powershell or Command Line)");
        Utility.setColor(!Utility.prompt());
    }
}
