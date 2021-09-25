package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class Farm extends Building {
    private boolean condition;
    private int fed;
    private static final ResourceEnum[][] farmArray = new ResourceEnum[2][2];
    private static final ResourceEnum[][][] farmPatternList = new ResourceEnum[1][2][2];

    public Farm() {
        condition = false;
        fed = 4;
        farmArray[0][0] = ResourceEnum.WHEAT;
        farmArray[0][1] = ResourceEnum.WHEAT;
        farmArray[1][0] = ResourceEnum.WOOD;
        farmArray[1][1] = ResourceEnum.WOOD;
        farmPatternList[0] = farmArray;
    }

    public BuildingEnum getType() {
        return BuildingEnum.FARM;
    }
    public void setCondition(boolean b) {
        condition = b;
    }
    public boolean getCondition() {
        return condition;
    }
    public int decrementFed() {
        return --fed;
    }
    public int getFed() {
        return fed;
    }
    public String wordDefinition() {
        return "Farm";
    }
    public void printPattern() {
        Utility.arrayPrinter(farmPatternList[0]);
    }
    public ResourceEnum[][][] getPatterns() {

        return farmPatternList;
    }
    public void placement(TownResource[][] rArray, Building[][] bArray, ArrayList<Building> buildings) {
        Scanner sc = new Scanner((System.in));
        String userInput;
        int[] coords;
        boolean validInput = false;
        do {
            System.out.println("Where would you like to place your Farm?");
            System.out.println("Valid positions for the Farm are:");
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
            if (rArray[coords[0]][coords[1]].getScannedBuilding() == BuildingEnum.FARM) {
                validInput = true;
            }
        }
        while (!validInput);

        for (TownResource validResource : Building.getValidResources()) {
            validResource.setResource(ResourceEnum.NONE);
        }
        clearResources(BuildingEnum.FARM);
        rArray[coords[0]][coords[1]].setResource(ResourceEnum.OBSTRUCTED);
        bArray[coords[0]][coords[1]] = new Farm();
    }
    public int scorer(Building[][] bArray, int row, int col) {
        return 0;
    }
}
