package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class Rodina implements Monument{
    private static final ResourceEnum[][] pattern = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private final Board board;
    private boolean condition;
    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.WOOD};
        pattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public Rodina(int r, int c, Board b) {
        row = r;
        col = c;
        board = b;
        condition = false;
    }
    @Override
    public String toString() {
        return "Grand Mausoleum of the Rodina";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.RODINA;
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
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) throws IOException {
        int score = 0;
        for (Building[] buildings : bArray) {
            for (Building building : buildings) {
                if (building.getType() == BuildingEnum.COTTAGE && !building.getCondition()) {
                    score++;
                }
            }
        }
        return score;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) throws IOException {

    }

    @Override
    public void printManualText() {
        System.out.println("3 Points for each unfed cottage.");
        System.out.println("Here's what it looks like:");
        Utility.printFormattedResourcePattern(pattern);
    }

    @Override
    public void onPlacement() throws IOException {

    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }
}
