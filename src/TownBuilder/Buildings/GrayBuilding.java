package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.Scanner;

public class GrayBuilding extends Building {
    private BuildingEnum buildingEnum;
    private static Scanner sc = new Scanner(System.in);
    private static ResourceEnum[][] wellArray = new ResourceEnum[2][1];
    private static ResourceEnum[][][] wellPatternList = new ResourceEnum[1][2][2];

    public GrayBuilding(BuildingEnum b) {
        super(b);
    }

    public static ResourceEnum[][][] getPatterns() {
        wellArray[0][0] = ResourceEnum.WOOD;
        wellArray[0][1] = ResourceEnum.STONE;
        wellPatternList[0] = wellArray;
        return wellPatternList;
    }
}
