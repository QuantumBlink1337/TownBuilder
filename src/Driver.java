import TownBuilder.Board;
import TownBuilder.Buildings.BlueBuilding;
import TownBuilder.Buildings.Building;

public class Driver {
    public static void main (String[] args) {
        System.out.println("Hello world!");
        BlueBuilding.setCottageArray();
        Board board = new Board();
        board.game();
    }

}
