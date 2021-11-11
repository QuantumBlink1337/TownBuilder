package TownBuilder.Buildings;

import TownBuilder.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;
import static TownBuilder.Buildings.BuildingFactory.patternBuilder;

public class Cottage implements Building {
    private boolean condition;
    private final int row;
    private final int col;
    private final String color = "blue";
    private static final ResourceEnum[][] cottageArray = new ResourceEnum[2][2];
    private static final ResourceEnum[][] cottageArrayMirror = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> cottagePatternList = new ArrayList<>();


    public Cottage(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    static {
        cottageArray[0] = new ResourceEnum[]{ResourceEnum.GLASS, ResourceEnum.WHEAT};
        cottageArray[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.NONE};
        patternBuilder(cottageArray, cottagePatternList);
    }
    public BuildingEnum getType() {
        return BuildingEnum.COTTAGE;
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
        return "Cottage";
    }

    @Override
    public boolean isFeedable() {
        return true;
    }

    public void printManualText() {
        System.out.println("The Cottage is a building grants three points when it is fed.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(cottageArray);
    }
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {

        return cottagePatternList;
    }
    public int scorer(Building[][] bArray) {
//        DebugTools.logging(Utility.generateColorizedString("Beginning "+ this+" scoring protocol.", this.getType()), 1);
//        DebugTools.logging("Cottage Score: checking " + DebugTools.buildingInformation(this), 1);
//        if (condition) {
//            DebugTools.logging("Cottage Score: condition is true, returning 3.", 2);
//            return 3;
//        }
//        DebugTools.logging("Cottage Score: condition is false, returning 0.", 2);

        return condition ? 3 : 0;
    }
    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

}
