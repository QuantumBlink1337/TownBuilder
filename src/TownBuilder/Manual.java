package TownBuilder;

import TownBuilder.Buildings.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Manual {
        private ArrayList<Building> gameBuildings;
        private Scanner sc = new Scanner(System.in);
    public Manual(ArrayList<Building> b) {
        gameBuildings = b;
    }
    public static void tutorial() throws InterruptedException {
        System.out.println("Welcome to TownBuilder! You are the mayor of a town founded deep in the woods. It's up to you to build the town to success!");
        System.out.println("To do so, you'll be using five resources: Wheat, Glass, Brick, Stone, and Wood. You can place these on the board using coordinates, ");
        System.out.println("like a1, or c4. You can create buildings from patterns of resources to earn points. The goal of the game is to earn as many points as possible!");
        System.out.println("When you successfully design a building, the game will let you decide if you'd like to place it or not");
        System.out.println("For the first two turns, the resource you place will be decided for you. On the third turn, you can choose which resource you need.");
        System.out.println("But be warned! For every resource you leave off the board, you lose a point! It's up to you to design the perfect town.");
        System.out.println("You can reread this prompt as well as access other game information with the 'help' command during your turn.");
        Thread.sleep(8000);
    }
    public void openManual() throws InterruptedException {

        String userInput = "";
        while (!userInput.equals("exit")) {
            System.out.println("Welcome to the TownBuilder Manual. What would you like to learn about? You can read the rules of the game using 'rules', see building info with 'buildings', ");
            System.out.println("or 'exit' to leave the manual.");
            userInput = sc.nextLine().toLowerCase();
            switch (userInput) {
                case "rules":
                    displayRules();
                    userInput = "";
                    break;
                case "buildings":
                    displayBuildingRules();
                    userInput = "";
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Oops! That wasn't a valid command. Please try again.");
                    userInput = "";
                    break;
            }
        }
    }
    public void displayRules() throws InterruptedException {
        String userInput = "";
        while (!userInput.equals("exit")) {
            System.out.println("What rules would you like to read? Type 'placement' for building rules, 'objective' to view the goal of the game, ");
            System.out.println("'scoring' to view how points are earned, 'tutorial' to reread the opening text, or 'exit' to leave the manual.");
            userInput = sc.nextLine().toLowerCase();
            switch (userInput) {
                case "placement":
                    System.out.println("Buildings are made by placing resources on your board. Each building has its own unique pattern of ");
                    System.out.println("what it looks like. The base pattern (see: display patterns) can be rotated and mirrored. For example,");
                    System.out.println("the Cottage can have 8 different orientations! Note: If a building pattern has NONE as part of it, it just means");
                    System.out.println("it doesn't matter what's there!");
                    Thread.sleep(5000);
                    userInput = "";
                    break;
                case "objective":
                    System.out.println("The goal of TownBuilder is to construct as many buildings as possible to earn points.");
                    System.out.println("The game ends when you have nowhere else to place a resource or building. Planning is key!");
                    userInput = "";
                    Thread.sleep(4000);
                    break;
                case "scoring":
                    System.out.println("Each building has it's own unique rules on how they accumulate points. (see: building score) ");
                    System.out.println("But remember - for each resource you leave on the board you LOSE a point! It's up to you ");
                    System.out.println("to figure out how to build your town for the most points possible.");
                    userInput = "";
                    Thread.sleep(4000);
                    break;
                case "tutorial":
                    userInput = "";
                    tutorial();
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Oops! That wasn't a valid command. Please try again.");
                    userInput = "";
                    break;
            }
        }
    }
    private void displayBuildingRules() throws InterruptedException {
        String userInput = "";
        while (!userInput.equals("exit")) {
            System.out.println("What building would you like to learn about? Or type 'exit' to go back.");
            displayBuildings();
            userInput = sc.nextLine().toLowerCase();
            for (int i = 0; i < gameBuildings.size(); i++) {
                if (gameBuildings.get(i).toString().toLowerCase().equals(userInput)) {
                    gameBuildings.get(i).printPattern();
                    Thread.sleep(4000);
                    break;

                }
            }

        }
    }
    public void displayBuildings() {
        System.out.println("The buildings in your game are:");
        for (int i = 0; i < gameBuildings.size(); i++) {
            System.out.println(gameBuildings.get(i).toString());
        }
    }
}
