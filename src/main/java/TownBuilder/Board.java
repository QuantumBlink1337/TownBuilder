package TownBuilder;


import TownBuilder.Buildings.*;
import TownBuilder.Buildings.Monuments.Monument;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.UI.BoardUI;
import TownBuilder.UI.InitializationUI;
import TownBuilder.UI.TileButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final ArrayList<Building> detectableBuildings;
    private final ArrayList<Building> scorableBuildings;

    public ArrayList<BuildingEnum> getBuildingsBuiltThisTurn() {
        return buildingsBuiltThisTurn;
    }

    private final ArrayList<BuildingEnum> buildingsBuiltThisTurn = new ArrayList<>();
    private static final ArrayList<BuildingEnum> monumentTypes = new ArrayList<>(Arrays.asList(BuildingEnum.AGUILD, BuildingEnum.ARCHIVE, BuildingEnum.BARRETT, BuildingEnum.CATERINA, BuildingEnum.IRONWEED,
            BuildingEnum.GROVEUNI, BuildingEnum.MANDRAS, BuildingEnum.OPALEYE, BuildingEnum.SHRINE, BuildingEnum.SILVAFRM, BuildingEnum.STARLOOM, BuildingEnum.OBELISK, BuildingEnum.SKYBATHS));
    public static ArrayList<BuildingEnum> getMonumentTypes() {
        return monumentTypes;
    }
    private ArrayList<ResourceEnum> blacklistedResources;
    public Scorer getScorer() {
        return scorer;
    }
    private final Scorer scorer;
    private int spResourceSelectionIncrement = 0;
    private int boardFinishPlace = 0;
    private boolean canBeMasterBuilder = true;
    private boolean monumentPlacement = false;
    private boolean isGameCompletion = false;
    private boolean turnOver = false;
    private boolean placeAnywhere = false;

    public boolean isSingleplayer() {
        return isSingleplayer;
    }

    private final boolean isSingleplayer;
    private final String boardName;
    private final Resource[][] gameResourceBoard = new Resource[4][4];
    private final Building[][] gameBuildingBoard = new Building[4][4];
    private final BuildingFactory buildingFactory;
    private final PlayerManager playerManager;
    private ResourceEnum currentResourceForTurn;
    private final BoardUI boardUI;
    private final Object notifier;

    private final boolean buildingCheat;
    private final boolean resourceCheat;

    public Board(ArrayList<Building> b, PlayerManager playerManager, boolean ISP, String boardName, boolean buildingCheat, boolean resourceCheat, boolean monumentCheat) throws IOException {
        this.playerManager = playerManager;
        this.boardName = boardName;
        isSingleplayer = ISP;
        this.buildingCheat = buildingCheat;
        this.resourceCheat = resourceCheat;
        // these monuments won't work / will crash the game if its singleplayer
        // the board game also doesn't let SP users use these monuments anyway
        if (ISP) {
            monumentTypes.removeIf(m -> m == BuildingEnum.IRONWEED || m == BuildingEnum.OPALEYE || m == BuildingEnum.STARLOOM) ;
        }
        // use b to create two separate arraylists because what is scorable isn't always a valid building to find (monuments)
        detectableBuildings = new ArrayList<>(b);
        scorableBuildings = new ArrayList<>(b);
        buildingFactory = new BuildingFactory();
        generateMonument(monumentCheat, playerManager.getInitializationUI());
        scorer = new Scorer(this, scorableBuildings);
        notifier = new Object();
        boardUI = new BoardUI(this);
        buildArrays();
        updateBoard();
    }
    /*
        Debug constructor for Board. Used for ScoringBuildingTest
     */
    public Board(ArrayList<Building> b, BuildingEnum mo, PlayerManager playerManager) throws IOException {
        this.buildingCheat = false;
        this.resourceCheat = false;
        this.playerManager = playerManager;
        boardName = "DEBUG";
        isSingleplayer = true;
        detectableBuildings = new ArrayList<>(b);
        scorableBuildings = new ArrayList<>(b);
        buildingFactory = new BuildingFactory();
        Monument monument = (Monument) BuildingFactory.getBuilding(mo,scorableBuildings, -1, -1, false,this);
        detectableBuildings.add(monument);
        scorableBuildings.add(monument);
        scorer = new Scorer(this, scorableBuildings);
        boardUI = new BoardUI(this);
        boardUI.setVisible(true);
        notifier = new Object();
        buildArrays();
        updateBoard();
    }


    public Object getNotifier() {
        return notifier;
    }
    public ArrayList<ResourceEnum> getBlacklistedResources() {
        return blacklistedResources;
    }
    public BoardUI getBoardUI() {
        return boardUI;
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
    public BuildingFactory getBuildingFactory() {
        return buildingFactory;
    }
    public boolean CanBeMasterBuilder() {
        return canBeMasterBuilder;
    }
    public void setCanBeMasterBuilder(boolean canBeMasterBuilder) {
        this.canBeMasterBuilder = canBeMasterBuilder;
    }
    public void setPlaceAnywhere(boolean placeAnywhere) {
        this.placeAnywhere = placeAnywhere;
    }
    /*
        Generates a monument for a user based on the available monument types arraylist, and depending on cheat mode,
        calls upon InitializationUI to prompt for monument selection.
     */
    private void generateMonument(boolean isCheat, InitializationUI iUI) throws IOException {
        Monument monument;
        if (isCheat) {
            JPanel panel = iUI.createMonumentSelectionPanel(monumentTypes, this);
            iUI.add(panel);
            iUI.updateUI();
            synchronized (Utility.getNotifier()) {
                try {
                    Utility.getNotifier().wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            monument = (Monument) BuildingFactory.getBuilding(iUI.getBuildingSelection(), scorableBuildings, -1, -1, true, this);
            iUI.remove(panel);
            iUI.updateUI();
        }
        else {
            int randomIndex = (int) (Math.random() * monumentTypes.size());
            monument = (Monument) BuildingFactory.getBuilding(monumentTypes.get(randomIndex), scorableBuildings, -1, -1, true, this);
            monumentTypes.remove(randomIndex);
        }
        detectableBuildings.add(monument);
        scorableBuildings.add(monument);
    }
    /*
        Fills the arrays with new "blank" representations of the objects they're tracking.
     */
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
    public int scoring() throws IOException {
        return scorer.scoring();
    }
    /*
        If either of the boards have a slot that is nonempty, then increment i by one.
        Returns true if i is greater than or equal to the area of the board.
     */
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
        return i >= (gameResourceBoard.length * gameResourceBoard.length);
    }
    /*
        Called when a monument is placed on the board.
     */
    public void monumentControl(Monument monument) throws IOException {
        monument.onPlacement();
        monumentPlacement = true;
    }
    /*
        Prompts the user on if they want to place a building that was detected.
     */
    private void placementPrompt(Building building) throws IOException {
        boardUI.highlightBoardTiles(buildingFactory.getValidResources());
        boardUI.promptYesNoPrompt("A valid "+building.toString()+" construction was found. Place it this turn?");
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
        // if they said yes, then call placeBuildingOnBoard
        if (selection) {
            buildingsBuiltThisTurn.add(building.getType());
            buildingFactory.placeBuildingOnBoard(building.getType(), detectableBuildings, building.getType() == BuildingEnum.SHED || placeAnywhere,this);
            updateBoard();
        }
        // otherwise, reset the tiles as if they were not detected at all
        else {
            boardUI.resetBoardTiles(true, true);
            buildingFactory.clearValidResourcesWithFlag(building.getType());
        }
    }
    /*
        Iterates through each index of resource board and detects if a valid pattern is present starting at that index.
     */
    public void detectValidBuilding() throws IOException {
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                for (Building building : detectableBuildings) {
                    // If the building in question is a monument, and we already placed a monument, do not attempt detection
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
    }
    /*
        Runs a buildings turn interval. E.g a Farm feeding buildings.
        Also used to detect how many active buildings are on the board to prevent further detection.
     */
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

                else if (building instanceof Warehouse || building instanceof Factory || building instanceof TradingPost) {
                    bankCounter++;
                }
            }
        }
        /*
           If we have more than 4 active buildings the active building UI panel would overflow into the scoring panel and look bad.
           We cannot have 4 banks because a 5th bank would softlock at resource selection. Also the board game doesn't let you have 4 banks.
        */
        if (bankCounter > 4) {
            detectableBuildings.removeIf(building -> building.getType() == BuildingEnum.BANK || building.getType() == BuildingEnum.WAREHOUSE || building.getType() == BuildingEnum.TRDINGPST || building.getType() == BuildingEnum.FACTORY);
        }
    }
    /*
        Handles resource selection. For multiplayer, we just call resourcePicker for the resource.
        For singleplayer, depending on spResourceSelectionIncrement we either do that or call a random.
        Either way, the resulting ResourceEnum is returned.
     */
    public ResourceEnum resourcePicker(String string) throws IOException {
        ResourceEnum turnResource;
        if (resourceCheat) {
            Utility.resetResourceArray();
            return Utility.resourcePicker(blacklistedResources, this, "Cheat mode enabled. Pick a resource!");
        }
        if (!isSingleplayer) {
            boardUI.setPrimaryTextLabel(" ");
            turnResource = Utility.resourcePicker(blacklistedResources, this, string);
        }
        else {

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
    /*
        Prompts user for what building they want and places it on the board. Returns the enum of the building chosen.
     */
    public BuildingEnum buildingPlacer(ArrayList<Building> buildingArrayList, boolean placeAnywhere, boolean placeOnBoard) throws IOException {
        boardUI.promptBuildingSelection("What building would you like?");
        synchronized (Utility.getNotifier()) {
            try {
                Utility.getNotifier().wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (placeOnBoard) {
            buildingFactory.placeBuildingOnBoard(boardUI.getSelectedBuildingForTurn(), buildingArrayList, placeAnywhere, this);
            updateBoard();
        }
        return boardUI.getSelectedBuildingForTurn();
    }
    /*
        Handles the actions that a Factory building can do.
     */
    private void factoryOption(Factory building) throws IOException {
        if (currentResourceForTurn == building.getFactorizedResource()) {
            currentResourceForTurn = building.exchangeResource();
        }
        else {
            boardUI.getErrorTextLabel().setVisible(true);
            boardUI.getErrorTextLabel().setText("That Factory doesn't match the resource this turn.");
        }
        turnOver = false;

    }
    /*
        Handles the actions that a Warehouse building can do.
     */
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
    }
    /*
        Handles the placement of a resource as well as prompting for active building use. Takes a resource to place,
        and a string to denote what kind of turn it is.
     */
    public void playerTurn(ResourceEnum resourceEnum, String string) throws IOException {
        buildingsBuiltThisTurn.clear();
        boardUI.setResourceSelectionLabel();
        currentResourceForTurn = resourceEnum;
        boardUI.setSecondaryTextLabel(string);
        if (buildingCheat) {
            buildingPlacer(scorableBuildings, true, true);
        }
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
            /*
                If a tile button was pressed, then we can assume the turn is over as they've asked to place a resource.
             */
            if (boardUI.isCoordinatesClicked()) {
                int[] coords = boardUI.getSelectedCoords();
                gameResourceBoard[coords[0]][coords[1]].setResource(currentResourceForTurn);
                turnOver = true;
            }
            else if (boardUI.isPlayerRestart()) {
                resetBoard();
                boardUI.setPlayerRestart(false);
                turnOver = true;
            }
            /*
                Otherwise, redirect to the appropriate active building option.
             */
            else if (boardUI.isActiveBuildingToggled()) {
                switch (boardUI.getActiveMode()) {
                    case "swap" -> warehouseOption(currentResourceForTurn, (Warehouse) boardUI.getSelectedActiveBuilding(), false);
                    case "place" -> warehouseOption(currentResourceForTurn, (Warehouse) boardUI.getSelectedActiveBuilding(), true);
                    case "exchange" -> factoryOption((Factory) boardUI.getSelectedActiveBuilding());
                }
            }
        }
        while (!turnOver);
        boardUI.getErrorTextLabel().setVisible(false);
        updateBoard();
    }
    /*
        Overloaded method that is used to update the board with its own resource and building matrix.
        Optionally, the board can be updated with a separate building and resource matrix. This is used for in multiplayer when
        one player wants to "peek" at another player's board.
     */
    public void updateBoard() throws IOException {
        updateBoard(gameResourceBoard, gameBuildingBoard);
    }
    /*
        Handles updating each of the resource and building matrices with their new placement information, and also
        calls the GUI update for the displayable matrix.
     */
    public void updateBoard(Resource[][] gRB, Building[][] gBB) {
        TileButton[][] accessMatrix = boardUI.getTileAccessMatrix();
        boolean activeBuilding = false;
        for (int row = 0; row < gRB.length; row++) {
            for (int col = 0; col < gBB[row].length; col++) {
                accessMatrix[row][col].setResourceEnum(gRB[row][col].getResource());
                accessMatrix[row][col].setBuildingEnum(gBB[row][col].getType());
                // If there's at least one "Active Building" type on the board, then we need to eventually attempt to update the Active Building panel.
                // To do: Maybe a method to return whether a building is active in the first place instead of using the building's enum.
                if (gBB[row][col].getType() == BuildingEnum.WAREHOUSE || gBB[row][col].getType() == BuildingEnum.FACTORY || gBB[row][col].getType() == BuildingEnum.BANK) {
                    activeBuilding = true;
                }
            }
        }
        if (activeBuilding) {
            boardUI.getActiveBuildingPanel().updateActiveBuildings();
        }
    }
    public void resetBoard() throws IOException {
        boardUI.promptYesNoPrompt("Are you sure you want to reset your board?");
        synchronized (Utility.getNotifier()) {
            try {
                Utility.getNotifier().wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (boardUI.getUserYesNoAnswer()) {
            buildArrays();
            updateBoard();
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
