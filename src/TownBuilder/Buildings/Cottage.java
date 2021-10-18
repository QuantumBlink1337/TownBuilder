package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Resource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;




public class Cottage extends Building{
    private boolean condition;
    private static final ResourceEnum[][] cottageArray = new ResourceEnum[2][2];
    private static final ResourceEnum[][] cottageArrayMirror = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> cottagePatternList = new ArrayList<>();


    public Cottage() {
        condition = false;
        cottageArray[0][0] = ResourceEnum.GLASS;
        cottageArray[0][1] = ResourceEnum.WHEAT;
        cottageArray[1][0] = ResourceEnum.BRICK;
        cottageArray[1][1] = ResourceEnum.NONE;
        patternBuilder(cottageArray, cottagePatternList, 3);
        cottageArrayMirror[0][0] = ResourceEnum.GLASS;
        cottageArrayMirror[1][0] = ResourceEnum.WHEAT;
        cottageArrayMirror[0][1] = ResourceEnum.BRICK;
        cottageArrayMirror[1][1] = ResourceEnum.NONE;
        patternBuilder(cottageArrayMirror, cottagePatternList, 3);
    }

    public BuildingEnum getType() {
        return BuildingEnum.COTTAGE;
    }

    public boolean getCondition() {
        return condition;
    }
    public String toString() {
        return "Cottage";
    }
    public void printManualText() {
        System.out.println("The Cottage is a building grants three points when it is fed.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(cottageArray);
    }
    public ArrayList<ResourceEnum[][]> getPatterns() {

        return cottagePatternList;
    }
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        int score = 0;
        for (Building[] buildings : bArray) {
            for (Building building : buildings) {
                if (building.getType() == BuildingEnum.FARM) {
                    Farm farm = (Farm) building;
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

        return score;
    }

}
