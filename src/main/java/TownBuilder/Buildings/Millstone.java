package TownBuilder.Buildings;

import TownBuilder.BoardTraverser;
import TownBuilder.ColorEnum;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class Millstone implements Building{
    private static final ResourceEnum[][] millstoneArray = new ResourceEnum[1][2];
    private static final ArrayList<ResourceEnum[][]> millstonePatternArray = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;
    private boolean buildingFound = false;

    static {
        millstoneArray[0] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(millstoneArray, millstonePatternArray);
        // millstoneArray[0] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.WOOD};
        // BuildingFactory.patternBuilder(millstoneArray, millstonePatternArray, 3);

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
    public void printManualText() {
        System.out.println("The Millstone grants 2 points if it is adjacent to a RED building or a YELLOW building.");
        System.out.println("Note: the maximum amount of points a Millstone can earn is 2.");
        System.out.println("Here's what it looks like:");
        Utility.printFormattedResourcePattern(millstonePatternArray.get(0));
    }

    @Override
    public String getManualEntry() {
        return "The Millstone grants 2 points if it is adjacent to a RED building or a YELLOW building.\nNote: the maximum amount of points a Millstone can earn is 2.";
    }
}
