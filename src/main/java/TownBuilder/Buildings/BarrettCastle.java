package TownBuilder.Buildings;

import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

public class BarrettCastle implements Monument{
    private static final ResourceEnum[][] pattern = new ResourceEnum[2][4];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;

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
        DebugTools.logging(Utility.generateColorizedString("Beginning "+ this+" scoring protocol.", this.getType()), 1);
        return condition ? 5 : 0;
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
       // DebugTools.logging("Barrett Castle: Removed from Scorable List", 2);
        //board.getScorableBuildings().remove(board.getScorableBuildings().size()-1);
        //DebugTools.printMembersOfArrayList(board.getScorableBuildings(), 3, "Barrett Castle: Showing members of scorable buildings");
    }
}
