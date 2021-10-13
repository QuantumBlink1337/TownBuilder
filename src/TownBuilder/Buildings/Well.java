package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class Well extends Building {
    private boolean condition;
    private int count = 0;
    private static Scanner sc = new Scanner(System.in);
    private static final ResourceEnum[][] wellArray = new ResourceEnum[2][1];
    private static final ResourceEnum[][] wellArrayMirror = new ResourceEnum[2][1];
    private static final ArrayList<ResourceEnum[][]> wellPatternList = new ArrayList<>();

    public Well() {
        wellArray[0][0] = ResourceEnum.WOOD;
        wellArray[1][0] = ResourceEnum.STONE;
        patternBuilder(wellArray, wellPatternList, 3);
        wellArrayMirror[0][0] = ResourceEnum.STONE;
        wellArrayMirror[1][0] = ResourceEnum.WOOD;
        patternBuilder(wellArrayMirror, wellPatternList, 3);

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
    public void printManualText() {
        System.out.println("The Well grants one point for each adjacent Cottage.");
        System.out.println("Note: these Cottages do not need to be fed. Note: diagonals do not count.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(wellArray);
    }
    public ArrayList<ResourceEnum[][]> getPatterns() {

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
            Utility.displayValidResources(rArray);
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
    @Override
    public void setCount() {
        count++;
    }

    @Override
    public int getCount() {
        return count;
    }
}
