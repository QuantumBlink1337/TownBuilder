package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

public class Orchard implements Building{
    private boolean condition;
    private static final ResourceEnum[][] orchardArray = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> orchardPatternList = new ArrayList<>();
    static {
        orchardArray[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.WHEAT};
        orchardArray[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.WOOD};
        BuildingFactory.patternBuilder(orchardArray, orchardPatternList, 3);
        orchardArray[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.WHEAT};
        orchardArray[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.WOOD};
        BuildingFactory.patternBuilder(orchardArray, orchardPatternList, 3);

    }
    public Orchard() {
        condition = false;
    }
    @Override
    public BuildingEnum getType() {
        return BuildingEnum.ORCHARD;
    }

    @Override
    public boolean getCondition() {
        return condition;
    }

    @Override
    public void setCondition(boolean condition) {
        this.condition = condition;
    }
    public String toString() {
        return "Orchard";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return orchardPatternList;
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard, int row, int col) {
        /*
            when performing maintenance, assuming that Orchard is at 1,1
         */


        // 0,0
        try {
            if (buildingBoard[row-1][col-1].isFeedable() && !buildingBoard[row-1][col-1].getCondition()) {
                buildingBoard[row-1][col-1].setCondition(true);
                System.out.println("Row: "+(row-1)+ "Col: "+(col-1)+ " is fed!");
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        // 0,1
        try {
            if (buildingBoard[row-1][col].isFeedable() && !buildingBoard[row-1][col].getCondition()) {
                buildingBoard[row-1][col].setCondition(true);
                System.out.println("Row: "+(row-1)+ "Col: "+(col)+ " is fed!");
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        // 0, 2
        try {
            if (buildingBoard[row-1][col+1].isFeedable() && !buildingBoard[row-1][col+1].getCondition()) {
                buildingBoard[row-1][col+1].setCondition(true);
                System.out.println("Row: "+(row-1)+ "Col: "+(col+1)+ " is fed!");
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        // 1,0
        try {
            if (buildingBoard[row][col-1].isFeedable() && !buildingBoard[row][col-1].getCondition()) {
                buildingBoard[row][col-1].setCondition(true);
                System.out.println("Row: "+(row)+ "Col: "+(col-1)+ " is fed!");
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        // 1, 2
        try {
            if (buildingBoard[row][col+1].isFeedable() && !buildingBoard[row][col+1].getCondition()) {
                buildingBoard[row][col+1].setCondition(true);
                System.out.println("Row: "+(row)+ "Col: "+(col+1)+ " is fed!");
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        // 2, 0
        try {
            if (buildingBoard[row+1][col-1].isFeedable() && !buildingBoard[row+1][col-1].getCondition()) {
                buildingBoard[row+1][col-1].setCondition(true);
                System.out.println("Row: "+(row-1)+ "Col: "+(col+1)+ " is fed!");
            }
            else {
                System.out.println("Not fed...");
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        // 2, 1
        try {
            if (buildingBoard[row+1][col].isFeedable() && !buildingBoard[row+1][col].getCondition()) {
                buildingBoard[row+1][col].setCondition(true);
                System.out.println("Row: "+(row)+ "Col: "+(col+1)+ " is fed!");
            }
            else {
                System.out.println("Not fed...");
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}
        // 2,2
        try {
            if (buildingBoard[row+1][col+1].isFeedable() && !buildingBoard[row+1][col+1].getCondition()) {
                buildingBoard[row+1][col+1].setCondition(true);
                System.out.println("Row: "+(row+1)+ "Col: "+(col+1)+ " is fed!");
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored) {}

    }

    @Override
    public void printManualText() {
        System.out.println("The Orchard feeds the tiles adjacent and diagonal to itself!");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(orchardPatternList.get(0));
    }
}
