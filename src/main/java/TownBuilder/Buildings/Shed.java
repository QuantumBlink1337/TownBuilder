package TownBuilder.Buildings;
import TownBuilder.ResourceEnum;

import java.util.ArrayList;

import static TownBuilder.Buildings.BuildingFactory.patternBuilder;

public class Shed implements Building {
    private boolean condition;
    private final int row;
    private final int col;
    private static final ResourceEnum[][] pattern = new ResourceEnum[1][2];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();

    public Shed(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    static {
        pattern[0][0] = ResourceEnum.WOOD;
        pattern[0][1] = ResourceEnum.STONE;
        patternBuilder(pattern, patternList);;
    }
    public BuildingEnum getType() {
        return BuildingEnum.SHED;
    }
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

    public String toString() {
        return "Shed";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public String getManualEntry() {
        return "Grants one point. May be placed on any empty square on your board.";
    }

    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }
    public int scorer(Building[][] bArray) {
        return 1;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        //
    }
}
