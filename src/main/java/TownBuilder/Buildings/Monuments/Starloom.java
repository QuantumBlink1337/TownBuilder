package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.ResourceEnum;

import java.io.IOException;
import java.util.ArrayList;

public class Starloom implements Monument{
    private static final ResourceEnum[][] pattern = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private final Board board;
    private boolean condition;
    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.GLASS, ResourceEnum.GLASS};
        pattern[1] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.WHEAT};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public String toString() {
        return "The Starloom";
    }
    public Starloom(int r, int c, Board b) {
        row = r;
        col = c;
        board = b;
        condition = false;
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.STARLOOM;
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
        switch (board.getBoardFinishPlace()) {
            case 1 -> {
                return 6;
            }
            case 2 -> {
                return 3;
            }
            case 3 -> {
                return 2;
            }
            default -> {
                return 0;
            }
        }
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) throws IOException {
        //
    }

    @Override
    public String getManualEntry() {
        return "Grants points based on when you finish your board:\n1st: 6 Points\n2nd: 3 Points\n3rd: 2 Points\n4th+: 0 Points\n";
    }

    @Override
    public void onPlacement() throws IOException {
        //
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
