package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.ArrayList;

public interface Building {


    ArrayList<ResourceEnum[][]> getBuildingPatternsList();
    BuildingEnum getType();
    boolean getCondition();
    void setCondition(boolean condition);
    int getRow();
    int getCol();
    String toString();
    boolean isFeedable();

    int scorer(Building[][] bArray, int row, int col, int scoreIncrement);
    void onTurnInterval(Building[][] buildingBoard);
    void printManualText();

}

