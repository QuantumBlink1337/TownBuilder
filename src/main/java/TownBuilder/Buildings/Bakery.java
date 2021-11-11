package TownBuilder.Buildings;

import TownBuilder.BoardTraverser;
import TownBuilder.ColorEnum;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

import static TownBuilder.Buildings.BuildingFactory.patternBuilder;

public class Bakery implements Building{
    private boolean condition;
    private final int row;
    private final int col;

    private static final ResourceEnum[][] bakeryArray = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> bakeryPatternList = new ArrayList<>();

    static {
        bakeryArray[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.WHEAT,  ResourceEnum.NONE};
        bakeryArray[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.GLASS, ResourceEnum.BRICK};
        patternBuilder(bakeryArray, bakeryPatternList);
    }
    public Bakery(int r, int c) {
        row = r;
        col =c;
        condition = false;
    }
    @Override
    public String toString() {
        return "Bakery";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return bakeryPatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.BAKERY;
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
    public int scorer(Building[][] bArray) {
        // didn't rewrite this using searchForBuilding because that'd make this O(n^2) when this is already O(n)
        for (Building building : BoardTraverser.getAdjacentBuildings(bArray, row, col)) {
            if (building.getType().getColor() == ColorEnum.RED || building.getType().getColor() == ColorEnum.WHITE) {
                return 2;
            }
        }
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("The Bakery provides two points if it's adjacent to a Red or White building.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(bakeryArray);
    }
}
