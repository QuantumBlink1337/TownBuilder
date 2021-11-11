package TownBuilder.Buildings;

import TownBuilder.ColorEnum;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Arrays;

import static TownBuilder.Buildings.BuildingFactory.patternBuilder;

public class Tailor implements Building{

    private boolean condition;
    private final int row;
    private final int col;
    private static final ResourceEnum[][] tailorArray = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> tailorPatternList = new ArrayList<>();
    static {
        tailorArray[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.WHEAT,  ResourceEnum.NONE};
        tailorArray[1] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.GLASS, ResourceEnum.STONE};
        patternBuilder(tailorArray, tailorPatternList);
    }
    public Tailor(int r, int c) {
        row = r;
        col =c;
        condition = false;
    }

    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return tailorPatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.TAILOR;
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
    public String toString() {
        return "Tailor";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) {
        int score = 1;
        ArrayList<Building> buildings = new ArrayList<>(Arrays.asList(Utility.getBuildingsInCorner(bArray)));
        buildings.removeIf(building -> building.getRow() == row && building.getCol() == col);
        score+=Utility.instancesOfBuilding(buildings.toArray(new Building[]{}), ColorEnum.YELLOW);
        return score;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("The Tailor is worth one point. The Tailor also earns one point per Tailor in a corner.");
        System.out.println("Note: the Tailor is always worth one point, even if not in a corner.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(tailorArray);
    }
}
