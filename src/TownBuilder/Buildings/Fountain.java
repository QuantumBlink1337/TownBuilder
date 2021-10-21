package TownBuilder.Buildings;

import TownBuilder.Resource;
import TownBuilder.ResourceEnum;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fountain implements Building{
    public static final ResourceEnum[][] fountainArray = new ResourceEnum[1][2];
    public static final ArrayList<ResourceEnum[][]> fountainPatternArray = new ArrayList<>();
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return null;
    }
    public String toString() {
        return "Fountain";
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

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {

    }

    @Override
    public void printManualText() {

    }
}
