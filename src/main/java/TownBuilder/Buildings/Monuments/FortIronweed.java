package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

public class FortIronweed implements Monument {
    private static final ResourceEnum[][] pattern = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;
    private final Board board;
    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.NONE,ResourceEnum.BRICK};
        pattern[1] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.WOOD, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public FortIronweed(int r, int c, Board b) {
        row = r;
        col = c;
        board =b;
        condition = false;

    }

    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.IRONWEED;
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
    public String toString() {
        return "Fort Ironweed";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) {
        return 7;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("Earns 7 points. You may not choose a resource for the rest of the game,\n unless you are the only player left.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(pattern);

    }

    @Override
    public void onPlacement() {
        board.setCanBeMasterBuilder(false);
    }
}
