package TownBuilder;
import java.awt.*;

public enum ColorEnum {

    // BUILDING COLORS

    RED (new Color(255, 0, 0)),
    YELLOW (new Color(255, 255, 0)),
    BLUE (new Color(28, 142, 182)),
    GREEN (new Color(0, 128, 0)),
    GRAY (new Color(115, 115, 115)),
    ORANGE (new Color(255, 165, 0)),
    WHITE(new Color(255, 255, 255)),
    PINK(new Color(255, 20, 147)),
    COLORLESS (new Color(255, 255, 255, 1)),

    // RESOURCE COLORS

    // glass
    TURQUIOSE (new Color(0, 206, 209)),
    // brick
    ORANGERED (new Color(255, 69, 0)),
    // wheat
    GOLD (new Color(255, 215, 0)),
    // wood
    BROWN (new Color(150, 75, 45)),
    // stone
    LIGHTGRAY(new Color(235, 235, 235));
    // empty

    private final Color color;

    ColorEnum(Color c) {
        this.color = c;

    }
    public Color getOverallColor() { return color;}


}
