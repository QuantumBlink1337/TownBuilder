package TownBuilder.Buildings;

import TownBuilder.BoardTraverser;
import TownBuilder.ColorEnum;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class Abbey implements Building {
    private static final ResourceEnum[][] abbeyPattern = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> abbeyPatternList = new ArrayList<>();
    private boolean condition;
    private final int row;
    private final int col;

    static {
        abbeyPattern[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.GLASS};
        abbeyPattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.STONE, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(abbeyPattern, abbeyPatternList);
    }
    public Abbey(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return abbeyPatternList;
    }

    @Override
    public String toString() {
        return "Abbey";
    }
    public BuildingEnum getType() {
        return BuildingEnum.ABBEY;
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
        return BoardTraverser.searchForBuilding(BoardTraverser.getAdjacentBuildings(bArray, row, col), ColorEnum.RED, ColorEnum.YELLOW, ColorEnum.WHITE) ? 0 : 3;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("The Abbey grants 3 points if it's not adjacent to a Green, Yellow, or White building.");
        System.out.println("Here's what it looks like:");
        Utility.printFormattedResourcePattern(abbeyPatternList.get(0));
    }

    @Override
    public String getManualEntry() {
        return "The Abbey grants 3 points if it's not adjacent to a Green, Yellow, or White building.";
    }
}
