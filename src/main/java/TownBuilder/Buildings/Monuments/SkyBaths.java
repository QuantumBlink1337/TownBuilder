package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.BoardTraverser;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.ResourceEnum;

import java.io.IOException;
import java.util.ArrayList;

public class SkyBaths implements Monument{
    private static final ResourceEnum[][] pattern = new ResourceEnum[3][3];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private final Board board;
    private boolean condition;


    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.WHEAT, ResourceEnum.NONE};
        pattern[1] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.GLASS, ResourceEnum.WOOD};
        pattern[2] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.NONE, ResourceEnum.BRICK};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public SkyBaths(int r, int c, Board b) {
        row = r;
        col = c;
        board = b;
        condition = false;
    }
    public String toString() {
        return "The Sky Baths";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.SKYBATHS;
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
        return  2 * (board.getScorableBuildings().size() - BoardTraverser.findUniqueBuildingsInGivenList(bArray, null, board.getScorableBuildings()));
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) throws IOException {

    }

    @Override
    public String getManualEntry() {
        return "2 Points for each building type your board is missing.";
    }

    @Override
    public void onPlacement() throws IOException {

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
