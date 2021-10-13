package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;

import java.util.ArrayList;

public abstract class Building {

    private static final ArrayList<TownResource> validResources = new ArrayList<>();


    public abstract BuildingEnum getType();
    public abstract boolean getCondition();
    public abstract ArrayList<ResourceEnum[][]> getPatterns();
    public abstract String toString();
    public abstract int scorer(Building[][] bArray, int row, int col);
    public abstract void setCount();
    public abstract int getCount();
    public void patternBuilder(ResourceEnum[][] pattern, ArrayList<ResourceEnum[][]> patternList, int rotations) {
        if (rotations > 0) {
            ResourceEnum[][] rotatedPattern = Building.buildingRotation(pattern);
            patternList.add(rotatedPattern);
            patternBuilder(rotatedPattern, patternList, rotations-1);
        }
    }

//    public static ArrayList<TownResource> getValidResources() {
//        return validResources;
//    }

    public static void clearResources(BuildingEnum building) {
        for (int i = 0; i < validResources.size(); i++) {
            if (validResources.get(i).getScannedBuilding() == building) {
                validResources.remove(i);
            }
        }
    }
    public static ArrayList<TownResource> getValidResources() {
        return validResources;
    }
    public abstract void placement(TownResource[][] rArray, Building[][] bArray, ArrayList<Building> buildings);
    public abstract void printManualText();

    public static boolean detection(int row, int col, TownResource[][] rArray, ArrayList<ResourceEnum[][]> bT, BuildingEnum buildingType) {
        for (int i = 0; i < bT.size(); i++) {
            if (compare(row, col, rArray, bT.get(i))) {
                for (TownResource validResource : validResources) {
                    validResource.setScannedBuilding(buildingType);
                }
                return true;
            }
        }
        return false;
    }
    private static boolean compare(int row, int col, TownResource[][] rArray, ResourceEnum[][] buildingTemplate) {
        try {
            for (int r = 0; r < buildingTemplate.length; r++) {
                for (int c = 0; c < buildingTemplate[r].length; c++) {
                    //System.out.println("received call for detection");
                    if (!match(rArray[row+r][col+c], buildingTemplate[r][c])) {
                        validResources.clear();
                        //System.out.print("Failed comparison at " + Utility.coordsToOutput(row+r, col+c));
                        return false;
                    }
                    else {
                        //System.out.println("Successful comparison at " + Utility.coordsToOutput(row+r, col+c));
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
    private static boolean match(TownResource toBeChecked, ResourceEnum checker) {
        if (checker == ResourceEnum.NONE) {
            //  System.out.println("Checked tile was matched to NONE, therefore it is true");
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
    protected static ResourceEnum[][] buildingRotation(ResourceEnum[][] a) {
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

