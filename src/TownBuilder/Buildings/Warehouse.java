package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.Scanner;

public class Warehouse extends Building{
    private boolean condition;
    private int fullness = 0;
    private static Scanner sc = new Scanner(System.in);
    private ResourceEnum[] storedResources = new ResourceEnum[] {ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.NONE};
    private static ResourceEnum[][] warehouseArray = new ResourceEnum[2][3];
    private static ResourceEnum[][][] warehousePatternList = new ResourceEnum[1][2][2];

    public Warehouse() {
        condition = false;
    }
    public BuildingEnum getType() {
        return BuildingEnum.WHOUSE;
    }
    public void setCondition(boolean b) {
        condition = b;
    }
    public boolean getCondition() {
        return condition;
    }
    public int getFullness() {
        return fullness;
    }
    public String wordDefinition() {
        return "Warehouse";
    }
    public void printStoredResources() {
        for (ResourceEnum resource : storedResources) {
            System.out.println(resource);
        }
    }
    public ResourceEnum[] getStoredResources() {
        ResourceEnum[] list = new ResourceEnum[3];
        for (int i = 0; i < storedResources.length; i++) {
            list[i] = storedResources[i];
        }
        return list;
    }
    public ResourceEnum placeResource(ResourceEnum swappedResource, ResourceEnum requestedResource) {
        if (fullness < 3 && requestedResource == ResourceEnum.NONE) {
            //System.out.println("Call made to place a resource inside of the Warehouse");
            storedResources[fullness] = swappedResource;
            fullness++;
            //printStoredResources();
            //System.out.println("Fullness after operation: " + fullness);
            return ResourceEnum.NONE;
        }
        else {
            //System.out.println("Warehouse is full, so swapping it out");
            int index = arraySearcher(requestedResource, storedResources);
            ResourceEnum deletedResource;
            if (index != -1) {
                deletedResource = storedResources[index];
                storedResources[index] = swappedResource;
                //printStoredResources();
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
    public ResourceEnum[][][] getPatterns() {
        warehouseArray[0][0] = ResourceEnum.WHEAT;
        warehouseArray[0][1] = ResourceEnum.WOOD;
        warehouseArray[0][2] = ResourceEnum.WHEAT;
        warehouseArray[1][0] = ResourceEnum.BRICK;
        warehouseArray[1][1] = ResourceEnum.NONE;
        warehouseArray[1][2] = ResourceEnum.BRICK;
        warehousePatternList[0] = warehouseArray;
        return warehousePatternList;
    }
    public int scorer(Building[][] bArray, int row, int col) {
        int score = 0;
        for (ResourceEnum resource : storedResources) {
            if (resource != ResourceEnum.NONE) {
                score--;
            }
        }
        return 0;
    }
}
