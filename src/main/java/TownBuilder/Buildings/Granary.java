package TownBuilder.Buildings;

import TownBuilder.BoardTraverser;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class Granary implements Building {
    private boolean condition;
    private final int row;
    private final int col;
    private static final ResourceEnum[][] orchardArray = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> orchardPatternList = new ArrayList<>();
    static {
        orchardArray[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.WHEAT};
        orchardArray[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.WOOD};
        BuildingFactory.patternBuilder(orchardArray, orchardPatternList);

    }
    public Granary(int r, int c) {
        row = r;
        col =c;
        condition = false;
    }
    @Override
    public BuildingEnum getType() {
        return BuildingEnum.GRANARY;
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

    public String toString() {
        return "Granary";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return orchardPatternList;
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
    public void onTurnInterval(Building[][] buildingBoard) throws IOException {
        Building[] adjacentBuildings = BoardTraverser.getAdjacentBuildings(buildingBoard, row, col);
        Building[] diagonalBuildings = BoardTraverser.getDiagonalBuildings(buildingBoard, row, col);
        Utility.feedBuildings(adjacentBuildings);
        Utility.feedBuildings(diagonalBuildings);
    }

    @Override
    public String getManualEntry() {
        return "The Granary feeds the tiles adjacent and diagonal to itself.";
    }
}
