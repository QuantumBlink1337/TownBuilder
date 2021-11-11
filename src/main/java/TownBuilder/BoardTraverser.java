package TownBuilder;

import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class BoardTraverser {
    public static Building getBuildingAt(Building[][] buildingBoard, int row, int col) {
        if ((row > buildingBoard.length-1 || row < 0) || (col > buildingBoard[row].length-1 || col < 0)) {
            return BuildingFactory.getBuilding(BuildingEnum.NONE, null, -1, -1, false);
        }
        else {
            return buildingBoard[row][col];
        }
    }

    public static Building[] getAdjacentBuildings(Building[][] buildingBoard, int row, int col) {
        return new Building[]{getBuildingAt(buildingBoard, row-1, col), getBuildingAt(buildingBoard, row+1, col), getBuildingAt(buildingBoard, row, col-1),
        getBuildingAt(buildingBoard, row, col+1)};
    }

    public static Building[] getDiagonalBuildings(Building[][] buildingBoard, int row, int col) {
        return new Building[]{getBuildingAt(buildingBoard, row-1, col-1), getBuildingAt(buildingBoard, row-1, col+1), getBuildingAt(buildingBoard, row+1, col-1),
                getBuildingAt(buildingBoard, row+1, col+1)};
    }

    public static Building[] getBuildingsInRowAndColumn(Building[][] buildingBoard, int row, int col) {
        ArrayList<Building> buildings = new ArrayList<>();
        for (Building[] value : buildingBoard) {
            buildings.add(value[col]);
        }
        Collections.addAll(buildings, buildingBoard[row]);
        buildings.removeIf(building -> building.getRow() == row && building.getCol() == col);
        return buildings.toArray(new Building[]{});
    }

    public static Building[] getBuildingsInCorner(Building[][] buildingBoard) {
        return new Building[]{getBuildingAt(buildingBoard, 0, 0), getBuildingAt(buildingBoard, 0, buildingBoard[0].length -1), getBuildingAt(buildingBoard, buildingBoard.length -1 , 0),
                getBuildingAt(buildingBoard, buildingBoard.length -1, buildingBoard[0].length -1)};
    }

    public static int instancesOfBuilding(Building[] buildings, BuildingEnum ...t) {
        int sum = 0;
        for (Building building : buildings) {
            for (BuildingEnum type : t) {
                if (building.getType() == type) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public static int instancesOfBuilding(Building[] buildings, ColorEnum ...c) {
        int sum = 0;
        for (Building building : buildings) {
            for (ColorEnum color : c) {
                if (building.getType().getColor() == color) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public static boolean searchForBuilding(Building[] buildings, BuildingEnum ...t) {
        for (Building building : buildings) {
            for (BuildingEnum type : t) {
                if (building.getType() == type) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unused")
    public static boolean searchForBuilding(Building[] buildings, ColorEnum ...c) {
        for (Building building : buildings) {
            for (ColorEnum color : c) {
                if (building.getType().getColor() == color) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean searchForBuilding(Building[] buildings, Function<Building, Boolean> function) {
        for (Building building : buildings) {
            if (function.apply(building)) {
                return true;
            }
        }
        return false;
    }
}
