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
        DebugTools.logging("Getting Building at row: " + row + " and col: " + col);
        if ((row > buildingBoard.length-1 || row < 0) || (col > buildingBoard[row].length-1 || col < 0)) {
            DebugTools.logging("Returning new empty building.");
            return BuildingFactory.getBuilding(BuildingEnum.NONE, null, -1, -1, false);
        }
        else {
            DebugTools.logging("Returning building at specified row and col");
            return buildingBoard[row][col];
        }
    }

    public static Building[] getAdjacentBuildings(Building[][] buildingBoard, int row, int col) throws IOException {
        DebugTools.logging("Retrieving adjacent buildings at row: " + row + " and col: "+col);
        return new Building[]{getBuildingAt(buildingBoard, row-1, col), getBuildingAt(buildingBoard, row+1, col), getBuildingAt(buildingBoard, row, col-1),
        getBuildingAt(buildingBoard, row, col+1)};
    }

    public static Building[] getDiagonalBuildings(Building[][] buildingBoard, int row, int col) throws IOException {
        DebugTools.logging("Retrieving diagonal buildings at row: " + row + " and col: "+col);
        return new Building[]{getBuildingAt(buildingBoard, row-1, col-1), getBuildingAt(buildingBoard, row-1, col+1), getBuildingAt(buildingBoard, row+1, col-1),
                getBuildingAt(buildingBoard, row+1, col+1)};
    }

    public static Building[] getBuildingsInRowAndColumn(Building[][] buildingBoard, int row, int col) throws IOException {
        DebugTools.logging("Retrieving buildings in same row/column " + row + " and col: "+col);

        ArrayList<Building> buildings = new ArrayList<>();
        for (Building[] value : buildingBoard) {
            DebugTools.logging("Retrieving building: "+DebugTools.buildingInformation(value[col]));
            buildings.add(value[col]);

        }
        Collections.addAll(buildings, buildingBoard[row]);
        buildings.removeIf(building -> building.getRow() == row && building.getCol() == col);
        return buildings.toArray(new Building[]{});
    }

    public static Building[] getBuildingsInCorner(Building[][] buildingBoard) throws IOException {
        DebugTools.logging("Retrieving buildings in corner");
        return new Building[]{getBuildingAt(buildingBoard, 0, 0), getBuildingAt(buildingBoard, 0, buildingBoard[0].length -1), getBuildingAt(buildingBoard, buildingBoard.length -1 , 0),
                getBuildingAt(buildingBoard, buildingBoard.length -1, buildingBoard[0].length -1)};
    }

    public static int instancesOfBuilding(Building[] buildings, BuildingEnum ...t) throws IOException {
        int sum = 0;
        DebugTools.logging("Retrieving instances of building types:" +DebugTools.createStringOfObjectsInArray(t));
        for (Building building : buildings) {
            for (BuildingEnum type : t) {
                DebugTools.logging("Checking " + DebugTools.buildingInformation(building));
                if (building.getType() == type) {
                    DebugTools.logging("Found building of type. Incrementing");
                    sum++;
                }
            }
        }
        DebugTools.logging("Returning " + sum + " instances of building types " + DebugTools.createStringOfObjectsInArray(t));
        return sum;
    }
    public static int filteredInstancesOfBuilding(Building[] buildings, Function<Building, Boolean> func, BuildingEnum... t) {
        int sum = 0;
        for (Building building : buildings) {
            for (BuildingEnum type : t) {
                if (building.getType() == type && func.apply(building)) {
                    sum++;
                }
            }
        }
        return sum;
    }
    public static int filteredInstancesOfBuilding(Building[][] buildings, Function<Building, Boolean> func, BuildingEnum... t) {
        int sum = 0;
        for (Building[] buildingRow : buildings) {
            sum+=filteredInstancesOfBuilding(buildingRow,func, t);
        }
        return sum;
    }

    public static int instancesOfBuilding(Building[] buildings, ColorEnum ...c) throws IOException {
        DebugTools.logging("Retrieving instances of color types:" +DebugTools.createStringOfObjectsInArray(c));

        int sum = 0;
        for (Building building : buildings) {
            for (ColorEnum color : c) {
                DebugTools.logging("Checking " + DebugTools.buildingInformation(building));
                if (building.getType().getColor() == color) {
                    DebugTools.logging("Found building of color. Incrementing");
                    sum++;
                }
            }
        }
        DebugTools.logging("Returning " + sum + " instances of color types " + DebugTools.createStringOfObjectsInArray(c));

        return sum;
    }

    public static boolean searchForBuilding(Building[] buildings, BuildingEnum ...t) throws IOException {
        DebugTools.logging("Searching for an instance of building types: " + DebugTools.createStringOfObjectsInArray(t));
        for (Building building : buildings) {
            for (BuildingEnum type : t) {
                DebugTools.logging("Searching building: " + DebugTools.buildingInformation(building));
                if (building.getType() == type) {
                    DebugTools.logging("Found a building. Returning true.");
                    return true;
                }
            }
        }
        DebugTools.logging("Did not find a building. Returning false");
        return false;
    }

    @SuppressWarnings("unused")
    public static boolean searchForBuilding(Building[] buildings, ColorEnum ...c) throws IOException {
        DebugTools.logging("Searching for an instance of building colors: " + DebugTools.createStringOfObjectsInArray(c));
        for (Building building : buildings) {
            for (ColorEnum color : c) {
                DebugTools.logging("Searching building: " + DebugTools.buildingInformation(building));
                if (building.getType().getColor() == color) {
                    DebugTools.logging("Found a building. Returning true.");
                    return true;
                }
            }
        }
        DebugTools.logging("Did not find a building. Returning false");
        return false;
    }

    public static boolean searchForBuilding(Building[] buildings, Function<Building, Boolean> function) throws IOException {
        DebugTools.logging("Searching for instance using custom function: "+function.toString());
        for (Building building : buildings) {
            DebugTools.logging("Searching building: " + DebugTools.buildingInformation(building));
            if (function.apply(building)) {
                DebugTools.logging("Found a building. Returning true.");

                return true;
            }
        }
        DebugTools.logging("Did not find a building. Returning false");

        return false;
    }
    public static int findUniqueBuildingsInGivenList(Building[] buildingsToCheck, Function<BuildingEnum, BuildingEnum> customCondition, ArrayList<Building> masterBuildings) throws IOException {
        int score = 0;
        ArrayList<Building> buildings = new ArrayList<>(masterBuildings);
        for (Building BuildingBeingChecked : buildingsToCheck) {
            DebugTools.logging("Find Uniques: Checking " + DebugTools.buildingInformation(BuildingBeingChecked));
            if (BuildingBeingChecked.getType() != BuildingEnum.NONE) {
                DebugTools.logging("Building is not empty. Checking it.");
                for (Building building : buildings) {
                    DebugTools.logging("Checking master building: " + DebugTools.buildingInformation(building));
                    if (customCondition != null) {
                        DebugTools.logging("Custom Condition is not null");
                        if (applyCondition(customCondition, BuildingBeingChecked, buildings)) {
                            DebugTools.logging("Custom condition satisfied and building is unique. Incrementing score.");
                            buildings.get(0).setCondition(true);
                            score++;
                        }
                    }
                    if (BuildingBeingChecked.getType() == building.getType() && !building.getCondition()) {
                        building.setCondition(true);
                        DebugTools.logging("Building is unique. Incrementing score.");
                        score++;
                    }
                    else {
                        DebugTools.logging("No matches found. Moving on.");
                    }
                }
            }
        }
        return score;
    }
    private static boolean applyCondition(Function<BuildingEnum, BuildingEnum> condition, Building buildingBeingChecked, ArrayList<Building> masterBuildings) {
        return condition.apply(buildingBeingChecked.getType()) == BuildingEnum.BARRETT && !masterBuildings.get(0).getCondition();
    }
}
