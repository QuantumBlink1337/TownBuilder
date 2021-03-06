package TownBuilder.Buildings;

import TownBuilder.Board;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class Factory implements Building{
    private static final ResourceEnum[][] factoryPattern = new ResourceEnum[2][4];
    private static final ArrayList<ResourceEnum[][]> factoryPatternList = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;
    private final Board board;
    private ResourceEnum factorizedResource;
    static {
        factoryPattern[0] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.NONE};
        factoryPattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.STONE, ResourceEnum.STONE, ResourceEnum.BRICK};
        BuildingFactory.patternBuilder(factoryPattern, factoryPatternList);
        // factoryPattern[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.WOOD};
        // factoryPattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.STONE, ResourceEnum.STONE, ResourceEnum.BRICK};
        // BuildingFactory.patternBuilder(factoryPattern, factoryPatternList, 3);
    }
    public Factory(int r, int c, boolean playerMade, Board board) throws IOException {
        row = r;
        col = c;
        this.board = board;
        condition = false;
        if (playerMade) {
            pickResource();
        }
        else {
            factorizedResource = ResourceEnum.NONE;
        }
    }
    private void pickResource() throws IOException {
        board.getBoardUI().setResourceSelectionLabel("Select a resource for your new Factory.");
        factorizedResource = Utility.resourcePicker(null, board, "");
    }
    public ResourceEnum exchangeResource() throws IOException {
        board.getBoardUI().setResourceSelectionLabel("Select a resource.");
        return Utility.resourcePicker(null, board, "");
    }
    public ResourceEnum getFactorizedResource() {
        return factorizedResource;
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return factoryPatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.FACTORY;
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
        return false;
    }

    @Override
    public void setFedStatus(boolean condition) {

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
        return "Factory";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) {
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public String getManualEntry() {
        return "When you place a Factory, you must also select a resource for it to use.\nIf your selected resource is the one given to you this turn, you may choose another resource.";
    }
}
