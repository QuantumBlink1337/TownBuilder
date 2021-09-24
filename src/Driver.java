import TownBuilder.Board;
        import TownBuilder.Buildings.*;
        import TownBuilder.Manual;

public class Driver {
    boolean debug = true;
    public static void main (String[] args) throws InterruptedException {

        Building[] buildings = new Building[]{new Cottage(),new Farm(), new Well(), new Theater(), new Warehouse(), new Tavern(), new Chapel()};
        Board board = new Board(buildings);
        Manual.tutorial();
        board.game();
    }

}
