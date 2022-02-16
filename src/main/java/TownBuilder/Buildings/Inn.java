package TownBuilder.Buildings;

import TownBuilder.BoardTraverser;
import TownBuilder.ResourceEnum;

import java.io.IOException;
import java.util.ArrayList;

import static TownBuilder.Buildings.BuildingFactory.patternBuilder;

public class Inn implements Building{
    private boolean condition;
    private final int row;
    private final int col;
    private static final ResourceEnum[][] innArray = new ResourceEnum[1][3];
    private static final ArrayList<ResourceEnum[][]> innPatternList = new ArrayList<>();

    static {
        innArray[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.STONE, ResourceEnum.GLASS};
        patternBuilder(innArray, innPatternList);
    }
    public Inn(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return innPatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.INN;
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

    public String toString() {
        return "Inn";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) throws IOException {
        return BoardTraverser.searchForBuilding(BoardTraverser.getBuildingsInRowAndColumn(bArray, row, col), BuildingEnum.INN) ? 0 : 3;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public String getManualEntry() {
        return "The Inn earns 3 points as long as there is no other Inn in the same row and column.";
    }
}
