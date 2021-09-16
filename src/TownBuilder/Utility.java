package TownBuilder;

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
        }
        while(true);
    }
}
