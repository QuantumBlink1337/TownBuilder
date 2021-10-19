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
    }
    static {
        cottageArray[0] = new ResourceEnum[]{ResourceEnum.GLASS, ResourceEnum.WHEAT};
        cottageArray[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.NONE};
        patternBuilder(cottageArray, cottagePatternList, 3);
        cottageArrayMirror[0] = new ResourceEnum[]{ResourceEnum.GLASS, ResourceEnum.BRICK};
        cottageArrayMirror[1] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.NONE};
        patternBuilder(cottageArrayMirror, cottagePatternList, 3);
        System.out.println("This statement fired!");
        Building.setbuildingMasterList("blue", new Cottage());
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
