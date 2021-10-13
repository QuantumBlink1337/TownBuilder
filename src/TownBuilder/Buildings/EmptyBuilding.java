package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;

import java.util.ArrayList;

public class EmptyBuilding extends Building{
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
    public ArrayList<ResourceEnum[][]> getPatterns() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        return 0;
    }

    @Override
    public void setCount() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public void placement(TownResource[][] rArray, Building[][] bArray, ArrayList<Building> buildings) {

    }

    @Override
    public void printManualText() {

    }
}
