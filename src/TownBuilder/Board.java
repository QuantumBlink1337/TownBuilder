package TownBuilder;


import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.RedBuilding;

import java.util.Scanner;

public class Board {
    private final Player player;
    private TownResource[][] gameResourceBoard = new TownResource[4][4];
    private Building[][] gameBuildingBoard = new Building[4][4];
    private String[][] gameBoard = new String[4][4];
    private String[][] coordinateBoard = new String[4][4];
    private final char[] letterCoords = {' ', 'a', 'b', 'c', 'd'};
    private int[][] numberCoords = new int[1][4];
    private boolean gameCompletion;
    private Scanner sc = new Scanner(System.in);

    private boolean redBuildingDetection;

    public Board() {
        player = new Player();
        gameCompletion = false;
        buildArrays();
    }
    public void buildArrays() {
        //System.out.print("Building coords...");
        for (int row = 0; row < numberCoords.length; row++) {
            for (int col = 0; col < numberCoords[row].length; col++) {
                numberCoords[row][col] = col;
                //System.out.println(numberCoords[row][col]);
            }
        }
        //System.out.println("Building resource array");
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                gameResourceBoard[row][col] = new TownResource(ResourceEnum.NULL);
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
            detection();

        }
    }
    public void detection() {
        redBuildingDetection = false;
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                if (gameResourceBoard[row][col].getResource() != ResourceEnum.NULL && !redBuildingDetection) {
                    redBuildingDetection = RedBuilding.redDetection(row, col, gameResourceBoard);
                }
            }
        }

    }
    public void playerTurn() {
        String playerChoice = "";
        System.out.println("What would you like to do this turn?");
        playerChoice = sc.nextLine().toLowerCase().strip();
        if (playerChoice.equals("clear")) {
            buildArrays();
        }
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
                case "0" -> {
                    row = 0;
                    //System.out.println("Case 0");
                }
                case "1" -> {
                    row = 1;
                    //.out.println("Case 1");
                }
                case "2" -> {
                    row = 2;
                    //System.out.println("Case 2");
                }
                case "3" -> {
                    row = 3;
                    //System.out.println("Case 3");
                }
            }
            //System.out.println("Row: " + row + "Col: " + col);
            if (gameResourceBoard[row][col].getResource() == ResourceEnum.NULL) {
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
                switch (gameResourceBoard[row][col].getResource()) {
                    case GLASS -> gameBoard[row][col] = "[Glass]";
                    case STONE -> gameBoard[row][col] = "[Stone]";
                    case BRICK -> gameBoard[row][col] = "[Brick]";
                    case WHEAT -> gameBoard[row][col] = "[Wheat]";
                    case WOOD -> gameBoard[row][col] = "[Wood]";
                    case NULL -> gameBoard[row][col] = "[EMPTY]";
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
                    System.out.print(numberCoords[0][row]);
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
