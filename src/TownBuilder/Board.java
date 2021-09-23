package TownBuilder;


import TownBuilder.Buildings.*;
import jdk.jshell.execution.Util;

import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    private final Player player;
    private int resourceTurn = 0;
    private TownResource[][] gameResourceBoard = new TownResource[4][4];
    private Building[][] gameBuildingBoard = new Building[4][4];
    private String[][] gameBoard = new String[4][4];
    private String[][] coordinateBoard = new String[4][4];
    private final char[] letterCoords = {' ', 'a', 'b', 'c', 'd'};
    private final char[] numberCoords = {'1', '2', '3','4'};
    private boolean gameCompletion;
    private Scanner sc = new Scanner(System.in);

    public Board() {
        player = new Player();
        gameCompletion = false;
        buildArrays();
    }
    private void buildArrays() {
        //System.out.println("Building resource array");
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                gameResourceBoard[row][col] = new TownResource(ResourceEnum.NONE);
                //System.out.println(gameResourceBoard[row][col]);
            }
        }
        for (int row = 0; row < gameBuildingBoard.length; row++) {
            for (int col = 0; col < gameBuildingBoard[row].length; col++) {
                gameBuildingBoard[row][col] = new BlackBuilding(BuildingEnum.NONE);
                //System.out.println(gameResourceBoard[row][col]);
            }
        }

        for (int row = 0; row < coordinateBoard.length; row++) {
            for (int col = 0; col < coordinateBoard[row].length; col++) {
                coordinateBoard[row][col] = "[Row: "+row+" Col: "+col+"]";
            }
        }
        gameBuildingBoard[1][0] = new BlackBuilding(BuildingEnum.WHOUSE);
