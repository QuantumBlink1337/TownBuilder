import TownBuilder.Board;
import TownBuilder.Buildings.*;
import TownBuilder.Manual;
import TownBuilder.ResourceEnum;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {

    public static void main (String[] args) throws IOException, URISyntaxException {
        Scanner sc = new Scanner(System.in);
        ArrayList<Building> buildingsForGame = new ArrayList<>();
        buildingsForGame.add(new Cottage());
        buildingsForGame.add(new Farm());
        buildingsForGame.add(new Theater());
        buildingsForGame.add(new Well());
        buildingsForGame.add(new Warehouse());
        buildingsForGame.add(new Tavern());
        buildingsForGame.add(new Chapel());
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
            Board temp = new Board(buildingsForGame);
            boardArrayList.add(temp);
            }
        Manual.tutorial();
        boardArrayList.get(0).getManual().displayBuildings();
        ResourceEnum resource = null;
        // if there's only one player, use default singleplayer code
        if (playerCount < 2) {
            Board board = boardArrayList.get(0);
            System.out.println("Town Hall mode enabled!");
            while (!board.isGameCompletion()) { // this loop continues until the player has no empty slots in board
                board.setGameCompletion(board.gameOver());
                if (!board.isGameCompletion()) {
                    resource = turnExecution(board, resource, true, false);
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
                Board pickResourceBoard = boardArrayList.get(0); // grabs first index
                resource = turnExecution(pickResourceBoard, resource, true, true);
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
                        turnExecution(temp, resource, false, true);
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
    /*
        This method faciliates the actions of each turn and includes code for use in both multiplayer and singleplayer applications.

        @params
        Set resourcePick to true if you're intending to allow the user to pick the resource (singleplayer and multiplayer first board)
        Set isMultiplayerGame to true if you're using a multiplayer game, otherwise false for singleplayer
            This tells resourcePicker() what instructions to follow

     */
    public static ResourceEnum turnExecution(Board board, ResourceEnum resource, boolean resourcePick, boolean isMultiplayerGame) throws IOException, URISyntaxException {
        // run code that returns a resource if method was called with resourcePick = true
        if (resourcePick) {
            ResourceEnum r;
            board.renderBoard();
            if (isMultiplayerGame) {
                System.out.println("It's "+ board.getBoardName() + "'s turn to DECIDE the resource!");
            }
            // if isMultiplayer = false, will use singleplayer code in resourcePicker()
            r = board.resourcePicker(isMultiplayerGame); // assigns the return value of resourcePicker()
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
    private static void turnActions(Board board, ResourceEnum resource) throws IOException, URISyntaxException {
        board.playerTurn(resource);
        board.detectValidBuilding();
        board.setGameCompletion(board.gameOver());
    }
}
