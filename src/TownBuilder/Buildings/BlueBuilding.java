package TownBuilder.Buildings;

import TownBuilder.BuildingColor;
import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;
import TownBuilder.Utility;

import java.util.Scanner;




public class BlueBuilding extends Building{

    private static ResourceEnum[][] cottageArray = new ResourceEnum[2][2];
    private static ResourceEnum[][] cottageArrayMirror = new ResourceEnum[2][2];
    private static ResourceEnum[][][] cottagePatternList = new ResourceEnum[2][2][2];
    private static Scanner sc = new Scanner(System.in);
    public BlueBuilding(BuildingEnum b) {
        super(b);

    }
    public static ResourceEnum[][][] getPatterns() {
        cottageArray[0][0] = ResourceEnum.GLASS;
        cottageArray[0][1] = ResourceEnum.WHEAT;
        cottageArray[1][0] = ResourceEnum.BRICK;
        cottageArray[1][1] = ResourceEnum.NONE;
        cottageArrayMirror[0][0] = ResourceEnum.GLASS;
        cottageArrayMirror[1][0] = ResourceEnum.WHEAT;
        cottageArrayMirror[0][1] = ResourceEnum.BRICK;
        cottageArrayMirror[1][1] = ResourceEnum.NONE;
//        cottageArrayMirror = ResourceEnum.swap(cottageArray, 0, 1, 1, 0);
        System.out.println("CottageArray");
        Utility.arrayPrinter(cottageArray);
        System.out.println("CottageArrayMirror");
        Utility.arrayPrinter(cottageArrayMirror);
        cottagePatternList[0] = cottageArray;
        cottagePatternList[1] = cottageArrayMirror;
        return cottagePatternList;
    }
}
