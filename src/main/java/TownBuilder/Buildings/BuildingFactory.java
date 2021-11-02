package TownBuilder.Buildings;

import TownBuilder.*;

import java.util.*;

public class BuildingFactory {
    private final ArrayList<Resource> validResources = new ArrayList<>();
    private static final HashMap<ColorEnum, ArrayList<Building>> buildingMasterList = new HashMap<>();
    public static void setbuildingMasterList() {
        buildingMasterList.put(ColorEnum.BLUE, new ArrayList<>(List.of(new Cottage(-1, -1))));
        buildingMasterList.put(ColorEnum.RED, new ArrayList<>(Arrays.asList(new Farm(-1, -1), new Granary(-1, -1), new Orchard(-1, -1))));
        buildingMasterList.put(ColorEnum.GRAY, new ArrayList<>(Arrays.asList(new Well(-1, -1), new Fountain(-1, -1), new Millstone(-1, -1))));
        buildingMasterList.put(ColorEnum.ORANGE, new ArrayList<>(Arrays.asList(new Chapel(-1, -1 ), new Abbey(-1, -1), new Cloister(-1, -1), new Temple(-1, -1))));
        buildingMasterList.put(ColorEnum.GREEN, new ArrayList<>(Arrays.asList(new Tavern(-1, -1), new Almshouse(-1, -1), new FeastHall(-1, -1), new Inn(-1, -1))));
        buildingMasterList.put(ColorEnum.YELLOW, new ArrayList<>(Arrays.asList(new Theater(), new Bakery(-1, -1), new Market(-1, -1), new Tailor(-1, -1))));
        buildingMasterList.put(ColorEnum.WHITE, new ArrayList<>(Arrays.asList(new Warehouse(-1, -1), new Factory(-1, -1, false), new Bank(-1, -1, false), new TradingPost(-1, -1))));
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
    public static Monument getMonument(BuildingEnum buildingEnum, Board board, int row, int col, ArrayList<Building> masterBuildings) {
        if (buildingEnum == BuildingEnum.AGUILD) {
            return new AGuild(row, col, board);
        }
        else if (buildingEnum == BuildingEnum.ARCHIVE) {
            return new Archive(row, col, board, masterBuildings);
        }
        else if (buildingEnum == BuildingEnum.BARRETT) {
            return new BarrettCastle(row, col, board);
        }
        return null;
    }
    public ArrayList<Resource> getValidResources() {
        return validResources;
    }
    public static HashMap<ColorEnum, ArrayList<Building>> getBuildingMasterList() {
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
    public static void patternBuilder(ResourceEnum[][] pattern, ArrayList<ResourceEnum[][]> patternList) {
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


    public void placeBuildingOnBoard(BuildingEnum buildingEnum, ArrayList<Building> buildingArrayList, boolean PlaceBuildingAnywhere, Board board) {
        Scanner sc = new Scanner((System.in));
        int[] coords = new int[]{-1, -1};
        boolean validInput = false;
        Building building = null;
        if (buildingEnum.isMonument()) {
             building = getMonument(buildingEnum, board, -1, -1, buildingArrayList);
        }
        else {
             building = getBuilding(buildingEnum,buildingArrayList, -1, -1, false);
        }
        Building[][] bArray = board.getGameBuildingBoard();
        Resource[][] rArray = board.getGameResourceBoard();

        do {
            try {
                assert building != null;
                System.out.println("Where would you like to place your " + Utility.generateColorizedString(building.toString(), buildingEnum) + "?");
                if (PlaceBuildingAnywhere) {
                    System.out.println("You can place your building wherever you want, provided there's nothing there already!");
                    coords = Utility.inputToCoords(sc.nextLine().toLowerCase());
                    if (rArray[coords[0]][coords[1]].getResource() == ResourceEnum.NONE && bArray[coords[0]][coords[1]].getType() == BuildingEnum.NONE) {
                        validInput = true;
                    }
                }
                else {
                    System.out.println("Valid positions for the "+ Utility.generateColorizedString(building.toString(), buildingEnum)+ " are:");
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
        if (buildingEnum.isMonument()) {
            bArray[coords[0]][coords[1]] = getMonument(buildingEnum, board, coords[0], coords[1], buildingArrayList);
            board.monumentControl((Monument) bArray[coords[0]][coords[1]]);
        }
        else {
            bArray[coords[0]][coords[1]] = getBuilding(buildingEnum,buildingArrayList, coords[0], coords[1], true);
        }
    }

}
