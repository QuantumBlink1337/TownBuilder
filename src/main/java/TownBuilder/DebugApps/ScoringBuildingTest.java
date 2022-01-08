package TownBuilder.DebugApps;

import TownBuilder.*;
import TownBuilder.Buildings.*;
import com.diogonunes.jcolor.Attribute;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class ScoringBuildingTest {
    public static void main(String[] args) throws IOException, URISyntaxException {
        DebugTools.logging("THIS IS NOT THE ACTUAL GAME. THIS IS A TEST OF THE AUTOMATED SCORING SYSTEM.");
        Utility.setColor(true);
        System.out.println(Utility.generateColorizedString("THIS IS NOT THE ACTUAL GAME. THIS IS A TEST OF THE MULTIPLAYER SYSTEM.", Attribute.RED_TEXT()));
        ArrayList<Building> masterBuildings = new ArrayList<>(Arrays.asList(new Cottage(-1, -1), new Farm(-1, -1), new Well(-1, -1), new Chapel(-1, -1), new Theater(), new Tavern(-1, -1), new Warehouse(-1, -1)));
        Board board = new Board(masterBuildings, BuildingEnum.BARRETT, null);
        Building[][] bBoard = board.getGameBuildingBoard();
        Resource[][] rBoard = board.getGameResourceBoard();
//        bBoard[0][0] = BuildingFactory.getBuilding(BuildingEnum.THEATER, masterBuildings, 0, 0, false);
//        bBoard[0][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 0, 1, false);
//        bBoard[0][2] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 0, 2, false);
//        bBoard[0][3] = BuildingFactory.getMonument(BuildingEnum.BARRETT, board, 0, 3, board.getScorableBuildings());
//
//        bBoard[1][0] = BuildingFactory.getBuilding(BuildingEnum.FARM, masterBuildings, 1, 0, false);
//        bBoard[1][1] = BuildingFactory.getBuilding(BuildingEnum.WAREHOUSE, masterBuildings, 1, 1, false);
//        bBoard[1][2] = BuildingFactory.getBuilding(BuildingEnum.CHAPEL, masterBuildings, 1, 2, false);
//        bBoard[1][3] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 1, 3, false);
//
//        bBoard[2][0] = BuildingFactory.getBuilding(BuildingEnum.WELL, masterBuildings, 2, 0, false);
//        bBoard[2][1] = BuildingFactory.getBuilding(BuildingEnum.TAVERN, masterBuildings, 2, 1, false);
//        bBoard[2][2] = BuildingFactory.getBuilding(BuildingEnum.WELL, masterBuildings, 2, 2, false);
//        bBoard[2][3] = BuildingFactory.getBuilding(BuildingEnum.TAVERN, masterBuildings, 2, 3, false);
//
//        bBoard[3][0] = BuildingFactory.getBuilding(BuildingEnum.THEATER, masterBuildings, 3, 0, false);
//        bBoard[3][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 3, 1, false);
//        bBoard[3][2] = BuildingFactory.getBuilding(BuildingEnum.WELL, masterBuildings, 3, 2, false);
//        bBoard[3][3] = BuildingFactory.getBuilding(BuildingEnum.THEATER, masterBuildings, 3, 3, false);
//        board.renderBoard();
//        board.runBuildingTurnAction();
//        board.scoring(false);
        DebugTools.logging("FIRST SCORING TEST COMPLETE.");


        DebugTools.logging("MOVING TO SECOND SET OF BUILDING TESTS.");

        masterBuildings = new ArrayList<>(Arrays.asList(new Cottage(-1, -1), new Granary(-1, -1), new Shed(-1, -1), new Abbey(-1, -1), new Bakery(-1, -1), new Almshouse(-1, -1), new Factory(-1, -1, false, null)));
        board = new Board(masterBuildings, BuildingEnum.MANDRAS, null);
        bBoard = board.getGameBuildingBoard();
//        bBoard[0][0] = BuildingFactory.getBuilding(BuildingEnum.BAKERY, masterBuildings, 0, 0, false);
//        bBoard[0][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 0, 1, false);
//        bBoard[0][2] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 0, 2, false);
//        bBoard[0][3] = BuildingFactory.getMonument(BuildingEnum.MANDRAS, board, 0, 3, board.getScorableBuildings());
//
//        bBoard[1][0] = BuildingFactory.getBuilding(BuildingEnum.ALMSHOUSE, masterBuildings, 1, 0, false);
//        bBoard[1][1] = BuildingFactory.getBuilding(BuildingEnum.BAKERY, masterBuildings, 1, 1, false);
//        bBoard[1][2] = BuildingFactory.getBuilding(BuildingEnum.GRANARY, masterBuildings, 1, 2, false);
//        bBoard[1][3] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 1, 3, false);
//
//        bBoard[2][0] = BuildingFactory.getBuilding(BuildingEnum.SHED, masterBuildings, 2, 0, false);
//        bBoard[2][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 2, 1, false);
//        bBoard[2][2] = BuildingFactory.getBuilding(BuildingEnum.ABBEY, masterBuildings, 2, 2, false);
//        bBoard[2][3] = BuildingFactory.getBuilding(BuildingEnum.ALMSHOUSE, masterBuildings, 2, 3, false);
//
//        bBoard[3][0] = BuildingFactory.getBuilding(BuildingEnum.FACTORY, masterBuildings, 3, 0, false);
//        bBoard[3][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 3, 1, false);
//        bBoard[3][2] = BuildingFactory.getBuilding(BuildingEnum.ABBEY, masterBuildings, 3, 2, false);
//        bBoard[3][3] = BuildingFactory.getBuilding(BuildingEnum.SHED, masterBuildings, 3, 3, false);
//        board.renderBoard();
//        board.runBuildingTurnAction();
//        board.scoring(false);

        masterBuildings = new ArrayList<>(Arrays.asList(new Cottage(-1, -1), new Granary(-1, -1), new Shed(-1, -1), new Abbey(-1, -1), new Bakery(-1, -1), new Almshouse(-1, -1), new Factory(-1, -1, false, null)));
        board = new Board(masterBuildings, BuildingEnum.MANDRAS, null);
        bBoard = board.getGameBuildingBoard();
        rBoard = board.getGameResourceBoard();
        rBoard[0][0] = new Resource(ResourceEnum.GLASS, 0, 0);
        rBoard[0][1] = new Resource(ResourceEnum.GLASS, 0, 1);
        rBoard[0][2] = new Resource(ResourceEnum.GLASS, 0, 2);
        rBoard[0][3] = new Resource(ResourceEnum.GLASS, 0, 3);

        rBoard[1][0] = new Resource(ResourceEnum.WHEAT, 1, 0);
        rBoard[1][1] = new Resource(ResourceEnum.WHEAT, 1, 1);
        rBoard[1][2] = new Resource(ResourceEnum.WOOD, 1, 2);
        rBoard[1][3] = new Resource(ResourceEnum.WOOD, 1, 3);

        rBoard[2][0] = new Resource(ResourceEnum.STONE, 2, 0);
        rBoard[2][1] = new Resource(ResourceEnum.STONE, 2, 1);
        rBoard[2][2] = new Resource(ResourceEnum.STONE, 2, 2);
        rBoard[2][3] = new Resource(ResourceEnum.STONE, 2, 3);

        rBoard[3][0] = new Resource(ResourceEnum.BRICK, 3, 0);
        rBoard[3][1] = new Resource(ResourceEnum.BRICK, 3, 1);
        rBoard[3][2] = new Resource(ResourceEnum.BRICK, 3, 2);
        rBoard[3][3] = new Resource(ResourceEnum.BRICK, 3, 3);
//        board.renderBoard();
//        board.runBuildingTurnAction();
//        board.scoring(false);

        masterBuildings = new ArrayList<>(Arrays.asList(new Cottage(-1, -1), new Granary(-1, -1), new Shed(-1, -1), new Abbey(-1, -1), new Bakery(-1, -1), new Almshouse(-1, -1), new Factory(-1, -1, false, null)));
        board = new Board(masterBuildings, BuildingEnum.CATERINA, null);
        bBoard = board.getGameBuildingBoard();
        rBoard = board.getGameResourceBoard();
        rBoard[0][0] = new Resource(ResourceEnum.GLASS, 0, 0);
        rBoard[0][1] = new Resource(ResourceEnum.GLASS, 0, 1);
        rBoard[0][2] = new Resource(ResourceEnum.GLASS, 0, 2);
        bBoard[0][3] = BuildingFactory.getMonument(BuildingEnum.CATERINA, board, 0, 3, masterBuildings);

        rBoard[1][0] = new Resource(ResourceEnum.WHEAT, 1, 0);
        rBoard[1][1] = new Resource(ResourceEnum.WHEAT, 1, 1);
        rBoard[1][2] = new Resource(ResourceEnum.WOOD, 1, 2);
        rBoard[1][3] = new Resource(ResourceEnum.WOOD, 1, 3);

        rBoard[2][0] = new Resource(ResourceEnum.STONE, 2, 0);
        rBoard[2][1] = new Resource(ResourceEnum.STONE, 2, 1);
        rBoard[2][2] = new Resource(ResourceEnum.STONE, 2, 2);
        rBoard[2][3] = new Resource(ResourceEnum.STONE, 2, 3);

        rBoard[3][0] = new Resource(ResourceEnum.BRICK, 3, 0);
        rBoard[3][1] = new Resource(ResourceEnum.BRICK, 3, 1);
        rBoard[3][2] = new Resource(ResourceEnum.BRICK, 3, 2);
        rBoard[3][3] = new Resource(ResourceEnum.BRICK, 3, 3);
//        board.renderBoard();
//        board.runBuildingTurnAction();
//        board.scoring(false);

        masterBuildings = new ArrayList<>(Arrays.asList(new Cottage(-1, -1), new Granary(-1, -1), new Shed(-1, -1), new Abbey(-1, -1), new Bakery(-1, -1), new Almshouse(-1, -1), new Factory(-1, -1, false, null)));
        board = new Board(masterBuildings, BuildingEnum.SKYBATHS, null);
        PlayerManager playerManager = new PlayerManager(masterBuildings, board);
        bBoard = board.getGameBuildingBoard();
//        bBoard[0][0] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 0, 0, false);
//        bBoard[0][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 0, 1, false);
//        bBoard[0][2] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 0, 2, false);
//        bBoard[0][3] = BuildingFactory.getMonument(BuildingEnum.SKYBATHS, board, 0, 3, board.getScorableBuildings());
//
//        bBoard[1][0] = BuildingFactory.getBuilding(BuildingEnum.ALMSHOUSE, masterBuildings, 1, 0, false);
//        bBoard[1][1] = BuildingFactory.getBuilding(BuildingEnum.BAKERY, masterBuildings, 1, 1, false);
//        bBoard[1][2] = BuildingFactory.getBuilding(BuildingEnum.GRANARY, masterBuildings, 1, 2, false);
//        bBoard[1][3] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 1, 3, false);
//
//        bBoard[2][0] = BuildingFactory.getBuilding(BuildingEnum.SHED, masterBuildings, 2, 0, false);
//        bBoard[2][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 2, 1, false);
//        bBoard[2][2] = BuildingFactory.getBuilding(BuildingEnum.ABBEY, masterBuildings, 2, 2, false);
//        bBoard[2][3] = BuildingFactory.getBuilding(BuildingEnum.ALMSHOUSE, masterBuildings, 2, 3, false);
//
//        bBoard[3][0] = BuildingFactory.getBuilding(BuildingEnum.FACTORY, masterBuildings, 3, 0, false);
//        bBoard[3][1] = BuildingFactory.getBuilding(BuildingEnum.COTTAGE, masterBuildings, 3, 1, false);
//        bBoard[3][2] = BuildingFactory.getBuilding(BuildingEnum.ABBEY, masterBuildings, 3, 2, false);
//        bBoard[3][3] = BuildingFactory.getBuilding(BuildingEnum.SHED, masterBuildings, 3, 3, false);

        board.updateBoard();
        board.runBuildingTurnAction();
        playerManager.manageTurn();
//        board.renderBoard();
//        board.runBuildingTurnAction();
//        board.scoring(false);
    }
}
