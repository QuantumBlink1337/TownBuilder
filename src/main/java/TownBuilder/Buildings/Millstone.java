package TownBuilder.Buildings;

import TownBuilder.BoardTraverser;
import TownBuilder.ColorEnum;
import TownBuilder.ResourceEnum;

import java.io.IOException;
import java.util.ArrayList;

public class Millstone implements Building{
    private static final ResourceEnum[][] millstoneArray = new ResourceEnum[1][2];
    private static final ArrayList<ResourceEnum[][]> millstonePatternArray = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;

    static {
        millstoneArray[0] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(millstoneArray, millstonePatternArray);
    }
    public Millstone(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return millstonePatternArray;
    }
    public String toString() {
        return "Millstone";
    }
    @Override
    public BuildingEnum getType() {
        return BuildingEnum.MILLSTONE;
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
        return BoardTraverser.searchForBuilding(BoardTraverser.getAdjacentBuildings(bArray, row, col), ColorEnum.RED, ColorEnum.YELLOW) ? 2 : 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public String getManualEntry() {
        return "The Millstone grants 2 points if it is adjacent to a Red building or a Yellow building.\nNote: the maximum amount of points a Millstone can earn is 2.";
    }
}
