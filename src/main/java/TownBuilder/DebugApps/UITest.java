package TownBuilder.DebugApps;

import TownBuilder.Board;
import TownBuilder.Buildings.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;


public class UITest {
    public static void main(String[] args) throws IOException, URISyntaxException {
        ArrayList<Building> masterBuildings = new ArrayList<>(Arrays.asList(new Cottage(-1, -1), new Granary(-1, -1), new Shed(-1, -1), new Abbey(-1, -1), new Bakery(-1, -1), new Almshouse(-1, -1), new Warehouse(-1, -1)));

        Board board = new Board(masterBuildings, BuildingEnum.STARLOOM, null);
        board.getGameBuildingBoard()[3][3] = BuildingFactory.getBuilding(BuildingEnum.WAREHOUSE, masterBuildings, 3, 3, false, board);
        board.setLastBuiltBuilding(BuildingEnum.WAREHOUSE);
        while (!board.isGameCompletion()) {
            board.playerTurn();
            board.detectValidBuilding();
            board.setGameCompletion(board.gameOver());
            //board.getBoardUI().repaint();
        }
    }
}
