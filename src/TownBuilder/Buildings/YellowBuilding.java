package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.Scanner;

public class YellowBuilding extends Building
{
    private BuildingEnum buildingEnum;
    private boolean condition;
    private static ResourceEnum[][] theaterArray = new ResourceEnum[2][3];
    private static ResourceEnum[][][] theaterPatternList = new ResourceEnum[1][2][2];

    public YellowBuilding(BuildingEnum b) {
        buildingEnum = b;
        condition = false;
    }
    public BuildingEnum getType() {
        return buildingEnum;
    }
    public void setCondition(boolean b) {
        condition = b;
    }
    public boolean getCondition() {
        return condition;
    }
    public static ResourceEnum[][][] getPatterns() {
        theaterArray[0][0] = ResourceEnum.NONE;
        theaterArray[0][1] = ResourceEnum.STONE;
        theaterArray[0][2] = ResourceEnum.NONE;
        theaterArray[1][0] = ResourceEnum.WOOD;
        theaterArray[1][1] = ResourceEnum.GLASS;
        theaterArray[1][2] = ResourceEnum.WOOD;
        theaterPatternList[0] = theaterArray;
        return theaterPatternList;
    }
    public int scorer(Building[][] bArray) {
        BuildingEnum scoredType = this.buildingEnum;
        if (scoredType == BuildingEnum.FARM) {
            //return 1;
        }
        return 0;
    }
    public BuildingEnum getBuildingEnum() {
        return buildingEnum;
    }
}

