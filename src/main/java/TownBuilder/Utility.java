package TownBuilder;

import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.Buildings.EmptyBuilding;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import com.diogonunes.jcolor.*;

public class Utility {
    private static final Scanner sc = new Scanner(System.in);
    public static <T> void arrayPrinter(T[][] array) {
        for (T[] ts : array) {
            for (int col = 0; col < ts.length; col++) {
                if (col == (ts.length - 1)) {
                    System.out.println("[" + ts[col] + "]");
                } else {
                    System.out.print("[" + ts[col] + "]");
                }
            }
        }
    }
    public static <T> void printMembersOfArrayList(ArrayList<T> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
    }
    public static <T> void printMembersof3dArrayList(ArrayList<T[][]> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            arrayPrinter(arrayList.get(i));
        }
    }
    public static AnsiFormat generateColors(Building building, Resource resource) {
        if (building != null) {
            return new AnsiFormat(building.getType().getColor().getTextColor(), Attribute.BOLD());
        }
        else if (resource != null){
            return new AnsiFormat(resource.getResource().getColor().getTextColor());
        }
        else {
            throw new NullPointerException();
        }
    }
    public static void displayValidResources(Resource[][] rArray, BuildingFactory buildingFactory) {

        for (int r = 0; r < rArray.length; r++) {
            for (int c = 0; c < rArray[r].length; c++) {
                for (int i = 0; i < buildingFactory.getValidResources().size(); i++) {
                    if (rArray[r][c] == buildingFactory.getValidResources().get(i) && i != (buildingFactory.getValidResources().size() -1)) {
                        System.out.print(Utility.coordsToOutput(r, c) + ", ");
                    }
                    else if (rArray[r][c] == buildingFactory.getValidResources().get(i)) {
                        System.out.println(Utility.coordsToOutput(r, c));
                    }
                }

            }
        }
    }
    public static String lengthResizer(String t, int length) {
        StringBuilder target = new StringBuilder(t);
        int targetLength = target.length();
        for (int i = 0; i < length - targetLength; i++) {
            target.append(" ");
        }
        return target.toString();
    }
    public static Building getBuildingAt(Building[][] buildingBoard, int row, int col) {
        if ((row > buildingBoard.length-1 || row < 0) || (col > buildingBoard[row].length-1 || col < 0)) {
            return BuildingFactory.getBuilding(BuildingEnum.NONE, null, -1, -1);
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
    public static ArrayList<Building> getBuildingsInRowAndColumn(Building[][] buildingBoard, int row, int col) {
        ArrayList<Building> buildings = new ArrayList<>();
        for (Building[] value : buildingBoard) {
            buildings.add(value[col]);
        }
        Collections.addAll(buildings, buildingBoard[row]);
        return buildings;
    }
    public static String lowerCaseLetters(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
    public static Building boardParser(BuildingEnum buildingEnum, Building[][] bArray) {
        for (Building[] buildings : bArray) {
            for (Building building : buildings) {
                if (buildingEnum == building.getType()) {
                    return building;
                }
            }
        }
        return new EmptyBuilding();
    }
    public static int[] inputToCoords(String i) {
        String[] input = i.split("", 2);
        int[] coords = new int[2];
        switch (input[0]) {
            case "a": //System.out.println("Case A");
                break;
            case "b": //System.out.println("Case B");
                    coords[1] = 1;
                    break;
            case "c": //System.out.println("Case C");
                    coords[1] = 2;
                    break;
            case "d": //System.out.println("Case D");
                    coords[1] = 3;
                    break;
            default: coords[1] = -1;
                break;

        }
        switch (input[1]) {
            case "1": //System.out.println("Case 0");
                break;
            case "2": //.out.println("Case 1");
                    coords[0] = 1;
                    break;
            case "3": //System.out.println("Case 2");
                    coords[0] = 2;
                    break;
            case "4": //System.out.println("Case 3");
                    coords[0] = 3;
                    break;
            default: coords[0] = -1;
                    break;
        }
        return coords;
    }
    public static String coordsToOutput(int r, int c) {
        String row = "";
        String col = "";
        switch (r) {
            case 0: //System.out.println("Case A");
                    row = "1";
                break;
            case 1: //System.out.println("Case B");
                    row = "2";
                break;
            case 2: //System.out.println("Case C");
                    row = "3";
                break;
            case 3: //System.out.println("Case D");
                    row = "4";
                break;
        }
        switch (c) {
            case 0: //System.out.println("Case 0");
                    col = "a";
                break;
            case 1: //.out.println("Case 1");
                    col = "b";
                break;
            case 2: //System.out.println("Case 2");
                    col = "c";
                break;
            case 3: //System.out.println("Case 3");
                    col = "d";
                break;
        }
        return col+row;
    }
    public static boolean prompt() {

        do {
            String prompt = sc.nextLine().toLowerCase();
            if (prompt.equals("y") || prompt.equals("yes")) {
                return true;
            }
            else if (prompt.equals("n") || prompt.equals("no"))  {
                return false;
            }
            else {
                System.out.println("Invalid input. Please use yes or no. (y or n)");
            }
        }
        while(true);
    }
    public static void anyKey() {
        System.out.println("Press any key to continue.");
        sc.nextLine();
    }


}