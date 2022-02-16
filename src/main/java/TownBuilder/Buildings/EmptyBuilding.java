package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.ArrayList;

public class EmptyBuilding implements Building {
    private final int row;
    private final int col;
    public EmptyBuilding() {
        row = -1;
        col = -1;
    }
    public EmptyBuilding(int r, int c) {
        row = r;
        col = c;
    }
    @Override
    public BuildingEnum getType() {
        return BuildingEnum.NONE;
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
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Empty Building [Please contact me on GitHub if this appears]";
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
    public void onTurnInterval(Building[][] buildingBoard) {

    }

    @Override
    public String getManualEntry() {
        return "Something is wrong if you see this message. Contact me on GitHub if this appears.";
    }
}
