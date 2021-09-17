package TownBuilder;


import java.util.Scanner;

public enum ResourceEnum {


    GLASS,
    BRICK,
    WOOD,
    WHEAT,
    STONE,
    OBSTRUCTED,
    NONE;

    public static ResourceEnum randomResource() {
        ResourceEnum resourceArray[] = {GLASS, BRICK, WOOD, WHEAT, STONE};
        int random = (int) (Math.random() * 5);
        return resourceArray[random];
    }
    // this method should NEVER be used for actual gameplay.

    public static ResourceEnum debugResourcePicker() {
        Scanner sc = new Scanner(System.in);
        String resourceChoice = "";
        System.out.println("What resource do you want?");
        resourceChoice = sc.nextLine().toLowerCase();
        switch (resourceChoice) {
            case "wheat":
                return WHEAT;
            case "glass":
                return GLASS;
            case "brick":
                return BRICK;
            case "stone":
                return STONE;
            case "wood":
                return WOOD;
        }
        return NONE;
    }
    public static ResourceEnum[][] swap (ResourceEnum[][] swap, int row1, int col1, int row2, int col2) {
        ResourceEnum temp;
        ResourceEnum[][] swapTarget = swap;
        temp = swapTarget[row1][col1];
        swapTarget[row1][col1] = swapTarget[row2][col2];
        swapTarget[row2][col2] = temp;
        return swapTarget;
    }


}


