package TownBuilder.Buildings;

import TownBuilder.BoardTraverser;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

public class Orchard implements Building {
    private static final ResourceEnum[][] orchardPattern = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> orchardPatternList = new ArrayList<>();
    private final int row;
    private final int col;
    private final String color = "red";
    private boolean condition;
    static {
        orchardPattern[0] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.WHEAT};
        orchardPattern[1] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.WOOD};
        BuildingFactory.patternBuilder(orchardPattern, orchardPatternList);
    }
    public Orchard(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }


    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return orchardPatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.ORCHARD;
    }
    public String toString() {
        return "Orchard";
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
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        Building[] buildingsInRowAndColumn = BoardTraverser.getBuildingsInRowAndColumn(buildingBoard, row, col);
        Utility.feedBuildings(buildingsInRowAndColumn);
    }

    @Override
    public void printManualText() {
        System.out.println("The Orchard feeds any building in its row or column.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(orchardPatternList.get(0));
    }
}
