package TownBuilder.Buildings;

import TownBuilder.*;
import TownBuilder.Buildings.Monuments.*;
import TownBuilder.DebugApps.DebugTools;

import java.io.IOException;
import java.util.*;

public class BuildingFactory {
    private final ArrayList<Resource> validResources = new ArrayList<>();
    private static final HashMap<ColorEnum, ArrayList<Building>> buildingMasterList = new HashMap<>();
    public static void setbuildingMasterList() throws IOException {
        buildingMasterList.put(ColorEnum.BLUE, new ArrayList<>(List.of(new Cottage(-1, -1))));
        buildingMasterList.put(ColorEnum.RED, new ArrayList<>(Arrays.asList(new Farm(-1, -1), new Granary(-1, -1), new Orchard(-1, -1), new Greenhouse(-1, -1))));
        buildingMasterList.put(ColorEnum.GRAY, new ArrayList<>(Arrays.asList(new Well(-1, -1), new Fountain(-1, -1), new Millstone(-1, -1), new Shed(-1, -1))));
        buildingMasterList.put(ColorEnum.ORANGE, new ArrayList<>(Arrays.asList(new Chapel(-1, -1 ), new Abbey(-1, -1), new Cloister(-1, -1), new Temple(-1, -1))));
        buildingMasterList.put(ColorEnum.GREEN, new ArrayList<>(Arrays.asList(new Tavern(-1, -1), new Almshouse(-1, -1), new FeastHall(-1, -1), new Inn(-1, -1))));
        buildingMasterList.put(ColorEnum.YELLOW, new ArrayList<>(Arrays.asList(new Theater(), new Bakery(-1, -1), new Market(-1, -1), new Tailor(-1, -1))));
        buildingMasterList.put(ColorEnum.WHITE, new ArrayList<>(Arrays.asList(new Warehouse(-1, -1), new Factory(-1, -1, false, null), new Bank(-1, -1, false, null), new TradingPost(-1, -1))));
    }
    public static Building getBuilding(BuildingEnum buildingEnum, ArrayList<Building> buildingMasterList, int row, int col, boolean isPlayerMade, Board board) throws IOException {
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
        else if(buildingEnum == BuildingEnum.GRENHOUSE) {
            return new Greenhouse(row, col);
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
            return new Factory(row, col, isPlayerMade, board);
        }
        else if (buildingEnum == BuildingEnum.BANK) {
            return new Bank(row, col ,isPlayerMade, board);
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
        else if (buildingEnum == BuildingEnum.SHED) {
            return new Shed(row, col);
        }
        else if (buildingEnum == BuildingEnum.AGUILD) {
            return new AGuild(row, col, board);
        }
        else if (buildingEnum == BuildingEnum.ARCHIVE) {
            return new Archive(row, col, board, board.getScorableBuildings());
        }
        else if (buildingEnum == BuildingEnum.BARRETT) {
            return new BarrettCastle(row, col);
        }
        else if (buildingEnum == BuildingEnum.CATERINA) {
            return new Caterina(row, col, board);
        }
        else if (buildingEnum == BuildingEnum.IRONWEED) {
            return new FortIronweed(row, col, board);
        }
        else if (buildingEnum == BuildingEnum.GROVEUNI) {
            return new GroveUni(row, col, board);
        }
        else if (buildingEnum == BuildingEnum.MANDRAS) {
            return new MandrasPalace(row, col, board);
        }
        else if(buildingEnum == BuildingEnum.SHRINE) {
            return new Shrine(row, col, board);
        }
        else if(buildingEnum == BuildingEnum.OPALEYE) {
            return new OpaleyeWatch(row, col, board);
        }
        else if(buildingEnum == BuildingEnum.SILVAFRM) {
            return new SilvaForum(row, col, board);
        }
        else if(buildingEnum == BuildingEnum.STARLOOM) {
            return new Starloom(row, col, board);
        }
        else if (buildingEnum == BuildingEnum.SKYBATHS) {
            return new SkyBaths(row, col ,board);
        }
        else if (buildingEnum == BuildingEnum.OBELISK) {
            return new Obelisk(row, col, board);
        }
        return null;
    }

    public static boolean getCondition(Building building) {
        return building.getCondition();
    }
    public static boolean determineFeedStatus(Building building) {
        return building.isFeedable() && !building.getCondition();
    }
    public ArrayList<Resource> getValidResources() {
        return validResources;
    }
    public static HashMap<ColorEnum, ArrayList<Building>> getBuildingMasterList() {
        return buildingMasterList;
    }

    public BuildingFactory() {
    }
    public void clearValidResourcesWithFlag(BuildingEnum building) throws IOException {
        DebugTools.logging("Clearing valid resources that have flag: " + building);
        for (int i = validResources.size()-1; i > 0; i--) {
            DebugTools.logging("Checking resource: " + DebugTools.resourceInformation(validResources.get(i)));
            if (validResources.get(i).getScannedBuilding() == building) {
                DebugTools.logging("Current resource matches flag. Removing from validResources.");
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
            p = Utility.rotate90(p);
            patternList.add(p);
        }

        // if the pattern isn't symmetrical, we have more transformations to compute
        ResourceEnum[][] vertMirror = Utility.vertMirror(pattern);
        boolean isVertSymmetric = Arrays.deepEquals(pattern, vertMirror);
        ResourceEnum[][] rotatedPattern = Utility.rotate90(pattern);
        boolean isHorizSymmetric = Arrays.deepEquals(rotatedPattern, Utility.vertMirror(rotatedPattern));
        boolean isSymmetric = isVertSymmetric || isHorizSymmetric;
        if (!isSymmetric) {
            patternList.add(vertMirror);
            ResourceEnum[][] m = vertMirror;
            for (int i = 0; i < 3; i++) {
                m = Utility.rotate90(m);
                patternList.add(m);
            }
        }
    }

    public boolean detection(int row, int col, Resource[][] rArray, ArrayList<ResourceEnum[][]> buildingPatterns, BuildingEnum buildingType) {
        for (ResourceEnum[][] buildingPattern : buildingPatterns) {
            if (compare(row, col, rArray, buildingPattern) && validResources.size() > 0) {
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
    private boolean match(Resource resourceOnBoard, ResourceEnum resourceInPattern) {
        if (resourceInPattern == ResourceEnum.NONE || resourceOnBoard.getResource() == ResourceEnum.TPOST) {
            return true;
        }
        else if (resourceOnBoard.getResource() == resourceInPattern) {
            validResources.add(resourceOnBoard);
            return true;
        }
        return false;
    }


    public void placeBuildingOnBoard(BuildingEnum buildingEnum, ArrayList<Building> buildingArrayList, boolean PlaceBuildingAnywhere, Board board) throws IOException {
        int[] coords = new int[]{};
        boolean validInput = false;
        Building building;
        building = getBuilding(buildingEnum,buildingArrayList, -1, -1, false, board);
        Building[][] bArray = board.getGameBuildingBoard();
        Resource[][] rArray = board.getGameResourceBoard();
        board.getBoardUI().setPrimaryTextLabel("Your selected building is " + building.toString() + ".");
        if (PlaceBuildingAnywhere) {
            board.getBoardUI().setSecondaryTextLabel("You can place your building wherever you want, provided there's nothing there already!");
            board.getBoardUI().resetBoardTiles(false, true);
            do {
                synchronized (board.getNotifier()) {
                    try {
                        board.getNotifier().wait();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (board.getBoardUI().isCoordinatesClicked()) {
                    coords = board.getBoardUI().getSelectedCoords();
                }
                if (bArray[coords[0]][coords[1]].getType() == BuildingEnum.NONE) {
                    validInput = true;
                }
            }
            while (!validInput);
        }
        else {
            board.getBoardUI().setSecondaryTextLabel("You may place it in the highlighted tiles.");

            synchronized (board.getNotifier()) {
                try {
                    board.getNotifier().wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (board.getBoardUI().isCoordinatesClicked()) {
                coords = board.getBoardUI().getSelectedCoords();
            }
            board.getBoardUI().resetBoardTiles(false, true);

        }
        for (Resource validResource : validResources) {
            validResource.setResource(ResourceEnum.NONE);
        }
        clearValidResourcesWithFlag(building.getType());
        if (buildingEnum != BuildingEnum.TRDINGPST) {
            rArray[coords[0]][coords[1]].setResource(ResourceEnum.OBSTRUCTED);
        }
        else {
            rArray[coords[0]][coords[1]].setResource(ResourceEnum.TPOST);
        }
        bArray[coords[0]][coords[1]] = getBuilding(buildingEnum,buildingArrayList, coords[0], coords[1], true, board);
        if (buildingEnum.isMonument()) {
            board.monumentControl((Monument) bArray[coords[0]][coords[1]]);
        }
        //board.getBoardUI().resetBoardTiles(true, true);

    }

}
