import TownBuilder.Board;
        import TownBuilder.Buildings.*;
        import TownBuilder.Manual;

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
        for (int i= 1; i <= playerCount; i++) {
            Board temp = new Board(buildings, i);
            boardArrayList.add(temp);
            }
        Manual.tutorial();
        boardArrayList.get(0).getManual().displayBuildings();
        for (int i = 0; gameCompletion != boardArrayList.size(); i++) {
            Board temp = boardArrayList.get(i);
            temp.renderBoard();
            if (!temp.gameOver()) {
                if (temp.getPlayerNumber() == i) {
                    temp.playerTurn(true);
                }
                else {
                    temp.playerTurn(false);
                }
                temp.detectValidBuilding();

            }
            else {
                temp.setGameCompletion(true);
            }
            if (i == boardArrayList.size()-1) {
                i = 0;
            }
            for (Board board : boardArrayList) {
                if (board.isGameCompletion()) {
                    gameCompletion++;
                }
            }
        }
    }

}
