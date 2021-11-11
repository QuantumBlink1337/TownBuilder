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
        return (Math.max(Utility.instancesOfBuilding(buildingsInRow.toArray(new Building[]{}), ColorEnum.YELLOW), Utility.instancesOfBuilding(buildingsInColumn.toArray(new Building[]{}), ColorEnum.YELLOW)));
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        System.out.println("The Market earns points for each other Yellow building in a row or column, whichever has more.");
        System.out.println("For example, if you have 2 Yellow buildings in the same row as your original, but 3 in the Column, then your\nbuilding is worth 3 points.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(marketArray);
    }

    @Override
    public void printManualText() {

    }
}
