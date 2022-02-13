package TownBuilder;

import TownBuilder.Buildings.Building;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.UI.InitializationUI;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class PlayerManager {




    private final ArrayList<Board> boards = new ArrayList<>();
    private final InitializationUI initializationUI;
    private ArrayList<Board> multiplayerModifiableBoards;
    private final ArrayList<Building> masterBuildings;
    private boolean isSingleplayer;
    private int boardFinishPlace = 1;
    // default constructor. takes the InitializationUI in order to prompt the GUI
    public PlayerManager(ArrayList<Building> masterBuildings, InitializationUI iUI) throws IOException {
        this.masterBuildings = masterBuildings;
        initializationUI = iUI;
        DebugTools.logging("[PLAYER_MANAGER_INIT]");
    }
    // this constructor should only be invoked in a debug setting, usually the MPTest executable uses this as it pre-gens the Boards
    public PlayerManager(ArrayList<Building> masterBuildings, Board... b) throws IOException {
        DebugTools.logging("[DEBUG_PLAYER_MANAGER_INIT]");
        initializationUI = null;
        this.masterBuildings = masterBuildings;
        boards.addAll(List.of(b));
        multiplayerModifiableBoards = new ArrayList<>(boards);
        isSingleplayer = boards.size() < 2;
    }
    // prompts GUI to select how many players there will be and creates n amount of Boards and places them in boards
    public void determineNumberOfBoards() throws IOException {
        System.out.println("board");
        int playerCount;
        DebugTools.logging("[PLAYER_MANAGER] - Determining number of boards");
        initializationUI.promptPlayerSelection();
        synchronized (Utility.getNotifier()) {
            try {
                Utility.getNotifier().wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        initializationUI.getPlayerSelectionPanel().setVisible(false);
        playerCount = initializationUI.getPlayerCount();


        DebugTools.logging("[PLAYER_MANAGER] - User specified " + playerCount + " boards.");

        for (int i= 0; i < playerCount; i++) {
            // generates a new Board object for each player
            JPanel panel = initializationUI.generatePlayerNameSelectionPanel(); // temporarily adds a unique player name selection panel and removes it when name is collected
            initializationUI.add(panel);
            initializationUI.updateUI();
            synchronized (Utility.getNotifier()) {
                try {
                    Utility.getNotifier().wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String boardName = initializationUI.getChosenBoardName();
            if (boardName.equalsIgnoreCase("_debug")) {
                initializationUI.remove(panel);
                initializationUI.updateUI();
                panel = initializationUI.createCheatsPanel();
                initializationUI.add(panel);
                initializationUI.updateUI();

                synchronized (Utility.getNotifier()) {
                    try {
                        Utility.getNotifier().wait();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            Board temp = new Board(masterBuildings, this, playerCount == 1, initializationUI.getChosenBoardName(), initializationUI.isBuildingSelectionCheat(), initializationUI.isResourceSelectionCheat(), initializationUI.isMonumentSelectionCheat(), initializationUI);
            boards.add(temp);
            initializationUI.remove(panel);
            initializationUI.updateUI();
        }
        isSingleplayer = !(boards.size() > 1);
        multiplayerModifiableBoards = new ArrayList<>(boards);
        Driver.getGameFrame().remove(initializationUI);
        Driver.initFrame(); // reset Frame when removing the initialization panel. it will not appear again in the program
        for (Board board : boards) {
            board.getBoardUI().createPlayerView();
        }
    }
    // returns true if there is at least one Board that is still able to place things
    public boolean gameActive() throws IOException {
        int finishedPlayers = 0;
        for (Board board : boards) {
            finishedPlayers += board.gameOver() ? 1 : 0;
        }
        return finishedPlayers != boards.size();
    }
    // returns true if board cannot place anything else on it
    public boolean boardComplete(Board board) {
        if (board.isGameCompletion()) {
            if (board.getBoardFinishPlace() == 0) {
                // gives the board its placement when finishing. useful for the Monument Starloom which determines points based on this value
                board.setBoardFinishPlace(boardFinishPlace);
                boardFinishPlace++;
            }
            return true;
        }
        return false;
    }
    public ArrayList<Board> getBoards() {
        return boards;
    }
    // gets "adjacent" boards. this is to simulate a real life board game system of "the player to your left/right".
    public Board[] getAdjacentBoards(Board b) throws IOException {
        Board[] adjacentBoards = new Board[2];
        DebugTools.logging("[GET_ADJACENT_BOARDS] - Starting process.");
        // if this was called from singleplayer something is very wrong, and we need to crash the program :(
        if (isSingleplayer) {
            DebugTools.logging("[GET_ADJACENT_BOARDS] - This was called from a singleplayer game. THIS WILL RESULT IN AN EXCEPTION.");
            throw new NullPointerException();
        }
        int index = 0;
        for (int i = 0; i < boards.size(); i++) {
            if (b == boards.get(i)) {
                DebugTools.logging("[GET_ADJACENT_BOARDS] - Found the given Board in the list with an index of " +i);
                index = i;
                break;
            }
        }
        // if we can't go to the index to the left of the given index, just wrap to the last one on the list
        if (index-1 < 0) {
            adjacentBoards[0] = boards.get(boards.size()-1);
            DebugTools.logging("[GET_ADJACENT_BOARDS] - Left Bound Board doesn't exist. Wrapping to the last Board in the list - " + adjacentBoards[0].getBoardName());
        }
        else {
            adjacentBoards[0] = boards.get(index-1);
            DebugTools.logging("[GET_ADJACENT_BOARDS] - Left Bound Board grabbed - " + adjacentBoards[0].getBoardName());
        }
        // if we can't go to the index to the right of the given index, just wrap to the first one on the list
        if (index+1 > boards.size() - 1) {
            adjacentBoards[1] = boards.get(0);
            DebugTools.logging("[GET_ADJACENT_BOARDS] - Right Bound Board doesn't exist. Wrapping to the first Board in the list - " + adjacentBoards[1].getBoardName());
        }
        else {
            adjacentBoards[1] = boards.get(index+1);
            DebugTools.logging("[GET_ADJACENT_BOARDS] - Right Bound Board grabbed - " + adjacentBoards[1].getBoardName());

        }
        return adjacentBoards;
    }
    // general turn actions for each Board. this is where the main game logic actually occurs and all processes (building placement, etc.) spawn from this method.
    public void manageTurn() throws IOException, InterruptedException {
        ResourceEnum resource;
        DebugTools.logging("[MANAGE_TURN] - Beginning turn management process. Is Singleplayer? : " + isSingleplayer);
        if (isSingleplayer) {
            Board board;
            board = boards.get(0);
            // this loop continues until the player has no empty slots in board
            board.setGameCompletion(board.gameOver());
            Driver.getGameFrame().add(board.getBoardUI());
            Driver.initFrame();
            if (!board.isGameCompletion()) {
                /*
                    Support for the debug version of the game is here. If the Board name is set to a debug string, we pass along variables that force turnExecution to do what the debug task is.
                 */
                if (board.getBoardName().equals("debug")) {
                    DebugTools.logging("[MANAGE_TURN] - Debug Singleplayer turnExecution");
                    turnExecution(board, null, true, true, false, masterBuildings);
                }
                else if (board.getBoardName().equals("debug_building")) {
                    System.out.println("Building debug");
                    DebugTools.logging("[MANAGE_TURN] - Debug Building Singleplayer turnExecution");

                    turnExecution(board, null, false, false, true, masterBuildings);
                }
                else {
                    DebugTools.logging("[MANAGE_TURN] -  Singleplayer turnExecution");
                    turnExecution(board, null, true, false, false, masterBuildings);
                }
            }
            if (boardComplete(board)) {
                board.getBoardUI().promptFinalScoreView();
                synchronized (Utility.getNotifier()) {
                    try {
                        Utility.getNotifier().wait();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        else {
             /*
                    multiplayer functions by using an ArrayList of Board objects. the first player is the first Board.
                    the first index of the ArrayList is used to pick the resource, and they play their turn, and then are removed from the
                    list temporarily. the remaining players execute their turns based on the assignment of resource. then, the first board
                    is added back to the end.
                 */
            Board pickResourceBoard;

            // first, we determine the player whose turn it is to select the resource for everyone else.

            // the monument FortIronweed removes the ability to pick the resource. if this applies to the board at 0, then just go to the next one available.
            if (multiplayerModifiableBoards.get(0).CanBeMasterBuilder()) {
                pickResourceBoard = multiplayerModifiableBoards.get(0);
            }
            else {
                pickResourceBoard = multiplayerModifiableBoards.get(1);
            }
            DebugTools.logging("[MULTIPLAYER_TURN] - Stored Board " +pickResourceBoard.getBoardName());
            Driver.getGameFrame().add(pickResourceBoard.getBoardUI());
            Driver.initFrame();

            resource = turnExecution(pickResourceBoard, true, false, masterBuildings);
            multiplayerModifiableBoards.remove(pickResourceBoard); // removes from board
            Thread.sleep(1000); // why is this here?
            Driver.getGameFrame().remove(pickResourceBoard.getBoardUI());
            Driver.initFrame();
            DebugTools.logging("[MULTIPLAYER_TURN] - Removed Board " +pickResourceBoard.getBoardName() + " from multiplayer boards TEMPORARILY");

            // if board is game complete, score them
            if (boardComplete(pickResourceBoard)) {
                DebugTools.logging("[MULTIPLAYER_TURN] - Scored Board " + pickResourceBoard.getBoardName());
                int score = pickResourceBoard.scoring();
                System.out.println(pickResourceBoard.getBoardName() + "'s final score: " + score);
            }
            // now that we have our resource picked, loop for remaining players
            for (int p = 0; p < multiplayerModifiableBoards.size(); p++) {
                Board temp = multiplayerModifiableBoards.get(p); // saves current board to temporary variable
                DebugTools.logging("[MULTIPLAYER_TURN] - Stored Board " +temp.getBoardName() + " to temporary variable");
                Driver.getGameFrame().add(temp.getBoardUI());
                Driver.initFrame();
                if (!boardComplete(temp))  {
                    turnExecution(temp, resource, false, true,false, masterBuildings);
                    if (boardComplete(temp)) {
                        // if game is complete, remove them permanently from board list
                        multiplayerModifiableBoards.remove(temp);
                    }
                }
                Driver.getGameFrame().remove(temp.getBoardUI());
                Driver.initFrame();
            }
            // the board that was removed won't be added back if it's game complete
            if (!boardComplete(pickResourceBoard)) {
                multiplayerModifiableBoards.add(pickResourceBoard);
            }
        }
        // continue while there are still boards that are playable
    }
    // fast track turnExecution method for picking a resource
    @SuppressWarnings("SameParameterValue")
    private static ResourceEnum turnExecution(Board board, boolean isMultiplayerGame, boolean placeBuilding, ArrayList<Building> buildingsForGame) throws IOException {
        return turnExecution(board, null, true, isMultiplayerGame, placeBuilding, buildingsForGame);
    }
    /*
        This handles the passing of resource to turnActions. If resourcePick is true, they're allowed to pick a resource. Otherwise, the resource will be whatever is passed in.

        resource should be set to null if resourcePick is also true.
     */
    private static ResourceEnum turnExecution(Board board, ResourceEnum resource, boolean resourcePick, boolean isMultiplayerGame, boolean placeBuilding, ArrayList<Building> buildingsForGame) throws IOException {
        board.updateBoard();
        board.getBoardUI().getMainPanel().updateUI();
        if (placeBuilding) {
            //board.renderBoard();
            board.setLastBuiltBuilding(board.buildingPlacer(buildingsForGame, true));
            turnActions(board, Utility.randomResource(), null);
            return null;
        }
        if (resourcePick) {
            ResourceEnum r;
            String string = " ";
            if (isMultiplayerGame) {
                string = "It's "+ board.getBoardName() + "'s turn to DECIDE the resource!";
            }
            // if isMultiplayer = false, will use singleplayer code in resourcePicker()
            r = board.resourcePicker(string); // assigns the return value of resourcePicker()
            System.out.println(r);
            turnActions(board, r, string);
            return r;
        }
        else {
            //board.renderBoard();
            turnActions(board, resource, "It's " + board.getBoardName() + "'s turn to place a resource.");
        }
        return null;
    }
    /*
        Contains all the actions an individual player's turn needs to do. Place a resource, look for valid buildings, do actions for in between turns, and check if the board is full.
       */
    private static void turnActions(Board board, ResourceEnum resource, String string) throws IOException{
        board.playerTurn(resource, string);
        board.detectValidBuilding();
        board.runBuildingTurnAction();
        board.setGameCompletion(board.gameOver());
    }
}
