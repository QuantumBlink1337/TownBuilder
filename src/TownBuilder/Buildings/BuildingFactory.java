package TownBuilder.Buildings;

import TownBuilder.Resource;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class BuildingFactory {
    private final ArrayList<Resource> validResources = new ArrayList<>();
    private static final HashMap<String, ArrayList<Building>> buildingMasterList = new HashMap<>();
    public static void setbuildingMasterList() {

        buildingMasterList.put("blue", new ArrayList<>(Arrays.asList(new Cottage(-1, -1))));
        buildingMasterList.put("red", new ArrayList<>(Arrays.asList(new Farm(-1, -1), new Granary(-1, -1), new Orchard(-1, -1))));
        buildingMasterList.put("gray", new ArrayList<>(Arrays.asList(new Well(-1, -1), new Fountain(-1, -1), new Millstone(-1, -1))));
        buildingMasterList.put("orange", new ArrayList<>(Arrays.asList(new Chapel(-1, -1 ))));
        buildingMasterList.put("green", new ArrayList<>(Arrays.asList(new Tavern(-1, -1))));
        buildingMasterList.put("yellow", new ArrayList<>(Arrays.asList(new Theater())));
        buildingMasterList.put("black", new ArrayList<>(Arrays.asList(new Warehouse(-1, -1))));
    }

    public static Building getBuilding(BuildingEnum buildingEnum, ArrayList<Building> buildingMasterList, int row, int col) {
        if (buildingEnum == BuildingEnum.CHAPEL) {
            return new Chapel(row, col);
        }
        else if (buildingEnum == BuildingEnum.COTTAGE) {
            return new Cottage(row, col);
        }
        else if (buildingEnum == BuildingEnum.FARM) {
            return new Farm(row, col);
        }
        else if (buildingEnum == BuildingEnum.GRANARY) {
            return new Granary(row, col);
        }
        else if(buildingEnum == BuildingEnum.ORCHARD) {
            return new Orchard(row, col);
        }
        else if (buildingEnum == BuildingEnum.TAVERN) {
            return new Tavern(row, col);
        }
        else if (buildingEnum == BuildingEnum.THEATER) {
            return new Theater(buildingMasterList, row, col);
        }
        else if (buildingEnum == BuildingEnum.WAREHOUSE) {
            return new Warehouse(row, col);
        }
        else if (buildingEnum == BuildingEnum.WELL) {
            return new Well(row, col);
        }
        else if (buildingEnum == BuildingEnum.FOUNTAIN) {
            return new Fountain(row, col);
        }
        else if (buildingEnum == BuildingEnum.MILLSTONE) {
            return new Millstone(row, col);
        }
        return null;
    }
    public ArrayList<Resource> getValidResources() {
        return validResources;
    }
    public static HashMap<String, ArrayList<Building>> getBuildingMasterList() {
        return buildingMasterList;
    }

    public void clearResources(BuildingEnum building) {
        for (int i = 0; i < validResources.size(); i++) {
            if (validResources.get(i).getScannedBuilding() == building) {
                validResources.remove(i);
            }
        }
    }
    public static void patternBuilder(ResourceEnum[][] pattern, ArrayList<ResourceEnum[][]> patternList, int rotations) {
        if (rotations >= 0) {
            ResourceEnum[][] rotatedPattern = buildingRotation(pattern);
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
    public boolean detection(int row, int col, Resource[][] rArray, ArrayList<ResourceEnum[][]> bT, BuildingEnum buildingType) {
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
    private boolean compare(int row, int col, Resource[][] rArray, ResourceEnum[][] buildingTemplate) {
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
    private boolean match(Resource toBeChecked, ResourceEnum checker) {
        if (checker == ResourceEnum.NONE) {
            return true;
        }
        else if (toBeChecked.getResource() == checker) {
            validResources.add(toBeChecked);
            return true;
        }
        return false;
    }
        public void placement(Resource[][] rArray, Building[][] bArray, BuildingEnum buildingEnum, ArrayList<Building> buildingArrayList) throws InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner((System.in));
        String userInput;
        int[] coords;
        boolean validInput = false;
        Building building = getBuilding(buildingEnum,buildingArrayList, -1, -1);
        do {
            System.out.println("Where would you like to place your " + building.toString() + "?");
            System.out.println("Valid positions for the "+building.toString()+ " are:");
            Utility.displayValidResources(rArray, this);
            userInput = sc.nextLine().toLowerCase();
            coords = Utility.inputToCoords(userInput);
            if (rArray[coords[0]][coords[1]].getScannedBuilding() == building.getType()) {
                validInput = true;
            }
        }
        while (!validInput);

        for (Resource validResource : validResources) {
            validResource.setResource(ResourceEnum.NONE);
        }
        clearResources(building.getType());
        rArray[coords[0]][coords[1]].setResource(ResourceEnum.OBSTRUCTED);
        bArray[coords[0]][coords[1]] = getBuilding(buildingEnum,buildingArrayList, coords[0], coords[1]);;
    }

}
