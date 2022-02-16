package TownBuilder.Buildings;

import TownBuilder.BoardTraverser;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;
import static TownBuilder.Buildings.BuildingFactory.patternBuilder;


public class Well implements Building {
    private boolean condition;
    private final int row;
    private final int col;
    private static final ResourceEnum[][] wellArray = new ResourceEnum[1][2];
    private static final ArrayList<ResourceEnum[][]> wellPatternList = new ArrayList<>();

    public Well(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    static {
        wellArray[0][0] = ResourceEnum.WOOD;
        wellArray[0][1] = ResourceEnum.STONE;
        patternBuilder(wellArray, wellPatternList);
    }
    public BuildingEnum getType() {
        return BuildingEnum.WELL;
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
        return "Well";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public String getManualEntry() {
        return "The Well grants one point for each adjacent Cottage.\nNote: these Cottages do not need to be fed. Note: diagonals do not count.";
    }

    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return wellPatternList;
    }
    public int scorer(Building[][] bArray) throws IOException {
        DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - SCORING: Beginning scoring protocol.");
        return BoardTraverser.instancesOfBuilding(BoardTraverser.getAdjacentBuildings(bArray, row, col), BuildingEnum.COTTAGE) + (2*BoardTraverser.instancesOfBuilding(BoardTraverser.getAdjacentBuildings(bArray, row, col), BuildingEnum.BARRETT));

    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        //
    }

}
