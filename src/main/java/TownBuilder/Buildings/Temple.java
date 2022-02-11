package TownBuilder.Buildings;

import TownBuilder.BoardTraverser;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class Temple implements Building{
    public static ResourceEnum[][] templeArray = new ResourceEnum[2][2];
    public static ArrayList<ResourceEnum[][]> templePatternList = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;

    static {
        templeArray[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.GLASS};
        templeArray[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.BRICK, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(templeArray, templePatternList);
    }
    public Temple(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    public String toString() {
        return "Temple";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return templePatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.TEMPLE;
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
        return BoardTraverser.instancesOfBuilding(BoardTraverser.getAdjacentBuildings(bArray, row, col), BuildingEnum.COTTAGE) > 1 ? 4 : 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("The Temple earns 4 points if it's adjacent to two fed Cottages.");
        System.out.println("Here's what it looks like:");
    }

    @Override
    public String getManualEntry() {
        return "The Temple earns 4 points if it's adjacent to two fed Cottages.";
    }


}
