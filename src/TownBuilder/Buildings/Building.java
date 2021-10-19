package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Resource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Building {

    private static final ArrayList<Resource> validResources = new ArrayList<>();
    public abstract BuildingEnum getType();
    public abstract boolean getCondition();
    private static final HashMap<String, ArrayList<Building>> buildingMasterList = new HashMap<>();
    private static final String[] colors = new String[]{"blue", "red", "gray", "orange", "green", "yellow", "black"};
    public abstract ArrayList<ResourceEnum[][]> getPatterns();
    public abstract String toString();
    public abstract int scorer(Building[][] bArray, int row, int col, int scoreIncrement);
    public abstract void printManualText();
    static {
        for (int i = 0; i < colors.length; i++) {
            buildingMasterList.put(colors[i], new ArrayList<Building>());

        }
    }
    public static void setbuildingMasterList(String key, Building building) {
        buildingMasterList.get(key).add(building);
    }
    public static String[] getColors() {
        return colors;
    }
    public static void patternBuilder(ResourceEnum[][] pattern, ArrayList<ResourceEnum[][]> patternList, int rotations) {
        if (rotations >= 0) {
            ResourceEnum[][] rotatedPattern = Building.buildingRotation(pattern);
            patternList.add(rotatedPattern);
            patternBuilder(rotatedPattern, patternList, rotations-1);
        }
    }
    public static void clearResources(BuildingEnum building) {
        for (int i = 0; i < validResources.size(); i++) {
            if (validResources.get(i).getScannedBuilding() == building) {
                validResources.remove(i);
            }
        }
    }
    public static ArrayList<Resource> getValidResources() {
        return validResources;
    }
    public void placement(Resource[][] rArray, Building[][] bArray, ArrayList<Building> buildings) throws InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner((System.in));
        String userInput;
        int[] coords;
        boolean validInput = false;
        Building building = this;
        do {
            System.out.println("Where would you like to place your " + building.toString() + "?");
            System.out.println("Valid positions for the "+building.toString()+ " are:");
            Utility.displayValidResources(rArray);
            userInput = sc.nextLine().toLowerCase();
            coords = Utility.inputToCoords(userInput);
            if (rArray[coords[0]][coords[1]].getScannedBuilding() == building.getType()) {
                validInput = true;
            }
        }
        while (!validInput);

        for (Resource validResource : Building.getValidResources()) {
            validResource.setResource(ResourceEnum.NONE);
        }
        clearResources(building.getType());
        rArray[coords[0]][coords[1]].setResource(ResourceEnum.OBSTRUCTED);
        bArray[coords[0]][coords[1]] = this.getClass().newInstance();
    }
    public static boolean detection(int row, int col, Resource[][] rArray, ArrayList<ResourceEnum[][]> bT, BuildingEnum buildingType) {
        for (ResourceEnum[][] resourceEnums : bT) {
            if (compare(row, col, rArray, resourceEnums)) {
                for (Resource validResource : validResources) {
                    validResource.setScannedBuilding(buildingType);
                }
                return true;
            }
        }
        return false;
    }
    private static boolean compare(int row, int col, Resource[][] rArray, ResourceEnum[][] buildingTemplate) {
        try {
            for (int r = 0; r < buildingTemplate.length; r++) {
                for (int c = 0; c < buildingTemplate[r].length; c++) {
                    if (!match(rArray[row+r][col+c], buildingTemplate[r][c])) {
                        validResources.clear();
                        return false;
                    }
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            validResources.clear();
            return false;
        }
        return true;
    }
    private static boolean match(Resource toBeChecked, ResourceEnum checker) {
        if (checker == ResourceEnum.NONE) {
            return true;
        }
        else if (toBeChecked.getResource() == checker) {
            validResources.add(toBeChecked);
            return true;
        }
        return false;
    }
    protected static ResourceEnum[][] buildingRotation(ResourceEnum[][] a) {
        final int M = a.length;
        final int N = a[0].length;
        ResourceEnum[][] ret = new ResourceEnum[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M - 1 - r] = a[r][c];
            }
        }
        return ret;
    }
    public static HashMap<String, ArrayList<Building>> getBuildingMasterList() {
        return buildingMasterList;
    }

}

