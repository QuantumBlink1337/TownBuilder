package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

public class Temple implements Building{
    public static ResourceEnum[][] templeArray = new ResourceEnum[2][2];
    public static ArrayList<ResourceEnum[][]> templePatternList = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;

    static {
        templeArray[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.GLASS};
        templeArray[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.BRICK, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(templeArray, templePatternList, 3);
        templeArray[0] = new ResourceEnum[]{ResourceEnum.GLASS, ResourceEnum.NONE, ResourceEnum.NONE};
        templeArray[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.BRICK, ResourceEnum.STONE};
        BuildingFactory.patternBuilder(templeArray, templePatternList, 3);
    }
    public Temple(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    public String toString() {
        return "Temple";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return templePatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.TEMPLE;
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
        return "blue";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray, int scoreIncrement) {
        Building building1;
        Building building2;
        Building building3;
        Building building4;
        try {
            building1 = bArray[row-1][col];
        }
        catch(ArrayIndexOutOfBoundsException ignored) {
            building1 = BuildingFactory.getBuilding(BuildingEnum.NONE, null, -1, -1);
        }
        try {
            building2 = bArray[row+1][col];
        }
        catch(ArrayIndexOutOfBoundsException ignored) {
            building2 = BuildingFactory.getBuilding(BuildingEnum.NONE, null, -1, -1);;
        }
        try {
            building3 = bArray[row][col-1];
        }
        catch(ArrayIndexOutOfBoundsException ignored) {
            building3 = BuildingFactory.getBuilding(BuildingEnum.NONE, null, -1, -1);;
        }
        try {
            building4 = bArray[row][col+1];
        }
        catch(ArrayIndexOutOfBoundsException ignored) {
            building4 = BuildingFactory.getBuilding(BuildingEnum.NONE, null, -1, -1);;
        }

        if (building1.getCondition() && building1.getColor().equals("blue")) {
            if (building2.getCondition() && building2.getColor().equals("blue")) {
                return 4;
            }
            else if (building3.getCondition() && building3.getColor().equals("blue")) {
                return 4;
            }
            else if (building4.getCondition() && building4.getColor().equals("blue")) {
                return 4;
            }
        }
        if (building2.getCondition() && building2.getColor().equals("blue")) {
            if (building1.getCondition() && building1.getColor().equals("blue")) {
                return 4;
            }
            else if (building3.getCondition() && building3.getColor().equals("blue")) {
                return 4;
            }
            else if (building4.getCondition() && building4.getColor().equals("blue")) {
                return 4;
            }
        }
        if (building3.getCondition() && building3.getColor().equals("blue")) {
            if (building2.getCondition() && building2.getColor().equals("blue")) {
                return 4;
            }
            else if (building1.getCondition() && building1.getColor().equals("blue")) {
                return 4;
            }
            else if (building4.getCondition() && building4.getColor().equals("blue")) {
                return 4;
            }
        }
        if (building4.getCondition() && building4.getColor().equals("blue")) {
            if (building2.getCondition() && building2.getColor().equals("blue")) {
                return 4;
            }
            else if (building3.getCondition() && building3.getColor().equals("blue")) {
                return 4;
            }
            else if (building1.getCondition() && building1.getColor().equals("blue")) {
                return 4;
            }
        }
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        // nothing
    }

    @Override
    public void printManualText() {
        System.out.println("The Temple earns 4 points if it's adjacent to two fed Cottages.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(getBuildingPatternsList().get(0));
    }


}
