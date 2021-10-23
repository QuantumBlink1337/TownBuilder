package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

public class Abbey implements Building{
    private static final ResourceEnum[][] abbeyPattern = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> abbeyPatternList = new ArrayList<>();
    private boolean condition;
    private int row;
    private int col;

    static {
        abbeyPattern[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.GLASS};
        abbeyPattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.STONE, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(abbeyPattern, abbeyPatternList, 3);
        abbeyPattern[0] = new ResourceEnum[]{ResourceEnum.GLASS, ResourceEnum.NONE, ResourceEnum.NONE};
        abbeyPattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.STONE, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(abbeyPattern, abbeyPatternList, 3);
    }

    public Abbey(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }

    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return abbeyPatternList;
    }

    @Override
    public String toString() {
        return "Abbey";
    }
    public BuildingEnum getType() {
        return BuildingEnum.ABBEY;
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
    public String getColor() {
        return "orange";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray, int scoreIncrement) {
        try {
            if ((bArray[row][col-1].getColor().equals("green")|| bArray[row][col-1].getColor().equals("yellow") || bArray[row][col-1].getColor().equals("black"))) {
                return 0;
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            if ((bArray[row-1][col].getColor().equals("green")|| bArray[row-1][col].getColor().equals("yellow") || bArray[row-1][col].getColor().equals("black"))) {
                return 0;
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            if ((bArray[row][col+1].getColor().equals("green")|| bArray[row][col+1].getColor().equals("yellow") || bArray[row][col+1].getColor().equals("black"))) {
                return 0;
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            if ((bArray[row+1][col].getColor().equals("green")|| bArray[row+1][col].getColor().equals("yellow") || bArray[row+1][col].getColor().equals("black"))) {
                return 0;
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        return 3;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("The Abbey grants 3 points if it's not adjacent to a Green, Yellow, or Black building.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(abbeyPatternList.get(0));
    }
}
