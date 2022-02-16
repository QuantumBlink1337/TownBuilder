package TownBuilder.Buildings;

import TownBuilder.Board;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

import static TownBuilder.Buildings.BuildingFactory.patternBuilder;

public class Bank implements Building{
    private static final ResourceEnum[][] bankPattern = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> bankPatternList = new ArrayList<>();
    private boolean condition;
    private final int row;
    private final int col;
    private final Board board;
    private ResourceEnum lockedResource;
    static {
        bankPattern[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.WHEAT, ResourceEnum.NONE};
        bankPattern[1] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.GLASS, ResourceEnum.BRICK};
        patternBuilder(bankPattern, bankPatternList);
    }
    public Bank(int r, int c, boolean playerMade, Board board) throws IOException {
        row = r;
        col = c;
        this.board = board;
        condition = false;
        if (playerMade) {
            setLockedResource();
        }
    }
    private void setLockedResource() throws IOException {
        board.getBoardUI().setResourceSelectionLabel("You must declare a blacklisted resource for your Bank.");
        lockedResource = Utility.resourcePicker(board.getBlacklistedResources(), board, "");
    }
    public ResourceEnum getLockedResource() {
        return lockedResource;
    }

    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return bankPatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.BANK;
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
        return "Bank";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) {
        return 4;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public String getManualEntry() {
        return "The Bank earns four points. When you place it, you must choose a resource. You may no longer choose that resource when it is your turn to pick a resource.";
    }
}
