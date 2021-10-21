package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.ArrayList;

public class Orchard implements Building{
    private boolean condition;
    private static final ResourceEnum[][] orchardArray = new ResourceEnum[2][2];
    private static final ResourceEnum[][] orchardMirrorArray = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> farmPatternList = new ArrayList<>();
    static {

    }
    public Orchard() {
        condition = false;
    }
    @Override
    public BuildingEnum getType() {
        return BuildingEnum.ORCHARD;
    }

    @Override
    public boolean getCondition() {
        return condition;
    }

    @Override
    public void setCondition(boolean condition) {

    }

    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return null;
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard, int row, int col) {

    }

    @Override
    public void printManualText() {

    }
}
