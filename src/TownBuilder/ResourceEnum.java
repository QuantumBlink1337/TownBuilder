package TownBuilder;

public enum ResourceEnum {
    GLASS,
    BRICK,
    WOOD,
    WHEAT,
    STONE,
    NULL;


    public static ResourceEnum randomResource() {
        ResourceEnum resourceArray[] = {GLASS, BRICK, WOOD, WHEAT, STONE};
        int random = (int) (Math.random() * 5);
        return resourceArray[random];
    }
}
