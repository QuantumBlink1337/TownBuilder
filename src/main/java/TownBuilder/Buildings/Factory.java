package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

public class Factory implements Building{
    private static final ResourceEnum[][] factoryPattern = new ResourceEnum[2][4];
    private static final ArrayList<ResourceEnum[][]> factoryPatternList = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;
    private ResourceEnum factorizedResource;
    static {
        factoryPattern[0] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.NONE};
        factoryPattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.STONE, ResourceEnum.STONE, ResourceEnum.BRICK};
        BuildingFactory.patternBuilder(factoryPattern, factoryPatternList, 3);
        factoryPattern[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.WOOD};
        factoryPattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.STONE, ResourceEnum.STONE, ResourceEnum.BRICK};
        BuildingFactory.patternBuilder(factoryPattern, factoryPatternList, 3);
    }
    public Factory(int r, int c, boolean playerMade) {
        row = r;
        col = c;
        condition = false;
        if (playerMade) {
            pickResource();
        }
        else {
            factorizedResource = ResourceEnum.NONE;
        }
    }
    private void pickResource() {
        System.out.println("You can choose a resource for your Factory.");
        factorizedResource = ResourceEnum.resourcePicker(null);
    }
    public ResourceEnum exchangeResource() {
        return ResourceEnum.resourcePicker(null);
    }
    public ResourceEnum getFactorizedResource() {
        return factorizedResource;
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return factoryPatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.FACTORY;
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
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("When you place a Factory, you must also select a resource for it to use.\nIf your selected resource is the one given to you this turn, you may choose another resource.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(factoryPattern);
    }
}
