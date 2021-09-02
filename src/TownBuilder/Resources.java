package TownBuilder;

public enum Resources {
    GLASS,
    BRICK,
    WOOD,
    WHEAT,
    STONE;
    public static Resources randomResource() {
        Resources resourceArray[] = {GLASS, BRICK, WOOD, WHEAT, STONE};
        int random = (int) (Math.random() * 5);
        return resourceArray[random];
    }
}
