package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

import static TownBuilder.Buildings.BuildingFactory.patternBuilder;
import static TownBuilder.Buildings.BuildingFactory.setbuildingMasterList;

public class Bank implements Building{
    private static final ResourceEnum[][] bankPattern = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> bankPatternList = new ArrayList<>();
    private boolean condition;
    private final int row;
    private final int col;
    private ResourceEnum lockedResource;
    static {
        bankPattern[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.WHEAT, ResourceEnum.NONE};
        bankPattern[1] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.GLASS, ResourceEnum.BRICK};
        patternBuilder(bankPattern, bankPatternList, 3);
    }
    public Bank(int r, int c, boolean playerMade) {
        row = r;
        col = c;
        condition = false;
        if (playerMade) {
            setLockedResource();
        }
    }
    private void setLockedResource() {
        System.out.println("You must declare a resource to be the Bank's type.\nOnce you do this, you may no longer ask for it. ");
        lockedResource = ResourceEnum.resourcePicker(null);
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
    public int scorer(Building[][] bArray, int scoreIncrement) {
        return 4;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("The Bank earns four points. When you place it, you must choose a resource. You may no longer\nchoose that resource when it is your turn to pick.");
        System.out.println("Note: You cannot make more than four banks. The game will refuse to recognize bank resource patterns beyond the fourth one.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(bankPattern);
    }
}