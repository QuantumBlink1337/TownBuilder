package TownBuilder;


import TownBuilder.Buildings.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import com.diogonunes.jcolor.*;

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
    private final String[] letterCoords = {"      ", "a", "         b",  "           c", "          d"};
    private final char[] numberCoords = {'1', '2', '3','4'};
    private final BuildingFactory buildingFactory;
    private final Scanner sc = new Scanner(System.in);

    public Board(ArrayList<Building> b) {
        System.out.println("What's your name?");
        boardName = sc.nextLine();
        if (boardName.equals("debug")) {
            System.out.println("Debug/testing mode activated. Disabled randomized resource collection");
        }
        else if (boardName.equals("debug_building")) {
            System.out.println("Debug/testing mode activated. Enabled direct building placement");
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





//        gameBuildingBoard[0][0] = BuildingFactory.getBuilding(BuildingEnum.THEATER, buildingsForGame, 0, 0);
//        gameBuildingBoard[0][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, buildingsForGame, 0, 1);
//        gameBuildingBoard[1][2] = BuildingFactory.getBuilding(BuildingEnum.TAVERN, buildingsForGame, 1, 0);
//        gameBuildingBoard[3][3] = BuildingFactory.getBuilding(BuildingEnum.FARM, buildingsForGame, 3,3);
//
////        gameBuildingBoard[0][0] = BuildingFactory.getBuilding(BuildingEnum.CLOISTER, buildingsForGame, 0, 0);
////        gameBuildingBoard[0][3] = BuildingFactory.getBuilding(BuildingEnum.CLOISTER, buildingsForGame, 0, 3);
////        gameBuildingBoard[3][0] = BuildingFactory.getBuilding(BuildingEnum.CLOISTER, buildingsForGame, 3, 0);
////        gameBuildingBoard[3][3] = BuildingFactory.getBuilding(BuildingEnum.CLOISTER, buildingsForGame, 3, 3);
////
//        gameBuildingBoard[1][0] = BuildingFactory.getBuilding(BuildingEnum.WAREHOUSE, buildingsForGame, 1, 0);
//        gameBuildingBoard[2][0] = BuildingFactory.getBuilding(BuildingEnum.WELL, buildingsForGame, 2, 0);
//        gameBuildingBoard[3][0] = BuildingFactory.getBuilding(BuildingEnum.CHAPEL, buildingsForGame, 3, 0);

    }
    public int scoring(boolean isMidGameCheck) {
        return scorer.scoring(isMidGameCheck);
    }
    public boolean gameOver() {
        int i = 0;
        this.updateBoard();
        for (String[] tileRow : gameBoard) {
            for (String tile : tileRow) {
                if (!(tile.equals("[EMPTY!   ]"))) {
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
            buildingFactory.placeBuildingOnBoard(gameResourceBoard, gameBuildingBoard, building.getType(), buildingsForGame, false);
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
    public void buildingPlacer(Resource[][] rArray, Building[][] bArray, ArrayList<Building> buildingArrayList) throws InstantiationException, IllegalAccessException {
        System.out.println("What building would you like?");
        Scanner sc = new Scanner(System.in);
        boolean validInput = false;
        do {
            String userInput = sc.nextLine().toLowerCase();
            for (Building building : buildingArrayList) {
                if (userInput.equalsIgnoreCase(building.toString())) {
                    buildingFactory.placeBuildingOnBoard(rArray, bArray, building.getType(), buildingArrayList, true);
                    validInput = true;
                }
            }
            if (!validInput) {
                System.out.println("Invalid input.");
            }

        }
        while (!validInput);
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
        boolean warehouseExists = false;
        String warehouseText = "";
        ArrayList<Warehouse> warehousesOnBoard = new ArrayList<>();
        System.out.println("Your resource for this turn is "+Utility.lowerCaseLetters(resource.toString()) +".");

        for (Building[] buildingRow : gameBuildingBoard) {
            for (Building building : buildingRow) {
                if (building instanceof Warehouse) {
                    warehousesOnBoard.add((Warehouse)building);
                }
            }
        }
        if (warehousesOnBoard.size() > 0) {
            if (warehousesOnBoard.get(0).getType() == BuildingEnum.WAREHOUSE) {
                warehouseExists = true;
            }
        }
        do {
            validSpot = false;
            while (userCoordinate.length() != 2 && resource != ResourceEnum.NONE) {
                System.out.println("Where would you like to place your "+ resource+ " resource? Alternatively, to view the game manual type 'help'");
                System.out.println("You may also run a tentative score check on your board with 'score'.");
                if (warehouseExists) {
                    System.out.println("You can use 'warehouse' to access Warehouse controls.");
                }
                if (warehouseExists) {
                    for (Warehouse warehouse : warehousesOnBoard) {
                        ResourceEnum[] list = warehouse.getStoredResources();
                        System.out.println("Here's what's inside the warehouse at " + Utility.coordsToOutput(warehouse.getRow(), warehouse.getCol())+ ": "+ list[0] + "," + list[1] + "," + list[2]);
                    }
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
                if (userCoordinate.equals("warehouse") && warehouseExists) {
                    boolean validInput = false;
                    String userInput = null;
                    Warehouse warehouse = null;
                    do {
                        do {
                            System.out.println("Which Warehouse would you like?");
                            for (Warehouse w : warehousesOnBoard) {
                                System.out.println("Warehouse at: " + Utility.coordsToOutput(w.getRow(), w.getCol()));
                            }
                            userInput = sc.nextLine().toLowerCase();
                            for (Warehouse w : warehousesOnBoard) {
                                if (Utility.coordsToOutput(w.getRow(), w.getCol()).equalsIgnoreCase(userInput)) {
                                    warehouse = w;
                                    validInput = true;
                                }
                            }
                        }
                        while (!validInput);
                        validInput = false;
                        System.out.println("What would you like to do with the warehouse selected? Use 'place' to place resources" +
                                "or 'swap' to swap a resource out.");
                        userInput = sc.nextLine().toLowerCase();
                        if (userInput.equals("place")) {
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
                        else if (userInput.equals("swap")) {
                            if (warehouse.getFullness() != Warehouse.getMinFullness()) {
                                resource = warehouseOption(resource, warehouse, false);
                                validInput = true;
                            }
                            else {
                                System.out.println("There's nothing to swap with in the warehouse.");
                            }
                        }
                    }
                    while (!validInput);
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
                    gameBoard[row][col] = "[" + Ansi.colorize(Utility.lengthResizer(gameResourceBoard[row][col].toString(), boardWhiteSpaceLength), Utility.generateColors(null, gameResourceBoard[row][col])) + "]";
                }
                else if (gameBuildingBoard[row][col].getType() != BuildingEnum.NONE) {
                    gameBoard[row][col] = "["+ Ansi.colorize(Utility.lengthResizer(gameBuildingBoard[row][col].getType().toString(), boardWhiteSpaceLength), Utility.generateColors(gameBuildingBoard[row][col], null)) + "]";
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
