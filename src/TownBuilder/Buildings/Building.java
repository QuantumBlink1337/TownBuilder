package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public abstract class Building {

    private BuildingEnum buildingEnum;
    private boolean condition;
    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<TownResource> validResources = new ArrayList<>();

//    public Building(BuildingEnum b) {
//        buildingEnum = b;
//        condition = false;
//    }
    public abstract BuildingEnum getType();
    public abstract void setCondition(boolean b);
    public abstract boolean getCondition();
    public abstract ResourceEnum[][][] getPatterns();

    public abstract int scorer(Building[][] bArray, int row, int col);
    public static ArrayList<TownResource> getValidResources() {
        return validResources;
    }

    @Override
    public String toString() {
        return "[" + buildingEnum +"]";
    }
    public static void clearResources(BuildingEnum building) {
        for (int i = 0; i < validResources.size(); i++) {
            if (validResources.get(i).getScannedBuilding() == building) {
                validResources.remove(i);
            }
        }
    }


    public static void placement(TownResource[][] rArray, Building[][] bArray, BuildingEnum building, Building[] buildings) {
        String userInput = "";
        int[] coords;
        boolean validInput = false;
        do {
            System.out.println("Where would you like to place your " + building + "?");
            System.out.println("Valid positions for the "+building+" are:");
            for (int r = 0; r < rArray.length; r++) {
                for (int c = 0; c < rArray[r].length; c++) {
                    for (int i = 0; i < validResources.size(); i++) {
                        if (rArray[r][c] == validResources.get(i)) {
                            System.out.println(Utility.coordsToOutput(r, c));
                        }
                    }

                }
            }
            userInput = sc.nextLine().toLowerCase();
            coords = Utility.inputToCoords(userInput);
            if (rArray[coords[0]][coords[1]].getScannedBuilding() == building) {
                validInput = true;
            }
        }
        while (!validInput);


//        for (int r = 0; r < rArray.length; r++){
//            for (int c = 0; c < rArray[r].length; c++) {
//                if (rArray[r][c].getScannedBuilding() == building) {
//                    System.out.println("Clearing type " + building + " from resource at row " + r + " and col "+c);
//                    rArray[r][c].setScannedBuilding(BuildingEnum.NONE);
//                    rArray[r][c].setResource(ResourceEnum.NONE);
//                }
//            }
//        }
//        System.out.println("Size at placement: "+ validResources.size());
        for (int i = 0; i < validResources.size(); i++) {
//            System.out.println("Is this statement firing?");
//            System.out.println(validResources.get(i).getResource());
            validResources.get(i).setResource(ResourceEnum.NONE);
        }
        clearResources(building);
        rArray[coords[0]][coords[1]].setResource(ResourceEnum.OBSTRUCTED);
        switch (building) {
            case FARM -> bArray[coords[0]][coords[1]] = new Farm();
            case COTTAGE -> bArray[coords[0]][coords[1]] = new Cottage();
            case WELL -> bArray[coords[0]][coords[1]] = new Well();
            case THEATER -> bArray[coords[0]][coords[1]] = new Theater(Arrays.asList(buildings));
            case WHOUSE -> bArray[coords[0]][coords[1]] = new Warehouse();
            case TAVERN -> bArray[coords[0]][coords[1]] = new Tavern();
            case CHAPEL -> bArray[coords[0]][coords[1]] = new Chapel();

        }

    }
    public static boolean detection(int row, int col, TownResource[][] rArray, ResourceEnum[][][] bT, BuildingEnum buildingType) {
            try {
                //System.out.println("Received detection call for " + buildingType);
                //System.out.println("Length of bT: " + bT.length);
                int checkTime = 0;
                int patternIndex = 0;
                for (ResourceEnum[][] board : bT) {
                    checkTime += 4;
                }
                //System.out.println("CheckTime: " + checkTime);
                ResourceEnum[][][] buildingTemplate = bT;
                for (int i = 1; i < checkTime+1; i++) {
                    //System.out.println("i: " + i);
                    if (i % 5 == 0) {
                        //System.out.println("Switching to next pattern available");
                        patternIndex++;
                    }
                    //Utility.arrayPrinter(bT[patternIndex]);
                    if (compare(row, col, rArray, buildingTemplate[patternIndex], buildingType)) {
                        for (int j = 0; j < validResources.size(); j++) {
                            validResources.get(j).setScannedBuilding(buildingType);
                        }
                        //System.out.println("Size at compare" + validResources.size());
                        return true;
                    }
                    else {
                        buildingTemplate[patternIndex] = buildingRotation(buildingTemplate[patternIndex]);
                    }
                }
            }
            catch (ArrayIndexOutOfBoundsException e){
                //System.out.println("Out of bounds exception?");
            }
        return false;
    }
    private static boolean compare(int row, int col, TownResource[][] rArray, ResourceEnum[][] buildingTemplate, BuildingEnum buildingType) {
        for (int r = 0; r < buildingTemplate.length; r++) {
            for (int c = 0; c < buildingTemplate[r].length; c++) {
                if (match(rArray[row+r][col+c], buildingTemplate[r][c])) {
                    //System.out.println("Successful comparison at " + (row+r) + ", " + (col+c));
                }
                else {
                    //System.out.println("Failure of comparison at " + (row+r) + ", " + (col+c));
                    validResources.clear();
                    return false;
                }
            }
        }
        return true;
    }
    private static boolean match(TownResource toBeChecked, ResourceEnum checker) {
        if (checker == ResourceEnum.NONE) {
            //System.out.println("Checked tile was matched to NONE, therefore it is true");
            return true;
        }
        else if (toBeChecked.getResource() == checker) {
            validResources.add(toBeChecked);
            //System.out.println("Checked tile was matched to an arbitrary value " + checker + ", therefore it is true");
            return true;
        }
        //System.out.println("Checked tile has no match");
        return false;
    }
    private static ResourceEnum[][] buildingRotation(ResourceEnum[][] a) {
        //System.out.println("No match found. Flipping array...");
        final int M = a.length;
        final int N = a[0].length;
        ResourceEnum[][] ret = new ResourceEnum[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M - 1 - r] = a[r][c];
            }
        }
        //System.out.println("Flipped array:");
        //System.out.println("["+ret[0][0]+"]["+ret[0][1]+"]");
        //System.out.println("["+ret[1][0]+"]["+ret[1][1]+"]");
        return ret;
    }
}

