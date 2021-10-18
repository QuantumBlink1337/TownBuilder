package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Resource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class Theater extends Building
{
    private boolean condition;
    private ArrayList<Building> buildingsOnBoard;
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
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        int score = 0;

        //noinspection ForLoopReplaceableByForEach
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

}

