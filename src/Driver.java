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

    public static void main (String[] args) throws InterruptedException, IOException, URISyntaxException {
        Scanner sc = new Scanner(System.in);
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(new Cottage());
        buildings.add(new Farm());
        buildings.add(new Theater());
        buildings.add(new Well());
        buildings.add(new Warehouse());
        buildings.add(new Tavern());
        buildings.add(new Chapel());
        int playerCount;
        ArrayList<Board> boardArrayList = new ArrayList<>();

        do {
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
        while (playerCount <= 0 || playerCount > 6);
        for (int i= 0; i < playerCount; i++) {
            Board temp = new Board(buildings);
            boardArrayList.add(temp);
            }
        Manual.tutorial();
        boardArrayList.get(0).getManual().displayBuildings();
        ResourceEnum resource;
        if (playerCount < 2) {
            Board board = boardArrayList.get(0);
            System.out.println("Town Hall mode enabled!");
            while (!board.isGameCompletion()) {
                board.setGameCompletion(board.gameOver());
                if (!board.isGameCompletion()) {
                    board.updateBoard();
                    board.renderBoard();
                    resource = board.resourcePicker(false);
                    board.playerTurn(resource);
                    board.detectValidBuilding();
                }
            }
            board.scoring(false);
        }
        else {
            do {
                Board pickResourceBoard = boardArrayList.get(0);
                pickResourceBoard.renderBoard();
                System.out.println("It's "+ pickResourceBoard.getBoardName() + "'s turn to DECIDE the resource!");
                resource = pickResourceBoard.resourcePicker(true);
                pickResourceBoard.playerTurn(resource);
                pickResourceBoard.detectValidBuilding();
                boardArrayList.remove(pickResourceBoard);
                pickResourceBoard.setGameCompletion(pickResourceBoard.gameOver());
                if (pickResourceBoard.isGameCompletion()) {
                    int score = pickResourceBoard.scoring(false);
                    System.out.println(pickResourceBoard.getBoardName() + "'s final score: "+score);
                }

                for (int p = 0; p < boardArrayList.size(); p++) {
                    Board temp = boardArrayList.get(p);
                    if (!temp.isGameCompletion())  {
                        temp.renderBoard();
                        System.out.println("It's " + temp.getBoardName() + "'s turn to place a resource.");
                        temp.playerTurn(resource);
                        temp.detectValidBuilding();
                        temp.setGameCompletion(temp.gameOver());
                        if (temp.isGameCompletion()) {
                            int score = pickResourceBoard.scoring(false);
                            System.out.println(temp.getBoardName() + "'s final score: "+score);
                            boardArrayList.remove(temp);
                        }
                    }
                }
                if (!pickResourceBoard.isGameCompletion()) {
                    boardArrayList.add(pickResourceBoard);
                }

            }
            while (boardArrayList.size() != 0);
        }

        System.out.println("All players have finished TownBuilder. Thanks for playing! -Matt");
    }

}
