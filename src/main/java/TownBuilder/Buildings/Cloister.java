package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

public class Cloister implements Building {
    private static final ResourceEnum[][] cloisterArray = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> cloisterPatternList = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;

    static {
        cloisterArray[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.GLASS};
        cloisterArray[1] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.BRICK, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(cloisterArray, cloisterPatternList, 3);
        cloisterArray[0] = new ResourceEnum[]{ResourceEnum.GLASS, ResourceEnum.NONE, ResourceEnum.NONE};
        cloisterArray[1] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.BRICK, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(cloisterArray, cloisterPatternList, 3);
    }
    public Cloister(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return cloisterPatternList;
    }

    @Override
    public String toString() {
        return "Cloister";
    }
    public BuildingEnum getType() {
        return BuildingEnum.CLOISTER;
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
    public int scorer(Building[][] bArray, int scoreIncrement) {
        int score = 0;
        for (Building[] buildingRow : bArray) {
            for (Building building : buildingRow) {
                int r = building.getRow();
                int c = building.getCol();
                if (building.getType() == BuildingEnum.CLOISTER) {
                    if ((r == 0 && c == 0) || (r == 0 && c == bArray[r].length -1) || (r == bArray.length -1 && c == 0) || (r == bArray.length -1 && c == bArray[r].length -1)) {
                        score+=1;
                    }
                }
            }
        }

        return score;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("The Cloister earns 1 point for every Yellow building in a corner of your board.");
        System.out.println("Note: a Cloister not in a corner still earns points, but it doesn't contribute to other Cloisters.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(cloisterPatternList.get(0));
    }
}
