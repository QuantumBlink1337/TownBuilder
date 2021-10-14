package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Resource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;




public class Cottage extends Building{
    private boolean condition;
    private int count = 0;
    private static final ResourceEnum[][] cottageArray = new ResourceEnum[2][2];
    private static final ResourceEnum[][] cottageArrayMirror = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> cottagePatternList = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);



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
    public void setCondition(boolean b) {
        condition = b;
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
    public void placement(Resource[][] rArray, Building[][] bArray, ArrayList<Building> buildings) {
        Scanner sc = new Scanner((System.in));
        String userInput;
        int[] coords;
        boolean validInput = false;
        do {
            System.out.println("Where would you like to place your Cottage?");
            System.out.println("Valid positions for the Cottage are: ");
            Utility.displayValidResources(rArray);
            userInput = sc.nextLine().toLowerCase();
            coords = Utility.inputToCoords(userInput);
            if (rArray[coords[0]][coords[1]].getScannedBuilding() == BuildingEnum.COTTAGE) {
                validInput = true;
            }
        }
        while (!validInput);

        for (Resource validResource : Building.getValidResources()) {
            validResource.setResource(ResourceEnum.NONE);
        }
        clearResources(BuildingEnum.COTTAGE);
        rArray[coords[0]][coords[1]].setResource(ResourceEnum.OBSTRUCTED);
        bArray[coords[0]][coords[1]] = new Cottage();
    }
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        int score = 0;
                for (int r = 0; r < bArray.length; r++) {
                    for (int c = 0; c < bArray[r].length; c++) {
                        if (bArray[r][c].getType() == BuildingEnum.FARM) {
                            Farm farm = (Farm) bArray[r][c];
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
