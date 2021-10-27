package TownBuilder;

import TownBuilder.Buildings.*;
import com.diogonunes.jcolor.Ansi;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
public class Manual {
        private final ArrayList<Building> gameBuildings;
        private final Scanner sc = new Scanner(System.in);
    public Manual(ArrayList<Building> b) {
        gameBuildings = b;
    }
    public static void tutorial() {
        System.out.println("Welcome to TownBuilder! You are the mayor of a town founded deep in the woods. It's up to you to build the town to success!");
        System.out.println("To do so, you'll be using five resources: Wheat, Glass, Brick, Stone, and Wood. You can place these on the board using coordinates, ");
        System.out.println("like a1, or c4. You can create buildings from patterns of resources to earn points. The goal of the game is to earn as many points as possible!");
        System.out.println("When you successfully design a building, the game will let you decide if you'd like to place it or not");
        System.out.println("But be warned! For every resource you leave off the board, you lose a point! It's up to you to design the perfect town.");
        System.out.println("You can reread this prompt as well as access other game information with the 'help' command during your turn.");
        Utility.anyKey();
    }
    public static void townHallNote() {
        System.out.println("Town Hall Mode enabled!");
        System.out.println("This is the Singleplayer mode of TownBuilder. Every two turns you'll have your resource picked for you.");
        System.out.println("On the third turn, you may choose the resource for your choice!");
        Utility.anyKey();
    }
    public void openManual() throws IOException, URISyntaxException {

        String userInput = "";
        while (!userInput.equals("exit")) {
            System.out.println("Welcome to the TownBuilder Manual. What would you like to learn about? You can read the rules of the game using 'rules', see building info with 'buildings', ");
            System.out.println("or 'exit' to leave the manual. You can also use 'internet' to download a copy of the manual to your computer.");
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
                case "internet":
                    openManualOnInternet();
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
    public void openManualOnInternet() throws IOException, URISyntaxException {
        System.out.println("This will open a link in your default web browser that will download a copy of the manual as a text file. Continue?");
        if (Utility.prompt()) {
            // ah yes, the floor is made of floor...
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI("https://drive.google.com/uc?export=download&id=1_otans2D6rnIKLWtAdEaG2_afLfEVhq7"));
            }
            catch (HeadlessException e) {
                System.out.println("This command doesn't work in headless environments :( Probably a WSL user? Try running in your OS's default shell.");
                e.printStackTrace();
            }
        }
    }

    public void displayRules() {
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
                    Utility.anyKey();
                    userInput = "";
                    break;
                case "objective":
                    System.out.println("The goal of TownBuilder is to construct as many buildings as possible to earn points.");
                    System.out.println("The game ends when you have nowhere else to place a resource or building. Planning is key!");
                    userInput = "";
                    Utility.anyKey();
                    break;
                case "scoring":
                    System.out.println("Each building has it's own unique rules on how they accumulate points. (see: building score) ");
                    System.out.println("But remember - for each resource you leave on the board you LOSE a point! It's up to you ");
                    System.out.println("to figure out how to build your town for the most points possible.");
                    userInput = "";
                    Utility.anyKey();
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
    private void displayBuildingRules() {
        String userInput = "";
        while (!userInput.equals("exit")) {
            System.out.println("What building would you like to learn about? Or type 'exit' to go back.");
            displayBuildings();
            userInput = sc.nextLine().toLowerCase();
            for (Building gameBuilding : gameBuildings) {
                if (gameBuilding.toString().toLowerCase().equals(userInput)) {
                    gameBuilding.printManualText();
                    Utility.anyKey();
                    break;

                }
            }

        }
    }
    public void displayBuildings() {
        System.out.println("The buildings in your game are:");
        for (Building gameBuilding : gameBuildings) {
            System.out.println(Ansi.colorize(gameBuilding.toString(), Utility.generateColors(gameBuilding, (Resource) null)));
        }
    }
}
