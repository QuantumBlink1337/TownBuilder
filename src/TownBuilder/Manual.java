package TownBuilder;

import TownBuilder.Buildings.Building;

import java.util.Scanner;

public class Manual {
        private Building[] gameBuildings;
        private Scanner sc = new Scanner(System.in);
    public Manual(Building[] b) {
        gameBuildings = b;
        System.out.println("New manual created.");
    }
    public void displayBuildingPatterns() {
        String userInput = "";
        while (userInput == "") {
            System.out.println("What building would you like to view?");

        }

    }

}
