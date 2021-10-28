package TownBuilder.Buildings;

import TownBuilder.Resource;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.*;

public class BuildingFactory {
    private final ArrayList<Resource> validResources = new ArrayList<>();
    private static final HashMap<String, ArrayList<Building>> buildingMasterList = new HashMap<>();
    public static void setbuildingMasterList() {
        buildingMasterList.put("blue", new ArrayList<>(Arrays.asList(new Cottage(-1, -1))));
        buildingMasterList.put("red", new ArrayList<>(Arrays.asList(new Farm(-1, -1), new Granary(-1, -1), new Orchard(-1, -1))));
        buildingMasterList.put("gray", new ArrayList<>(Arrays.asList(new Well(-1, -1), new Fountain(-1, -1), new Millstone(-1, -1))));
        buildingMasterList.put("orange", new ArrayList<>(Arrays.asList(new Chapel(-1, -1 ), new Abbey(-1, -1), new Cloister(-1, -1), new Temple(-1, -1))));
        buildingMasterList.put("green", new ArrayList<>(Arrays.asList(new Tavern(-1, -1), new Almshouse(-1, -1), new FeastHall(-1, -1), new Inn(-1, -1))));
        buildingMasterList.put("yellow", new ArrayList<>(Arrays.asList(new Theater(), new Bakery(-1, -1), new Market(-1, -1), new Tailor(-1, -1))));
        buildingMasterList.put("black", new ArrayList<>(Arrays.asList(new Warehouse(-1, -1), new Factory(-1, -1, false), new Bank(-1, -1, false), new TradingPost(-1, -1))));
    }
    public static Building getBuilding(BuildingEnum buildingEnum, ArrayList<Building> buildingMasterList, int row, int col, boolean isPlayerMade) {
        if (buildingEnum == BuildingEnum.NONE) {
            return new EmptyBuilding();
        }
        else if (buildingEnum == BuildingEnum.CHAPEL) {
            return new Chapel(row, col);
        }
        else if (buildingEnum == BuildingEnum.ABBEY) {
            return new Abbey(row, col);
        }
        else if (buildingEnum == BuildingEnum.CLOISTER) {
            return new Cloister(row, col);
        }
        else if (buildingEnum == BuildingEnum.TEMPLE) {
            return new Temple(row, col);
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
        else if (buildingEnum == BuildingEnum.ALMSHOUSE) {
            return new Almshouse(row, col);
        }
        else if (buildingEnum == BuildingEnum.FEASTHALL) {
            return new FeastHall(row, col);
        }
        else if (buildingEnum == BuildingEnum.INN) {
            return new Inn(row, col);
        }
        else if (buildingEnum == BuildingEnum.THEATER) {
            return new Theater(buildingMasterList, row, col);
        }
        else if (buildingEnum == BuildingEnum.BAKERY) {
            return new Bakery(row, col);
        }
        else if (buildingEnum == BuildingEnum.MARKET) {
            return new Market(row, col);
        }
        else if (buildingEnum == BuildingEnum.TAILOR) {
            return new Tailor(row, col);
        }
        else if (buildingEnum == BuildingEnum.WAREHOUSE) {
            return new Warehouse(row, col);
        }
        else if (buildingEnum == BuildingEnum.FACTORY) {
            return new Factory(row, col, isPlayerMade);
        }
        else if (buildingEnum == BuildingEnum.BANK) {
            return new Bank(row, col ,isPlayerMade);
        }
        else if (buildingEnum == BuildingEnum.TRDINGPST) {
            return new TradingPost(row, col);
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

    /*
        Build all possible transformations of the pattern.
    */
    public static void patternBuilder(ResourceEnum[][] pattern, ArrayList<ResourceEnum[][]> patternList, int rotations) {
        // add the pattern definition
        patternList.add(pattern);

        // add the 3 rotations
        ResourceEnum[][] p = pattern;
        for (int i = 0; i < 3; i++) {
            p = Utility.rotatePattern(p);
            patternList.add(p);
        }

        // mirror, then repeat
        ResourceEnum[][] mirror = Utility.mirrorPattern(pattern);
        // if the mirror is different (i.e. the pattern isn't symmetrical) we
        // have some more transformations to compute
        if (!Arrays.deepEquals(mirror, pattern)) {
            patternList.add(mirror);
            ResourceEnum[][] m = mirror;
            for (int i = 0; i < 3; i++) {
                m = Utility.rotatePattern(m);
                patternList.add(m);
            }
        }
    }

    public boolean detection(int row, int col, Resource[][] rArray, ArrayList<ResourceEnum[][]> bT, BuildingEnum buildingType) {
        for (ResourceEnum[][] resourceEnums : bT) {
            if (compare(row, col, rArray, resourceEnums) && validResources.size() > 0) {
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
    // toBeChecked is the resource on the board we're checking, and checker is the resource we're looking for
    private boolean match(Resource toBeChecked, ResourceEnum checker) {
        if (checker == ResourceEnum.NONE || toBeChecked.getResource() == ResourceEnum.TPOST) {
            return true;
        }
        else if (toBeChecked.getResource() == checker) {
            validResources.add(toBeChecked);
            return true;
        }
        return false;
    }


    public void placeBuildingOnBoard(Resource[][] rArray, Building[][] bArray, BuildingEnum buildingEnum, ArrayList<Building> buildingArrayList, boolean PlaceBuildingAnywhere) throws InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner((System.in));
        int[] coords = new int[]{-1, -1};
        boolean validInput = false;
        Building building = getBuilding(buildingEnum,buildingArrayList, -1, -1, false);

        do {
            try {
                System.out.println("Where would you like to place your " + building.toString() + "?");
                if (PlaceBuildingAnywhere) {
                    System.out.println("You can place your building wherever you want, provided there's nothing there already!");
                    coords = Utility.inputToCoords(sc.nextLine().toLowerCase());
                    if (rArray[coords[0]][coords[1]].getResource() == ResourceEnum.NONE && bArray[coords[0]][coords[1]].getType() == BuildingEnum.NONE) {
                        validInput = true;
                    }
                }
                else {
                    System.out.println("Valid positions for the "+building.toString()+ " are:");
                    Utility.displayValidResources(rArray, this);
                    coords = Utility.inputToCoords(sc.nextLine().toLowerCase());
                    if (rArray[coords[0]][coords[1]].getScannedBuilding() == building.getType()) {
                        validInput = true;
                    }
                }
            }
            catch(ArrayIndexOutOfBoundsException ignored) {
                System.out.println("Invalid input.");
                sc.next();

            }

        }
        while (!validInput);

        for (Resource validResource : validResources) {
            validResource.setResource(ResourceEnum.NONE);
        }
        clearResources(building.getType());
        if (buildingEnum != BuildingEnum.TRDINGPST) {
            rArray[coords[0]][coords[1]].setResource(ResourceEnum.OBSTRUCTED);

        }
        else {
            rArray[coords[0]][coords[1]].setResource(ResourceEnum.TPOST);
        }
        bArray[coords[0]][coords[1]] = getBuilding(buildingEnum,buildingArrayList, coords[0], coords[1], true);
    }

}
