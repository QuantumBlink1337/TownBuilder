package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Resource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class Tavern extends Building{
    private final boolean condition;
    private static final ResourceEnum[][] tavernArray = new ResourceEnum[1][3];
    private static final ArrayList<ResourceEnum[][]> tavernPatternList = new ArrayList<>();
    private static final int SCORE_INCREMENT_STARTING_VALUE = 2;

    public Tavern() {
        tavernArray[0][0] = ResourceEnum.BRICK;
        tavernArray[0][1] = ResourceEnum.BRICK;
        tavernArray[0][2] = ResourceEnum.GLASS;
        patternBuilder(tavernArray, tavernPatternList, 3);
        condition = false;
    }
    public BuildingEnum getType() {
        return BuildingEnum.TAVERN;
    }

    public boolean getCondition() {
        return condition;
    }
    public String toString() {
        return "Tavern";
    }
    public void printManualText() {
        System.out.println("The Tavern grants points based on how many Taverns you have:");
        System.out.print("1 Tavern: 2 Points | ");
        System.out.print("2 Taverns: 5 Points | ");
        System.out.print("3 Taverns: 9 Points | ");
        System.out.print("4 Taverns: 14 Points | ");
        System.out.print("5 Taverns: 20 Points");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(tavernArray);
    }
    public ArrayList<ResourceEnum[][]> getPatterns() {

        return tavernPatternList;
    }
    public void placement(Resource[][] rArray, Building[][] bArray, ArrayList<Building> buildings) {
        Scanner sc = new Scanner((System.in));
        String userInput;
        int[] coords;
        boolean validInput = false;
        do {
            System.out.println("Where would you like to place your Tavern?");
            System.out.println("Valid positions for the Tavern are:");
            Utility.displayValidResources(rArray);
            userInput = sc.nextLine().toLowerCase();
            coords = Utility.inputToCoords(userInput);
            if (rArray[coords[0]][coords[1]].getScannedBuilding() == BuildingEnum.TAVERN) {
                validInput = true;
            }
        }
        while (!validInput);

        for (Resource validResource : Building.getValidResources()) {
            validResource.setResource(ResourceEnum.NONE);
        }
        clearResources(BuildingEnum.TAVERN);
        rArray[coords[0]][coords[1]].setResource(ResourceEnum.OBSTRUCTED);
        bArray[coords[0]][coords[1]] = new Tavern();
    }
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        if (condition) {
            return 0;
        }
        int score =0;
        //System.out.println("Adding scoreIncrement " + scoreIncrement + " to score " + score);
        score += scoreIncrement;

        return score;
    }

    public static int getSCORE_INCREMENT_STARTING_VALUE() {
        return SCORE_INCREMENT_STARTING_VALUE;
    }
}
