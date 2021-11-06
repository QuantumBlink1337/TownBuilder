package TownBuilder.Buildings;

import TownBuilder.ColorEnum;

public enum BuildingEnum {
    FARM (ColorEnum.RED, false),
    GRANARY (ColorEnum.RED, false),
    GRENHOUSE (ColorEnum.RED, false),
    ORCHARD (ColorEnum.RED, false),
    CHAPEL (ColorEnum.ORANGE, false),
    ABBEY (ColorEnum.ORANGE, false),
    CLOISTER (ColorEnum.ORANGE, false),
    TEMPLE (ColorEnum.ORANGE, false),
    COTTAGE (ColorEnum.BLUE ,false),
    THEATER (ColorEnum.YELLOW,false),
    BAKERY (ColorEnum.YELLOW,false),
    MARKET (ColorEnum.YELLOW,false),
    TAILOR (ColorEnum.YELLOW,false),
    TAVERN (ColorEnum.GREEN,false),
    ALMSHOUSE (ColorEnum.GREEN,false),
    FEASTHALL (ColorEnum.GREEN,false),
    INN (ColorEnum.GREEN,false),
    WELL (ColorEnum.GRAY,false),
    FOUNTAIN (ColorEnum.GRAY,false),
    MILLSTONE (ColorEnum.GRAY,false),
    WAREHOUSE (ColorEnum.WHITE,false),
    FACTORY (ColorEnum.WHITE,false),
    BANK (ColorEnum.WHITE,false),
    TRDINGPST(ColorEnum.WHITE,false),

    AGUILD(ColorEnum.PINK, true),
    ARCHIVE(ColorEnum.PINK, true),
    BARRETT(ColorEnum.PINK, true),
    CATERINA(ColorEnum.PINK, true),



    NONE (ColorEnum.COLORLESS, false);

    private final ColorEnum color;
    private final boolean isMonument;
    BuildingEnum(ColorEnum color, boolean isMonument) {
        this.color = color;
        this.isMonument = isMonument;
    }

    public ColorEnum getColor() {
        return color;
    }
    public boolean isMonument() {return isMonument;}
}
