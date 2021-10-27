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
    TPOST (ColorEnum.COLORLESS),
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
    public static ResourceEnum resourcePicker(ResourceEnum[] blacklistedResources) {
        //resourceArray = new ArrayList<>(Arrays.asList(WOOD, BRICK, WHEAT, GLASS, STONE));
        Scanner sc = new Scanner(System.in);
        String resourceChoice = "";
        while (true) {
            System.out.println("What resource do you want?");
            resourceChoice = sc.nextLine().toLowerCase();
            try {
                for (ResourceEnum blackListedResource : blacklistedResources)
                if (blackListedResource.toString().equalsIgnoreCase(resourceChoice)) {
                    resourceChoice = "blacklisted";

                }
            }
            catch (NullPointerException ignored) {}
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
                case "blacklisted":
                    System.out.println("This resource is unavailable to pick. Please choose another resource.");
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
        //return NONE;
    }



}


