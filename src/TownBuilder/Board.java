package TownBuilder;


public class Board {
    private Player player;
    private TownResource[][] gameBoard = new TownResource[4][4];
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
        for (int row = 0; row < gameBoard.length; row++) {
            System.out.println(numberCoords[row][0]);
            for (int col = 0; col < gameBoard[row].length; col++) {
                switch (gameBoard[row][col].getColor()) {
                    case CYAN:
                        System.out.println("[Glass]");
                        break;
                    case LIGHTGRAY:
                        System.out.println("[Stone]");
                        break;
                    case ORANGE:
                        System.out.println("[Brick]");
                        break;
                    case GOLD:
                        System.out.println("[Wheat]");
                        break;
                    case BROWN:
                        System.out.println("[Wood]");
                        break;
                }



            }
        }
    }
}
