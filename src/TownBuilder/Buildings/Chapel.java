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
        templeArray[0][0] = ResourceEnum.NONE;
        templeArray[0][1] = ResourceEnum.NONE;
        templeArray[0][2] = ResourceEnum.GLASS;
        templeArray[1][0] = ResourceEnum.STONE;
        templeArray[1][1] = ResourceEnum.GLASS;
        templeArray[1][2] = ResourceEnum.STONE;
        patternBuilder(templeArray, templeList, 3);

        templeArrayMirror[0][0] = ResourceEnum.GLASS;
        templeArrayMirror[0][1] = ResourceEnum.NONE;
        templeArrayMirror[0][2] = ResourceEnum.NONE;
        templeArrayMirror[1][0] = ResourceEnum.STONE;
        templeArrayMirror[1][1] = ResourceEnum.GLASS;
        templeArrayMirror[1][2] = ResourceEnum.STONE;
        patternBuilder(templeArrayMirror, templeList, 3);
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
    public void placement(Resource[][] rArray, Building[][] bArray, ArrayList<Building> buildings) {
        Scanner sc = new Scanner((System.in));
        String userInput;
        int[] coords;
        boolean validInput = false;
        do {
            System.out.println("Where would you like to place your Chapel?");
            System.out.println("Valid positions for the Chapel are:");
            Utility.displayValidResources(rArray);
            userInput = sc.nextLine().toLowerCase();
            coords = Utility.inputToCoords(userInput);
            if (rArray[coords[0]][coords[1]].getScannedBuilding() == BuildingEnum.CHAPEL) {
                validInput = true;
            }
        }
        while (!validInput);

        for (Resource validResource : Building.getValidResources()) {
            validResource.setResource(ResourceEnum.NONE);
        }
        clearResources(BuildingEnum.CHAPEL);
        rArray[coords[0]][coords[1]].setResource(ResourceEnum.OBSTRUCTED);
        bArray[coords[0]][coords[1]] = new Chapel();
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
