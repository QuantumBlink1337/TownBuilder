package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.BuildingColor;
import TownBuilder.TownResource;
import TownBuilder.Utility;

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


    public static boolean detection(int row, int col, TownResource[][] rArray, ResourceEnum[][][] bT, BuildingEnum buildingType) {
            try {
                int checkTime = 0;
                int patternIndex = 0;
                for (ResourceEnum[][] board : bT) {
                    checkTime += 4;
                }
                System.out.println("CheckTime: " + checkTime);


                ResourceEnum[][][] buildingTemplate = bT;
                for (int i = 0; i < checkTime; i++) {
                    if (i % 4 == 0) {
                        patternIndex++;
                    }
                    Utility.arrayPrinter(bT[patternIndex]);
                    if (compare(row, col, rArray, buildingTemplate[patternIndex], buildingType)) {
                        return true;
                    }
                    else {
                        buildingTemplate[patternIndex] = buildingRotation(buildingTemplate[patternIndex]);
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
        final int M = a.length;
        final int N = a[0].length;
        ResourceEnum[][] ret = new ResourceEnum[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M - 1 - r] = a[r][c];
            }
        }
        System.out.println("Flipped array:");
        System.out.println("["+ret[0][0]+"]["+ret[0][1]+"]");
        System.out.println("["+ret[1][0]+"]["+ret[1][1]+"]");
        return ret;
    }
}

