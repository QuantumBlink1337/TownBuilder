package TownBuilder.Buildings;

import TownBuilder.Board;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BarrettCastle implements Monument{
    private static final ResourceEnum[][] pattern = new ResourceEnum[2][4];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;
    private final Board board;

    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.WOOD};
        pattern[1] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.GLASS, ResourceEnum.GLASS, ResourceEnum.BRICK};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public BarrettCastle(int r, int c, Board b) {
        row = r;
        col = c;
        condition = false;
        board = b;
    }
    public String toString() {
        return "Barrett Castle";
    }

    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.BARRETT;
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
        return true;
    }

    @Override
    public int scorer(Building[][] bArray) {
        if (condition) {
            return 5;
        }
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        //
    }

    @Override
    public void printManualText() {
        System.out.println("Five points if fed. Barrett Castle counts as two Blue buildings\nfor scoring purposes.");
        Utility.arrayPrinter(pattern);
    }

    @Override
    public void onPlacement() {
        board.getScorableBuildings().remove(this);
    }
}
