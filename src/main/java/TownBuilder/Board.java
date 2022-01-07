package TownBuilder;


import TownBuilder.Buildings.*;
import TownBuilder.Buildings.Monuments.Monument;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.UI.BoardUI;
import TownBuilder.UI.TileButton;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Board {
    private final ArrayList<Building> detectableBuildings;
    private final ArrayList<Building> scorableBuildings;



    private static final ArrayList<BuildingEnum> monumentTypes = new ArrayList<>(Arrays.asList(BuildingEnum.AGUILD, BuildingEnum.ARCHIVE, BuildingEnum.BARRETT, BuildingEnum.CATERINA, BuildingEnum.IRONWEED,
            BuildingEnum.GROVEUNI, BuildingEnum.MANDRAS, BuildingEnum.OPALEYE, BuildingEnum.SHRINE, BuildingEnum.SILVAFRM, BuildingEnum.STARLOOM, BuildingEnum.OBELISK, BuildingEnum.SKYBATHS));
    //private static ArrayList<BuildingEnum> monumentTypes = new ArrayList<>(Arrays.asList(BuildingEnum.OBELISK));

    private ArrayList<ResourceEnum> blacklistedResources;
    private final Manual manual;
    private final Scorer scorer;
    private int spResourceSelectionIncrement = 0;
    private int boardFinishPlace = 0;
    private boolean canBeMasterBuilder = true;
    private boolean monumentPlacement = false;
    private boolean isGameCompletion = false;



    private boolean activeBuildingUse = false;



    private boolean placeAnywhere = false;
    private BuildingEnum lastBuiltBuilding;
    private final boolean isSingleplayer;
    private final String boardName;
    private final Resource[][] gameResourceBoard = new Resource[4][4];
    private final Building[][] gameBuildingBoard = new Building[4][4];
    private final String[][] gameBoard = new String[4][4];
    private final String[] letterCoords = {"      ", "a", "         b",  "           c", "          d"};
    private final char[] numberCoords = {'1', '2', '3','4'};

    private final BuildingFactory buildingFactory;
    private final PlayerManager playerManager;
    private final Scanner sc = new Scanner(System.in);

    private ResourceEnum currentResourceForTurn;


    public BoardUI getBoardUI() {
        return boardUI;
    }

    private final BoardUI boardUI;




    public Board(ArrayList<Building> b, boolean ISP, PlayerManager pM) throws IOException {
        isSingleplayer = ISP;
        playerManager = pM;
        System.out.println("What's your name?");
        boardName = sc.nextLine();
        if (boardName.equals("debug")) {
            System.out.println("Debug/testing mode activated. Disabled randomized resource collection");
        }
        else if (boardName.equals("debug_building")) {
            System.out.println("Debug/testing mode activated. Enabled direct building placement");
        }
        if (ISP) {
            monumentTypes.removeIf(m -> m == BuildingEnum.IRONWEED || m == BuildingEnum.OPALEYE || m == BuildingEnum.STARLOOM) ;
        }
        detectableBuildings = new ArrayList<>(b);
        scorableBuildings = new ArrayList<>(b);
        buildingFactory = new BuildingFactory(this);
        generateMonument();
        manual = new Manual(detectableBuildings);
        scorer = new Scorer(this, scorableBuildings);
        boardUI = null;
        buildArrays();
        updateBoard();
    }
    public Board(ArrayList<Building> b, BuildingEnum mo, PlayerManager playerManager) throws IOException {
        this.playerManager = playerManager;
        boardName = "DEBUG";
        isSingleplayer = true;
        detectableBuildings = new ArrayList<>(b);
        scorableBuildings = new ArrayList<>(b);
        buildingFactory = new BuildingFactory(this);
        Monument monument = BuildingFactory.getMonument(mo,this, -1, -1, scorableBuildings);
        detectableBuildings.add(monument);
        scorableBuildings.add(monument);
        manual = new Manual(detectableBuildings);
        scorer = new Scorer(this, scorableBuildings);
        boardUI = new BoardUI(this);
        boardUI.setVisible(true);
        buildArrays();
        updateBoard();



    }
    public BuildingEnum getLastBuiltBuilding() {
        return lastBuiltBuilding;
    }
    public boolean isActiveBuildingUse() {
        return activeBuildingUse;
    }

    public void setActiveBuildingUse(boolean activeBuildingUse) {
        this.activeBuildingUse = activeBuildingUse;
    }
    public void setLastBuiltBuilding(BuildingEnum lastBuiltBuilding) {
        this.lastBuiltBuilding = lastBuiltBuilding;
    }
    public void setBoardFinishPlace(int place) {
        boardFinishPlace = place;
    }
    public int getBoardFinishPlace() {
        return boardFinishPlace;
    }
    public PlayerManager getPlayerManager() {
        return playerManager;
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
    public boolean isPlaceAnywhere() {
        return placeAnywhere;
    }
    public void setPlaceAnywhere(boolean placeAnywhere) {
        this.placeAnywhere = placeAnywhere;
    }
    private void generateMonument() throws IOException {
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
        DebugTools.logging("Initializing Resource Board.");
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                gameResourceBoard[row][col] = new Resource(ResourceEnum.NONE, row,col);
            }
        }
        DebugTools.logging("Initializing Building Board.");
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
    public boolean gameOver() throws IOException {
        int i = 0;
        this.updateBoard();
        for (int r = 0; r < gameResourceBoard.length; r++) {
            for (int c = 0; c < gameResourceBoard[r].length; c++) {
                if (gameResourceBoard[r][c].getResource() != ResourceEnum.NONE || gameBuildingBoard[r][c].getType() != BuildingEnum.NONE) {
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
        boardUI.highlightBoardTiles(buildingFactory.getValidResources());
        if (boardUI.promptYesNoPrompt("A valid "+Utility.generateColorizedString(building.toString(), building.getType())+" construction was found. Place it this turn?")) {
            lastBuiltBuilding = building.getType();
            buildingFactory.placeBuildingOnBoard(building.getType(), detectableBuildings, building.getType() == BuildingEnum.SHED || placeAnywhere,this);
        }
        else {
            boardUI.resetBoardTiles();
            buildingFactory.clearValidResourcesWithFlag(building.getType());
        }
    }
    public void detectValidBuilding() throws IOException {
        //long initialTime = System.nanoTime();a
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
                spResourceSelectionIncrement = 0;
                ResourceEnum.resetResourceArray();
                return boardUI.promptUserResourceSelection();
            }
            else {
                spResourceSelectionIncrement++;

                return ResourceEnum.randomResource();
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
//    private ResourceEnum warehouseChoice(ArrayList<Warehouse> warehousesOnBoard, ResourceEnum resource) {
//        boolean validInput = false;
//        String userInput;
//        Warehouse warehouse = null;
//        do {
//            do {
//                System.out.println("Which Warehouse would you like?");
//                for (Warehouse w : warehousesOnBoard) {
//                    System.out.println("Warehouse at: " + Utility.MachineIndexesToHumanCoords(w.getRow(), w.getCol()));
//                }
//                userInput = sc.nextLine().toLowerCase();
//                for (Warehouse w : warehousesOnBoard) {
//                    if (Utility.MachineIndexesToHumanCoords(w.getRow(), w.getCol()).equalsIgnoreCase(userInput)) {
//                        warehouse = w;
//                        validInput = true;
//                    }
//                }
//            }
//            while (!validInput);
//            validInput = false;
//            System.out.println("What would you like to do with the warehouse selected? Use 'place' to place resources" +
//                    "or 'swap' to swap a resource out.");
//            userInput = sc.nextLine().toLowerCase();
//            if (userInput.equals("place")) {
//                if (warehouse.getFullness() != Warehouse.getMaxFullness()) {
//                    resource = warehouseOption(resource, warehouse, true);
//                    validInput = true;
//
//                }
//                else {
//                    System.out.println("The warehouse is full. You need to swap something instead.");
//                }
//
//            }
//            else if (userInput.equals("swap")) {
//                if (warehouse.getFullness() != Warehouse.getMinFullness()) {
//                    resource = warehouseOption(resource, warehouse, false);
//                    validInput = true;
//                }
//                else {
//                    System.out.println("There's nothing to swap with in the warehouse.");
//                }
//            }
//        }
//        while (!validInput);
//        return resource;
//    }
    public void warehouseOption(ResourceEnum t, Warehouse warehouse, boolean mode) {

        ResourceEnum turnResource = t;
        //System.out.println(warehouse.getFullness());
        if (mode) {
            if (warehouse.getFullness() != Warehouse.getMaxFullness()) {
                System.out.println("Warehouse is not full, so placing it there");

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
        //return turnResource;
    }

    public void playerTurn() throws IOException, URISyntaxException {
        ResourceEnum resource = resourcePicker();
        boardUI.setSelectedResourceForTurn(resource);

        int[] coords = boardUI.promptUserInputOfBoard();
        gameResourceBoard[coords[0]][coords[1]].setResource(resource);
        updateBoard();

    }
    public void updateBoard() throws IOException {
        TileButton[][] accessMatrix = boardUI.getTileAccessMatrix();
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                accessMatrix[row][col].setResourceEnum(gameResourceBoard[row][col].getResource());
                accessMatrix[row][col].setBuildingEnum(gameBuildingBoard[row][col].getType());
                accessMatrix[row][col].updateButton();
            }
        }
        System.out.println("Updating...");
        if (lastBuiltBuilding == BuildingEnum.WAREHOUSE) {
            boardUI.getActiveBuildingPanel().updateActiveBuildings();
        }
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                int boardWhiteSpaceLength = 9;
                DebugTools.logging("[UPDATE_BOARD] - Checking row: " + row + " col: "+col);
                if (gameResourceBoard[row][col].getResource() != ResourceEnum.NONE && gameResourceBoard[row][col].getResource() != ResourceEnum.OBSTRUCTED && gameResourceBoard[row][col].getResource() != ResourceEnum.TPOST) {
                    DebugTools.logging("[UPDATE_BOARD] - "+DebugTools.resourceInformation(gameResourceBoard[row][col]) + " NONEMPTY RESOURCE. Updating it");
                    gameBoard[row][col] = "[" + Utility.generateColorizedString(Utility.lengthResizer(gameResourceBoard[row][col].toString(), boardWhiteSpaceLength), gameResourceBoard[row][col].getResource()) + "]";
                }
                else if (gameBuildingBoard[row][col].getType() != BuildingEnum.NONE) {
                    DebugTools.logging("[UPDATE_BOARD] - "+DebugTools.buildingInformation(gameBuildingBoard[row][col]) + " NONEMPTY BUILDING. Updating it");
                    gameBoard[row][col] = "["+ Utility.generateColorizedString(Utility.lengthResizer(gameBuildingBoard[row][col].getType().toString(), boardWhiteSpaceLength), gameBuildingBoard[row][col].getType())+ "]";
                }
                else {
                    DebugTools.logging("[UPDATE_BOARD] - Nothing here. Updating with empty tile");
                    gameBoard[row][col] = "["+Utility.lengthResizer("EMPTY!", boardWhiteSpaceLength)+"]";
                }

            }
        }
    }
    public void renderBoard() throws IOException {
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
