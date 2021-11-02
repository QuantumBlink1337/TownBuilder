package TownBuilder.Buildings;

import TownBuilder.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;
import static TownBuilder.Buildings.BuildingFactory.patternBuilder;


public class Well implements Building {
    private final boolean condition;
    private final int row;
    private final int col;
    private final String color = "gray";
    private static final ResourceEnum[][] wellArray = new ResourceEnum[2][1];
    private static final ResourceEnum[][] wellArrayMirror = new ResourceEnum[2][1];
    private static final ArrayList<ResourceEnum[][]> wellPatternList = new ArrayList<>();

    public Well(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    static {
        wellArray[0][0] = ResourceEnum.WOOD;
        wellArray[1][0] = ResourceEnum.STONE;
        patternBuilder(wellArray, wellPatternList);
        // wellArrayMirror[0][0] = ResourceEnum.STONE;
        // wellArrayMirror[1][0] = ResourceEnum.WOOD;
        // patternBuilder(wellArrayMirror, wellPatternList, 3);
    }
    public BuildingEnum getType() {
        return BuildingEnum.WELL;
    }
    public boolean getCondition() {
        return condition;
    }

    @Override
    public void setCondition(boolean condition) {

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

    public void printManualText() {
        System.out.println("The Well grants one point for each adjacent Cottage.");
        System.out.println("Note: these Cottages do not need to be fed. Note: diagonals do not count.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(wellArray);
    }
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return wellPatternList;
    }
    public int scorer(Building[][] bArray) {
        int score = 0;
        DebugTools.logging(Utility.generateColorizedString("Beginning "+ this+" scoring protocol.", this.getType()), 1);
        for (Building building : Utility.getAdjacentBuildings(bArray, row, col)) {
            DebugTools.logging("Well Scoring: Searching adjacent buildings for Cottage. Current building: " + DebugTools.buildingInformation(building), 3);
            if (building.getType() == BuildingEnum.COTTAGE) {
                score++;
                DebugTools.logging("Well Scoring: Cottage found. Information: " + DebugTools.buildingInformation(building) + "Incrementing score. Current score: "+ score, 2);
            }
            else if (building.getType() == BuildingEnum.BARRETT) {
                score+=2;
                DebugTools.logging("Well Scoring: Barrett Castle found. Information: " + DebugTools.buildingInformation(building) + "Incrementing score. Current score: "+ score, 2);

            }
        }
        return score;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        //
    }

}
