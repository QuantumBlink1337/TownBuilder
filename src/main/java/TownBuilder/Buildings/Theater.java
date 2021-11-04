package TownBuilder.Buildings;

import TownBuilder.DebugTools;
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

        DebugTools.logging(Utility.generateColorizedString("Beginning "+ this+" scoring protocol.", this.getType()), 1);
        for (Building building : Utility.getBuildingsInRowAndColumn(bArray, row, col)) {

            DebugTools.logging("Theater Scoring: searching buildings in common row/column. Current building: " + DebugTools.buildingInformation(building), 3);
            if (building.getType() != BuildingEnum.NONE) {
                score += buildingMatch(building, this);
            }
        }
        // I don't know why I have to do this but I do apparently
        for (Building building : buildingsOnBoard) {
            building.setCondition(false);
        }
        return score;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        //
    }

    private int buildingMatch(Building toBeChecked, Building scoreTarget) {
        int score = 0;
        DebugTools.logging("Theater Scoring: Sending " + toBeChecked.getType() + " to buildingMatch()", 3);
        ArrayList<Building> buildings = new ArrayList<>(buildingsOnBoard);
        for (Building building : buildings) {
            DebugTools.logging("Theater Scoring: Checking " + DebugTools.buildingInformation(building) + " of master buildings list.", 3);
            if (toBeChecked.getType() == BuildingEnum.BARRETT && !buildings.get(0).getCondition()) {
                buildings.get(0).setCondition(true);
                score++;
            }
            else if (toBeChecked.getType() == building.getType()) {
                if (!toBeChecked.equals(scoreTarget) && !building.getCondition()) {
                    DebugTools.logging("Theater Scoring: " + building.getType() + " of master buildings list is the same as " + toBeChecked.getType()  +
                            "", 2);
                    building.setCondition(true);
                    score++;
                }

            }
        }
        return score;
    }

}

