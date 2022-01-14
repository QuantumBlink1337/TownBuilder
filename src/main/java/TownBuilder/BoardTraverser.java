package TownBuilder;

import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.DebugApps.DebugTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class BoardTraverser{
    public static Building getBuildingAt(Building[][] buildingBoard, int row, int col) throws IOException {
        DebugTools.logging("[GET_BUILDING_AT] - Getting Building at row: " + row + " and col: " + col);
        if ((row > buildingBoard.length-1 || row < 0) || (col > buildingBoard[row].length-1 || col < 0)) {
            DebugTools.logging("[GET_BUILDING_AT] - Returning new empty building.");
            return BuildingFactory.getBuilding(BuildingEnum.NONE, null, -1, -1, false, null);
        }
        else {
            DebugTools.logging("[GET_BUILDING_AT] - Returning building at specified row and col");
            return buildingBoard[row][col];
        }
    }

    public static Building[] getAdjacentBuildings(Building[][] buildingBoard, int row, int col) throws IOException {
        DebugTools.logging("[GET_ADJACENT_BUILDINGS] - Retrieving adjacent buildings at row: " + row + " and col: "+col);
        return new Building[]{getBuildingAt(buildingBoard, row-1, col), getBuildingAt(buildingBoard, row+1, col), getBuildingAt(buildingBoard, row, col-1),
        getBuildingAt(buildingBoard, row, col+1)};
    }

    public static Building[] getDiagonalBuildings(Building[][] buildingBoard, int row, int col) throws IOException {
        DebugTools.logging("[GET_DIAGONAL_BUILDINGS] - Retrieving diagonal buildings at row: " + row + " and col: "+col);
        return new Building[]{getBuildingAt(buildingBoard, row-1, col-1), getBuildingAt(buildingBoard, row-1, col+1), getBuildingAt(buildingBoard, row+1, col-1),
                getBuildingAt(buildingBoard, row+1, col+1)};
    }

    public static Building[] getBuildingsInRowAndColumn(Building[][] buildingBoard, int row, int col) throws IOException {
        DebugTools.logging("[GET_BUILDINGS_ROW_COLUMN] - Retrieving buildings in same row/column " + row + " and col: "+col);
        ArrayList<Building> buildings = new ArrayList<>();
        for (Building[] value : buildingBoard) {
            buildings.add(value[col]);

        }
        Collections.addAll(buildings, buildingBoard[row]);
        buildings.removeIf(building -> building.getRow() == row && building.getCol() == col);
        return buildings.toArray(new Building[]{});
    }

    public static Building[] getBuildingsInCorner(Building[][] buildingBoard) throws IOException {
        DebugTools.logging("[GET_BUILDINGS_CORNER] - Retrieving buildings in corner");
        return new Building[]{getBuildingAt(buildingBoard, 0, 0), getBuildingAt(buildingBoard, 0, buildingBoard[0].length -1), getBuildingAt(buildingBoard, buildingBoard.length -1 , 0),
                getBuildingAt(buildingBoard, buildingBoard.length -1, buildingBoard[0].length -1)};
    }

    public static int instancesOfBuilding(Building[] buildings, BuildingEnum ...t) throws IOException {
        int sum = 0;
        DebugTools.logging("[INSTANCES_OF_BUILDING] - Retrieving instances of building types:" +DebugTools.createStringOfObjectsInArray(t));
        for (Building building : buildings) {
            for (BuildingEnum type : t) {
                DebugTools.logging("[INSTANCES_OF_BUILDING] - Checking " + DebugTools.buildingInformation(building));
                if (building.getType() == type) {
                    DebugTools.logging("[INSTANCES_OF_BUILDING] - Found building of type. Incrementing");
                    sum++;
                }
            }
        }
        DebugTools.logging("[INSTANCES_OF_BUILDING] - Returning " + sum + " instances of building types " + DebugTools.createStringOfObjectsInArray(t));
        return sum;
    }
//    public static int filteredInstancesOfBuilding(Building[] buildings, Function<Building, Boolean> func, BuildingEnum... t) throws IOException {
//        int sum = 0;
//        DebugTools.logging("[FILTERED_INSTANCES_OF_BUILDING] - Retrieving instances of building types:" +DebugTools.createStringOfObjectsInArray(t));
//        for (Building building : buildings) {
//            for (BuildingEnum type : t) {
//                DebugTools.logging("[FILTERED_INSTANCES_OF_BUILDING] - Checking " + DebugTools.buildingInformation(building));
//                if (building.getType() == type && func.apply(building)) {
//                    sum++;
//                    DebugTools.logging("[FILTERED_INSTANCES_OF_BUILDING] - Found building of type. Incrementing");
//
//                }
//            }
//        }
//        return sum;
//    }
//    public static int filteredInstancesOfBuilding(Building[][] buildings, Function<Building, Boolean> func, BuildingEnum... t) throws IOException {
//        int sum = 0;
//        for (Building[] buildingRow : buildings) {
//            sum+=filteredInstancesOfBuilding(buildingRow,func, t);
//        }
//        return sum;
//    }

    public static int instancesOfBuilding(Building[] buildings, ColorEnum ...c) throws IOException {
        DebugTools.logging("[INSTANCES_OF_BUILDING] - Retrieving instances of color types:" +DebugTools.createStringOfObjectsInArray(c));

        int sum = 0;
        for (Building building : buildings) {
            for (ColorEnum color : c) {
                DebugTools.logging("[INSTANCES_OF_BUILDING] - Checking " + DebugTools.buildingInformation(building));
                if (building.getType().getColor() == color) {
                    DebugTools.logging("[INSTANCES_OF_BUILDING] - Found building of color. Incrementing");
                    sum++;
                }
            }
        }
        DebugTools.logging("[INSTANCES_OF_BUILDING] - Returning " + sum + " instances of color types " + DebugTools.createStringOfObjectsInArray(c));

        return sum;
    }

    public static boolean searchForBuilding(Building[] buildings, BuildingEnum ...t) throws IOException {
        DebugTools.logging("[SEARCH_FOR_BUILDING] - Searching for an instance of building types: " + DebugTools.createStringOfObjectsInArray(t));
        for (Building building : buildings) {
            for (BuildingEnum type : t) {
                DebugTools.logging("[SEARCH_FOR_BUILDING] - Searching building: " + DebugTools.buildingInformation(building));
                if (building.getType() == type) {
                    DebugTools.logging("[SEARCH_FOR_BUILDING] - Found a building. Returning true.");
                    return true;
                }
            }
        }
        DebugTools.logging("[SEARCH_FOR_BUILDING] - Did not find a building. Returning false");
        return false;
    }

    @SuppressWarnings("unused")
    public static boolean searchForBuilding(Building[] buildings, ColorEnum ...c) throws IOException {
        DebugTools.logging("[SEARCH_FOR_BUILDING] - Searching for an instance of building colors: " + DebugTools.createStringOfObjectsInArray(c));
        for (Building building : buildings) {
            for (ColorEnum color : c) {
                DebugTools.logging("[SEARCH_FOR_BUILDING] - Searching building: " + DebugTools.buildingInformation(building));
                if (building.getType().getColor() == color) {
                    DebugTools.logging("[SEARCH_FOR_BUILDING] - Found a building. Returning true.");
                    return true;
                }
            }
        }
        DebugTools.logging("[SEARCH_FOR_BUILDING] - Did not find a building. Returning false");
        return false;
    }

    public static boolean searchForBuilding(Building[] buildings, Function<Building, Boolean> function) throws IOException {
        DebugTools.logging("[SEARCH_FOR_BUILDING] - Searching for instance using custom function: "+function.toString());
        for (Building building : buildings) {
            DebugTools.logging("[SEARCH_FOR_BUILDING] - Searching building: " + DebugTools.buildingInformation(building));
            if (function.apply(building)) {
                DebugTools.logging("[SEARCH_FOR_BUILDING] - Found a building. Returning true.");

                return true;
            }
        }
        DebugTools.logging("[SEARCH_FOR_BUILDING] - Did not find a building. Returning false");

        return false;
    }
    public static int findUniqueBuildingsInGivenList(Building[] buildingsToCheck, Function<BuildingEnum, BuildingEnum> customCondition, ArrayList<Building> masterBuildings) throws IOException {
        int score = 0;
        DebugTools.logging("[FIND_UNIQUE_BUILDINGS] - Searching given list for unique instances of buildings.");
        ArrayList<Building> buildings = new ArrayList<>(masterBuildings);
        for (Building BuildingBeingChecked : buildingsToCheck) {
            DebugTools.logging("[FIND_UNIQUE_BUILDINGS] - Find Uniques: Checking " + DebugTools.buildingInformation(BuildingBeingChecked));
            if (BuildingBeingChecked.getType() != BuildingEnum.NONE) {
                DebugTools.logging("[FIND_UNIQUE_BUILDINGS] - Building is not empty. Checking it.");
                for (Building building : buildings) {
                    DebugTools.logging("[FIND_UNIQUE_BUILDINGS] - Checking master building: " + DebugTools.buildingInformation(building));
                    if (customCondition != null) {
                        DebugTools.logging("[FIND_UNIQUE_BUILDINGS] - Custom Condition is not null");
                        if (applyCondition(customCondition, BuildingBeingChecked, buildings)) {
                            DebugTools.logging("[FIND_UNIQUE_BUILDINGS] - Custom condition satisfied and building is unique. Incrementing score.");
                            buildings.get(0).setCondition(true);
                            score++;
                        }
                    }
                    if (BuildingBeingChecked.getType() == building.getType() && !building.getCondition()) {
                        building.setCondition(true);
                        DebugTools.logging("[FIND_UNIQUE_BUILDINGS] - Building is unique. Incrementing score.");
                        score++;
                    }
                    else {
                        DebugTools.logging("[FIND_UNIQUE_BUILDINGS] - No matches found. Moving on.");
                    }
                }
            }
        }
        return score;
    }
    public static int findUniqueBuildingsInGivenList(Building[][] buildingsToCheck, Function<BuildingEnum, BuildingEnum> customCondition, ArrayList<Building> masterBuildings) throws IOException {
        int sum = 0;
        for (Building[] row : buildingsToCheck) {
            sum += findUniqueBuildingsInGivenList(row, customCondition, masterBuildings);
        }
        return sum;
    }
    private static boolean applyCondition(Function<BuildingEnum, BuildingEnum> condition, Building buildingBeingChecked, ArrayList<Building> masterBuildings) {
        return condition.apply(buildingBeingChecked.getType()) == BuildingEnum.BARRETT && !masterBuildings.get(0).getCondition();
    }
}
