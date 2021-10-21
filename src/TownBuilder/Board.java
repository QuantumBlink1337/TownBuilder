package TownBuilder;


import TownBuilder.Buildings.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    private final ArrayList<Building> buildingsForGame;
    private final Manual manual;
    private final Scorer scorer;
    private final int boardWhiteSpaceLength = 9;
    private int spResourceSelectionIncrement = 0;
    private boolean isGameCompletion = false;
    private final String boardName;
    private final Resource[][] gameResourceBoard = new Resource[4][4];
    private final Building[][] gameBuildingBoard = new Building[4][4];
    private final String[][] gameBoard = new String[4][4];
    private final String[][] coordinateBoard = new String[4][4];
    private final String[] letterCoords = {"      ", "a", "      b",  "      c", "      d"};
    private final char[] numberCoords = {'1', '2', '3','4'};
    private final BuildingFactory buildingFactory;
    private final Scanner sc = new Scanner(System.in);

    public Board(ArrayList<Building> b) {
        System.out.println("What's your name?");
        boardName = sc.nextLine();
        if (boardName.equals("debug")) {
            System.out.println("Debug/testing mode activated. Disabled randomized resource collection");
        }
        buildingsForGame = new ArrayList<>(b);
        buildingFactory = new BuildingFactory();
        manual = new Manual(buildingsForGame);
        scorer = new Scorer(this, buildingsForGame);
        buildArrays();
        updateBoard();
    }
    public Manual getManual() {
        return manual;
    }
    private void buildArrays() {
        //System.out.println("Building resource array");
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                gameResourceBoard[row][col] = new Resource(ResourceEnum.NONE);
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
         //Test Case - Legitimate Game Board

        //gameBuildingBoard[0][0] = new Cottage();
//        gameBuildingBoard[0][1] = new Cottage();
//        gameResourceBoard[0][2].setResource(ResourceEnum.GLASS);
//        gameBuildingBoard[0][3] = new Cottage();
//
//        gameBuildingBoard[1][0] = new Well();
//        gameResourceBoard[1][1].setResource(ResourceEnum.WOOD);
//        gameResourceBoard[1][2].setResource(ResourceEnum.WOOD);
//        gameBuildingBoard[1][3] = new Theater(buildingsForGame);
//
//        gameBuildingBoard[2][0] = new Theater(buildingsForGame);
//        gameBuildingBoard[2][1] = new Cottage();
//        gameBuildingBoard[2][2] = new Well();
//        gameBuildingBoard[2][3] = new Tavern();
//
//        gameBuildingBoard[3][0] = new Chapel();
        //gameBuildingBoard[3][1] = new Farm();
//          gameBuildingBoard[3][2] = new Warehouse();
    }
    public int scoring(boolean isMidGameCheck) {
        return scorer.scoring(isMidGameCheck);
    }
    public boolean gameOver() {
        int i = 0;
        this.updateBoard();
        for (String[] tileRow : gameBoard) {
            for (String tile : tileRow) {
                if (!(tile.equals("[EMPTY! ]"))) {
                    i++;
                }
            }
        }
        if (i >= (gameResourceBoard.length * gameResourceBoard.length))  {
            System.out.println("=====GAME OVER=====");
            System.out.println("     "+boardName+"     ");
            return true;
        }
        else {
            return false;
        }
    }
    private void placementPrompt(Building building) throws InstantiationException, IllegalAccessException {
        System.out.println("A valid "+building.toString().toLowerCase() +" construction was found at the following coordinates:");
        Utility.displayValidResources(gameResourceBoard, buildingFactory);
        System.out.println("Place it this turn?");
        if (Utility.prompt()) {
            buildingFactory.placement(gameResourceBoard, gameBuildingBoard, building.getType(), buildingsForGame);
        }
        else {
            buildingFactory.clearResources(building.getType());
        }
    }
    public void detectValidBuilding() throws InstantiationException, IllegalAccessException {
        long initialTime = System.nanoTime();
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                for (Building building : buildingsForGame) {
                    if (buildingFactory.detection(row, col, gameResourceBoard, building.getBuildingPatternsList(), building.getType())) {
                        placementPrompt(building);
                    }
                }
            }
        }
        //System.out.println("Time elapsed: "+(System.nanoTime()-initialTime));
    }
    public void runBuildingTurnAction() {
        for (int row = 0; row < gameBuildingBoard.length; row++) {
            for (int col = 0; col < gameBuildingBoard[row].length; col++) {
                gameBuildingBoard[row][col].onTurnInterval(gameBuildingBoard);
            }
        }
    }
    public ResourceEnum resourcePicker(boolean isMultiplayerGame) throws IOException, URISyntaxException {
        ResourceEnum turnResource;
        if (isMultiplayerGame) {
            do {
                turnResource = ResourceEnum.resourcePicker();
                if (turnResource == ResourceEnum.NONE) {
                    manual.openManual();
                    renderBoard();
                }
            }
            while (turnResource == ResourceEnum.NONE);
        }
        else {
            if (spResourceSelectionIncrement == 2) {
                do {
                    turnResource = ResourceEnum.resourcePicker();
                    if (turnResource == ResourceEnum.NONE) {
                        manual.openManual();
                        renderBoard();
                    }
                }
                while (turnResource == ResourceEnum.NONE);
                spResourceSelectionIncrement = 0;
            }
            else {
                turnResource = ResourceEnum.randomResource();
                spResourceSelectionIncrement++;
            }
        }
        return turnResource;
    }
    private ResourceEnum warehouseOption(ResourceEnum t, Warehouse warehouse, boolean mode) {
        ResourceEnum turnResource = t;
        //System.out.println(warehouse.getFullness());
        if (mode) {
            if (warehouse.getFullness() != Warehouse.getMaxFullness()) {
                //System.out.println("Warehouse is not full, so placing it there");
                 return warehouse.placeResource(turnResource, ResourceEnum.NONE);
            }
        }
        else {
            ResourceEnum swap;
            do {
                    turnResource = t;
                    swap = ResourceEnum.resourcePicker();
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

    public void playerTurn(ResourceEnum resource) throws IOException, URISyntaxException {
        String userCoordinate = "";
        boolean validSpot;
        Warehouse warehouse = null;
        boolean warehouseExists = false;
        String warehouseText = "";
        System.out.println("Your resource for this turn is "+Utility.lowerCaseLetters(resource.toString()) +".");

        try {
            warehouse = (Warehouse) Utility.boardParser(BuildingEnum.WAREHOUSE, gameBuildingBoard);
            warehouseExists = true;
        }
        catch(ClassCastException ignored) {}
        do {
            validSpot = false;

            if (warehouseExists) {
                warehouseText = "You can also place or swap it on your warehouse with 'place' or 'swap'.";
            }
            while (userCoordinate.length() != 2) {
                System.out.println("Where would you like to place your "+ resource+ " resource? "+warehouseText+" Alternatively, to view the game manual type 'help'");
                System.out.println("You may also run a tentative score check on your board with 'score'.");
                if (warehouseExists) {
                    ResourceEnum[] list = warehouse.getStoredResources();
                    System.out.println("Here's what's inside the warehouse: "+ list[0] + "," + list[1] + "," + list[2]);
                }
                userCoordinate = sc.nextLine().toLowerCase();
                if (userCoordinate.equals("help") || userCoordinate.equals("h") || userCoordinate.equals("manual") || userCoordinate.equals("m")) {
                    manual.openManual();
                    renderBoard();
                }
                if (userCoordinate.equals("score") || userCoordinate.equals("s")) {
                    scoring(true);
                    renderBoard();
                }
                else if (userCoordinate.equals("place") && warehouseExists) {
                    if (warehouse.getFullness() != Warehouse.getMaxFullness()) {
                        resource = warehouseOption(resource, warehouse, true);
                        if (resource == ResourceEnum.NONE) {
                            validSpot = true;
                            break;

                        }
                    }
                    else {
                        System.out.println("The warehouse is full. You need to swap something instead.");
                    }

                }
                else if (userCoordinate.equals("swap") && warehouseExists) {
                    if (warehouse.getFullness() != Warehouse.getMinFullness()) {
                        resource = warehouseOption(resource, warehouse, false);
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
                if (validSpot) {
                    break;
                }
                else if (coords[0] == -1 || coords[1] == -1) {
                    System.out.println("Your coordinate is not valid.");
                }
                else if (gameResourceBoard[coords[0]][coords[1]].getResource() == ResourceEnum.NONE)
                {
                    gameResourceBoard[coords[0]][coords[1]].setResource(resource);
                    validSpot = true;
                }
                else {
                    System.out.println("You can't place a resource on a tile that already has something on it!");
                }
                userCoordinate = "   ";
        }
        while (!validSpot);

    }
    public void updateBoard() {
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                if (gameResourceBoard[row][col].getResource() != ResourceEnum.NONE && gameResourceBoard[row][col].getResource() != ResourceEnum.OBSTRUCTED) {
                    gameBoard[row][col] = "[" + Utility.lengthResizer(gameResourceBoard[row][col].toString(), boardWhiteSpaceLength) + "]";
                }
                else if (gameBuildingBoard[row][col].getType() != BuildingEnum.NONE) {
                    gameBoard[row][col] = "["+ Utility.lengthResizer(gameBuildingBoard[row][col].getType().toString(), boardWhiteSpaceLength) + "]";
                }
                else {
                    gameBoard[row][col] = "["+Utility.lengthResizer("EMPTY!", boardWhiteSpaceLength)+"]";
                }

            }
        }
    }
    public void renderBoard() {
        this.updateBoard();
        System.out.println("============="+boardName.toUpperCase()+"'s BOARD============");
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
                } else {
                    if (col == 3) {
                        System.out.println(gameBoard[row][col]);
                    } else {
                        System.out.print(gameBoard[row][col]);
                    }
                }
            }
        }
    }

    public String getBoardName() {
        return boardName;
    }

    public boolean isGameCompletion() {
        return isGameCompletion;
    }

    public void setGameCompletion(boolean gameCompletion) {
        isGameCompletion = gameCompletion;
    }

    public Resource[][] getGameResourceBoard() {
        return gameResourceBoard;
    }
    public Building[][] getGameBuildingBoard() {
        return gameBuildingBoard;
    }
}
