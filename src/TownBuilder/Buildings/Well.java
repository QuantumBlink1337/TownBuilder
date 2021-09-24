package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.Scanner;

public class Well extends Building {
    private boolean condition;
    private static Scanner sc = new Scanner(System.in);
    private static ResourceEnum[][] wellArray = new ResourceEnum[2][1];
    private static ResourceEnum[][] wellArrayMirror = new ResourceEnum[2][1];
    private static ResourceEnum[][][] wellPatternList = new ResourceEnum[2][2][2];

    public Well() {
        condition = false;
    }
    public BuildingEnum getType() {
        return BuildingEnum.WELL;
    }
    public void setCondition(boolean b) {
        condition = b;
    }
    public boolean getCondition() {
        return condition;
    }
    public static ResourceEnum[][][] getPatterns() {
        wellArray[0][0] = ResourceEnum.WOOD;
        wellArray[1][0] = ResourceEnum.STONE;
        wellArrayMirror[0][0] = ResourceEnum.STONE;
        wellArrayMirror[1][0] = ResourceEnum.WOOD;
        wellPatternList[0] = wellArray;
        wellPatternList[1] = wellArrayMirror;
        return wellPatternList;
    }
    public int scorer(Building[][] bArray, int row, int col) {
        int score = 0;
        try {
            if (bArray[row][col-1].getType() == BuildingEnum.COTTAGE) {
                score++;
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            if (bArray[row-1][col].getType() == BuildingEnum.COTTAGE) {
                score++;
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            if (bArray[row][col+1].getType() == BuildingEnum.COTTAGE) {
                score++;
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            if (bArray[row+1][col].getType() == BuildingEnum.COTTAGE) {
                score++;
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        return score;
    }
}
