package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.Scanner;

public class BlackBuilding extends Building{
    private BuildingEnum buildingEnum;
    private boolean condition;
    private static Scanner sc = new Scanner(System.in);
    private static ResourceEnum[][] warehouseArray = new ResourceEnum[2][3];
    private static ResourceEnum[][][] warehousePatternList = new ResourceEnum[1][2][2];

    public BlackBuilding(BuildingEnum b) {
        buildingEnum = b;
        condition = false;
    }
    public BuildingEnum getType() {
        return buildingEnum;
    }
    public static ResourceEnum[][][] getPatterns() {
        warehouseArray[0][0] = ResourceEnum.WHEAT;
        warehouseArray[0][1] = ResourceEnum.WOOD;
        warehouseArray[0][2] = ResourceEnum.WHEAT;
        warehouseArray[1][0] = ResourceEnum.BRICK;
        warehouseArray[1][1] = ResourceEnum.NONE;
        warehouseArray[1][2] = ResourceEnum.BRICK;
        warehousePatternList[0] = warehouseArray;
        return warehousePatternList;
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
