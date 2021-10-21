package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.ArrayList;

public class EmptyBuilding implements Building {
    public EmptyBuilding() {}
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
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
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
