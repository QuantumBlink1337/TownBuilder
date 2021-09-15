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
    public static ResourceEnum[][] swap (ResourceEnum[][] swap, int row1, int col1, int row2, int col2) {
        ResourceEnum temp;
        ResourceEnum[][] swapTarget = swap;
        temp = swapTarget[row1][col1];
        swapTarget[row1][col1] = swapTarget[row2][col2];
        swapTarget[row2][col2] = temp;
        return swapTarget;
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


