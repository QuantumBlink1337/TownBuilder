package TownBuilder;


import TownBuilder.Buildings.*;

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

    public Board() {
        player = new Player();
        gameCompletion = false;
        buildArrays();
    }
    private void buildArrays() {
        //System.out.println("Building resource array");
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                gameResourceBoard[row][col] = new TownResource(ResourceEnum.NONE);
                //System.out.println(gameResourceBoard[row][col]);
            }
        }
        for (int row = 0; row < gameBuildingBoard.length; row++) {
            for (int col = 0; col < gameBuildingBoard[row].length; col++) {
                gameBuildingBoard[row][col] = new Building(BuildingEnum.NONE);
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
            detectValidBuilding();
            gameCompletion = gameOver();
        }
    }
    private boolean gameOver() {
        int i = 0;
        for (String[] tileRow : gameBoard) {
            for (String tile : tileRow) {
                if (!(tile.equals("[Empty!]"))) {
                    i++;
                }
            }
        }
        if (i >= 16)  {
            return true;
        }
        else {
            return false;
        }
    }
    private void placementPrompt(BuildingEnum building) {
        System.out.println("A valid "+building.toString().toLowerCase() +" construction was found! Place it this turn?");
        if (Utility.prompt()) {
            Building.placement(gameResourceBoard, gameBuildingBoard, building);
        }
        else {
            Building.clearResources(building);
        }
    }
    private void detectValidBuilding() {
        boolean redBuildingDetection = false;
        boolean blueBuildingDetection = false;
        boolean grayBuildingDetection = false;
        boolean yellowBuildingDetection = false;
        boolean blackBuildingDetection = false;
        boolean greenBuildingDetection = false;
        boolean orangeBuildingDetection = false;
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                if (!redBuildingDetection && !blueBuildingDetection && !grayBuildingDetection && !yellowBuildingDetection && !blackBuildingDetection &&
                !greenBuildingDetection && !orangeBuildingDetection) {
                    //System.out.println("Scanning row: " + row + " and column: " + col);
                    redBuildingDetection = Building.detection(row, col, gameResourceBoard, RedBuilding.getPatterns(), BuildingEnum.FARM);
                    blueBuildingDetection = Building.detection(row, col, gameResourceBoard, BlueBuilding.getPatterns(), BuildingEnum.COTTAGE);
                    grayBuildingDetection = Building.detection(row, col, gameResourceBoard, GrayBuilding.getPatterns(), BuildingEnum.WELL);
                    yellowBuildingDetection = Building.detection(row, col, gameResourceBoard, YellowBuilding.getPatterns(), BuildingEnum.THEATER);
                    blackBuildingDetection = Building.detection(row, col, gameResourceBoard, BlackBuilding.getPatterns(), BuildingEnum.WHOUSE);
                    greenBuildingDetection = Building.detection(row, col, gameResourceBoard, GreenBuilding.getPatterns(), BuildingEnum.TAVERN);
                    orangeBuildingDetection = Building.detection(row, col, gameResourceBoard, OrangeBuilding.getPatterns(), BuildingEnum.TEMPLE);
                    if (redBuildingDetection) {
                        placementPrompt(BuildingEnum.FARM);
                    }
                    if (blueBuildingDetection) {
                        placementPrompt(BuildingEnum.COTTAGE);
                    }
                    if (grayBuildingDetection) {
                        System.out.println(Building.getValidResources().size());
                        placementPrompt(BuildingEnum.WELL);
                    }
                    if (yellowBuildingDetection) {
                        placementPrompt(BuildingEnum.THEATER);
                    }
                    if (blackBuildingDetection) {
                        placementPrompt(BuildingEnum.WHOUSE);
                    }
                    if (greenBuildingDetection) {
                        placementPrompt(BuildingEnum.TAVERN);
                    }
                    if (orangeBuildingDetection) {
                        placementPrompt(BuildingEnum.TEMPLE);
                    }
                }
            }
        }
    }
    private void playerTurn() {
        ResourceEnum turnResource = ResourceEnum.debugResourcePicker(); // debug mode active
        System.out.println("Your resource for this turn is "+turnResource);
        resourcePlacer(turnResource);
    }
    private void resourcePlacer(ResourceEnum random) {
        String userCoordinate = "   ";
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
            int[] coords = Utility.inputToCoords(userCoordinate);
            //System.out.println("Row: " + row + "Col: " + col);
            if (gameResourceBoard[coords[0]][coords[1]].getResource() == ResourceEnum.NONE)
            {
                gameResourceBoard[coords[0]][coords[1]].setResource(random);
                validSpot = true;
            }
            else {
                System.out.println("You can't place a resource on a tile that already has something on it!");
            }
            userCoordinate = "   ";

        }
        while (!validSpot);

    }
    private void renderBoard() {
        //System.out.println("Rendering game board");
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                if (gameResourceBoard[row][col].getResource() != ResourceEnum.NONE && gameResourceBoard[row][col].getResource() != ResourceEnum.OBSTRUCTED) {
                    gameBoard[row][col] = gameResourceBoard[row][col].toString();
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
//        System.out.println("This grid helps with figuring out where things should go");
//        for (int row = 0; row < coordinateBoard.length; row++) {
//            for (int col = 0; col < coordinateBoard[row].length; col++) {
//                if (col == 3) {
//                    System.out.println(coordinateBoard[row][col]);
//                }
//                else {
//                    System.out.print(coordinateBoard[row][col]);
//                }
//
//            }
//        }
    }
}
