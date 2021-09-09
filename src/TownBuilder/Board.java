package TownBuilder;


import TownBuilder.Buildings.BlueBuilding;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.RedBuilding;

import java.util.Scanner;

public class Board {
    private final Player player;
    private TownResource[][] gameResourceBoard = new TownResource[4][4];
    private Building[][] gameBuildingBoard = new Building[4][4];
    private String[][] gameBoard = new String[4][4];
    private String[][] coordinateBoard = new String[4][4];
    private final char[] letterCoords = {' ', 'a', 'b', 'c', 'd'};
    private final char[] numberCoords = {'1', '2', '3','4'};
    private boolean gameCompletion;
    private Scanner sc = new Scanner(System.in);

    private boolean redBuildingDetection;
    private boolean blueBuildingDetection;

    public Board() {
        player = new Player();
        gameCompletion = false;
        buildArrays();
    }
    public void buildArrays() {
        //System.out.println("Building resource array");
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                gameResourceBoard[row][col] = new TownResource(ResourceEnum.NONE);
                //System.out.println(gameResourceBoard[row][col]);
            }
        }
        for (int row = 0; row < gameBuildingBoard.length; row++) {
            for (int col = 0; col < gameBuildingBoard[row].length; col++) {
                gameBuildingBoard[row][col] = new Building(BuildingEnum.NONE, BuildingColor.NULL);
                //System.out.println(gameResourceBoard[row][col]);
            }
        }
        for (int row = 0; row < coordinateBoard.length; row++) {
            for (int col = 0; col < coordinateBoard[row].length; col++) {
                coordinateBoard[row][col] = "[Row: "+row+" Col: "+col+"]";
            }
        }


    }
    public void game() {
        while (!gameCompletion) {
            renderBoard();
            playerTurn();
//            gameResourceBoard[0][3].setResource(ResourceEnum.GLASS);
//            gameResourceBoard[0][2].setResource(ResourceEnum.WHEAT);
//            gameResourceBoard[1][3].setResource(ResourceEnum.BRICK);
            detection();
            //renderBoard();
       //     gameCompletion = true;

        }
    }
    public void detection() {
        String userInput = "";
        redBuildingDetection = false;
        blueBuildingDetection = false;
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {

                if (gameResourceBoard[row][col].getResource() != ResourceEnum.NONE && !redBuildingDetection && !blueBuildingDetection) {
                    System.out.println("Scanning row: " + row + " and column: " + col);
                   // redBuildingDetection = RedBuilding.redDetection(row, col, gameResourceBoard);
                    blueBuildingDetection = BlueBuilding.blueDetection(row, col, gameResourceBoard);
                }
            }
        }
        if (redBuildingDetection) {
            System.out.println("A valid farm construction was found!");
            RedBuilding.redPlacement(gameResourceBoard, gameBuildingBoard);
        }
        if (blueBuildingDetection) {
            System.out.println("A valid cottage construction was found!");
            BlueBuilding.bluePlacement(gameResourceBoard, gameBuildingBoard);
        }


    }
    public void playerTurn() {
//        String playerChoice = "";
//        System.out.println("What would you like to do this turn?");
//        playerChoice = sc.nextLine().toLowerCase().strip();
//        if (playerChoice.equals("clear")) {
//            buildArrays();
//        }
        ResourceEnum turnResource = ResourceEnum.randomResource();
        System.out.println("Your resource for this turn is "+turnResource);
        resourcePlacer(turnResource);
    }
    public void resourcePlacer(ResourceEnum random) {
        String userCoordinate = "   ";
        int col = 0;
        int row = 0;
        boolean validSpot = false;
        do {
            validSpot = false;
            while (userCoordinate.length() > 2) {
                System.out.println("Where would you like to place your resource?");
                userCoordinate = sc.nextLine().toLowerCase();
                if (userCoordinate.length() > 2) {
                    System.out.println("Oops! That's not a coordinate.");
                }

            }
            String[] coordinateHelper = userCoordinate.split("", 2);

//            for (String character : coordinateHelper) {
//                System.out.println(character);
//            }
            switch (coordinateHelper[0]) {
                case "a" -> {
                    col = 0;
                    //System.out.println("Case A");
                }
                case "b" -> {
                    col = 1;
                    //System.out.println("Case B");
                }
                case "c" -> {
                    col = 2;
                    //System.out.println("Case C");
                }
                case "d" -> {
                    col = 3;
                    //System.out.println("Case D");
                }
            }
            switch (coordinateHelper[1]) {
                case "1" -> {
                    row = 0;
                    //System.out.println("Case 0");
                }
                case "2" -> {
                    row = 1;
                    //.out.println("Case 1");
                }
                case "3" -> {
                    row = 2;
                    //System.out.println("Case 2");
                }
                case "4" -> {
                    row = 3;
                    //System.out.println("Case 3");
                }
            }
            //System.out.println("Row: " + row + "Col: " + col);
            if (gameResourceBoard[row][col].getResource() == ResourceEnum.NONE) {
                gameResourceBoard[row][col].setResource(random);
                validSpot = true;
            }
            else {
                System.out.println("You can't place a resource on a tile that already has one!");
            }
            userCoordinate = "   ";


        }
        while (!validSpot);

    }
    public void renderBoard() {
        //System.out.println("Rendering game board");
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                if (gameResourceBoard[row][col].getResource() != ResourceEnum.NONE) {
                    gameBoard[row][col] = gameResourceBoard[row][col].toString();
                    gameResourceBoard[row][col].setScannedBuilding(BuildingEnum.NONE);
                }
                else if (gameBuildingBoard[row][col].getType() != BuildingEnum.NONE) {
                    gameBoard[row][col] = gameBuildingBoard[row][col].toString();
                }
                else {
                    gameBoard[row][col] = "[Empty!]";
                }

            }
        }

        for (int i = 0; i < letterCoords.length; i++) {
            if (i == 4) {
                System.out.println(letterCoords[i]);

            }
            else {
                System.out.print(letterCoords[i]);
                System.out.print("     ");
            }

        }
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = -1; col < gameBoard[row].length; col++) {
                if (col == -1) {
                    System.out.print(numberCoords[row]);
                }
                else {
                    if (col == 3) {
                        System.out.println(gameBoard[row][col]);
                    }
                    else {
                        System.out.print(gameBoard[row][col]);
                    }
                }

            }
        }
        System.out.println("This grid helps with figuring out where things should go");
        for (int row = 0; row < coordinateBoard.length; row++) {
            for (int col = 0; col < coordinateBoard[row].length; col++) {
                if (col == 3) {
                    System.out.println(coordinateBoard[row][col]);
                }
                else {
                    System.out.print(coordinateBoard[row][col]);
                }

            }
        }
    }
}
