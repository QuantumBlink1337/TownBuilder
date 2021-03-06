package TownBuilder.Buildings.Monuments;

import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;

import java.io.IOException;
import java.util.ArrayList;

public class BarrettCastle implements Monument {
    private static final ResourceEnum[][] pattern = new ResourceEnum[2][4];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;
    private boolean isFed;

    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.WOOD};
        pattern[1] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.GLASS, ResourceEnum.GLASS, ResourceEnum.BRICK};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public BarrettCastle(int r, int c) {
        row = r;
        col = c;
        condition = false;
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
    public boolean getFedStatus() {
        return isFed;
    }

    @Override
    public void setFedStatus(boolean condition) {
        isFed = condition;
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
    public int scorer(Building[][] bArray) throws IOException {
        DebugTools.logging("[BARRETT_CASTLE] - Beginning Scoring Process.");
        return condition ? 5 : 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        //
    }

    @Override
    public String getManualEntry() {
        return "Five points if fed. Barrett Castle counts as two Blue buildings for scoring purposes.";
    }

    @Override
    public void onPlacement() {
       // DebugTools.logging("Barrett Castle: Removed from Scorable List", 2);
        //board.getScorableBuildings().remove(board.getScorableBuildings().size()-1);
        //DebugTools.printMembersOfArrayList(board.getScorableBuildings(), 3, "Barrett Castle: Showing members of scorable buildings");
    }
}
