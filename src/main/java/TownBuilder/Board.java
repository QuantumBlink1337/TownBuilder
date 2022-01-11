package TownBuilder;


import TownBuilder.Buildings.*;
import TownBuilder.Buildings.Monuments.Monument;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.UI.BoardUI;
import TownBuilder.UI.TileButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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

    public Scorer getScorer() {
        return scorer;
    }

    private final Scorer scorer;
    private int spResourceSelectionIncrement = 0;
    private int boardFinishPlace = 0;
    private boolean canBeMasterBuilder = true;
    private boolean monumentPlacement = false;
    private boolean isGameCompletion = false;
    private boolean activeBuildingUse = false;
    private boolean turnOver = false;
    private int[] coords;
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
    private ResourceEnum currentResourceForTurn;
    private final BoardUI boardUI;
    private final Object notifier;


    public Board(ArrayList<Building> b, boolean ISP, PlayerManager pM) throws IOException {
        isSingleplayer = ISP;
        playerManager = pM;
        System.out.println("What's your name?");
        Scanner sc = new Scanner(System.in);
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
        buildingFactory = new BuildingFactory();
        generateMonument();
        manual = new Manual(detectableBuildings);
        scorer = new Scorer(this, scorableBuildings);
        boardUI = null;
        notifier = new Object();
        buildArrays();
        updateBoard();
    }
    public Board(ArrayList<Building> b, BuildingEnum mo, PlayerManager playerManager) throws IOException {
        this.playerManager = playerManager;
        boardName = "DEBUG";
        isSingleplayer = true;
        detectableBuildings = new ArrayList<>(b);
        scorableBuildings = new ArrayList<>(b);
        buildingFactory = new BuildingFactory();
        Monument monument = BuildingFactory.getMonument(mo,this, -1, -1, scorableBuildings);
        detectableBuildings.add(monument);
        scorableBuildings.add(monument);
        manual = new Manual(detectableBuildings);
        scorer = new Scorer(this, scorableBuildings);
        boardUI = new BoardUI(this);
        boardUI.setVisible(true);
        notifier = new Object();

        buildArrays();
        updateBoard();
    }
    public Board(ArrayList<Building> b, PlayerManager playerManager, boolean ISP, JFrame jF, String boardName) throws IOException {
        this.playerManager = playerManager;
        this.boardName = boardName;
        isSingleplayer = ISP;
        if (ISP) {
            monumentTypes.removeIf(m -> m == BuildingEnum.IRONWEED || m == BuildingEnum.OPALEYE || m == BuildingEnum.STARLOOM) ;
        }
        detectableBuildings = new ArrayList<>(b);
        scorableBuildings = new ArrayList<>(b);
        buildingFactory = new BuildingFactory();
        generateMonument();
        manual = new Manual(detectableBuildings);
        scorer = new Scorer(this, scorableBuildings);
        boardUI = new BoardUI(this);
        //jF.add(boardUI);
        notifier = new Object();

        buildArrays();
        updateBoard();
    }
    public BuildingEnum getLastBuiltBuilding() {
        return lastBuiltBuilding;
    }
    public Object getNotifier() {
        return notifier;
    }
    public boolean isActiveBuildingUse() {
        return activeBuildingUse;
    }
    public ArrayList<ResourceEnum> getBlacklistedResources() {
        return blacklistedResources;
    }
    public BoardUI getBoardUI() {
        return boardUI;
    }
    public int[] getCoords() {
        return coords;
    }
    public void setCoords(int[] coords) {
        this.coords = coords;
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
        boardUI.promptYesNoPrompt("A valid "+Utility.generateColorizedString(building.toString(), building.getType())+" construction was found. Place it this turn?");
        boolean selection;
        synchronized (Utility.getNotifier()) {
            try {
                Utility.getNotifier().wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        selection = boardUI.getUserYesNoAnswer();
        if (selection) {
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
                    System.out.println("Bank found!");
                    ResourceEnum blacklistedResource = bank.getLockedResource();
                    if (!blacklistedResources.contains(blacklistedResource)) {
                        blacklistedResources.add(blacklistedResource);
                    }
                }
            }
        }
        if (bankCounter > 4) {
            detectableBuildings.removeIf(building -> building.getType() == BuildingEnum.BANK);
        }
    }
    public ResourceEnum resourcePicker(String string) throws IOException {
        ResourceEnum turnResource;
        //System.out.println("Singleplayer? " + isSingleplayer);
        if (!isSingleplayer) {
            //System.out.println("Hit");
            boardUI.setPrimaryTextLabel(" ");
            turnResource = Utility.resourcePicker(blacklistedResources, this, string);
        }
        else {
            //System.out.println("I shouldn't be hit rn");
            if (spResourceSelectionIncrement == 2) {
                spResourceSelectionIncrement = 0;
                Utility.resetResourceArray();
                return Utility.resourcePicker(blacklistedResources, this, string);
            }
            else {
                spResourceSelectionIncrement++;

                return Utility.randomResource();
            }
        }
        return turnResource;
    }
    public void buildingPlacer() throws IOException {
        buildingPlacer(detectableBuildings, true);
    }
    public BuildingEnum buildingPlacer(ArrayList<Building> buildingArrayList, boolean placeBuildingOnBoard) throws IOException {
        boardUI.promptBuildingSelection("What building would you like?");
        synchronized (Utility.getNotifier()) {
            try {
                Utility.getNotifier().wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buildingFactory.placeBuildingOnBoard(boardUI.getSelectedBuildingForTurn(), buildingArrayList, placeBuildingOnBoard, this);
        updateBoard();
        return boardUI.getSelectedBuildingForTurn();
    }
    private void factoryOption(ResourceEnum resource, Factory building) throws IOException {
        if (currentResourceForTurn == building.getFactorizedResource()) {
            currentResourceForTurn = building.exchangeResource();
        }
        else {
            System.out.println("Invalid swap");
            boardUI.getErrorTextLabel().setVisible(true);
            boardUI.getErrorTextLabel().setText("That Factory doesn't match the resource this turn.");
        }
        turnOver = false;

    }
    public void warehouseOption(ResourceEnum t, Warehouse warehouse, boolean mode) throws IOException {

        ResourceEnum turnResource = t;
        if (mode) {
            if (warehouse.getFullness() != Warehouse.getMaxFullness()) {
                currentResourceForTurn = warehouse.placeResource(currentResourceForTurn, ResourceEnum.NONE);
                boardUI.getActiveBuildingPanel().updateActiveBuildings();
                turnOver = true;
                boardUI.setCoordinatesClicked(false);
            }
            else {
                boardUI.setSecondaryTextLabel("The selected warehouse is full.", Color.RED);
            }
        }
        else {
            if (warehouse.getFullness() != 0) {
                ResourceEnum swap;
                do {
                    boardUI.setCoordinatesClicked(false);
                    boardUI.setResourceSelectionLabel("Select a resource to swap out from your Warehouse.");
                    swap = Utility.resourcePicker(null, this, "");
                    System.out.println(swap);
                    turnResource = warehouse.placeResource(turnResource, swap);
                    if (turnResource == ResourceEnum.OBSTRUCTED) {
                        boardUI.setSecondaryTextLabel("You asked for a resource that is not in the warehouse!", Color.RED);
                    }
                }
                while (turnResource == ResourceEnum.OBSTRUCTED);
                currentResourceForTurn = turnResource;
                turnOver = false;

            }
            else {
                boardUI.setSecondaryTextLabel("Your Warehouse has nothing in it to swap out!", Color.RED);
            }
        }
        //return turnResource;
    }

    public void playerTurn(ResourceEnum resourceEnum, String string) throws IOException {
        boardUI.setResourceSelectionLabel();
        currentResourceForTurn = resourceEnum;
        boardUI.setSecondaryTextLabel(string);
        do {
            boardUI.setSelectedResourceForTurn(currentResourceForTurn);
            synchronized (notifier) {
                try {
                    notifier.wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (boardUI.isCoordinatesClicked()) {
                int[] coords = boardUI.getSelectedCoords();
                gameResourceBoard[coords[0]][coords[1]].setResource(currentResourceForTurn);
                turnOver = true;
            }
            else if (boardUI.isActiveBuildingToggled()) {
                switch (boardUI.getActiveMode()) {
                    case "swap" -> warehouseOption(currentResourceForTurn, (Warehouse) boardUI.getSelectedActiveBuilding(), false);
                    case "place" -> warehouseOption(currentResourceForTurn, (Warehouse) boardUI.getSelectedActiveBuilding(), true);
                    case "exchange" -> factoryOption(currentResourceForTurn, (Factory) boardUI.getSelectedActiveBuilding());
                }
            }
        }
        while (!turnOver);
        boardUI.getErrorTextLabel().setVisible(false);
        updateBoard();
    }
    public void updateBoard() throws IOException {
        System.out.println("Updating board");

        boolean activeBuilding = false;
        TileButton[][] accessMatrix = boardUI.getTileAccessMatrix();
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                accessMatrix[row][col].setResourceEnum(gameResourceBoard[row][col].getResource());
                accessMatrix[row][col].setBuildingEnum(gameBuildingBoard[row][col].getType());
                accessMatrix[row][col].updateButton();
                if (gameBuildingBoard[row][col].getType() == BuildingEnum.WAREHOUSE || gameBuildingBoard[row][col].getType() == BuildingEnum.FACTORY || gameBuildingBoard[row][col].getType() == BuildingEnum.BANK) {
                    activeBuilding = true;
                }
            }
        }
        if (activeBuilding) {
            boardUI.getActiveBuildingPanel().updateActiveBuildings();
        }


//        for (int row = 0; row < gameResourceBoard.length; row++) {
//            for (int col = 0; col < gameResourceBoard[row].length; col++) {
//                int boardWhiteSpaceLength = 9;
//                DebugTools.logging("[UPDATE_BOARD] - Checking row: " + row + " col: "+col);
//                if (gameResourceBoard[row][col].getResource() != ResourceEnum.NONE && gameResourceBoard[row][col].getResource() != ResourceEnum.OBSTRUCTED && gameResourceBoard[row][col].getResource() != ResourceEnum.TPOST) {
//                    DebugTools.logging("[UPDATE_BOARD] - "+DebugTools.resourceInformation(gameResourceBoard[row][col]) + " NONEMPTY RESOURCE. Updating it");
//                    gameBoard[row][col] = "[" + Utility.generateColorizedString(Utility.lengthResizer(gameResourceBoard[row][col].toString(), boardWhiteSpaceLength), gameResourceBoard[row][col].getResource()) + "]";
//                }
//                else if (gameBuildingBoard[row][col].getType() != BuildingEnum.NONE) {
//                    DebugTools.logging("[UPDATE_BOARD] - "+DebugTools.buildingInformation(gameBuildingBoard[row][col]) + " NONEMPTY BUILDING. Updating it");
//                    gameBoard[row][col] = "["+ Utility.generateColorizedString(Utility.lengthResizer(gameBuildingBoard[row][col].getType().toString(), boardWhiteSpaceLength), gameBuildingBoard[row][col].getType())+ "]";
//                }
//                else {
//                    DebugTools.logging("[UPDATE_BOARD] - Nothing here. Updating with empty tile");
//                    gameBoard[row][col] = "["+Utility.lengthResizer("EMPTY!", boardWhiteSpaceLength)+"]";
//                }
//
//            }
//        }
    }
//    public void renderBoard()  {
//        //this.updateBoard();
//        System.out.println("============="+boardName.toUpperCase()+"'s BOARD============");
//        for (int i = 0; i < letterCoords.length; i++) {
//            if (i == 4) {
//                System.out.println(letterCoords[i]);
//            }
//            else {
//                System.out.print(letterCoords[i]);
//
//            }
//        }
//        for (int row = 0; row < gameBoard.length; row++) {
//            for (int col = -1; col < gameBoard[row].length; col++) {
//                if (col == -1) {
//                    System.out.print(numberCoords[row]);
//                } else {
//                    if (col == 3) {
//                        System.out.println(gameBoard[row][col]);
//                    } else {
//                        System.out.print(gameBoard[row][col]);
//                    }
//                }
//            }
//        }
//    }

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
