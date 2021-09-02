package TownBuilder;

public enum Resources {
    GLASS,
    BRICK,
    WOOD,
    WHEAT,
    STONE;
    public Resources randomResource() {
        Resources resourceArray[] = {GLASS, BRICK, WOOD, WHEAT, STONE};
        int random = (int) (Math.random() * 5) + 1;
        return resourceArray[random];
    }
}
