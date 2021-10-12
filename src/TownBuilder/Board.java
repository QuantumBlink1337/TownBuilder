package TownBuilder;


import TownBuilder.Buildings.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    private final ArrayList<Building> buildingsForGame;
    private final Manual manual;
    private int boardNumber = 0;
    private boolean isGameCompletion = false;
    private boolean turnComplete = false;
    private String boardName;
    private final TownResource[][] gameResourceBoard = new TownResource[4][4];
    private final Building[][] gameBuildingBoard = new Building[4][4];
    private final String[][] gameBoard = new String[4][4];
    private final String[][] coordinateBoard = new String[4][4];
    private final String[] letterCoords = {"      ", "a", "      b",  "      c", "      d"};
    private final char[] numberCoords = {'1', '2', '3','4'};

    private boolean gameCompletion;
    private final Scanner sc = new Scanner(System.in);

    public Board(ArrayList<Building> b, int p) {
        System.out.println("What's your name?");
        boardNumber = p;
        boardName = sc.nextLine();
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
        // Test Case - Legitimate Game Board
//        gameBuildingBoard[0][0] = new Cottage();
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
//        gameBuildingBoard[3][1] = new Farm();
          //gameBuildingBoard[3][2] = new Warehouse();
//        gameBuildingBoard[3][3] = new Theater(buildingsForGame);



    }
    public int scoring() {
        int totalScore = 0;
        int score = 0;
        ArrayList<Building> chapels = new ArrayList<>();
        for (int r = 0; r < gameBuildingBoard.length; r++) {
            for (int c = 0; c < gameBuildingBoard[r].length; c++) {
                if (gameBuildingBoard[r][c].getType() != BuildingEnum.NONE) {
                    System.out.println("A building was found at " + Utility.coordsToOutput(r, c) + ". Scoring it.");
                    if (gameBuildingBoard[r][c].getType() == BuildingEnum.CHAPEL) {
                        chapels.add(gameBuildingBoard[r][c]);
                    }
                    else {
                        score = gameBuildingBoard[r][c].scorer(gameBuildingBoard, r, c);
                        System.out.println("Score of "+gameBuildingBoard[r][c] + " at " + Utility.coordsToOutput(r, c) + " : "+ score);
                        totalScore += score;
                        System.out.println("Total score now: "+totalScore);
                    }
                }
                else if (gameResourceBoard[r][c].getResource() != ResourceEnum.NONE || gameResourceBoard[r][c].getResource() != ResourceEnum.OBSTRUCTED) {
                    System.out.println("Resource found. Decrementing");
                    totalScore--;
                }
            }
        }
        for (Building chapel : chapels) {
            totalScore += chapel.scorer(gameBuildingBoard, 0, 0);
        }
        return totalScore;
    }
    public boolean gameOver() {
        int i = 0;
        for (String[] tileRow : gameBoard) {
            for (String tile : tileRow) {
                if (!(tile.equals("[EMPTY! ]"))) {
                    i++;
                }
            }
        }
        if (i >= (gameResourceBoard.length * gameResourceBoard.length))  {
            System.out.println("Game over!");
            return true;
        }
        else {
            return false;
        }
    }
    private void placementPrompt(Building building, int row, int col) {
        System.out.println("A valid "+building.toString().toLowerCase() +" construction was found at the following coordinates:");
        Utility.displayValidResources(gameResourceBoard);
        System.out.println("Place it this turn?");
        if (Utility.prompt()) {
            building.placement(gameResourceBoard, gameBuildingBoard, buildingsForGame);
        }
        else {
            Building.clearResources(building.getType());
        }
    }
    public void detectValidBuilding() {
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                for (int i = 0; i < buildingsForGame.size(); i++) {
                    if (Building.detection(row, col, gameResourceBoard, buildingsForGame.get(i).getPatterns(), buildingsForGame.get(i).getType())) {
                        placementPrompt(buildingsForGame.get(i), row, col);
                    }
                }
            }
        }
    }
    public ResourceEnum resourcePicker(boolean isMultiplayerGame) throws InterruptedException, IOException, URISyntaxException {
        ResourceEnum turnResource = null;
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
            int turn = 0;
            if (turn == 2) {
                do {
                    turnResource = ResourceEnum.resourcePicker();
                    if (turnResource == ResourceEnum.NONE) {
                        manual.openManual();
                        renderBoard();
                    }
                }
                while (turnResource == ResourceEnum.NONE);
                turn = 0;
            }
            else {
                turnResource = ResourceEnum.resourcePicker();
                turn++;
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

    public void playerTurn(ResourceEnum resource) throws InterruptedException, IOException, URISyntaxException {
        String userCoordinate = "";
        boolean validSpot;
        Warehouse warehouse = null;
        boolean warehouseExists = false;
        String warehouseText = "";
        System.out.println("Your resource for this turn is "+Utility.lowerCaseLetters(resource.toString()) +".");

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
                System.out.println("Where would you like to place your "+ resource+ " resource? "+warehouseText+" Alternatively, to view the game manual type 'help'");
                if (warehouseExists) {
                    ResourceEnum[] list = warehouse.getStoredResources();
                    System.out.println("Here's what's inside the warehouse: "+ list[0] + "," + list[1] + "," + list[2]);
                }
                userCoordinate = sc.nextLine().toLowerCase();
                if (userCoordinate.equals("help") || userCoordinate.equals("h") || userCoordinate.equals("manual") || userCoordinate.equals("m")) {
                    manual.openManual();
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
                else if ((coords[0] == -1 || coords[1] == -1 && userCoordinate.length() > 2)) {
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
    public void renderBoard() {
        //System.out.println("Rendering game board");
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                if (gameResourceBoard[row][col].getResource() != ResourceEnum.NONE && gameResourceBoard[row][col].getResource() != ResourceEnum.OBSTRUCTED) {
                    gameBoard[row][col] = "[" + Utility.lengthResizer(gameResourceBoard[row][col].toString(), 7) + "]";
                }
                else if (gameBuildingBoard[row][col].getType() != BuildingEnum.NONE) {
//                    System.out.println("Printing Building list");
//                    System.out.println(gameBuildingBoard[row][col].getType());
                    gameBoard[row][col] = "["+ Utility.lengthResizer(gameBuildingBoard[row][col].getType().toString(), 7) + "]";
                }
                else {
                    gameBoard[row][col] = "["+Utility.lengthResizer("EMPTY!", 7)+"]";
                }

            }
        }
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

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public int getBoardNumber() {
        return boardNumber;
    }

    public void setBoardNumber(int boardNumber) {
        this.boardNumber = boardNumber;
    }

    public boolean isGameCompletion() {
        return isGameCompletion;
    }

    public void setGameCompletion(boolean gameCompletion) {
        isGameCompletion = gameCompletion;
    }

    public boolean isTurnComplete() {
        return turnComplete;
    }

    public void setTurnComplete(boolean turnComplete) {
        this.turnComplete = turnComplete;
    }
}
