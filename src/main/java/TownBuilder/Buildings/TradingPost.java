package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.ArrayList;

import static TownBuilder.Buildings.BuildingFactory.patternBuilder;

public class TradingPost implements Building{
    private boolean condition;
    private final int row;
    private final int col;
    private static final ResourceEnum[][] tPostPattern = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> tPostList = new ArrayList<>();

    static {
        tPostPattern[0] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.WOOD, ResourceEnum.NONE};
        tPostPattern[1] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.WOOD, ResourceEnum.BRICK};
        patternBuilder(tPostPattern, tPostList);
    }
    public TradingPost(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return tPostList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.TRDINGPST;
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
        return "Trading Post";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) {
        return 1;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public String getManualEntry() {
        return "The Trading Post earns one point. The Trading Post acts as any resource when constructing a building.";
    }

}
