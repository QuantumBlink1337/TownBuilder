package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.Scanner;

public class RedBuilding extends Building {
    private BuildingEnum buildingEnum;
    private boolean condition;
    private static ResourceEnum[][] farmArray = new ResourceEnum[2][2];
    private static ResourceEnum[][][] farmPatternList = new ResourceEnum[1][2][2];

    public RedBuilding(BuildingEnum b) {
        super(b);
        condition = false;
    }
    public static ResourceEnum[][][] getPatterns() {
        farmArray[0][0] = ResourceEnum.WHEAT;
        farmArray[0][1] = ResourceEnum.WHEAT;
        farmArray[1][0] = ResourceEnum.WOOD;
        farmArray[1][1] = ResourceEnum.WOOD;
        farmPatternList[0] = farmArray;
        return farmPatternList;
    }
    public int scorer() {
        BuildingEnum scoredType = this.buildingEnum;
        if (scoredType == BuildingEnum.FARM) {
            for (int i = 0; i < 4; i++) {

            }
        }
        return 0;
    }
    public BuildingEnum getBuildingEnum() {
        return buildingEnum;
    }
}
