package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.ResourceEnum;

import java.io.IOException;
import java.util.ArrayList;

public class Shrine implements Monument {
    private static final ResourceEnum[][] pattern = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private final Board board;
    private int score;
    private boolean condition;
    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.WHEAT, ResourceEnum.STONE};
        pattern[1] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.GLASS, ResourceEnum.WOOD};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public Shrine(int r, int c, Board b) throws IOException {
        row = r;
        col = c;
        board = b;
        condition = false;

    }
    @Override
    public String toString() {
        return "Shrine of the Elder Tree";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.SHRINE;
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
    public boolean getFedStatus() {
        return false;
    }

    @Override
    public void setFedStatus(boolean condition) {

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
        return score;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) throws IOException {
        // nothing
    }

    @Override
    public String getManualEntry() {
        return "Grants points based on how many buildings were on your board when you built Shrine of the Elder Tree.\n1: 1 Points\n2: 2 Points\n3: 3 Points\n4: 4 Points\n5: 5 Points\n6: 8 Points\nNote: If this is the 7th or more building you've built, it still only earns 8 points.";
    }

    @Override
    public void onPlacement() throws IOException {
        for (Building[] buildings : board.getGameBuildingBoard()) {
            for (Building building : buildings) {
                if (building.getType() != BuildingEnum.NONE) {
                    score++;
                }
                if (score > 7) {
                    score = 8;
                    return;
                }
            }
        }

    }
}
