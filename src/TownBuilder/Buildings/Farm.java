package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Resource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class Farm extends Building {
    private final boolean condition;
    private int fed;
    private static final ResourceEnum[][] farmArray = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> farmPatternList = new ArrayList<>();

    public Farm() {
        condition = false;
        fed = 4;
        farmArray[0][0] = ResourceEnum.WHEAT;
        farmArray[0][1] = ResourceEnum.WHEAT;
        farmArray[1][0] = ResourceEnum.WOOD;
        farmArray[1][1] = ResourceEnum.WOOD;
       patternBuilder(farmArray, farmPatternList, 3);
    }

    public BuildingEnum getType() {
        return BuildingEnum.FARM;
    }

    public boolean getCondition() {
        return condition;
    }
    public void decrementFed() {
        --fed;
    }
    public int getFed() {
        return fed;
    }
    public String toString() {
        return "Farm";
    }
    public void printManualText() {
        System.out.println("The Farm feeds up to four buildings on the board.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(farmArray);
    }
    public ArrayList<ResourceEnum[][]> getPatterns() {

        return farmPatternList;
    }
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        return 0;
    }

}
