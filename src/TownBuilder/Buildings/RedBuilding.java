package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;

import java.util.Scanner;

public class RedBuilding extends Building {
    private BuildingEnum buildingEnum;
    private boolean condition;
    private static ResourceEnum[][] farmArray = new ResourceEnum[2][2];
    private static ResourceEnum[][][] farmPatternList = new ResourceEnum[1][2][2];
    private Object BlueBuilding;

    public RedBuilding(BuildingEnum b) {
        buildingEnum = b;
        condition = false;
    }
    public BuildingEnum getType() {
        return buildingEnum;
    }
    public static ResourceEnum[][][] getPatterns() {
        farmArray[0][0] = ResourceEnum.WHEAT;
        farmArray[0][1] = ResourceEnum.WHEAT;
        farmArray[1][0] = ResourceEnum.WOOD;
        farmArray[1][1] = ResourceEnum.WOOD;
        farmPatternList[0] = farmArray;
        return farmPatternList;
    }
    public int scorer(Building[][] bArray) {
        BuildingEnum scoredType = this.buildingEnum;
        if (scoredType == BuildingEnum.FARM) {
            int i = 0;
            while (i < 4) {
                for (int r = 0; r < bArray.length; r++) {
                    for (int c = 0; c < bArray[r].length; c++) {
                        if (bArray[r][c].getClass() == BlueBuilding) {

                        }
                    }
                }
            }
        }
        return 0;
    }
    public BuildingEnum getBuildingEnum() {
        return buildingEnum;
    }
}
