package TownBuilder;


import TownBuilder.Buildings.*;
import TownBuilder.DebugApps.DebugTools;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Board {
    private final ArrayList<Building> detectableBuildings;
    private final ArrayList<Building> scorableBuildings;
    //private static final ArrayList<BuildingEnum> monumentTypes = new ArrayList<>(Arrays.asList(BuildingEnum.AGUILD, BuildingEnum.ARCHIVE, BuildingEnum.BARRETT, BuildingEnum.CATERINA, BuildingEnum.IRONWEED));
    private static final ArrayList<BuildingEnum> monumentTypes = new ArrayList<>(Arrays.asList(BuildingEnum.AGUILD));

    private ArrayList<ResourceEnum> blacklistedResources;
    private final Manual manual;
    private final Scorer scorer;
    private int spResourceSelectionIncrement = 0;



    private boolean canBeMasterBuilder = true;
    private boolean monumentPlacement = false;
    private boolean isGameCompletion = false;
    private final boolean isSingleplayer;
    private final String boardName;
    private final Resource[][] gameResourceBoard = new Resource[4][4];
    private final Building[][] gameBuildingBoard = new Building[4][4];
    private final String[][] gameBoard = new String[4][4];
    //private final String[][] coordinateBoard = new String[4][4];
    private final String[] letterCoords = {"      ", "a", "         b",  "           c", "          d"};
    private final char[] numberCoords = {'1', '2', '3','4'};



    private final BuildingFactory buildingFactory;
    private final Scanner sc = new Scanner(System.in);

    public Board(ArrayList<Building> b, boolean ISP) throws IOException {
        isSingleplayer = ISP;
        System.out.println("What's your name?");
        boardName = sc.nextLine();
        if (boardName.equals("debug")) {
            System.out.println("Debug/testing mode activated. Disabled randomized resource collection");
        }
        else if (boardName.equals("debug_building")) {
            System.out.println("Debug/testing mode activated. Enabled direct building placement");
        }
        if (ISP) {
            monumentTypes.removeIf(m -> m == BuildingEnum.IRONWEED);
        }
        detectableBuildings = new ArrayList<>(b);
        scorableBuildings = new ArrayList<>(b);
        buildingFactory = new BuildingFactory();
        generateMonument();
        manual = new Manual(detectableBuildings);
        scorer = new Scorer(this, scorableBuildings);
        buildArrays();
        updateBoard();
    }
    public Manual getManual() {
        return manual;
    }
    public BuildingFactory getBuildingFactory() {
        return buildingFactory;
    }
    public boolean CanBeMasterBuilder() {
        return canBeMasterBuilder;
    }
    public void setCanBeMasterBuilder(boolean canBeMasterBuilder) {
        this.canBeMasterBuilder = canBeMasterBuilder;
    }
    private void generateMonument() {
        int randomIndex = (int) (Math.random() * monumentTypes.size());
        Monument monument = BuildingFactory.getMonument(monumentTypes.get(randomIndex), this, -1, -1, scorableBuildings);
        monumentTypes.remove(randomIndex);
        detectableBuildings.add(monument);
        scorableBuildings.add(monument);
        System.out.print(boardName + ", your Monument is ");
        if (monument != null) {
            Utility.printBuildingInfo(monument);
        }
    }
    private void buildArrays() throws IOException {
        DebugTools.logging("Initializing Resource Board.", 3);
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                gameResourceBoard[row][col] = new Resource(ResourceEnum.NONE, row,col);
            }
        }
        DebugTools.logging("Initializing Building Board.", 3);
        for (int row = 0; row < gameBuildingBoard.length; row++) {
            for (int col = 0; col < gameBuildingBoard[row].length; col++) {
                gameBuildingBoard[row][col] = new EmptyBuilding(row, col);
            }
        }
        blacklistedResources = new ArrayList<>();
    }
    public ArrayList<Building> getDetectableBuildings() {
        return detectableBuildings;
    }
    public ArrayList<Building> getScorableBuildings() {
        return scorableBuildings;
    }
    public int scoring(boolean isMidGameCheck) throws IOException {
        return scorer.scoring(isMidGameCheck);
    }
    public boolean gameOver() {
        int i = 0;
        this.updateBoard();
        for (Resource[] resourceRow : gameResourceBoard) {
            for (Resource resource : resourceRow) {
                if (resource.getResource() != ResourceEnum.NONE) {
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
    public void monumentControl(Monument monument) throws IOException {
        monument.onPlacement();
        monumentPlacement = true;
    }
    private void placementPrompt(Building building) throws IOException {
        System.out.println("A valid "+Utility.generateColorizedString(building.toString(), building.getType())+" construction was found at the following coordinates:");
        Utility.displayValidResources(gameResourceBoard, buildingFactory);
        System.out.println("Place it this turn?");
        if (Utility.prompt()) {
            buildingFactory.placeBuildingOnBoard(building.getType(), detectableBuildings, building.getType() == BuildingEnum.SHED,this);
        }
        else {
            buildingFactory.clearValidResourcesWithFlag(building.getType());
        }
    }
    public void detectValidBuilding() throws IOException {
        //long initialTime = System.nanoTime();
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                for (Building building : detectableBuildings) {
                    if (building instanceof Monument) {
                        if (!monumentPlacement) {
                            if (buildingFactory.detection(row, col, gameResourceBoard, building.getBuildingPatternsList(), building.getType())) {
                                placementPrompt(building);
                            }
                        }
                    }
                    else {
                        if (buildingFactory.detection(row, col, gameResourceBoard, building.getBuildingPatternsList(), building.getType())) {
                            placementPrompt(building);
                        }
                    }
                }
            }
        }
        //System.out.println("Time elapsed: "+(System.nanoTime()-initialTime));
    }
    public void runBuildingTurnAction() throws IOException {
        int bankCounter = 0;
        for (Building[] buildings : gameBuildingBoard) {
            for (Building building : buildings) {
                building.onTurnInterval(gameBuildingBoard);
                if (building instanceof Bank bank) {
                    bankCounter++;
                    ResourceEnum blacklistedResource = bank.getLockedResource();
                    if (!blacklistedResources.contains(blacklistedResource)) {
                        blacklistedResources.add(blacklistedResource);
                    }
                }
            }
        }
        if (bankCounter > 4) {
            scorableBuildings.removeIf(building -> building.getType() == BuildingEnum.BANK);
        }
    }
    public ResourceEnum resourcePicker() throws IOException, URISyntaxException {
        ResourceEnum turnResource;
        if (boardName.equalsIgnoreCase("debug")) {
            return ResourceEnum.resourcePicker(null);
        }
        if (!isSingleplayer) {
            do {
                turnResource = ResourceEnum.resourcePicker(blacklistedResources.toArray(ResourceEnum[]::new));
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
                    turnResource = ResourceEnum.resourcePicker(blacklistedResources.toArray(ResourceEnum[]::new));
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
    public void buildingPlacer() throws IOException {
        buildingPlacer(detectableBuildings, true);
    }
    public BuildingEnum buildingPlacer(ArrayList<Building> buildingArrayList, boolean placeBuildingOnBoard) throws IOException {
        System.out.println("What building would you like?");
        Scanner sc = new Scanner(System.in);
        boolean validInput = false;
        do {
            String userInput = sc.nextLine().toLowerCase();
            for (Building building : buildingArrayList) {
                if (userInput.equalsIgnoreCase(building.toString())) {
                    if (placeBuildingOnBoard) {
                        buildingFactory.placeBuildingOnBoard(building.getType(), buildingArrayList, true, this);
                    }
                    return building.getType();
                }
            }
            System.out.println("Invalid input.");

        }
        while (true);
    }
    private ResourceEnum factoryOption(ResourceEnum resource, ArrayList<Factory> factoriesOnBoard) {
        boolean validInput = false;
        String userInput;
        do {
            System.out.println("Which Factory would you like to use?");
            userInput = sc.nextLine().toLowerCase();
            for (Factory factory : factoriesOnBoard) {
                int row = factory.getRow();
                int col = factory.getCol();
                if (Utility.MachineIndexesToHumanCoords(row, col).equalsIgnoreCase(userInput) && resource == factory.getFactorizedResource()) {
                    resource = factory.exchangeResource();
                    validInput = true;
                    break;
                }
                else if (Utility.MachineIndexesToHumanCoords(row, col).equalsIgnoreCase(userInput) && resource != factory.getFactorizedResource()) {
                    System.out.println("The Factory selected does match the type of the resource you're exchanging.");
                }
                else if (userInput.equals("exit")) {
                    validInput = true;
                }
                else {
                    System.out.println("Invalid input.");
                }
            }
        }
        while (!validInput);
        return resource;
    }
    private ResourceEnum warehouseChoice(ArrayList<Warehouse> warehousesOnBoard, ResourceEnum resource) {
        boolean validInput = false;
        String userInput;
        Warehouse warehouse = null;
        do {
            do {
                System.out.println("Which Warehouse would you like?");
                for (Warehouse w : warehousesOnBoard) {
                    System.out.println("Warehouse at: " + Utility.MachineIndexesToHumanCoords(w.getRow(), w.getCol()));
                }
                userInput = sc.nextLine().toLowerCase();
                for (Warehouse w : warehousesOnBoard) {
                    if (Utility.MachineIndexesToHumanCoords(w.getRow(), w.getCol()).equalsIgnoreCase(userInput)) {
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
                    validInput = true;

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
        return resource;
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
                    swap = ResourceEnum.resourcePicker(null);
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
        boolean factoryExists = false;
        ArrayList<Warehouse> warehousesOnBoard = new ArrayList<>();
        ArrayList<Factory> factoriesOnBoard = new ArrayList<>();

        System.out.println("Your resource for this turn is "+Utility.generateColorizedString(Utility.lowerCaseLetters(resource.toString()), resource));

        for (Building[] buildingRow : gameBuildingBoard) {
            for (Building building : buildingRow) {
                if (building instanceof Warehouse) {
                    warehousesOnBoard.add((Warehouse)building);
                    warehouseExists = true;

                }
                else if (building instanceof Factory) {
                    factoriesOnBoard.add((Factory) building);
                    factoryExists = true;
                }
            }
        }
        do {
            validSpot = false;
            while (userCoordinate.length() != 2 && resource != ResourceEnum.NONE) {
                System.out.println("Where would you like to place your "+ Utility.generateColorizedString(Utility.lowerCaseLetters(resource.toString()), resource)+ " resource? Alternatively, to view the game manual type 'help'");
                System.out.println("You may also run a tentative score check on your board with 'score'.");
                if (warehouseExists) {
                    System.out.println("You can use 'warehouse' to access Warehouse controls.");
                }
                if (warehouseExists) {
                    for (Warehouse warehouse : warehousesOnBoard) {
                        ResourceEnum[] list = warehouse.getStoredResources();
                        System.out.println("Here's what's inside the warehouse at " + Utility.MachineIndexesToHumanCoords(warehouse.getRow(), warehouse.getCol())+ ": "+ list[0] + "," + list[1] + "," + list[2]);
                    }
                }
                else if (factoryExists) {
                    for (Factory factory : factoriesOnBoard) {
                        System.out.println("Type of resource in Factory at " +Utility.MachineIndexesToHumanCoords(factory.getRow(), factory.getCol()) + ": " + factory.getFactorizedResource());
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
                if (userCoordinate.equals("factory") || (userCoordinate.equals("f")) && factoryExists) {
                   resource = factoryOption(resource, factoriesOnBoard);
                }
                if (userCoordinate.equals("warehouse") || (userCoordinate.equals("w")) && warehouseExists) {
                    resource = warehouseChoice(warehousesOnBoard, resource);
                    if (resource == ResourceEnum.NONE) {
                        validSpot = true;
                    }
                }
                else if (userCoordinate.length() != 2) {
                    System.out.println("Oops! That's not a coordinate or a command.");
                }
            }

                int[] coords = Utility.humanCoordsToMachineIndexes(userCoordinate);
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
                int boardWhiteSpaceLength = 9;
                if (gameResourceBoard[row][col].getResource() != ResourceEnum.NONE && gameResourceBoard[row][col].getResource() != ResourceEnum.OBSTRUCTED && gameResourceBoard[row][col].getResource() != ResourceEnum.TPOST) {

                    gameBoard[row][col] = "[" + Utility.generateColorizedString(Utility.lengthResizer(gameResourceBoard[row][col].toString(), boardWhiteSpaceLength), gameResourceBoard[row][col].getResource()) + "]";
                }
                else if (gameBuildingBoard[row][col].getType() != BuildingEnum.NONE) {

                    gameBoard[row][col] = "["+ Utility.generateColorizedString(Utility.lengthResizer(gameBuildingBoard[row][col].getType().toString(), boardWhiteSpaceLength), gameBuildingBoard[row][col].getType())+ "]";
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
