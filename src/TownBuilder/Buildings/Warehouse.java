package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Resource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class Warehouse extends Building{
    private boolean condition;
    private int fullness = 0;
    private final ResourceEnum[] storedResources = new ResourceEnum[] {ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.NONE};
    private static final ResourceEnum[][] warehouseArray = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> warehousePatternList = new ArrayList<>();
    private static final int MAX_FULLNESS = 3;
    private static final int MIN_FULLNESS = 0;

    public Warehouse() {
        condition = false;
    }
    static {
        warehouseArray[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.WOOD, ResourceEnum.WHEAT};
        warehouseArray[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.NONE, ResourceEnum.BRICK};
        patternBuilder(warehouseArray, warehousePatternList, 3);
    }
    public Warehouse(ResourceEnum a, ResourceEnum b, ResourceEnum c) {
        storedResources[0] = a;
        storedResources[1] = b;
        storedResources[2] = c;
    }
    public BuildingEnum getType() {
        return BuildingEnum.WHOUSE;
    }

    public boolean getCondition() {
        return condition;
    }
    public int getFullness() {
        return fullness;
    }
    public String toString() {
        return "Warehouse";
    }
    public void printManualText() {
        System.out.println("While the Warehouse earns no points, it does allow you to store up to three resources inside of it, off the board.");
        System.out.println("Note: these resources still count as a negative point!");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(warehouseArray);
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
    public ArrayList<ResourceEnum[][]> getPatterns() {
        return warehousePatternList;
    }
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        int score = 0;
        for (ResourceEnum resource : storedResources) {
            if (resource != ResourceEnum.NONE) {
                score--;
            }
        }
        return score;
    }

}
