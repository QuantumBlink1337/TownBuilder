package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Placeable;

import java.io.IOException;
import java.util.ArrayList;

public interface Building extends Placeable {


    ArrayList<ResourceEnum[][]> getBuildingPatternsList();
    BuildingEnum getType();
    boolean getCondition();
    void setCondition(boolean condition);
    boolean getFedStatus();
    void setFedStatus(boolean condition);
    boolean isFeedable();

    int scorer(Building[][] bArray) throws IOException;
    void onTurnInterval(Building[][] buildingBoard) throws IOException;

    String getManualEntry();

}

