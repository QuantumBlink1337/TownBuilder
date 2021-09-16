package TownBuilder;

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
    public static ResourceEnum[][] swap (ResourceEnum[][] swap, int row1, int col1, int row2, int col2) {
        ResourceEnum temp;
        ResourceEnum[][] swapTarget = swap;
        temp = swapTarget[row1][col1];
        swapTarget[row1][col1] = swapTarget[row2][col2];
        swapTarget[row2][col2] = temp;
        return swapTarget;
    }
}


