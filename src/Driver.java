import TownBuilder.Board;
        import TownBuilder.Buildings.*;
        import TownBuilder.Manual;

import java.util.ArrayList;

public class Driver {
    boolean debug = true;
    public static void main (String[] args) throws InterruptedException {
        boolean gameCompletion = false;
        ArrayList<Building> buildings = new ArrayList<Building>();
        buildings.add(new Cottage());
        buildings.add(new Farm());
        buildings.add(new Theater());
        buildings.add(new Well());
        buildings.add(new Warehouse());
        buildings.add(new Chapel());
        buildings.add(new Tavern());

        Board board = new Board(buildings);
        Manual.tutorial();
        board.getManual().displayBuildings();
        while (!gameCompletion) {
            board.renderBoard();
            gameCompletion = board.gameOver();
            if (gameCompletion) {
                break;
            }
            board.playerTurn();
            board.detectValidBuilding();
        }
        System.out.println("Final score: " + board.scoring());
    }

}
