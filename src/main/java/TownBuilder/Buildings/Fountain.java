package TownBuilder.Buildings;

import TownBuilder.BoardTraverser;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class Fountain implements Building{
    private static final ResourceEnum[][] fountainArray = new ResourceEnum[1][2];
    private static final ArrayList<ResourceEnum[][]> fountainPatternArray = new ArrayList<>();
    private final int row;
    private final int col;
    private final String color = "gray";
    private boolean condition;

    static {
        fountainArray[0] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(fountainArray, fountainPatternArray);
        // fountainArray[0] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.WOOD};
        // BuildingFactory.patternBuilder(fountainArray, fountainPatternArray, 3);

    }
    public Fountain(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return fountainPatternArray;
    }
    public String toString() {
        return "Fountain";
    }
    @Override
    public BuildingEnum getType() {
        return BuildingEnum.FOUNTAIN;
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
        return 2 * BoardTraverser.instancesOfBuilding(BoardTraverser.getAdjacentBuildings(bArray, row, col), BuildingEnum.FOUNTAIN);
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("The Fountain grants 2 points for each adjacent Fountain.");
        System.out.println("Here's what it looks like:");
        Utility.printFormattedResourcePattern(fountainPatternArray.get(0));
    }
}
