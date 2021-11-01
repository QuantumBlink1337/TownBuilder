package TownBuilder;

import com.diogonunes.jcolor.Attribute;

public enum ColorEnum {

    // buildings
    RED (Attribute.BRIGHT_RED_TEXT()),
    YELLOW (Attribute.BRIGHT_YELLOW_TEXT()),
    BLUE (Attribute.BRIGHT_BLUE_TEXT()),
    GREEN (Attribute.TEXT_COLOR(0, 128, 0)),
    GRAY (Attribute.TEXT_COLOR(115, 115, 115)),
    ORANGE (Attribute.TEXT_COLOR(255, 165, 0)),
    BLACK (Attribute.TEXT_COLOR(95, 95, 95)),
    WHITE(Attribute.TEXT_COLOR(255, 255, 255)),
    COLORLESS (Attribute.NONE()),

    PINK(Attribute.TEXT_COLOR(255, 20, 147)),


    // resources

    // glass
    TURQUIOSE (Attribute.TEXT_COLOR(0, 206, 209)),
    // brick
    ORANGERED (Attribute.TEXT_COLOR(255, 69, 0)),
    // wheat
    GOLD (Attribute.TEXT_COLOR(255, 215, 0)),
    // wood
    BROWN (Attribute.TEXT_COLOR(150, 75, 45)),
    // stone
    LIGHTGRAY(Attribute.TEXT_COLOR(235, 235, 235));
    // empty
    //WHITE(Attribute.WHITE_TEXT());

    private final Attribute textColor;
    ColorEnum(Attribute t) {
        this.textColor = t;
    }
    public Attribute getTextColor() {
        return textColor;
    }


}
