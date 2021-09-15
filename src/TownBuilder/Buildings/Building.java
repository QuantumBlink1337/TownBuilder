package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.BuildingColor;
import TownBuilder.TownResource;

public class Building {
    private BuildingEnum buildingEnum;
    private BuildingColor color;
    public Building(BuildingEnum b, BuildingColor c) {
        buildingEnum = b;
        color = c;
    }
    public BuildingEnum getType() {
        return buildingEnum;
    }
    public BuildingColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "[" + buildingEnum +"]";
    }
    public static boolean detection(int row, int col, TownResource[][] rArray, ResourceEnum[][] bT, BuildingEnum buildingType) {
            try {
                int rotations = 0;
                ResourceEnum[][] buildingTemplate = bT;
                for (int i = 0; i < rArray.length; i++) {
                    for (int r = 0; r < buildingTemplate.length; r++) {
                        for (int c = 0; c < buildingTemplate[r].length; r++) {
                            if (compare(rArray[row][col], buildingTemplate[r][c], buildingType)) {
                               continue;
                            }
                            else {
                                buildingTemplate = buildingRotation(buildingTemplate);
                            }
                        }
                    }
                }

            }
            catch (ArrayIndexOutOfBoundsException e){}

        return false;
    }
    public static boolean compare(TownResource toBeChecked, ResourceEnum checker, BuildingEnum building) {
        if (checker == ResourceEnum.NONE) {
            return true;
        }
        else if (toBeChecked.getResource() == checker) {
            toBeChecked.setScannedBuilding(building);
            return true;
        }
        return false;
    }

    public static ResourceEnum[][] buildingRotation(ResourceEnum[][] a) {
        System.out.println("No match found. Flipping array...");
        ResourceEnum[][] resourceArray = a;
        final int M = resourceArray.length;
        final int N = resourceArray[0].length;
        ResourceEnum[][] ret = new ResourceEnum[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M - 1 - r] = resourceArray[r][c];
            }
        }
        System.out.println("Flipped array:");
        System.out.println("["+ret[0][0]+"]["+ret[0][1]+"]");
        System.out.println("["+ret[1][0]+"]["+ret[1][1]+"]");
        return ret;
    }
}

