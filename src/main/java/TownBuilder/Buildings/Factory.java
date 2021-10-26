package TownBuilder.Buildings;

import TownBuilder.Resource;
import TownBuilder.ResourceEnum;

import java.util.ArrayList;
import java.util.Scanner;

public class Factory implements Building{
    private static final ResourceEnum[][] factoryPattern = new ResourceEnum[2][4];
    private static final ArrayList<ResourceEnum[][]> factoryPatternList = new ArrayList<>();
    private final int row;
    private final int col;
    private ResourceEnum factorizedResource;
    static {
        factoryPattern[0] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.NONE};
        factoryPattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.STONE, ResourceEnum.STONE, ResourceEnum.BRICK};
        BuildingFactory.patternBuilder(factoryPattern, factoryPatternList, 3);
        factoryPattern[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.WOOD};
        factoryPattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.STONE, ResourceEnum.STONE, ResourceEnum.BRICK};
        BuildingFactory.patternBuilder(factoryPattern, factoryPatternList, 3);
    }
    public Factory(int r, int c) {
        row = r;
        col = c;
        pickResource();
    }
    private void pickResource() {
        System.out.println("You can choose a resource for your Factory.");
        factorizedResource = ResourceEnum.resourcePicker();
    }

    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return null;
    }

    @Override
    public BuildingEnum getType() {
        return null;
    }

    @Override
    public boolean getCondition() {
        return false;
    }

    @Override
    public void setCondition(boolean condition) {

    }

    @Override
    public int getRow() {
        return 0;
    }

    @Override
    public int getCol() {
        return 0;
    }

    public String toString() {
        return "Factory";
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

    }

    @Override
    public void printManualText() {

    }
}
