package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.BoardTraverser;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class Archive implements Monument {
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
        DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - SCORING: Beginning scoring protocol.");
        return BoardTraverser.findUniqueBuildingsInGivenList(bArray, null, masterBuildings);
    }


    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("The Archive of the Second Age earns 1 point for each unique building\n(not including itself) on the board.");
        System.out.println("Here's what it looks like:");
        Utility.printFormattedResourcePattern(pattern);
    }

    @Override
    public String getManualEntry() {
        return "The Archive of the Second Age earns 1 point for each unique building\n(not including itself) on the board.";
    }

    @Override
    public void onPlacement() {
        // nothing
    }
}
