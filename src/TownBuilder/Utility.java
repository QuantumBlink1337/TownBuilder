package TownBuilder;

import TownBuilder.Buildings.EmptyBuilding;
import TownBuilder.Buildings.Warehouse;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;

import java.util.Scanner;

public class Utility {
    public static <T> void arrayPrinter(T[][] array) {
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length; col++) {
                if (array.length == 4) {
                    if (col == 3) {
                        System.out.println(array[row][col]);
                    }
                    else {
                        System.out.print(array[row][col]);
                    }
                }
                else if (array.length == 2) {
                    if (col == 1) {
                        System.out.println(array[row][col]);
                    }
                    else {
                        System.out.print(array[row][col]);
                    }
                }
            }
        }
    }
    public static void log(String s, int level) {}
    public static String lowerCaseLetters(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
    public static Building boardParser(BuildingEnum buildingEnum, Building[][] bArray) {
        for (int r = 0; r < bArray.length; r++) {
            for (int c = 0; c < bArray[r].length; c++) {
                if (buildingEnum == bArray[r][c].getType()) {
                    return bArray[r][c];
                }
            }
        }
        return new EmptyBuilding();
    }
    public static int[] inputToCoords(String i) {
        String[] input = i.split("", 2);
        int[] coords = new int[2];
        switch (input[0]) {
            case "a" -> //System.out.println("Case A");
                    coords[1] = 0;
            case "b" -> //System.out.println("Case B");
                    coords[1] = 1;
            case "c" -> //System.out.println("Case C");
                    coords[1] = 2;
            case "d" -> //System.out.println("Case D");
                    coords[1] = 3;
        }
        switch (input[1]) {
            case "1" -> //System.out.println("Case 0");
                    coords[0] = 0;
            case "2" -> //.out.println("Case 1");
                    coords[0] = 1;
            case "3" -> //System.out.println("Case 2");
                    coords[0] = 2;
            case "4" -> //System.out.println("Case 3");
                    coords[0] = 3;
        }
        return coords;
    }
    public static String coordsToOutput(int r, int c) {
        String row = "";
        String col = "";
        switch (r) {
            case 0 -> //System.out.println("Case A");
                    row = "1";
            case 1 -> //System.out.println("Case B");
                    row = "2";
            case 2 -> //System.out.println("Case C");
                    row = "3";
            case 3 -> //System.out.println("Case D");
                    row = "4";
        }
        switch (c) {
            case 0 -> //System.out.println("Case 0");
                    col = "a";
            case 1 -> //.out.println("Case 1");
                    col = "b";
            case 2 -> //System.out.println("Case 2");
                    col = "c";
            case 3 -> //System.out.println("Case 3");
                    col = "d";
        }
        return col+row;
    }
    public static boolean prompt() {
        String prompt = "";
        Scanner sc = new Scanner(System.in);
        prompt = sc.nextLine().toLowerCase();
        do {
            if (prompt.equals("y") || prompt.equals("yes")) {
                return true;
            }
            else if (prompt.equals("n") || prompt.equals("no"))  {
                return false;
            }
            else {
                System.out.println("Invalid input. Please use yes or no. (y or n)");
                prompt = sc.nextLine().toLowerCase();
            }
        }
        while(true);
    }
}
