package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.Scanner;

public class GrayBuilding extends Building {
    private BuildingEnum buildingEnum;
    private static Scanner sc = new Scanner(System.in);
    private static ResourceEnum[][] wellArray = new ResourceEnum[2][2];
    private static ResourceEnum[][][] wellPatternList = new ResourceEnum[1][2][2];

    public GrayBuilding(BuildingEnum b) {
        super(b);
    }

    public static ResourceEnum[][][] getPatterns() {
        wellArray[0][0] = ResourceEnum.WOOD;
        wellArray[1][0] = ResourceEnum.STONE;
        wellArray[0][1] = ResourceEnum.NONE;
        wellArray[1][1] = ResourceEnum.NONE;
        wellPatternList[0] = wellArray;
        return wellPatternList;
    }
}
