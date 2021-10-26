package TownBuilder.Buildings;

import TownBuilder.ColorEnum;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

import static TownBuilder.Buildings.BuildingFactory.patternBuilder;

public class FeastHall implements Building{
    private static final ResourceEnum[][] feastArray = new ResourceEnum[1][3];
    private static final ArrayList<ResourceEnum[][]> feastPatternList = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;


    static {
        feastArray[0] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.BRICK, ResourceEnum.GLASS};
        patternBuilder(feastArray, feastPatternList, 3);
    }
    public FeastHall(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    @Override
    public String toString() {
        return "Feast Hall";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return feastPatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.FEASTHALL;
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
        return false;
    }

    @Override
    public int scorer(Building[][] bArray, int scoreIncrement) {
        int score = 0;
        for (Building building : Utility.getAdjacentBuildings(bArray, row, col)) {
            if (building.getType().getColor() == ColorEnum.YELLOW) {
                score++;
            }
        }
        return score;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("The Feast Hall earns one point per Yellow building adjacent to it.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(feastArray);
    }
}