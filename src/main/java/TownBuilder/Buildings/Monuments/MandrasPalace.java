package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.BoardTraverser;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.Buildings.Monument;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class MandrasPalace implements Monument {
    private static final ResourceEnum[][] pattern = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private final Board board;
    private boolean condition;
    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.GLASS};
        pattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.WOOD};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public MandrasPalace(int r, int c, Board b) {
        row = r;
        col = c;
        board = b;
        condition = false;
    }
    @Override
    public String toString() {
        return "Mandras Palace";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.MANDRAS;
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
        DebugTools.logging("["+ Utility.lengthResizer(this.getType().toString(), 9)+"] - SCORING: Beginning scoring protocol.");
        return 2 * BoardTraverser.findUniqueBuildingsInGivenList(BoardTraverser.getAdjacentBuildings(bArray,row, col), null, board.getScorableBuildings());
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) throws IOException {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("2 points for each unique adjacent building.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(pattern);
    }

    @Override
    public void onPlacement() throws IOException {
        // nothing
    }
}
