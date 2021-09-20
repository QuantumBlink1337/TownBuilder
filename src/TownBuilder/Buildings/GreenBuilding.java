package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.Scanner;

public class GreenBuilding extends Building{
    private BuildingEnum buildingEnum;
    private boolean condition;
    private static ResourceEnum[][] tavernArray = new ResourceEnum[1][3];
    private static ResourceEnum[][][] tavernPatternList = new ResourceEnum[1][2][2];

    public GreenBuilding(BuildingEnum b) {
        buildingEnum = b;
        condition = false;
    }
    public BuildingEnum getType() {
        return buildingEnum;
    }
    public static ResourceEnum[][][] getPatterns() {
        tavernArray[0][0] = ResourceEnum.BRICK;
        tavernArray[0][1] = ResourceEnum.BRICK;
        tavernArray[0][2] = ResourceEnum.GLASS;
        tavernPatternList[0] = tavernArray;
        return tavernPatternList;
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
