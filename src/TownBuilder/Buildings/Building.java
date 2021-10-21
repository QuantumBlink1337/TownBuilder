package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.ArrayList;

public interface Building {

    BuildingEnum getType();
    boolean getCondition();
    void setCondition(boolean condition);
    ArrayList<ResourceEnum[][]> getBuildingPatternsList();
    String toString();
    boolean isFeedable();

    int scorer(Building[][] bArray, int row, int col, int scoreIncrement);
    void onTurnInterval(Building[][] buildingBoard, int row, int col);
    void printManualText();

}

