package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.BuildingColor;
import TownBuilder.TownResource;
import TownBuilder.Utility;

import java.util.Scanner;

public class Building {

    private BuildingEnum buildingEnum;
    private static Scanner sc = new Scanner(System.in);



    public Building(BuildingEnum b) {
        buildingEnum = b;
    }
    public BuildingEnum getType() {
        return buildingEnum;
    }
//    public BuildingColor getColor() {
//        return color;
//    }

    @Override
    public String toString() {
        return "[" + buildingEnum +"]";
    }

    public static void placement(TownResource[][] rArray, Building[][] bArray, BuildingEnum building) {
        String userInput = "";
        int row = 0;
        int col = 0;
        boolean validInput = false;
        do {
            System.out.println("Where would you like to place your " + building + "?");
            userInput = sc.nextLine().toLowerCase();
            String[] coordinateHelper = userInput.split("", 2);
            switch (coordinateHelper[0]) {
                case "a" -> //System.out.println("Case A");
                        col = 0;
                case "b" -> //System.out.println("Case B");
                        col = 1;
                case "c" -> //System.out.println("Case C");
                        col = 2;
                case "d" -> //System.out.println("Case D");
                        col = 3;
            }
            switch (coordinateHelper[1]) {
                case "1" -> //System.out.println("Case 0");
                        row = 0;
                case "2" -> //.out.println("Case 1");
                        row = 1;
                case "3" -> //System.out.println("Case 2");
                        row = 2;
                case "4" -> //System.out.println("Case 3");
                        row = 3;
            }
            if (rArray[row][col].getScannedBuilding() == building) {
                validInput = true;
            }
        }
        while (!validInput);


        for (int r = 0; r < rArray.length; r++){
            for (int c = 0; c < rArray[r].length; c++) {
                if (rArray[r][c].getScannedBuilding() == building) {
                    rArray[r][c].setResource(ResourceEnum.NONE);
                    rArray[r][c].setScannedBuilding(BuildingEnum.NONE);
                }
            }
        }
        switch (building) {
            case FARM -> bArray[row][col] = new RedBuilding(BuildingEnum.FARM);
            case COTTAGE -> bArray[row][col] = new BlueBuilding(BuildingEnum.COTTAGE);

        }

    }
    public static boolean detection(int row, int col, TownResource[][] rArray, ResourceEnum[][][] bT, BuildingEnum buildingType) {
            try {
                System.out.println("Received detection call for " + buildingType);
                System.out.println("Length of bT: " + bT.length);
                int checkTime = 0;
                int patternIndex = 0;
                for (ResourceEnum[][] board : bT) {
                    checkTime += 4;
                }
                System.out.println("CheckTime: " + checkTime);


                ResourceEnum[][][] buildingTemplate = bT;
                for (int i = 1; i < checkTime+1; i++) {
                    System.out.println("i: " + i);
                    if (i % 4 == 0) {
                        System.out.println("Switching to next pattern available");
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
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Out of bounds exception?");
            }

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

