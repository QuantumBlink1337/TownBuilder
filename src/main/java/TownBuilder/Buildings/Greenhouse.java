package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

public class Greenhouse implements Building{
    private final int row;
    private final int col;
    private boolean condition;
    private final String color = "red";
    private static final ResourceEnum[][] greenhouseArray = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> greenhousePatternList = new ArrayList<>();
    static {
        greenhouseArray[0] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.WOOD};
        greenhouseArray[1] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.GLASS};
        BuildingFactory.patternBuilder(greenhouseArray, greenhousePatternList, 3);
        greenhouseArray[0] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.WOOD};
        greenhouseArray[1] = new ResourceEnum[]{ResourceEnum.GLASS, ResourceEnum.WHEAT};
        BuildingFactory.patternBuilder(greenhouseArray, greenhousePatternList, 3);
    }

    public Greenhouse(int r, int c) {
        row = r;
        col =c;
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return greenhousePatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.GRENHOUSE;
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
        return "Greenhouse";
    }
    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray, int scoreIncrement) {
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {






    }
    private void contiguousCheck(int row, int col, Building[][] buildingBoard, ArrayList<Building> feedableBuildings) {
            for (int r = 0; r < buildingBoard.length; r++) {
                for (int c = 0; c < buildingBoard[r].length; c++) {
                    if (buildingBoard[r][c].isFeedable() && !buildingBoard[row][col].getCondition()) {
                        try {
                            if (buildingBoard[r+1][c].isFeedable() && !buildingBoard[r+1][c].getCondition()) {

                            }
                        }
                        catch (ArrayIndexOutOfBoundsException ignored){}
                        try {
                            if (buildingBoard[r][c+1].isFeedable() && !buildingBoard[r][c+1].getCondition()) {

                            }
                        }
                        catch (ArrayIndexOutOfBoundsException ignored) {}
                    }
                }
            }






    }
    @Override
    public void printManualText() {
        System.out.println("The Greenhouse feeds any contiguous group of buildings on the board.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(greenhousePatternList.get(0));
    }
}
