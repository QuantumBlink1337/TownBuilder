package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

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
        BuildingFactory.patternBuilder(orchardArray, orchardPatternList, 3);
        orchardArray[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.WHEAT};
        orchardArray[1] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.BRICK};
        BuildingFactory.patternBuilder(orchardArray, orchardPatternList, 3);

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
    public int scorer(Building[][] bArray, int scoreIncrement) {
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        Building[] adjacentBuildings = Utility.getAdjacentBuildings(buildingBoard, row, col);
        Building[] diagonalBuildings = Utility.getDiagonalBuildings(buildingBoard, row, col);
        for (Building building : adjacentBuildings) {
            if (building.isFeedable() && !building.getCondition()) {
                building.setCondition(true);
            }
        }
        for (Building building : diagonalBuildings) {
            if (building.isFeedable() && !building.getCondition()) {
                building.setCondition(true);
            }
        }
    }

    @Override
    public void printManualText() {
        System.out.println("The Orchard feeds the tiles adjacent and diagonal to itself!");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(orchardPatternList.get(0));
    }
}