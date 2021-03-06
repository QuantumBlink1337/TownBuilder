package TownBuilder.Buildings;

import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;
import static TownBuilder.Buildings.BuildingFactory.patternBuilder;

public class Cottage implements Building {
    private boolean condition;
    private final int row;
    private final int col;
    private boolean isFed;
    private static final ResourceEnum[][] cottageArray = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> cottagePatternList = new ArrayList<>();


    public Cottage(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    static {
        cottageArray[0] = new ResourceEnum[]{ResourceEnum.GLASS, ResourceEnum.WHEAT};
        cottageArray[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.NONE};
        patternBuilder(cottageArray, cottagePatternList);
    }
    public BuildingEnum getType() {
        return BuildingEnum.COTTAGE;
    }

    public boolean getCondition() {
        return condition;
    }
    @Override
    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    @Override
    public boolean getFedStatus() {
        return isFed;
    }

    @Override
    public void setFedStatus(boolean condition) {
        isFed = condition;
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
        return "Cottage";
    }

    @Override
    public boolean isFeedable() {
        return true;
    }

    @Override
    public String getManualEntry() {
        return "The Cottage grants three points when it is fed.";
    }
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {

        return cottagePatternList;
    }
    public int scorer(Building[][] bArray) throws IOException {
        DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - SCORING: Beginning scoring protocol. Condition: " + condition);
        return isFed ? 3 : 0;
    }
    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

}
