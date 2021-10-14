package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Resource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class Warehouse extends Building{
    private boolean condition;
    private int fullness = 0;
    private int count = 0;
    private static Scanner sc = new Scanner(System.in);
    private final ResourceEnum[] storedResources = new ResourceEnum[] {ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.NONE};
    private static final ResourceEnum[][] warehouseArray = new ResourceEnum[2][3];
    private static final ArrayList<ResourceEnum[][]> warehousePatternList = new ArrayList<>();
    private static final int MAX_FULLNESS = 3;
    private static final int MIN_FULLNESS = 0;

    public Warehouse() {
        condition = false;
        warehouseArray[0][0] = ResourceEnum.WHEAT;
        warehouseArray[0][1] = ResourceEnum.WOOD;
        warehouseArray[0][2] = ResourceEnum.WHEAT;
        warehouseArray[1][0] = ResourceEnum.BRICK;
        warehouseArray[1][1] = ResourceEnum.NONE;
        warehouseArray[1][2] = ResourceEnum.BRICK;
        patternBuilder(warehouseArray, warehousePatternList, 3);
    }
    public Warehouse(ResourceEnum a, ResourceEnum b, ResourceEnum c) {
        storedResources[0] = a;
        storedResources[1] = b;
        storedResources[2] = c;
        warehouseArray[0][0] = ResourceEnum.WHEAT;
        warehouseArray[0][1] = ResourceEnum.WOOD;
        warehouseArray[0][2] = ResourceEnum.WHEAT;
        warehouseArray[1][0] = ResourceEnum.BRICK;
        warehouseArray[1][1] = ResourceEnum.NONE;
        warehouseArray[1][2] = ResourceEnum.BRICK;
        patternBuilder(warehouseArray, warehousePatternList, 3);
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
    public String toString() {
        return "Warehouse";
    }
    public void printManualText() {
        System.out.println("While the Warehouse earns no points, it does allow you to store up to three resources inside of it, off the board.");
        System.out.println("Note: these resources still count as a negative point!");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(warehouseArray);
    }
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
    public void placement(Resource[][] rArray, Building[][] bArray, ArrayList<Building> buildings) {
        Scanner sc = new Scanner((System.in));
        String userInput;
        int[] coords;
        boolean validInput = false;
        do {
            System.out.println("Where would you like to place your Warehouse?");
            System.out.println("Valid positions for the Warehouse are:");
            Utility.displayValidResources(rArray);
            userInput = sc.nextLine().toLowerCase();
            coords = Utility.inputToCoords(userInput);
            if (rArray[coords[0]][coords[1]].getScannedBuilding() == BuildingEnum.WHOUSE) {
                validInput = true;
            }
        }
        while (!validInput);

        for (Resource validResource : Building.getValidResources()) {
            validResource.setResource(ResourceEnum.NONE);
        }
        clearResources(BuildingEnum.WHOUSE);
        rArray[coords[0]][coords[1]].setResource(ResourceEnum.OBSTRUCTED);
        bArray[coords[0]][coords[1]] = new Warehouse();
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
