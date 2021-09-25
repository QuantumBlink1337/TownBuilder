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
    public ResourceEnum[][][] getPatterns() {
        return new ResourceEnum[0][][];
    }

    @Override
    public String wordDefinition() {
        return null;
    }

    @Override
    public int scorer(Building[][] bArray, int row, int col) {
        return 0;
    }

    @Override
    public void placement(TownResource[][] rArray, Building[][] bArray, ArrayList<Building> buildings) {

    }

    @Override
    public void printPattern() {

    }
}
