package TownBuilder;

import TownBuilder.Buildings.*;

import java.util.Scanner;

public class Manual {
        private Building[] gameBuildings = new Building[7];
        private Scanner sc = new Scanner(System.in);
    public Manual(Building[] b) {
        gameBuildings = b;
        System.out.println("New manual created.");
    }
    public void displayBuildingPatterns() {
        String userInput = "";
        BuildingEnum choice;

        while (userInput == "") {
            System.out.println("What building would you like to view?");
            System.out.println("The buildings in your game are:");
            for (int i = 0; i < gameBuildings.length; i++) {
                System.out.println(gameBuildings[i].wordDefinition());
            }
            userInput = sc.nextLine().toLowerCase();
            switch (userInput) {
                case "cottage":
                    Cottage.printPattern();
                    break;
                case "farm":
                    Farm.printPattern();
                    break;
                case "well":
                    Well.printPattern();
                    break;
                case "chapel":
                    Chapel.printPattern();
                    break;
                case "warehouse":
                    Warehouse.printPattern();
                    break;
                case "theater":
                    Theater.printPattern();
                    break;
                case "tavern":
                    Tavern.printPattern();
                    break;
            }

        }

    }

}
