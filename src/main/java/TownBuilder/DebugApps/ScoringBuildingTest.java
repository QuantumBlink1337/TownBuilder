package TownBuilder.DebugApps;

import TownBuilder.Board;
import TownBuilder.Buildings.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ScoringBuildingTest {
    public static void main(String[] args) throws IOException {
        DebugTools.logging("THIS IS NOT THE ACTUAL GAME. THIS IS A TEST OF THE AUTOMATED SCORING SYSTEM.");
        ArrayList<Building> masterBuildings = new ArrayList<>(Arrays.asList(new Cottage(-1, -1), new Farm(-1, -1), new Well(-1, -1), new Chapel(-1, -1), new Theater(), new Tavern(-1, -1), new Warehouse(-1, -1)));
        Board board = new Board(masterBuildings, BuildingEnum.BARRETT);
        Building[][] bBoard = board.getGameBuildingBoard();
        bBoard[0][0] = BuildingFactory.getBuilding(BuildingEnum.THEATER, masterBuildings, 0, 0, false);
        bBoard[0][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 0, 1, false);
        bBoard[0][2] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 0, 2, false);
        bBoard[0][3] = BuildingFactory.getMonument(BuildingEnum.BARRETT, board, 0, 3, board.getScorableBuildings());

        bBoard[1][0] = BuildingFactory.getBuilding(BuildingEnum.FARM, masterBuildings, 1, 0, false);
        bBoard[1][1] = BuildingFactory.getBuilding(BuildingEnum.WAREHOUSE, masterBuildings, 1, 1, false);
        bBoard[1][2] = BuildingFactory.getBuilding(BuildingEnum.CHAPEL, masterBuildings, 1, 2, false);
        bBoard[1][3] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 1, 3, false);

        bBoard[2][0] = BuildingFactory.getBuilding(BuildingEnum.WELL, masterBuildings, 2, 0, false);
        bBoard[2][1] = BuildingFactory.getBuilding(BuildingEnum.TAVERN, masterBuildings, 2, 1, false);
        bBoard[2][2] = BuildingFactory.getBuilding(BuildingEnum.WELL, masterBuildings, 2, 2, false);
        bBoard[2][3] = BuildingFactory.getBuilding(BuildingEnum.TAVERN, masterBuildings, 2, 3, false);

        bBoard[3][0] = BuildingFactory.getBuilding(BuildingEnum.THEATER, masterBuildings, 3, 0, false);
        bBoard[3][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 3, 1, false);
        bBoard[3][2] = BuildingFactory.getBuilding(BuildingEnum.WELL, masterBuildings, 3, 2, false);
        bBoard[3][3] = BuildingFactory.getBuilding(BuildingEnum.THEATER, masterBuildings, 3, 3, false);
        board.renderBoard();
        board.runBuildingTurnAction();
        board.scoring(false);
        DebugTools.logging("FIRST SCORING TEST COMPLETE.");


        DebugTools.logging("MOVING TO SECOND SET OF BUILDING TESTS.");

        masterBuildings = new ArrayList<>(Arrays.asList(new Cottage(-1, -1), new Granary(-1, -1), new Shed(-1, -1), new Abbey(-1, -1), new Bakery(-1, -1), new Almshouse(-1, -1), new Factory(-1, -1, false)));
        board = new Board(masterBuildings, BuildingEnum.MANDRAS);
        bBoard = board.getGameBuildingBoard();
        bBoard[0][0] = BuildingFactory.getBuilding(BuildingEnum.BAKERY, masterBuildings, 0, 0, false);
        bBoard[0][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 0, 1, false);
        bBoard[0][2] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 0, 2, false);
        bBoard[0][3] = BuildingFactory.getBuilding(BuildingEnum.MANDRAS, masterBuildings, 0, 3, false);

        bBoard[1][0] = BuildingFactory.getBuilding(BuildingEnum.ALMSHOUSE, masterBuildings, 1, 0, false);
        bBoard[1][1] = BuildingFactory.getBuilding(BuildingEnum.BAKERY, masterBuildings, 1, 1, false);
        bBoard[1][2] = BuildingFactory.getBuilding(BuildingEnum.GRANARY, masterBuildings, 1, 2, false);
        bBoard[1][3] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 1, 3, false);

        bBoard[2][0] = BuildingFactory.getBuilding(BuildingEnum.SHED, masterBuildings, 2, 0, false);
        bBoard[2][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 2, 1, false);
        bBoard[2][2] = BuildingFactory.getBuilding(BuildingEnum.ABBEY, masterBuildings, 2, 2, false);
        bBoard[2][3] = BuildingFactory.getBuilding(BuildingEnum.ALMSHOUSE, masterBuildings, 2, 3, false);

        bBoard[3][0] = BuildingFactory.getBuilding(BuildingEnum.FACTORY, masterBuildings, 3, 0, false);
        bBoard[3][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 3, 1, false);
        bBoard[3][2] = BuildingFactory.getBuilding(BuildingEnum.ABBEY, masterBuildings, 3, 2, false);
        bBoard[3][3] = BuildingFactory.getBuilding(BuildingEnum.SHED, masterBuildings, 3, 3, false);
        board.renderBoard();
        board.runBuildingTurnAction();
        board.scoring(false);

        masterBuildings = new ArrayList<>(Arrays.asList(new Cottage(-1, -1), new Granary(-1, -1), new Shed(-1, -1), new Abbey(-1, -1), new Bakery(-1, -1), new Almshouse(-1, -1), new Factory(-1, -1, false)));
        board = new Board(masterBuildings, BuildingEnum.MANDRAS);
        bBoard = board.getGameBuildingBoard();
        bBoard[0][0] = BuildingFactory.getBuilding(BuildingEnum.BAKERY, masterBuildings, 0, 0, false);
        bBoard[0][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 0, 1, false);
        bBoard[0][2] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 0, 2, false);
        bBoard[0][3] = BuildingFactory.getBuilding(BuildingEnum.MANDRAS, masterBuildings, 0, 3, false);

        bBoard[1][0] = BuildingFactory.getBuilding(BuildingEnum.ALMSHOUSE, masterBuildings, 1, 0, false);
        bBoard[1][1] = BuildingFactory.getBuilding(BuildingEnum.BAKERY, masterBuildings, 1, 1, false);
        bBoard[1][2] = BuildingFactory.getBuilding(BuildingEnum.GRANARY, masterBuildings, 1, 2, false);
        bBoard[1][3] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 1, 3, false);

        bBoard[2][0] = BuildingFactory.getBuilding(BuildingEnum.SHED, masterBuildings, 2, 0, false);
        bBoard[2][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 2, 1, false);
        bBoard[2][2] = BuildingFactory.getBuilding(BuildingEnum.ABBEY, masterBuildings, 2, 2, false);
        bBoard[2][3] = BuildingFactory.getBuilding(BuildingEnum.ALMSHOUSE, masterBuildings, 2, 3, false);

        bBoard[3][0] = BuildingFactory.getBuilding(BuildingEnum.FACTORY, masterBuildings, 3, 0, false);
        bBoard[3][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 3, 1, false);
        bBoard[3][2] = BuildingFactory.getBuilding(BuildingEnum.ABBEY, masterBuildings, 3, 2, false);
        bBoard[3][3] = BuildingFactory.getBuilding(BuildingEnum.SHED, masterBuildings, 3, 3, false);
        board.renderBoard();
        board.runBuildingTurnAction();
        board.scoring(false);
    }
}
