package TownBuilder;

public enum ResourceEnum {
    GLASS,
    BRICK,
    WOOD,
    WHEAT,
    STONE,
    OVERRULED,
    NONE;




    public static ResourceEnum randomResource() {
        //ResourceEnum resourceArray[] = {GLASS, BRICK, WOOD, WHEAT, STONE};
        ResourceEnum resourceArray[] = {BRICK, WHEAT, GLASS, WOOD};

        int random = (int) (Math.random() * 4);
        return resourceArray[random];
    }
    public static boolean PatternRandomCheck(ResourceEnum check) {
        ResourceEnum resourceArray[] = {ResourceEnum.GLASS, ResourceEnum.BRICK, ResourceEnum.WOOD, ResourceEnum.WHEAT, ResourceEnum.STONE, ResourceEnum.NONE};
        for (int i = 0; i < resourceArray.length; i++) {
            if (check == resourceArray[i]) {
                return true;
            }
        }
        return false;
    }

    // credit to Alex Martelli of StackExchange



    }


