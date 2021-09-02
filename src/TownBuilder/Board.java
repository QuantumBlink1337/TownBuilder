package TownBuilder;


import java.util.Locale;
import java.util.Scanner;

public class Board {
    private final Player player;
    private TownResource[][] gameResourceBoard = new TownResource[4][4];
    private Building[][] gameBuildingBoard = new Building[4][4];
    private String[][] gameBoard = new String[4][4];
    private final char[] letterCoords = {' ', 'a', 'b', 'c', 'd'};
    private int[][] numberCoords = new int[1][4];
    private boolean gameCompletion;
    private Scanner sc = new Scanner(System.in);

    public Board() {
        player = new Player();
        gameCompletion = false;
        buildCoords();
    }
    public void buildCoords() {
        System.out.print("Building coords...");
        for (int row = 0; row < numberCoords.length; row++) {
            for (int col = 0; col < numberCoords[row].length; col++) {
                numberCoords[row][col] = col;
                System.out.println(numberCoords[row][col]);
            }
        }

    }
    public void game() {
        while (!gameCompletion) {
            renderBoard();
            playerTurn();
            gameCompletion = true;
        }
    }
    public void playerTurn() {
        Resources turnResource = randomResource();
        System.out.println("Your resource for this turn is "+turnResource);
        resourcePlacer(turnResource);
    }
    public void resourcePlacer(Resources random) {
        String userCoordinate = "   ";
        int col = 0;
        int row = 0;
        while (userCoordinate.length() > 2) {
            System.out.println("Where would you like to place your resource?");
            userCoordinate = sc.nextLine().toLowerCase();
            if (userCoordinate.length() > 2) {
                System.out.println("Oops! That's not a coordinate.");
            }
        }
        String[] coordinateHelper = userCoordinate.split("", 2);
       switch (coordinateHelper[0]) {
            case "a":
                col = 0;
            case "b":
                col = 1;
           case "c":
               col = 2;
           case "d":
               col = 3;
       }
       switch (coordinateHelper[1]) {
           case "0":
               row = 0;
           case "1":
               row = 1;
           case "2":
               row = 2;
           case "3":
               row = 3;
       }
       gameResourceBoard[row][col] = new TownResource(random);
    }
    public void renderBoard() {
        System.out.println("Rendering game board");
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {
                try  {
                    switch (gameResourceBoard[row][col].getResource()) {
                        case GLASS -> gameBoard[row][col] = "[Glass]";
                        case STONE -> gameBoard[row][col] = "[Stone]";
                        case BRICK -> gameBoard[row][col] = "[Brick]";
                        case WHEAT -> gameBoard[row][col] = "[Wheat]";
                        case WOOD -> gameBoard[row][col] = "[Wood]";
                    }
                }
                catch (Exception e) {
                    gameBoard[row][col] = "[EMPTY!]";
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
    }
}
