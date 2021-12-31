package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;
import java.util.ArrayList;

public class Caterina implements Monument {
    private static final ResourceEnum[][] pattern = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;
    private Board board;
    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.WHEAT};
        pattern[1] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.GLASS};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public Caterina(int r, int c, Board b) {
        row = r;
        col = c;
        board = b;
    }
    @Override
    public String toString() {
        return "Cathedral of Caterina";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.CATERINA;
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
    public int scorer(Building[][] bArray) {
        return 2;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("The Cathedral of Caterina is worth 2 points. Tiles with resources\non them are worth 0 points instead of -1.");
        System.out.println("Here's what it looks like:");
        Utility.printFormattedResourcePattern(pattern);
    }

    @Override
    public String getManualEntry() {
        return "The Cathedral of Caterina is worth 2 points. Tiles with resources on them are worth 0 points instead of -1.";
    }

    @Override
    public void onPlacement() {
        // nothing
    }
}
