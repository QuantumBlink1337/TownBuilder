package TownBuilder;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public enum ResourceEnum {
    GLASS (ColorEnum.TURQUIOSE),
    BRICK (ColorEnum.ORANGERED),
    WOOD (ColorEnum.BROWN),
    WHEAT (ColorEnum.GOLD),
    STONE (ColorEnum.LIGHTGRAY),
    OBSTRUCTED (ColorEnum.COLORLESS),
    NONE (ColorEnum.COLORLESS);

    private final ColorEnum color;
    ResourceEnum(ColorEnum color) {
        this.color = color;
    }

    public ColorEnum getColor() {
        return color;
    }

    private static ArrayList<ResourceEnum> resourceArray = new ArrayList<>(Arrays.asList(WOOD, BRICK, WHEAT, GLASS, STONE));
    public static ResourceEnum randomResource() {
        int random = (int) (Math.random() * resourceArray.size());
        ResourceEnum result = resourceArray.get(random);
        resourceArray.remove(random);
        return result;
    }
    public static ResourceEnum resourcePicker() {
        resourceArray = new ArrayList<>(Arrays.asList(WOOD, BRICK, WHEAT, GLASS, STONE));
        Scanner sc = new Scanner(System.in);
        String resourceChoice = "";
        while (resourceChoice.equals("")) {
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
                case "help":
                    return NONE;
                default:
                    System.out.println("Invalid input. Please try again.");
                    resourceChoice = "";
                    break;
            }
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


