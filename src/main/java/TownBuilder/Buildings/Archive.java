package TownBuilder.Buildings;

import TownBuilder.Board;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class Archive implements Monument{
    private static final ResourceEnum[][] pattern = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private final Board board;
    private final ArrayList<Building> masterBuildings;
    private boolean condition;
    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.WHEAT};
        pattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.GLASS};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public Archive(int r, int c, Board b, ArrayList<Building> m) {
        row = r;
        col = c;
        condition = false;
        board = b;
        masterBuildings = m;
    }

    @Override
    public String toString() {
        return "Archive of the Second Age";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.ARCHIVE;
    }

    @Override
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

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) throws IOException {
        int score = 0;
        for (Building[] buildingRow : bArray) {
            for (Building building : buildingRow) {
                if(building.getType() != BuildingEnum.NONE) {
                    DebugTools.logging("Archive Scoring: searching buildings in board Current building: " + DebugTools.buildingInformation(building));
                    score += buildingMatch(building);
                }
            }
        }
        return score;
    }
    private int buildingMatch(Building comparisonBuilding) throws IOException {
        int score = 0;
        DebugTools.logging("Archive Scoring: Sending " + comparisonBuilding.getType() + " to buildingMatch()");
        ArrayList<Building> buildings = new ArrayList<>(masterBuildings);
        DebugTools.logging("Archive Scoring: Size of buildings:"+buildings.size());
        for (Building building : buildings) {
            DebugTools.logging("Archive Scoring: Checking " + DebugTools.buildingInformation(building) + " of master buildings list.");
            if (comparisonBuilding.getType() == building.getType()) {
                if (!comparisonBuilding.equals(this) && !building.getCondition()) {
                    DebugTools.logging("Archive Scoring: " + building.getType() + " of master buildings list is the same as " + comparisonBuilding.getType()  +
                            "");
                    building.setCondition(true);
                    score++;
                }

            }
        }
        return score;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("The Archive of the Second Age earns 1 point for each unique building\n(not including itself) on the board.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(pattern);
    }

    @Override
    public void onPlacement() {
        // nothing
    }
}
