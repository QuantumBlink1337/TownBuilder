package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class Theater extends Building
{
    private boolean condition;
    private ArrayList<Building> buildingsOnBoard;
    private int count = 0;
    private static final ResourceEnum[][] theaterArray = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> theaterPatternList= new ArrayList<>();

    public Theater(ArrayList<Building> b) {
        buildingsOnBoard = new ArrayList<>(b);
        condition = false;
        theaterArray[0][0] = ResourceEnum.NONE;
        theaterArray[0][1] = ResourceEnum.STONE;
        theaterArray[0][2] = ResourceEnum.NONE;
        theaterArray[1][0] = ResourceEnum.WOOD;
        theaterArray[1][1] = ResourceEnum.GLASS;
        theaterArray[1][2] = ResourceEnum.WOOD;
        patternBuilder(theaterArray, theaterPatternList, 3);
    }

    public Theater() {
        theaterArray[0][0] = ResourceEnum.NONE;
        theaterArray[0][1] = ResourceEnum.STONE;
        theaterArray[0][2] = ResourceEnum.NONE;
        theaterArray[1][0] = ResourceEnum.WOOD;
        theaterArray[1][1] = ResourceEnum.GLASS;
        theaterArray[1][2] = ResourceEnum.WOOD;
        patternBuilder(theaterArray, theaterPatternList, 3);
    }

    public BuildingEnum getType() {
        return BuildingEnum.THEATER;
    }
    public void setCondition(boolean b) {
        condition = b;
    }
    public boolean getCondition() {
        return condition;
    }
    public String toString() {
        return "Theater";
    }
    public ArrayList<ResourceEnum[][]> getPatterns() {
        return theaterPatternList;
    }
    public void printManualText() {
        System.out.println("The Theater grants one point for each unique building in it's row and column.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(theaterArray);
    }
    public void placement(TownResource[][] rArray, Building[][] bArray, ArrayList<Building> buildings) {
        Scanner sc = new Scanner((System.in));
        String userInput;
        int[] coords;
        boolean validInput = false;
        do {
            System.out.println("Where would you like to place your Theater?");
            System.out.println("Valid positions for the Theater are:");
            Utility.displayValidResources(rArray);
            userInput = sc.nextLine().toLowerCase();
            coords = Utility.inputToCoords(userInput);
            if (rArray[coords[0]][coords[1]].getScannedBuilding() == BuildingEnum.THEATER) {
                validInput = true;
            }
        }
        while (!validInput);

        for (TownResource validResource : Building.getValidResources()) {
            validResource.setResource(ResourceEnum.NONE);
        }
        clearResources(BuildingEnum.THEATER);
        rArray[coords[0]][coords[1]].setResource(ResourceEnum.OBSTRUCTED);
        bArray[coords[0]][coords[1]] = new Theater(buildings);
    }
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        int score = 0;

        for (int r = 0; r < bArray.length; r++) {
            score += buildingMatch(bArray[r][col], this);
        }
        for (int c = 0; c < bArray[row].length; c++) {
            score += buildingMatch(bArray[row][c], this);
        }
        return score;
    }
    private int buildingMatch(Building toBeChecked, Building scoreTarget) {
        int score = 0;
        ArrayList<Building> buildings = new ArrayList<>(buildingsOnBoard);
        for (int i = 0;  i < buildings.size(); i++) {
            if (toBeChecked.getType() == buildings.get(i).getType()) {
                if (!toBeChecked.equals(scoreTarget)) {
                    buildings.remove(i);
                    score++;
                }

            }
        }
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

