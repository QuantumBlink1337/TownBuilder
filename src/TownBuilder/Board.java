package TownBuilder;


public class Board {
    private Player player;
    private TownResource[][] gameResourceBoard = new TownResource[4][4];
    private Building[][] gameBuildingBoard = new Building[4][4];
    private String[][] gameBoard = new String[4][4];
    private char[][] letterCoords = new char[4][1];
    private int[][] numberCoords = new int[1][4];

    public Board() {
        player = new Player();
        buildCoords();



    }
    public void buildCoords() {
        System.out.print("Building coords...");
        char tempChar = 'a';
        for (int row = 0; row < letterCoords.length; row++) {
            if (row == 1) {
                tempChar = 'b';
            }
            else if (row == 2) {
                tempChar = 'c';
            }
            else if (row == 3) {
                tempChar = 'd';
            }
            for (int col = 0; col < letterCoords[row].length; col++) {
                letterCoords[row][col] = tempChar;
                System.out.println(letterCoords[row][col]);
            }
        }
        for (int row = 0; row < numberCoords.length; row++) {
            for (int col = 0; col < numberCoords[row].length; col++) {
                numberCoords[row][col] = col;
                System.out.println(numberCoords[row][col]);
            }
        }

    }
    public void renderBoard() {
        System.out.println("Rendering game board");
        for (int row = 0; row < gameResourceBoard.length; row++) {
            for (int col = 0; col < gameResourceBoard[row].length; col++) {

                if (gameResourceBoard[row][col].getResource() == null) {
                    gameBoard[row][col] = "[EMPTY!]";
                }
                else {
                    System.out.println("Switch case invoked!");
                    switch (gameResourceBoard[row][col].getResource()) {

                        case GLASS:
                            gameBoard[row][col] = "[Glass]";
                            break;
                        case STONE:
                            gameBoard[row][col] = "[Stone]";
                            break;
                        case BRICK:
                            gameBoard[row][col] = "[Brick]";
                            break;
                        case WHEAT:
                            gameBoard[row][col] = "[Wheat]";
                            break;
                        case WOOD:
                            gameBoard[row][col] = "[Wood]";
                            break;
                    }
                }
            }
        }
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[row].length; col++) {
                System.out.println(gameBoard[row][col]);
            }
        }
    }
}
