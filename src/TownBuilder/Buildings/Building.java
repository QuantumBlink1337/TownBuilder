package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Resource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Arrays;
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
    public static void setbuildingMasterList() {
        buildingMasterList.put("blue", new ArrayList<>(Arrays.asList(new Cottage())));
        buildingMasterList.put("red", new ArrayList<>(Arrays.asList(new Farm())));
        buildingMasterList.put("gray", new ArrayList<>(Arrays.asList(new Well())));
        buildingMasterList.put("orange", new ArrayList<>(Arrays.asList(new Chapel())));
        buildingMasterList.put("green", new ArrayList<>(Arrays.asList(new Tavern())));
        buildingMasterList.put("yellow", new ArrayList<>(Arrays.asList(new Theater())));
        buildingMasterList.put("black", new ArrayList<>(Arrays.asList(new Warehouse())));

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

