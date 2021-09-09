package TownBuilder;

public enum ResourceEnum {
    GLASS,
    BRICK,
    WOOD,
    WHEAT,
    STONE,
    NONE;


    public static ResourceEnum randomResource() {
        //ResourceEnum resourceArray[] = {GLASS, BRICK, WOOD, WHEAT, STONE};
        ResourceEnum resourceArray[] = {BRICK, WHEAT, GLASS, WOOD};

        int random = (int) (Math.random() * 4);
        return resourceArray[random];
    }

    // credit to Alex Martelli of StackExchange



    }


