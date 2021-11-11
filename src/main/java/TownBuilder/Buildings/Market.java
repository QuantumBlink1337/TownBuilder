package TownBuilder.Buildings;

import TownBuilder.ColorEnum;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Arrays;

import static TownBuilder.Buildings.BuildingFactory.patternBuilder;

public class Market implements Building {
    private boolean condition;
    private final int row;
    private final int col;

    private static final ResourceEnum[][] marketArray = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> marketPatternList = new ArrayList<>();

    static {
        marketArray[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.WOOD,  ResourceEnum.NONE};
        marketArray[1] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.GLASS, ResourceEnum.STONE};
        patternBuilder(marketArray, marketPatternList);
    }
    public Market(int r, int c) {
        row = r;
        col =c;
        condition = false;
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return marketPatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.MARKET;
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
    public String toString() {
        return "Market";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) {
        ArrayList<Building> rowsandcolumns = (ArrayList<Building>) Arrays.asList(Utility.getBuildingsInRowAndColumn(bArray, row, col));
        ArrayList<Building> buildingsInRow = (ArrayList<Building>) rowsandcolumns.subList(0, rowsandcolumns.size()/2);
        ArrayList<Building> buildingsInColumn = (ArrayList<Building>) rowsandcolumns.subList(rowsandcolumns.size()/2, rowsandcolumns.size());
        int yellowBuildingInRow = 0;
        int yellowBuildingInColumn = 0;
        for (Building building : buildingsInRow) {
            if (building.getType().getColor() == ColorEnum.YELLOW) {
                yellowBuildingInRow++;
            }
        }
        for (Building building : buildingsInColumn) {
            if (building.getType().getColor() == ColorEnum.YELLOW) {
                yellowBuildingInColumn++;
            }
        }
        return (Math.max(yellowBuildingInColumn, yellowBuildingInRow));
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {

    }

    @Override
    public void printManualText() {

    }
}
