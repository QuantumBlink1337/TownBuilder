package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;

import java.io.IOException;
import java.util.ArrayList;

public class OpaleyeWatch implements Monument{
    private static final ResourceEnum[][] pattern = new ResourceEnum[3][4];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private final Board board;
    private boolean condition;
    private final ArrayList<BuildingEnum> chosenBuildings = new ArrayList<>();
    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.NONE,ResourceEnum.NONE,ResourceEnum.NONE};
        pattern[1] = new ResourceEnum[]{ResourceEnum.BRICK,ResourceEnum.GLASS,ResourceEnum.WHEAT,ResourceEnum.WHEAT};
        pattern[2] = new ResourceEnum[]{ResourceEnum.STONE,ResourceEnum.NONE,ResourceEnum.NONE,ResourceEnum.NONE};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public OpaleyeWatch(int r, int c, Board b) {
        row = r;
        col = c;
        board = b;
        condition = false;
    }


    public String toString() {
        return "Opaleye's Watch";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.OPALEYE;
    }

    @Override
    public boolean getCondition() {
        return condition;
    }

    @Override
    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    @Override
    public boolean getFedStatus() {
        return false;
    }

    @Override
    public void setFedStatus(boolean condition) {

    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) throws IOException {
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) throws IOException {
        DebugTools.logging("[MONUMENT_OPALEYE] - Beginning turn interval process.");
        Board[] adjacentBoards = board.getPlayerManager().getAdjacentBoards(board);
        for (Board adjacentBoard : adjacentBoards) {
            for (BuildingEnum buildingEnum : chosenBuildings) {
                for (BuildingEnum lastPlacedBuilding : adjacentBoard.getBuildingsBuiltThisTurn())
                    if (lastPlacedBuilding == buildingEnum)  {
                        board.getBoardUI().getErrorTextLabel().setText("Someone next to you built a " + buildingEnum.toString() + " which is on your Opaleye's Watch monument.");
                        board.getBoardUI().getErrorTextLabel().setVisible(true);
                        board.getBuildingFactory().placeBuildingOnBoard(lastPlacedBuilding, board.getDetectableBuildings(), true, board);
                        chosenBuildings.remove(lastPlacedBuilding);
                    }
            }
        }
    }

    @Override
    public String getManualEntry() {
        return "Upon placement, declare three different buildings. Whenever a player to your left or right constructs one of those buildings, place that building on an empty spot on your board and remove it from Opaleye's Watch.";
    }

    @Override
    public void onPlacement() throws IOException {
        ArrayList<Building> uniqueBuildings = new ArrayList<>(board.getScorableBuildings());

        for (int i = 0; i < 3; i++) {
            BuildingEnum type = board.buildingPlacer(uniqueBuildings, false, false);
            chosenBuildings.add(type);
            uniqueBuildings.removeIf(m -> m.getType() == type);
        }
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }
}
