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
        ResourceEnum resourceArray[] = {WOOD, WHEAT};

        int random = (int) (Math.random() * 2);
        return resourceArray[random];
    }
}
