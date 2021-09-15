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
    public static boolean prompt() {
        String prompt = "";
        Scanner sc = new Scanner(System.in);
        prompt = sc.nextLine().toLowerCase();
        if (prompt.equals("y") || prompt.equals("yes")) {
            return true;
        }
        else {
            return false;
        }
    }
}
