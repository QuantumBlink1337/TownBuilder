package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class Obelisk implements Monument {
    private static final ResourceEnum[][] pattern = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;
    private final Board board;
    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.NONE,ResourceEnum.NONE};
        pattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.GLASS, ResourceEnum.BRICK};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public Obelisk(int r, int c, Board b) {
        row = r;
        col = c;
        board =b;
        condition = false;

    }
    @Override
    public String toString() {
        return "Obelisk of the Crescent";
    }
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.OBELISK;
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
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) throws IOException {
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) throws IOException {

    }

    @Override
    public void printManualText() {
        System.out.println("You may place all future buildings on any empty square on your board.");
        System.out.println("Here's what it looks like:");
        Utility.printFormattedResourcePattern(pattern);
    }

    @Override
    public String getManualEntry() {
        return "You may place all future buildings on any empty square on your board.";
    }

    @Override
    public void onPlacement() throws IOException {
        board.setPlaceAnywhere(true);
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }
}
