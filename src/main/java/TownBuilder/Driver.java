package TownBuilder;

import TownBuilder.Buildings.*;
import TownBuilder.DebugApps.DebugTools;

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
        ResourceEnum resource = null;
        // if there's only one player, use default singleplayer code
        if (playerCount < 2) {
            Board board;

            board = boardArrayList.get(0);
            Manual.townHallNote();
            while (!board.isGameCompletion()) { // this loop continues until the player has no empty slots in board
                board.setGameCompletion(board.gameOver());
                if (!board.isGameCompletion()) {
                    if (board.getBoardName().equals("debug")) {
                        resource = turnExecution(board, resource, true, true, false,buildingsForGame );
                    }
                    else if (board.getBoardName().equals("debug_building")) {
                        System.out.println("Building debug");
                        resource = turnExecution(board, resource, false, false, true, buildingsForGame);
                    }
                    else {
                        resource = turnExecution(board, resource, true, false, false, buildingsForGame);
                    }
                }
            }
            // when loop exits, immediately perform the final score check
            board.scoring(false);
        }
        else {
            do {
                /*
                    multiplayer functions by using an ArrayList of Board objects. the first player is the first Board.
                    the first index of the ArrayList is used to pick the resource and they play their turn, and then are removed from the
                    list temporarily. the remaining players execute their turns based on the assignment of resource. then, the first board
                    is added back to the end.
                 */
                Board pickResourceBoard;

                if (boardArrayList.get(0).CanBeMasterBuilder()) {
                    pickResourceBoard = boardArrayList.get(0);
                }
                else {
                    pickResourceBoard = boardArrayList.get(1);
                }
                resource = turnExecution(pickResourceBoard, resource, true, true, false,buildingsForGame );
                boardArrayList.remove(pickResourceBoard); // removes from board
                // if board is game complete, score them
                if (pickResourceBoard.isGameCompletion()) {
                    int score = pickResourceBoard.scoring(false);
                    System.out.println(pickResourceBoard.getBoardName() + "'s final score: "+score);
                }
                // loop for remaining players
                for (int p = 0; p < boardArrayList.size(); p++) {
                    Board temp = boardArrayList.get(p); // saves current board to temporary variable
                    if (!temp.isGameCompletion())  {
                        turnExecution(temp, resource, false, true,false, buildingsForGame);
                        if (temp.isGameCompletion()) {
                            int score = pickResourceBoard.scoring(false);
                            System.out.println(temp.getBoardName() + "'s final score: "+score);
                            // if game is complete, remove them permanently from board list
                            boardArrayList.remove(temp);
                        }
                    }
                }
                // the board that was removed won't be added back if it's game complete
                if (!pickResourceBoard.isGameCompletion()) {
                    boardArrayList.add(pickResourceBoard);
                }
            }
            // continue while there are still boards that are playable
            while (boardArrayList.size() != 0);
        }
        System.out.println("All players have finished TownBuilder. Thanks for playing! -Matt");
    }
    public static void buildingSelection(ArrayList<Building> buildingsForGame) {
        Scanner sc = new Scanner(System.in);

        HashMap<ColorEnum, ArrayList<Building>> buildingMasterList = BuildingFactory.getBuildingMasterList();
        String userInput;
        boolean isUserInputValid = false;
        boolean isCustomGame = false;
        boolean isRandomGame = false;
        //Utility.setColor(false);
        ColorEnum[] colors = new ColorEnum[]{ColorEnum.BLUE, ColorEnum.RED, ColorEnum.GRAY, ColorEnum.ORANGE, ColorEnum.GREEN, ColorEnum.YELLOW, ColorEnum.WHITE};
        do {
            System.out.println("Welcome to TownBuilder. Would you like to play a default game (recommended for new players), a custom game, or a random game?");
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
    /*
        This method faciliates the actions of each turn and includes code for use in both multiplayer and singleplayer applications.

        @params
        Set resourcePick to true if you're intending to allow the user to pick the resource (singleplayer and multiplayer first board)
        Set isMultiplayerGame to true if you're using a multiplayer game, otherwise false for singleplayer
            This tells resourcePicker() what instructions to follow

     */
    public static ResourceEnum turnExecution(Board board, ResourceEnum resource, boolean resourcePick, boolean isMultiplayerGame, boolean placeBuilding, ArrayList<Building> buildingsForGame) throws IOException, URISyntaxException {
        // run code that returns a resource if method was called with resourcePick = true
        if (placeBuilding) {
            board.renderBoard();
            board.buildingPlacer(buildingsForGame, true);
            turnActions(board, ResourceEnum.randomResource());
            return null;
        }
        if (resourcePick) {
            ResourceEnum r;
            board.renderBoard();
            if (isMultiplayerGame) {
                System.out.println("It's "+ board.getBoardName() + "'s turn to DECIDE the resource!");
            }
            // if isMultiplayer = false, will use singleplayer code in resourcePicker()
            r = board.resourcePicker(); // assigns the return value of resourcePicker()
            turnActions(board, r);
            return r;
        }
        else {
            board.renderBoard();
            System.out.println("It's " + board.getBoardName() + "'s turn to place a resource.");
            turnActions(board, resource);
        }
        return null;
    }
    /*
        every turn does this, so I decided to split it into its own method. Runs the turn actions for all players.
     */
    private static void turnActions(Board board, ResourceEnum resource) throws IOException, URISyntaxException{
        board.playerTurn(resource);
        board.detectValidBuilding();
        board.runBuildingTurnAction();
        board.setGameCompletion(board.gameOver());
    }
}
