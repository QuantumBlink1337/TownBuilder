package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Resource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class Well extends Building {
    private final boolean condition;
    private static final ResourceEnum[][] wellArray = new ResourceEnum[2][1];
    private static final ResourceEnum[][] wellArrayMirror = new ResourceEnum[2][1];
    private static final ArrayList<ResourceEnum[][]> wellPatternList = new ArrayList<>();

    public Well() {
        condition = false;
    }
    static {
        wellArray[0][0] = ResourceEnum.WOOD;
        wellArray[1][0] = ResourceEnum.STONE;
        patternBuilder(wellArray, wellPatternList, 3);
        wellArrayMirror[0][0] = ResourceEnum.STONE;
        wellArrayMirror[1][0] = ResourceEnum.WOOD;
        patternBuilder(wellArrayMirror, wellPatternList, 3);
    }
    public BuildingEnum getType() {
        return BuildingEnum.WELL;
    }
    public boolean getCondition() {
        return condition;
    }
    public String toString() {
        return "Well";
    }
    public void printManualText() {
        System.out.println("The Well grants one point for each adjacent Cottage.");
        System.out.println("Note: these Cottages do not need to be fed. Note: diagonals do not count.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(wellArray);
    }
    public ArrayList<ResourceEnum[][]> getPatterns() {
        return wellPatternList;
    }
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
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
