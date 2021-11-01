package TownBuilder.Buildings;

import TownBuilder.Board;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

public class AGuild implements Monument{
    private static final ResourceEnum[][] pattern = new ResourceEnum[3][3];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private final Board board;
    private boolean condition;

    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.GLASS};
        pattern[1] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.WHEAT, ResourceEnum.STONE};
        pattern[2] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.BRICK, ResourceEnum.NONE};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public AGuild(int r, int c, Board b) {
        row = r;
        col = c;
        board = b;
        condition = false;
    }
    @Override
    public String toString() {
        return "Architect's Guild";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.AGUILD;
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
        return 1;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        //
    }

    @Override
    public void printManualText() {
        System.out.println("The Architect's Guild is worth one point. \nWhen built, you may place two buildings of your choice.");
        System.out.println("Here's what it look's like:");
        Utility.arrayPrinter(pattern);
    }

    @Override
    public void onPlacement() {
        for (int i = 0; i < 2; i++) {
            board.buildingPlacer();
        }
    }
}
