package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.Scanner;

public class OrangeBuilding extends Building{
    private BuildingEnum buildingEnum;
    private boolean condition;
    private static ResourceEnum[][] templeArray = new ResourceEnum[2][3];
    private static ResourceEnum[][][] templePatternList = new ResourceEnum[1][2][2];

    public OrangeBuilding(BuildingEnum b) {
        buildingEnum = b;
        condition = false;
    }
    public BuildingEnum getType() {
        return buildingEnum;
    }
    public static ResourceEnum[][][] getPatterns() {
        templeArray[0][0] = ResourceEnum.NONE;
        templeArray[0][1] = ResourceEnum.NONE;
        templeArray[0][2] = ResourceEnum.GLASS;
        templeArray[1][0] = ResourceEnum.STONE;
        templeArray[1][1] = ResourceEnum.GLASS;
        templeArray[1][2] = ResourceEnum.STONE;
        templePatternList[0] = templeArray;
        return templePatternList;
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
