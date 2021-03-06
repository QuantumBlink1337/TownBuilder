package TownBuilder.Buildings;

import TownBuilder.BoardTraverser;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import static TownBuilder.Buildings.BuildingFactory.patternBuilder;


import java.io.IOException;
import java.util.ArrayList;

public class Theater implements Building
{
    private boolean condition;
    private final int row;
    private final int col;
    private ArrayList<Building> buildingsOnBoard;
    private static final ResourceEnum[][] theaterArray = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> theaterPatternList= new ArrayList<>();
    private static final BuildingEnum[] buildingExceptions = new BuildingEnum[]{BuildingEnum.BARRETT};
    static {
        theaterArray[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.STONE,  ResourceEnum.NONE};
        theaterArray[1] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.GLASS, ResourceEnum.WOOD};
        patternBuilder(theaterArray, theaterPatternList);
    }
    public Theater(ArrayList<Building> b, int r, int c) {
        row = r;
        col = c;
        buildingsOnBoard = new ArrayList<>(b);
        condition = false;
    }
    public Theater() {
        row = -1;
        col = 1;
    }
    /*
        If the enum is in the blacklist list, return true. Otherwise,
        return false.
     */
    private BuildingEnum checkBlacklist(BuildingEnum buildingEnum) {
        for (BuildingEnum blacklistedEnum : buildingExceptions) {
            if (blacklistedEnum == buildingEnum) {
                return buildingEnum;
            }
        }
        return BuildingEnum.NONE;
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
    public boolean getFedStatus() {
        return false;
    }

    @Override
    public void setFedStatus(boolean condition) {

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

    @Override
    public String getManualEntry() {
        return "The Theater grants one point for each unique building in it's row and column.";
    }

    public int scorer(Building[][] bArray) throws IOException {
        int score = 0;
        /*
            Use the checkBlacklist function to verify that Monument Barrett is not on the board (it is an edge case - it counts as two buildings!)
         */
        DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - SCORING: Beginning scoring protocol.");
        score += BoardTraverser.findUniqueBuildingsInGivenList(BoardTraverser.getBuildingsInRowAndColumn(bArray, row, col), this::checkBlacklist, buildingsOnBoard);

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
}

