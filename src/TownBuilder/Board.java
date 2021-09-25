package TownBuilder;


import TownBuilder.Buildings.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    //private final Player player;
    private final ArrayList<Building> buildingsForGame;
    private final Manual manual;
    private final TownResource[][] gameResourceBoard = new TownResource[4][4];
    private final Building[][] gameBuildingBoard = new Building[4][4];
    private final String[][] gameBoard = new String[4][4];
    private final String[][] coordinateBoard = new String[4][4];
    private final String[] letterCoords = {"      ", "a", "      b",  "      c", "      d"};
    private final char[] numberCoords = {'1', '2', '3','4'};
    private int resourceTurn = 0;
    private boolean gameCompletion;
    private final Scanner sc = new Scanner(System.in);

    public Board(ArrayList<Building> b) {
        //player = new Player();
        buildingsForGame = b;
        manual = new Manual(buildingsForGame);
        gameCompletion = false;
        buildArrays();
    }
    public Manual getManual() {
        return manual;
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
                gameBuildingBoard[row][col] = new EmptyBuilding();
                //System.out.println(gameResourceBoard[row][col]);
            }
        }

        for (int row = 0; row < coordinateBoard.length; row++) {
            for (int col = 0; col < coordinateBoard[row].length; col++) {
                coordinateBoard[row][col] = "[Row: "+row+" Col: "+col+"]";
            }
        }

    }
    public int scoring() {
        int totalScore = 0;
        ArrayList<Building> temples = new ArrayList<>();
        for (int r = 0; r < gameBuildingBoard.length; r++) {
            for (int c = 0; c < gameBuildingBoard[r].length; c++) {
                if (gameBuildingBoard[r][c].getType() != BuildingEnum.NONE) {
                    System.out.println("A building was found at " + Utility.coordsToOutput(r, c) + ". Scoring it.");
                    if (gameBuildingBoard[r][c].getType() == BuildingEnum.CHAPEL) {
                        temples.add(gameBuildingBoard[r][c]);
                    }
                    else {
                        totalScore += gameBuildingBoard[r][c].scorer(gameBuildingBoard, r, c);
                    }
                }
                if (gameResourceBoard[r][c].getResource() != ResourceEnum.NONE || gameResourceBoard[r][c].getResource() != ResourceEnum.OBSTRUCTED) {
                    totalScore--;
                }
            }
        }
        for (Building temple : temples) {
            totalScore += temple.scorer(gameBuildingBoard, 0, 0);
        }
        return totalScore;
    }
    public boolean gameOver() {
        int i = 0;
        for (String[] tileRow : gameBoard) {
            for (String tile : tileRow) {
                if (!(tile.equals("[EMPTY!]"))) {
                    i++;
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
            Building.placement(gameResourceBoard, gameBuildingBoard, building, buildingsForGame);
        }
        else {
            Building.clearResources(building);
        }
    }
    public void detectValidBuilding() {
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                for (int i = 0; i < buildingsForGame.size(); i++) {
                    if (Building.detection(row, col, gameResourceBoard, buildingsForGame.get(i).getPatterns(), buildingsForGame.get(i).getType())) {
                        placementPrompt(buildingsForGame.get(i).getType(), row, col);
                    }
                }
            }
        }
    }
    public void playerTurn() throws InterruptedException {
        ResourceEnum turnResource;

        if (resourceTurn == 2) {
            turnResource = ResourceEnum.resourcePicker();
            resourceTurn = 0;
        }
        else {
            turnResource = ResourceEnum.randomResource();
            resourceTurn++;
        }
        //turnResource = ResourceEnum.resourcePicker();
        System.out.println("Your resource for this turn is "+Utility.lowerCaseLetters(turnResource.toString()) +".");
        resourcePlacer(turnResource);
    }
    private ResourceEnum warehouseOption(ResourceEnum t, Warehouse warehouse, boolean mode) {
        ResourceEnum turnResource = t;
        //System.out.println(warehouse.getFullness());
        if (mode) {
            if (warehouse.getFullness() != 3) {
                //System.out.println("Warehouse is not full, so placing it there");
                 return warehouse.placeResource(turnResource, ResourceEnum.NONE);
            }
        }
        else {
            String resourceChoice;
            ResourceEnum swap = ResourceEnum.NONE;
            do {
                do {
                    turnResource = t;
                    System.out.println("What resource do you want to swap out?");
                    resourceChoice = sc.nextLine().toLowerCase();
                    switch (resourceChoice) {
                        case "wheat" -> swap = ResourceEnum.WHEAT;
                        case "glass" -> swap = ResourceEnum.GLASS;
                        case "brick" -> swap = ResourceEnum.BRICK;
                        case "stone" -> swap = ResourceEnum.STONE;
                        case "wood" -> swap = ResourceEnum.WOOD;
                        default -> resourceChoice = "";
                    }
                }
                while (resourceChoice.equals(""));
                if (turnResource != ResourceEnum.OBSTRUCTED) {
                    turnResource = warehouse.placeResource(turnResource, swap);
                }
                else {
                    System.out.println("You asked for a resource that is not in the warehouse!");
                }
            }
            while (turnResource == ResourceEnum.OBSTRUCTED);
        }
        return turnResource;
    }

    private void resourcePlacer(ResourceEnum random) throws InterruptedException {
        String userCoordinate = "";
        boolean validSpot;
        Warehouse warehouse = null;
        boolean warehouseExists = false;
        String warehouseText = "";
        try {
            warehouse = (Warehouse) Utility.boardParser(BuildingEnum.WHOUSE, gameBuildingBoard);
            warehouseExists = true;
        }
        catch(ClassCastException e) {}
        do {
            validSpot = false;

            if (warehouseExists) {
                warehouseText = "You can also place or swap it on your warehouse with 'place' or 'swap'.";
            }
            while (userCoordinate.length() != 2) {
                System.out.println("Where would you like to place your "+ random+ " resource? "+warehouseText+" Alternatively, to view the game manual type 'help'");
                if (warehouseExists) {
                    ResourceEnum[] list = warehouse.getStoredResources();
                    System.out.println("Here's what's inside the warehouse: "+ list[0] + "," + list[1] + "," + list[2]);
                }
                userCoordinate = sc.nextLine().toLowerCase();
                if (userCoordinate.equals("help")) {
                    manual.openManual();
                    renderBoard();
                }
                else if (userCoordinate.equals("place") && warehouseExists) {
                    if (warehouse.getFullness() != 3) {
                        random = warehouseOption(random, warehouse, true);
                        if (random == ResourceEnum.NONE) {
                            break;
                        }
                    }
                    else {
                        System.out.println("The warehouse is full. You need to swap something instead.");
                    }

                }
                else if (userCoordinate.equals("swap") && warehouseExists) {
                    if (warehouse.getFullness() != 0) {
                        random = warehouseOption(random, warehouse, false);
                    }
                    else {
                        System.out.println("There's nothing to swap with in the warehouse.");
                    }
                }
                else if (userCoordinate.length() != 2) {
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
    public void renderBoard() {
        //System.out.println("Rendering game board");
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                if (gameResourceBoard[row][col].getResource() != ResourceEnum.NONE && gameResourceBoard[row][col].getResource() != ResourceEnum.OBSTRUCTED) {
                    gameBoard[row][col] = "[" + Utility.lengthResizer(gameResourceBoard[row][col].toString(), 6) + "]";
                }
                else if (gameBuildingBoard[row][col].getType() != BuildingEnum.NONE) {
//                    System.out.println("Printing Building list");
//                    System.out.println(gameBuildingBoard[row][col].getType());
                    gameBoard[row][col] = "["+ Utility.lengthResizer(gameBuildingBoard[row][col].getType().toString(), 6) + "]";
                }
                else {
                    gameBoard[row][col] = "["+Utility.lengthResizer("EMPTY!", 6)+"]";
                }

            }
        }

        for (int i = 0; i < letterCoords.length; i++) {
            if (i == 4) {
                System.out.println(letterCoords[i]);

            }
            else {
                System.out.print(letterCoords[i]);

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
