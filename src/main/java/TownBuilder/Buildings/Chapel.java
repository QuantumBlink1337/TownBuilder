package TownBuilder.Buildings;

import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

import static TownBuilder.Buildings.BuildingFactory.patternBuilder;

public class Chapel implements Building {
    private boolean condition;
    private final boolean FEEDABLE = false;
    private final int row;
    private final int col;
    private static final ResourceEnum[][] templeArray = new ResourceEnum[2][3];
    // private static final ResourceEnum[][] templeArrayMirror = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> templeList = new ArrayList<>();

    public Chapel(int r, int c) {
        condition = false;
        row = r;
        col = c;
    }
    static {
        templeArray[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.GLASS};
        templeArray[1] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.GLASS, ResourceEnum.STONE};
        patternBuilder(templeArray, templeList);
    }
    public BuildingEnum getType() {
        return BuildingEnum.CHAPEL;
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
        return "Chapel";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    public void printManualText() {
        System.out.println("The Chapel grants one point for each fed Cottage.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(templeArray);
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return templeList;
    }
    public int scorer(Building[][] bArray) throws IOException {

        // decided not to simplify this because the runtime would be greater since we do different things depending on what is found
        int score = 0;
        DebugTools.logging("[CHAPEL] - SCORING: Beginning scoring protocol.");
        for (Building[] buildings : bArray) {
            for (Building building : buildings) {
                DebugTools.logging("[CHAPEL] - SCORING: Checking " + DebugTools.buildingInformation(building));
                if (building.getType() == BuildingEnum.COTTAGE && building.getCondition()) {
                    score += 1;
                    DebugTools.logging("[CHAPEL] - SCORING: Found " + DebugTools.buildingInformation(building)+ " adding 1 to score. Score: " + score);
                }
                else if (building.getType() == BuildingEnum.BARRETT && building.getCondition()) {
                    score+=2;
                    DebugTools.logging("[CHAPEL] - SCORING: Found " + DebugTools.buildingInformation(building)+ " adding 2 to score. Score: " + score);
                }
            }
        }
        return score;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // no actions taken in between turns
    }
}