package TownBuilder.DebugApps;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Manual;
import TownBuilder.ResourceEnum;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GameManager {


    private ArrayList<Board> boards;
    private ArrayList<Board> multiplayerModifyableBoards;
    private ArrayList<Building> masterBuildings;
    private boolean isSingleplayer;

    public GameManager(ArrayList<Board> b, ArrayList<Building> masterBuildings) {
        this.masterBuildings = masterBuildings;
        boards = b;
        isSingleplayer = (boards.size() > 1);
        ArrayList<Board> multiplayerModifyableBoards = new ArrayList<>(boards);
    }
    public boolean gameActive() throws IOException {
        int disabledPlayers = 0;
        for (Board board : boards) {
            disabledPlayers += board.gameOver() ? 1 : 0;
        }
        return disabledPlayers == boards.size();
    }
    public ArrayList<Board> getBoards() {
        return boards;
    }
    public Board[] getAdjacentBoards(Board b) {
        Board[] adjacentBoards = new Board[2];
        if (isSingleplayer) {
            return null;
        }
        int index = 0;
        for (int i = 0; i < boards.size(); i++) {
            if (b == boards.get(i)) {
                index = i;
                break;
            }
        }
        if (index-1 < 0) {
            adjacentBoards[0] = boards.get(boards.size()-1);
        }
        else {
            adjacentBoards[0] = boards.get(index-1);
        }
        if (index+1 > boards.size() - 1) {
            adjacentBoards[1] = boards.get(0);
        }
        else {
            adjacentBoards[1] = boards.get(index+1);
        }
        return adjacentBoards;
    }
    public void manageTurn() throws IOException, URISyntaxException {
        ResourceEnum resource = null;
        if (isSingleplayer) {
            Board board;
            board = boards.get(0);
            // this loop continues until the player has no empty slots in board
            board.setGameCompletion(board.gameOver());
            if (!board.isGameCompletion()) {
                if (board.getBoardName().equals("debug")) {
                    resource = turnExecution(board, resource, true, true, false, masterBuildings);
                }
                else if (board.getBoardName().equals("debug_building")) {
                    System.out.println("Building debug");
                    resource = turnExecution(board, resource, false, false, true, masterBuildings);
                }
                else {
                    resource = turnExecution(board, resource, true, false, false, masterBuildings);
                }
            }
            if (board.isGameCompletion()) {
                board.scoring(false);
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


            if (multiplayerModifyableBoards.get(0).CanBeMasterBuilder()) {
                pickResourceBoard = multiplayerModifyableBoards.get(0);
            }
            else {
                pickResourceBoard = multiplayerModifyableBoards.get(1);
            }
            resource = turnExecution(pickResourceBoard, resource, true, true, false,masterBuildings );
            multiplayerModifyableBoards.remove(pickResourceBoard); // removes from board
            // if board is game complete, score them
            if (pickResourceBoard.isGameCompletion()) {
                int score = pickResourceBoard.scoring(false);
                System.out.println(pickResourceBoard.getBoardName() + "'s final score: "+score);
            }
            // loop for remaining players
            for (int p = 0; p < multiplayerModifyableBoards.size(); p++) {
                Board temp = multiplayerModifyableBoards.get(p); // saves current board to temporary variable
                if (!temp.isGameCompletion())  {
                    turnExecution(temp, resource, false, true,false, masterBuildings);
                    if (temp.isGameCompletion()) {
                        int score = pickResourceBoard.scoring(false);
                        System.out.println(temp.getBoardName() + "'s final score: "+score);
                        // if game is complete, remove them permanently from board list
                        multiplayerModifyableBoards.remove(temp);
                    }
                }
            }
            // the board that was removed won't be added back if it's game complete
            if (!pickResourceBoard.isGameCompletion()) {
                multiplayerModifyableBoards.add(pickResourceBoard);
            }
        }
        // continue while there are still boards that are playable
    }
    private static ResourceEnum turnExecution(Board board, ResourceEnum resource, boolean resourcePick, boolean isMultiplayerGame, boolean placeBuilding, ArrayList<Building> buildingsForGame) throws IOException, URISyntaxException {
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
