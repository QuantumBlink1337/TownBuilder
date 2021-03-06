package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;

import java.io.IOException;
import java.util.ArrayList;

public class GroveUni implements Monument {
    private static final ResourceEnum[][] pattern = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private final Board board;
    private boolean condition;

    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.BRICK, ResourceEnum.NONE};
        pattern[1] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.GLASS, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public GroveUni(int r, int c, Board b) {
        row = r;
        col = c;
        board = b;
    }
    @Override
    public String toString() {
        return "Grove University";
    }

    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.GROVEUNI;
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
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) throws IOException {
        DebugTools.logging("GROVE UNIVERSITY SCORING: Returning 3");
        return 3;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) throws IOException {
        // nothing
    }

    @Override
    public String getManualEntry() {
        return "Earns 3 points. On placement, immediately place a building of your choice on an empty square on your board.";
    }

    @Override
    public void onPlacement() throws IOException {
        DebugTools.logging("GROVE UNIVERSITY: Placing building");
        board.buildingPlacer(board.getDetectableBuildings(), true, true);
    }
}
