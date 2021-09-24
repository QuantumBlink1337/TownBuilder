package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.Scanner;




public class Cottage extends Building{
    private boolean condition;
    private BuildingEnum buildingEnum;
    private static ResourceEnum[][] cottageArray = new ResourceEnum[2][2];
    private static ResourceEnum[][] cottageArrayMirror = new ResourceEnum[2][2];
    private static ResourceEnum[][][] cottagePatternList = new ResourceEnum[2][2][2];
    private static Scanner sc = new Scanner(System.in);
    public Cottage(BuildingEnum b) {
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
        cottageArray[0][0] = ResourceEnum.GLASS;
        cottageArray[0][1] = ResourceEnum.WHEAT;
        cottageArray[1][0] = ResourceEnum.BRICK;
        cottageArray[1][1] = ResourceEnum.NONE;
        cottageArrayMirror[0][0] = ResourceEnum.GLASS;
        cottageArrayMirror[1][0] = ResourceEnum.WHEAT;
        cottageArrayMirror[0][1] = ResourceEnum.BRICK;
        cottageArrayMirror[1][1] = ResourceEnum.NONE;
        cottagePatternList[0] = cottageArray;
        cottagePatternList[1] = cottageArrayMirror;
        return cottagePatternList;
    }
    public int scorer(Building[][] bArray, int row, int col) {
        BuildingEnum scoredType = this.buildingEnum;
        int score = 0;
        if (scoredType == BuildingEnum.COTTAGE) {
                for (int r = 0; r < bArray.length; r++) {
                    for (int c = 0; c < bArray[r].length; c++) {
                        if (bArray[r][c].getType() == BuildingEnum.FARM) {
                            Farm farm = (Farm) bArray[r][c];
                            if (farm.getFed() != 0) {
                                farm.decrementFed();
                                condition = true;
                            }
                        }
                    }
                }
            if (this.condition) {
                score += 3;
            }
        }
        return score;
    }
}
