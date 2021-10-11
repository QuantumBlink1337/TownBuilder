import TownBuilder.Board;
        import TownBuilder.Buildings.*;
        import TownBuilder.Manual;
import TownBuilder.ResourceEnum;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

    public static void main (String[] args) throws InterruptedException, IOException, URISyntaxException {
        int gameCompletion = 0;
        Scanner sc = new Scanner(System.in);
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(new Cottage());
        buildings.add(new Farm());
        buildings.add(new Theater());
        buildings.add(new Well());
        buildings.add(new Warehouse());
        buildings.add(new Tavern());
        buildings.add(new Chapel());
        int playerCount = 0;
        ArrayList<Board> boardArrayList = new ArrayList<>();

        do {
            System.out.println("How many players would you like? You can have up to 6.");
            playerCount = sc.nextInt();
        }
        while (playerCount <= 0 || playerCount > 6);
        for (int i= 0; i < playerCount; i++) {
            Board temp = new Board(buildings, i);
            boardArrayList.add(temp);
            }
        Manual.tutorial();
        boardArrayList.get(0).getManual().displayBuildings();
        do {
            ResourceEnum resource;
            Board pickResourceBoard = boardArrayList.get(0);
            pickResourceBoard.renderBoard();
            resource = pickResourceBoard.playerTurn(true);
            pickResourceBoard.resourcePlacer(resource);
            pickResourceBoard.detectValidBuilding();
            boardArrayList.remove(pickResourceBoard);
            pickResourceBoard.setGameCompletion(pickResourceBoard.gameOver());
            for (int p = 0; p < boardArrayList.size(); p++) {
                Board temp = boardArrayList.get(p);
                if (!temp.isGameCompletion())  {
                    temp.renderBoard();
                    System.out.println("It's " + temp.getBoardName() + "'s turn to place a resource.");
                    temp.resourcePlacer(resource);
                    temp.detectValidBuilding();
                    temp.setGameCompletion(temp.gameOver());
                }
            }
            boardArrayList.add(pickResourceBoard);
            for (int i = 0; i < boardArrayList.size(); i++) {
                if (boardArrayList.get(i).isGameCompletion()) {
                    boardArrayList.remove(i);
                }
            }
            for (Board board : boardArrayList) {
                if (board.isGameCompletion()) {
                    gameCompletion++;
                }
            }
        }
        while (gameCompletion != boardArrayList.size());
    }

}
