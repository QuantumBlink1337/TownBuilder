package TownBuilder.Buildings;

import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;
import static TownBuilder.Buildings.BuildingFactory.patternBuilder;

import java.io.IOException;
import java.util.ArrayList;

public class Warehouse implements Building {
    private boolean condition;
    private int fullness = 0;
    private final int row;
    private final int col;
    private final ResourceEnum[] storedResources = new ResourceEnum[] {ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.NONE};
    private static final ResourceEnum[][] warehouseArray = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> warehousePatternList = new ArrayList<>();
    private static final int MAX_FULLNESS = 3;
    private static final int MIN_FULLNESS = 0;

    public Warehouse(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    static {
        warehouseArray[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.WOOD, ResourceEnum.WHEAT};
        warehouseArray[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.NONE, ResourceEnum.BRICK};
        patternBuilder(warehouseArray, warehousePatternList);
    }
//    public Warehouse(ResourceEnum a, ResourceEnum b, ResourceEnum c) {
//        storedResources[0] = a;
//        storedResources[1] = b;
//        storedResources[2] = c;
//    }
    public BuildingEnum getType() {
        return BuildingEnum.WAREHOUSE;
    }

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

    public int getFullness() {
        return fullness;
    }
    public String toString() {
        return "Warehouse";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    public void printManualText() {
        System.out.println("While the Warehouse earns no points, it does allow you to store up to three resources inside of it, off the board.");
        System.out.println("Note: these resources still count as a negative point!");
        System.out.println("Here's what it looks like:");
        Utility.printFormattedResourcePattern(warehouseArray);
    }

    @Override
    public String getManualEntry() {
        return "While the Warehouse earns no points, it does allow you to store up to three resources inside of it, off the board.\nNote: these resources still count as a negative point!";
    }

    @SuppressWarnings("unused")
    public void fillResources() {
        storedResources[0] = ResourceEnum.GLASS;
        storedResources[1] = ResourceEnum.GLASS;
        storedResources[2] = ResourceEnum.GLASS;

    }
    public static int getMaxFullness() {
        return MAX_FULLNESS;
    }
    public static int getMinFullness() {
        return MIN_FULLNESS;
    }

    public ResourceEnum[] getStoredResources() {
        ResourceEnum[] list = new ResourceEnum[3];
        System.arraycopy(storedResources, 0, list, 0, storedResources.length);
        return list;
    }
    public ResourceEnum placeResource(ResourceEnum swappedResource, ResourceEnum requestedResource) {
        if (fullness < 3 && requestedResource == ResourceEnum.NONE) {
            storedResources[fullness] = swappedResource;
            fullness++;
            return ResourceEnum.NONE;
        }
        else {
            int index = arraySearcher(requestedResource, storedResources);
            ResourceEnum deletedResource;
            if (index != -1) {
                deletedResource = storedResources[index];
                storedResources[index] = swappedResource;
                return deletedResource;
            }
        }
        return ResourceEnum.OBSTRUCTED;
    }
    private int arraySearcher(ResourceEnum term, ResourceEnum[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == term) {
                return i;
            }
        }
        return -1;
    }
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return warehousePatternList;
    }
    public int scorer(Building[][] bArray) throws IOException {
        int score = 0;
        DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - SCORING: Beginning scoring protocol.");
        for (ResourceEnum resource : storedResources) {
            DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - SCORING: Checking resource:.");

            if (resource != ResourceEnum.NONE) {
                score--;
                DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - SCORING: Found a resource. Score: " + score);
            }
        }
        return score;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {

    }
}
