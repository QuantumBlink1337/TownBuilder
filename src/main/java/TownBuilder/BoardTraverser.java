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
        DebugTools.logging("Getting Building at row: " + row + " and col: " + col, 1);
        if ((row > buildingBoard.length-1 || row < 0) || (col > buildingBoard[row].length-1 || col < 0)) {
            DebugTools.logging("Returning new empty building.", 2);
            return BuildingFactory.getBuilding(BuildingEnum.NONE, null, -1, -1, false);
        }
        else {
            DebugTools.logging("Returning building at specified row and col", 2);
            return buildingBoard[row][col];
        }
    }

    public static Building[] getAdjacentBuildings(Building[][] buildingBoard, int row, int col) throws IOException {
        DebugTools.logging("Retrieving adjacent buildings at row: " + row + " and col: "+col, 1);
        return new Building[]{getBuildingAt(buildingBoard, row-1, col), getBuildingAt(buildingBoard, row+1, col), getBuildingAt(buildingBoard, row, col-1),
        getBuildingAt(buildingBoard, row, col+1)};
    }

    public static Building[] getDiagonalBuildings(Building[][] buildingBoard, int row, int col) throws IOException {
        DebugTools.logging("Retrieving diagonal buildings at row: " + row + " and col: "+col, 1);
        return new Building[]{getBuildingAt(buildingBoard, row-1, col-1), getBuildingAt(buildingBoard, row-1, col+1), getBuildingAt(buildingBoard, row+1, col-1),
                getBuildingAt(buildingBoard, row+1, col+1)};
    }

    public static Building[] getBuildingsInRowAndColumn(Building[][] buildingBoard, int row, int col) throws IOException {
        DebugTools.logging("Retrieving buildings in same row/column " + row + " and col: "+col, 1);

        ArrayList<Building> buildings = new ArrayList<>();
        for (Building[] value : buildingBoard) {
            DebugTools.logging("Retrieving building: "+DebugTools.buildingInformation(value[col]), 1);
            buildings.add(value[col]);

        }
        Collections.addAll(buildings, buildingBoard[row]);
        buildings.removeIf(building -> building.getRow() == row && building.getCol() == col);
        return buildings.toArray(new Building[]{});
    }

    public static Building[] getBuildingsInCorner(Building[][] buildingBoard) throws IOException {
        DebugTools.logging("Retrieving buildings in corner", 1);
        return new Building[]{getBuildingAt(buildingBoard, 0, 0), getBuildingAt(buildingBoard, 0, buildingBoard[0].length -1), getBuildingAt(buildingBoard, buildingBoard.length -1 , 0),
                getBuildingAt(buildingBoard, buildingBoard.length -1, buildingBoard[0].length -1)};
    }

    public static int instancesOfBuilding(Building[] buildings, BuildingEnum ...t) throws IOException {
        int sum = 0;
        DebugTools.logging("Retrieving instances of building types:" +DebugTools.createStringOfObjectsInArray(t), 1);
        for (Building building : buildings) {
            for (BuildingEnum type : t) {
                DebugTools.logging("Checking " + DebugTools.buildingInformation(building), 3);
                if (building.getType() == type) {
                    DebugTools.logging("Found building of type. Incrementing", 2);
                    sum++;
                }
            }
        }
        DebugTools.logging("Returning " + sum + " instances of building types " + DebugTools.createStringOfObjectsInArray(t), 2);
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
        DebugTools.logging("Retrieving instances of color types:" +DebugTools.createStringOfObjectsInArray(c), 1);

        int sum = 0;
        for (Building building : buildings) {
            for (ColorEnum color : c) {
                DebugTools.logging("Checking " + DebugTools.buildingInformation(building), 3);
                if (building.getType().getColor() == color) {
                    DebugTools.logging("Found building of color. Incrementing", 2);
                    sum++;
                }
            }
        }
        DebugTools.logging("Returning " + sum + " instances of color types " + DebugTools.createStringOfObjectsInArray(c), 2);

        return sum;
    }

    public static boolean searchForBuilding(Building[] buildings, BuildingEnum ...t) throws IOException {
        DebugTools.logging("Searching for an instance of building types: " + DebugTools.createStringOfObjectsInArray(t), 1);
        for (Building building : buildings) {
            for (BuildingEnum type : t) {
                DebugTools.logging("Searching building: " + DebugTools.buildingInformation(building), 3);
                if (building.getType() == type) {
                    DebugTools.logging("Found a building. Returning true.", 2);
                    return true;
                }
            }
        }
        DebugTools.logging("Did not find a building. Returning false", 2);
        return false;
    }

    @SuppressWarnings("unused")
    public static boolean searchForBuilding(Building[] buildings, ColorEnum ...c) throws IOException {
        DebugTools.logging("Searching for an instance of building colors: " + DebugTools.createStringOfObjectsInArray(c), 1);
        for (Building building : buildings) {
            for (ColorEnum color : c) {
                DebugTools.logging("Searching building: " + DebugTools.buildingInformation(building), 3);
                if (building.getType().getColor() == color) {
                    DebugTools.logging("Found a building. Returning true.", 2);
                    return true;
                }
            }
        }
        DebugTools.logging("Did not find a building. Returning false", 2);
        return false;
    }

    public static boolean searchForBuilding(Building[] buildings, Function<Building, Boolean> function) throws IOException {
        DebugTools.logging("Searching for instance using custom function: "+function.toString(), 1);
        for (Building building : buildings) {
            DebugTools.logging("Searching building: " + DebugTools.buildingInformation(building), 3);
            if (function.apply(building)) {
                DebugTools.logging("Found a building. Returning true.", 2);

                return true;
            }
        }
        DebugTools.logging("Did not find a building. Returning false", 2);

        return false;
    }
    public static int findUniqueBuildingsInGivenList(Building[] buildingsToCheck, Function<BuildingEnum, BuildingEnum> customCondition, ArrayList<Building> masterBuildings) throws IOException {
        int score = 0;
        ArrayList<Building> buildings = new ArrayList<>(masterBuildings);
        for (Building BuildingBeingChecked : buildingsToCheck) {
            DebugTools.logging("Find Uniques: Checking " + DebugTools.buildingInformation(BuildingBeingChecked), 3);
            if (BuildingBeingChecked.getType() != BuildingEnum.NONE) {
                DebugTools.logging("Building is not empty. Checking it.", 3);
                for (Building building : buildings) {
                    DebugTools.logging("Checking master building: " + DebugTools.buildingInformation(building), 3);
                    if (customCondition.apply(BuildingBeingChecked.getType()) == BuildingEnum.BARRETT && !buildings.get(0).getCondition()) {
                        DebugTools.logging("Custom condition satisfied and building is unique. Incrementing score.", 2);
                        buildings.get(0).setCondition(true);
                        score++;
                    }
                    else if (BuildingBeingChecked.getType() == building.getType() && !building.getCondition()) {
                        building.setCondition(true);
                        DebugTools.logging("Building is unique. Incrementing score.", 2);

                        score++;

                    }
                }
            }
        }
        return score;
    }
}
