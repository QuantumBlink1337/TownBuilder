package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;
import static TownBuilder.Buildings.BuildingFactory.patternBuilder;


import java.util.ArrayList;

public class Theater implements Building
{
    private boolean condition;
    private final int row;
    private final int col;
    private ArrayList<Building> buildingsOnBoard;
    private static final ResourceEnum[][] theaterArray = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> theaterPatternList= new ArrayList<>();

    public Theater(ArrayList<Building> b, int r, int c) {
        row = r;
        col = c;
        buildingsOnBoard = new ArrayList<>(b);
        condition = false;
    }
    static {
        theaterArray[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.STONE,  ResourceEnum.NONE};
        theaterArray[1] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.GLASS, ResourceEnum.WOOD};
        patternBuilder(theaterArray, theaterPatternList);
    }
    public Theater() {
        row = -1;
        col = 1;
    }

    public BuildingEnum getType() {
        return BuildingEnum.THEATER;
    }

    public boolean getCondition() {
        return condition;
    }

    @Override
    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    public String toString() {
        return "Theater";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return theaterPatternList;
    }
    public void printManualText() {
        System.out.println("The Theater grants one point for each unique building in it's row and column.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(theaterArray);
    }
    public int scorer(Building[][] bArray) {
        int score = 0;
        for (Building building : Utility.getBuildingsInRowAndColumn(bArray, row, col)) {
            score += buildingMatch(building, this);
        }
        return score;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {

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

