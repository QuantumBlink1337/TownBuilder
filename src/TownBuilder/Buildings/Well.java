package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class Well extends Building {
    private boolean condition;
    private static Scanner sc = new Scanner(System.in);
    private static final ResourceEnum[][] wellArray = new ResourceEnum[2][1];
    private static final ResourceEnum[][] wellArrayMirror = new ResourceEnum[2][1];
    private static final ResourceEnum[][][] wellPatternList = new ResourceEnum[2][2][2];

    public Well() {
        wellArray[0][0] = ResourceEnum.WOOD;
        wellArray[1][0] = ResourceEnum.STONE;
        wellArrayMirror[0][0] = ResourceEnum.STONE;
        wellArrayMirror[1][0] = ResourceEnum.WOOD;
        wellPatternList[0] = wellArray;
        wellPatternList[1] = wellArrayMirror;
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
    public String toString() {
        return "Well";
    }
    public void printPattern() {
        Utility.arrayPrinter(wellPatternList[0]);
    }
    public ResourceEnum[][][] getPatterns() {

        return wellPatternList;
    }
    public void placement(TownResource[][] rArray, Building[][] bArray, ArrayList<Building> buildings) {
        Scanner sc = new Scanner((System.in));
        String userInput;
        int[] coords;
        boolean validInput = false;
        do {
            System.out.println("Where would you like to place your Well?");
            System.out.println("Valid positions for the Well are:");
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
            if (rArray[coords[0]][coords[1]].getScannedBuilding() == BuildingEnum.WELL) {
                validInput = true;
            }
        }
        while (!validInput);

        for (TownResource validResource : Building.getValidResources()) {
            validResource.setResource(ResourceEnum.NONE);
        }
        clearResources(BuildingEnum.WELL);
        rArray[coords[0]][coords[1]].setResource(ResourceEnum.OBSTRUCTED);
        bArray[coords[0]][coords[1]] = new Well();
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
