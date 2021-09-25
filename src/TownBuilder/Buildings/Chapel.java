package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class Chapel extends Building{
    private boolean condition;
    private static ResourceEnum[][] templeArray = new ResourceEnum[2][3];
    private static ResourceEnum[][] templeArrayMirror = new ResourceEnum[2][3];
    private static ResourceEnum[][][] templePatternList = new ResourceEnum[2][2][2];

    public Chapel() {
        condition = false;
        templeArray[0][0] = ResourceEnum.NONE;
        templeArray[0][1] = ResourceEnum.NONE;
        templeArray[0][2] = ResourceEnum.GLASS;
        templeArray[1][0] = ResourceEnum.STONE;
        templeArray[1][1] = ResourceEnum.GLASS;
        templeArray[1][2] = ResourceEnum.STONE;
        templeArrayMirror[0][0] = ResourceEnum.GLASS;
        templeArrayMirror[0][1] = ResourceEnum.NONE;
        templeArrayMirror[0][2] = ResourceEnum.NONE;
        templeArrayMirror[1][0] = ResourceEnum.STONE;
        templeArrayMirror[1][1] = ResourceEnum.GLASS;
        templeArrayMirror[1][2] = ResourceEnum.STONE;
        templePatternList[0] = templeArray;
        templePatternList[1] = templeArrayMirror;
    }
    public BuildingEnum getType() {
        return BuildingEnum.CHAPEL;
    }
    public void setCondition(boolean b) {
        condition = b;
    }
    public boolean getCondition() {
        return condition;
    }
    public String toString() {
        return "Chapel";
    }
    public void printPattern() {
        Utility.arrayPrinter(templePatternList[0]);
    }
    public void placement(TownResource[][] rArray, Building[][] bArray, ArrayList<Building> buildings) {
        Scanner sc = new Scanner((System.in));
        String userInput;
        int[] coords;
        boolean validInput = false;
        do {
            System.out.println("Where would you like to place your Chapel?");
            System.out.println("Valid positions for the Chapel are:");
            for (int r = 0; r < rArray.length; r++) {
                for (int c = 0; c < rArray[r].length; c++) {
                    for (TownResource validResource : Building.getValidResources()) {
                        if (rArray[r][c] == validResource) {
                            System.out.println(Utility.coordsToOutput(r, c));
                        }
                    }

                }
            }
            userInput = sc.nextLine().toLowerCase();
            coords = Utility.inputToCoords(userInput);
            if (rArray[coords[0]][coords[1]].getScannedBuilding() == BuildingEnum.CHAPEL) {
                validInput = true;
            }
        }
        while (!validInput);

        for (TownResource validResource : Building.getValidResources()) {
            validResource.setResource(ResourceEnum.NONE);
        }
        clearResources(BuildingEnum.CHAPEL);
        rArray[coords[0]][coords[1]].setResource(ResourceEnum.OBSTRUCTED);
        bArray[coords[0]][coords[1]] = new Chapel();
    }

    @Override

    public ResourceEnum[][][] getPatterns() {

        return templePatternList;
    }
    public int scorer(Building[][] bArray, int row, int col) {
        int score = 0;
        for (int r = 0; r < bArray.length; r++) {
            for (int c = 0; c < bArray[r].length; c++) {
                if (bArray[r][c].getType() == BuildingEnum.COTTAGE && bArray[r][c].getCondition()) {
                    score += 1;
                }
            }
        }
        return score;
    }
}
