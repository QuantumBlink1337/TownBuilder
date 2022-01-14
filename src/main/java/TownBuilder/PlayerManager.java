package TownBuilder;

import TownBuilder.Buildings.Building;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.UI.InitializationUI;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class PlayerManager {




    private final ArrayList<Board> boards = new ArrayList<>();
    private final InitializationUI initializationUI;
    private ArrayList<Board> multiplayerModifiableBoards;
    private final ArrayList<Building> masterBuildings;
    private boolean isSingleplayer;
    private int boardFinishPlace = 1;

    public PlayerManager(ArrayList<Building> masterBuildings, InitializationUI iUI) throws IOException {
        this.masterBuildings = masterBuildings;
        initializationUI = iUI;
        DebugTools.logging("[PLAYER_MANAGER_INIT]");
    }
    public PlayerManager(ArrayList<Building> masterBuildings, Board... b) throws IOException {
        DebugTools.logging("[DEBUG_PLAYER_MANAGER_INIT]");
        initializationUI = null;
        this.masterBuildings = masterBuildings;
        boards.addAll(List.of(b));
        multiplayerModifiableBoards = new ArrayList<>(boards);
        isSingleplayer = boards.size() < 2;
    }
    public void determineNumberOfBoards() throws IOException {
        System.out.println("board");
        int playerCount = 0;
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
            JPanel panel = initializationUI.generatePlayerNameSelectionPanel();
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

            Board temp = new Board(masterBuildings, this, playerCount == 1, initializationUI.getChosenBoardName());
            boards.add(temp);
            initializationUI.remove(panel);
            initializationUI.updateUI();
        }
        isSingleplayer = !(boards.size() > 1);
        multiplayerModifiableBoards = new ArrayList<>(boards);
        Driver.getGameFrame().remove(initializationUI);
        Driver.initFrame();
        for (Board board : boards) {
            board.getBoardUI().createPlayerView();
        }
    }
    public boolean gameActive() throws IOException {
        int finishedPlayers = 0;
        for (Board board : boards) {
            finishedPlayers += board.gameOver() ? 1 : 0;
        }
        return finishedPlayers != boards.size();
    }
    public boolean boardComplete(Board board) {
        if (board.isGameCompletion()) {
            if (board.getBoardFinishPlace() == 0) {
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
    public Board[] getAdjacentBoards(Board b) throws IOException {
        Board[] adjacentBoards = new Board[2];
        DebugTools.logging("[GET_ADJACENT_BOARDS] - Starting process.");
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
        if (index-1 < 0) {
            adjacentBoards[0] = boards.get(boards.size()-1);
            DebugTools.logging("[GET_ADJACENT_BOARDS] - Left Bound Board doesn't exist. Wrapping to the last Board in the list - " + adjacentBoards[0].getBoardName());
        }
        else {
            adjacentBoards[0] = boards.get(index-1);
            DebugTools.logging("[GET_ADJACENT_BOARDS] - Left Bound Board grabbed - " + adjacentBoards[0].getBoardName());
        }
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
    public void manageTurn() throws IOException, URISyntaxException, InterruptedException {
        ResourceEnum resource = null;
        DebugTools.logging("[MANAGE_TURN] - Beginning turn management process. Is Singleplayer? : " + isSingleplayer);
        if (isSingleplayer) {
            Board board;
            board = boards.get(0);
            // this loop continues until the player has no empty slots in board
            board.setGameCompletion(board.gameOver());
            Driver.getGameFrame().add(board.getBoardUI());
            Driver.initFrame();
            if (!board.isGameCompletion()) {
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
                    the first index of the ArrayList is used to pick the resource and they play their turn, and then are removed from the
                    list temporarily. the remaining players execute their turns based on the assignment of resource. then, the first board
                    is added back to the end.
                 */
            Board pickResourceBoard;


            if (multiplayerModifiableBoards.get(0).CanBeMasterBuilder()) {
                pickResourceBoard = multiplayerModifiableBoards.get(0);
            }
            else {
                pickResourceBoard = multiplayerModifiableBoards.get(1);
            }
            DebugTools.logging("[MULTIPLAYER_TURN] - Stored Board " +pickResourceBoard.getBoardName());
            Driver.getGameFrame().add(pickResourceBoard.getBoardUI());
            Driver.initFrame();
            resource = turnExecution(pickResourceBoard, null, true, true, false, masterBuildings );
            multiplayerModifiableBoards.remove(pickResourceBoard); // removes from board

            Thread.sleep(1000);
            Driver.getGameFrame().remove(pickResourceBoard.getBoardUI());
            Driver.initFrame();
            DebugTools.logging("[MULTIPLAYER_TURN] - Removed Board " +pickResourceBoard.getBoardName() + " from multiplayer boards TEMPORARILY");

            // if board is game complete, score them
            if (boardComplete(pickResourceBoard)) {
                DebugTools.logging("[MULTIPLAYER_TURN] - Scored Board " +pickResourceBoard.getBoardName());
                int score = pickResourceBoard.scoring(false);
                System.out.println(pickResourceBoard.getBoardName() + "'s final score: "+score);
            }
            // loop for remaining players
            for (int p = 0; p < multiplayerModifiableBoards.size(); p++) {
                Board temp = multiplayerModifiableBoards.get(p); // saves current board to temporary variable
                DebugTools.logging("[MULTIPLAYER_TURN] - Stored Board " +temp.getBoardName() + " to temporary variable");
                Driver.getGameFrame().add(temp.getBoardUI());
                Driver.initFrame();
                if (!boardComplete(temp))  {
                    turnExecution(temp, resource, false, true,false, masterBuildings);
                    if (boardComplete(temp)) {
                        int score = pickResourceBoard.scoring(false);
                        System.out.println(temp.getBoardName() + "'s final score: "+score);
                        // if game is complete, remove them permanently from board list
                        multiplayerModifiableBoards.remove(temp);
                    }
                }
                Thread.sleep(1000);
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
    private static ResourceEnum turnExecution(Board board, ResourceEnum resource, boolean resourcePick, boolean isMultiplayerGame, boolean placeBuilding, ArrayList<Building> buildingsForGame) throws IOException, URISyntaxException {
        // run code that returns a resource if method was called with resourcePick = true
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
            //board.renderBoard();
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
        every turn does this, so I decided to split it into its own method. Runs the turn actions for all players.
     */
    private static void turnActions(Board board, ResourceEnum resource, String string) throws IOException, URISyntaxException{
        board.playerTurn(resource, string);
        board.detectValidBuilding();
        board.runBuildingTurnAction();
        board.setGameCompletion(board.gameOver());
    }
}