//        gameBuildingBoard[1][1] = new BlueBuilding(BuildingEnum.COTTAGE);
//        gameBuildingBoard[0][1] = new BlueBuilding(BuildingEnum.COTTAGE);
//        gameBuildingBoard[1][2] = new BlueBuilding(BuildingEnum.COTTAGE);
//        gameBuildingBoard[2][2] = new BlueBuilding(BuildingEnum.COTTAGE);
//        gameBuildingBoard[3][2] = new OrangeBuilding(BuildingEnum.TEMPLE);
//        gameBuildingBoard[1][3] = new RedBuilding(BuildingEnum.FARM);


    }

    public void game() {
        while (!gameCompletion) {
            renderBoard();
            gameCompletion = gameOver();
            if (gameCompletion) {
                break;
            }
            playerTurn();
            detectValidBuilding();
        }
        System.out.println("Final score: " + scoring());

    }
    private int scoring() {
        int totalScore = 0;
        ArrayList<Building> temples = new ArrayList<>();
        for (int r = 0; r < gameBuildingBoard.length; r++) {
            for (int c = 0; c < gameBuildingBoard[r].length; c++) {
                if (gameBuildingBoard[r][c].getType() != BuildingEnum.NONE) {
                    System.out.println("A building was found at " + Utility.coordsToOutput(r, c) + ". Scoring it.");
                    if (gameBuildingBoard[r][c].getType() == BuildingEnum.TEMPLE) {
                        temples.add(gameBuildingBoard[r][c]);
                    }
                    else {
                        totalScore += gameBuildingBoard[r][c].scorer(gameBuildingBoard, r, c);
                    }
                }
            }
        }
        for (int i = 0; i < temples.size(); i++) {
            totalScore += temples.get(i).scorer(gameBuildingBoard, 0, 0);
        }
        return totalScore;
    }
    private boolean gameOver() {
        int i = 0;
        for (String[] tileRow : gameBoard) {
            for (String tile : tileRow) {
                if (!(tile.equals("[Empty!]"))) {
                    i++;
                }
                else {
                }
            }
        }
        if (i >= 16)  {
            System.out.println("Game over!");
            return true;
        }
        else {
            return false;
        }
    }
    private void placementPrompt(BuildingEnum building, int row, int col) {
        System.out.println("A valid "+building.toString().toLowerCase() +" construction was found at " + Utility.coordsToOutput(row, col) + "! Place it this turn?");
        if (Utility.prompt()) {
            Building.placement(gameResourceBoard, gameBuildingBoard, building);
        }
        else {
            Building.clearResources(building);
        }
    }
    private void detectValidBuilding() {
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                    if (Building.detection(row, col, gameResourceBoard, RedBuilding.getPatterns(), BuildingEnum.FARM)) {
                        placementPrompt(BuildingEnum.FARM, row, col);
                    }
                    if (Building.detection(row, col, gameResourceBoard, BlueBuilding.getPatterns(), BuildingEnum.COTTAGE)) {
                        placementPrompt(BuildingEnum.COTTAGE, row, col);
                    }
                    if (Building.detection(row, col, gameResourceBoard, GrayBuilding.getPatterns(), BuildingEnum.WELL)) {
                        placementPrompt(BuildingEnum.WELL, row, col);
                    }
                    if (Building.detection(row, col, gameResourceBoard, YellowBuilding.getPatterns(), BuildingEnum.THEATER)) {
                        placementPrompt(BuildingEnum.THEATER, row, col);
                    }
                    if (Building.detection(row, col, gameResourceBoard, BlackBuilding.getPatterns(), BuildingEnum.WHOUSE)) {
                        placementPrompt(BuildingEnum.WHOUSE, row, col);
                    }
                    if (Building.detection(row, col, gameResourceBoard, GreenBuilding.getPatterns(), BuildingEnum.TAVERN)) {
                        placementPrompt(BuildingEnum.TAVERN, row, col);
                    }
                    if (Building.detection(row, col, gameResourceBoard, OrangeBuilding.getPatterns(), BuildingEnum.TEMPLE)) {
                        placementPrompt(BuildingEnum.TEMPLE, row, col);
                }
            }
        }
    }
    private void playerTurn() {
        ResourceEnum turnResource;
//        if (resourceTurn == 2) {
//            turnResource = ResourceEnum.resourcePicker();
//            resourceTurn = 0;
//        }
//        else {
//            turnResource = ResourceEnum.randomResource();
//            resourceTurn++;
//        }
        turnResource = ResourceEnum.resourcePicker();
        System.out.println("Your resource for this turn is "+Utility.lowerCaseLetters(turnResource.toString()) +".");
        resourcePlacer(turnResource);
    }
    private ResourceEnum warehouseOption(ResourceEnum t, BlackBuilding warehouse) {
        ResourceEnum turnResource = t;
        System.out.println(warehouse.getFullness());
        if (warehouse.getFullness() != 3) {
            System.out.println("Warehouse is not full, so placing it there");
            turnResource = warehouse.placeResource(turnResource, ResourceEnum.NONE);
        }
        else {
            String resourceChoice = "";
            ResourceEnum swap = ResourceEnum.NONE;
            do {
                System.out.println("The warehouse is full. What resource do you want to swap out?");
                resourceChoice = sc.nextLine().toLowerCase();
                switch (resourceChoice) {
                    case "wheat":
                        swap = ResourceEnum.WHEAT;
                        break;
                    case "glass":
                        swap = ResourceEnum.GLASS;
                        break;
                    case "brick":
                        swap = ResourceEnum.BRICK;
                        break;
                    case "stone":
                        swap = ResourceEnum.STONE;
                        break;
                    case "wood":
                        swap = ResourceEnum.WOOD;
                        break;
                    default:
                        resourceChoice = "";
                        break;
                }
            }
            while (resourceChoice == "");
            turnResource = warehouse.placeResource(turnResource, swap);
        }
        return turnResource;
    }

    private void resourcePlacer(ResourceEnum random) {
        String userCoordinate = "   ";
        boolean validSpot = false;
        BlackBuilding warehouse;
        boolean warehouseExists = false;
        String warehouseText = "";
        warehouse = (BlackBuilding) Utility.boardParser(BuildingEnum.WHOUSE, gameBuildingBoard);
        if (warehouse.getBuildingEnum() == BuildingEnum.WHOUSE) {
            warehouseExists = true;
        }
        do {
            validSpot = false;

            if (warehouseExists) {
                warehouseText = "You can also place or swap it on your warehouse with 'whouse'";
            }
            while (userCoordinate.length() > 2) {
                System.out.println("Where would you like to place your "+ random+ " resource?"+warehouseText+" Alternatively, to view building patterns type \'help\'");
                userCoordinate = sc.nextLine().toLowerCase();
                if (userCoordinate.equals("help")) {
                    System.out.println("You typed help, good for you");
                }
                else if (userCoordinate.equals("whouse") && warehouseExists) {
                    random = warehouseOption(random, warehouse);
                    if (random == ResourceEnum.NONE) {
                        break;
                    }
                }
                else if (userCoordinate.length() > 2) {
                    System.out.println("Oops! That's not a coordinate or a command.");
                }
            }

                int[] coords = Utility.inputToCoords(userCoordinate);
                //System.out.println("Row: " + row + "Col: " + col);
                if (gameResourceBoard[coords[0]][coords[1]].getResource() == ResourceEnum.NONE)
                {
                    gameResourceBoard[coords[0]][coords[1]].setResource(random);
                    validSpot = true;
                }
                else {
                    System.out.println("You can't place a resource on a tile that already has something on it!");
                }
                userCoordinate = "   ";
        }
        while (!validSpot);

    }
    private void renderBoard() {
        //System.out.println("Rendering game board");
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                if (gameResourceBoard[row][col].getResource() != ResourceEnum.NONE && gameResourceBoard[row][col].getResource() != ResourceEnum.OBSTRUCTED) {
                    gameBoard[row][col] = gameResourceBoard[row][col].toString();
                }
                else if (gameBuildingBoard[row][col].getType() != BuildingEnum.NONE) {
//                    System.out.println("Printing Building list");
//                    System.out.println(gameBuildingBoard[row][col].getType());
                    gameBoard[row][col] = "["+ gameBuildingBoard[row][col].getType().toString() + "]";
                }
                else {
                    gameBoard[row][col] = "[Empty!]";
                }

            }
        }

        for (int i = 0; i < letterCoords.length; i++) {
            if (i == 4) {
                System.out.println(letterCoords[i]);

            }
            else {
                System.out.print(letterCoords[i]);
                System.out.print("     ");
            }

        }
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = -1; col < gameBoard[row].length; col++) {
                if (col == -1) {
                    System.out.print(numberCoords[row]);
                }
                else {
                    if (col == 3) {
                        System.out.println(gameBoard[row][col]);
                    }
                    else {
                        System.out.print(gameBoard[row][col]);
                    }
                }

            }
        }
//        System.out.println("This grid helps with figuring out where things should go");
//        for (int row = 0; row < coordinateBoard.length; row++) {
//            for (int col = 0; col < coordinateBoard[row].length; col++) {
//                if (col == 3) {
//                    System.out.println(coordinateBoard[row][col]);
//                }
//                else {
//                    System.out.print(coordinateBoard[row][col]);
//                }
//
//            }
//        }
    }
}
