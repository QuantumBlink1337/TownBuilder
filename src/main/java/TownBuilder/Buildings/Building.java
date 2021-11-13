package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.io.IOException;
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

    int scorer(Building[][] bArray) throws IOException;
    void onTurnInterval(Building[][] buildingBoard) throws IOException;
    void printManualText();

}

