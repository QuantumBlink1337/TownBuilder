import TownBuilder.Board;
        import TownBuilder.Buildings.*;
        import TownBuilder.Manual;

import java.util.ArrayList;

public class Driver {
    boolean debug = true;
    public static void main (String[] args) throws InterruptedException {

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
        board.game();
    }

}
