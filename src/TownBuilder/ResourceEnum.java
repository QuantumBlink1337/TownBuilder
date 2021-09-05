package TownBuilder;

public enum ResourceEnum {
    GLASS,
    BRICK,
    WOOD,
    WHEAT,
    STONE,
    NULL;


    public static ResourceEnum randomResource() {
        //ResourceEnum resourceArray[] = {GLASS, BRICK, WOOD, WHEAT, STONE};
        ResourceEnum resourceArray[] = {BRICK, WHEAT, GLASS};

        int random = (int) (Math.random() * 3);
        return resourceArray[random];
    }

    // credit to Alex Martelli of StackExchange



    }


