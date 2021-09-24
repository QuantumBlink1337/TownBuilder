import TownBuilder.Board;
import TownBuilder.Buildings.BuildingEnum;

public class Driver {
    boolean debug = true;
    public static void main (String[] args) {

        BuildingEnum[] buildings = new BuildingEnum[]{BuildingEnum.COTTAGE, BuildingEnum.FARM, BuildingEnum.WELL, BuildingEnum.THEATER, BuildingEnum.WHOUSE, BuildingEnum.TAVERN, BuildingEnum.CHAPEL};
        Board board = new Board(buildings);
        board.game();
    }

}
