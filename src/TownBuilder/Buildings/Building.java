package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.BuildingColor;
import TownBuilder.TownResource;
import TownBuilder.Utility;

public class Building {
    private static ResourceEnum[][] cottageArray = new ResourceEnum[2][2];
    private BuildingEnum buildingEnum;
    private BuildingColor color;

    public static void setCottageArray() {
        cottageArray[0][0] = ResourceEnum.GLASS;
        cottageArray[0][1] = ResourceEnum.WHEAT;
        cottageArray[1][0] = ResourceEnum.BRICK;
        cottageArray[1][1] = ResourceEnum.NONE;
    }
    public static ResourceEnum[][] getCottageArray() {
        return cottageArray;
    }

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
                int checkTime = 4;
                Utility.arrayPrinter(bT);
                switch (buildingType) {
                    case COTTAGE:
                        checkTime = 6;
                        System.out.println("Cottage is being checked, so checktime is longer");

                }
                ResourceEnum[][] buildingTemplate = bT;
                for (int i = 0; i < checkTime; i++) {
                    System.out.println("i: " + i);
                    if (i == 4) {
                        buildingTemplate = ResourceEnum.swap(buildingTemplate, 0, 1, 1, 0);
                        System.out.println("Swapped array");
                        Utility.arrayPrinter(buildingTemplate);
                    }
                    if (compare(row, col, rArray, buildingTemplate, buildingType)) {
                        return true;
                    }
                    else {
                        buildingTemplate = buildingRotation(buildingTemplate);
                    }
                }
            }
            catch (ArrayIndexOutOfBoundsException e){}

        return false;
    }
    public static boolean compare(int row, int col, TownResource[][] rArray, ResourceEnum[][] buildingTemplate, BuildingEnum buildingType) {
        for (int r = 0; r < buildingTemplate.length; r++) {
            for (int c = 0; c < buildingTemplate[r].length; c++) {
                if (match(rArray[row+r][col+c], buildingTemplate[r][c], buildingType)) {
                    System.out.println("Successful comparison at " + (row+r) + ", " + (col+c));
                    continue;
                }
                else {
                    System.out.println("Failure of comparison at " + (row+r) + ", " + (col+c));
                    return false;
                }
            }
        }
        return true;

    }

    public static boolean match(TownResource toBeChecked, ResourceEnum checker, BuildingEnum building) {
        if (checker == ResourceEnum.NONE) {
            System.out.println("Checked tile was matched to NONE, therefore it is true");
            return true;
        }
        else if (toBeChecked.getResource() == checker) {
            toBeChecked.setScannedBuilding(building);
            System.out.println("Checked tile was matched to an arbitrary value " + checker + ", therefore it is true");
            return true;
        }
        System.out.println("Checked tile has no match");
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

