package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Resource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class Chapel extends Building{
    private final boolean condition;
    private static final ResourceEnum[][] templeArray = new ResourceEnum[2][3];
    private static final ResourceEnum[][] templeArrayMirror = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> templeList = new ArrayList<>();

    public Chapel() {
        condition = false;
    }
    static {
        templeArray[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.GLASS};
        templeArray[1] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.GLASS, ResourceEnum.STONE};
        patternBuilder(templeArray, templeList, 3);
        templeArrayMirror[0] = new ResourceEnum[]{ResourceEnum.GLASS, ResourceEnum.NONE, ResourceEnum.NONE};
        templeArrayMirror[1] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.GLASS, ResourceEnum.STONE};
        patternBuilder(templeArrayMirror, templeList, 3);
        Building.setbuildingMasterList("orange", new Chapel());

    }
    public BuildingEnum getType() {
        return BuildingEnum.CHAPEL;
    }
    public boolean getCondition() {
        return condition;
    }
    public String toString() {
        return "Chapel";
    }
    public void printManualText() {
        System.out.println("The Chapel grants one point for each fed Cottage.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(templeArray);
    }
    @Override
    public ArrayList<ResourceEnum[][]> getPatterns() {

        return templeList;
    }
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        int score = 0;
        for (Building[] buildings : bArray) {
            for (Building building : buildings) {
                if (building.getType() == BuildingEnum.COTTAGE && building.getCondition()) {
                    score += 1;
                }
            }
        }
        return score;
    }
}