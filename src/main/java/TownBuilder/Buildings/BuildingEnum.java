package TownBuilder.Buildings;

import TownBuilder.ColorEnum;

public enum BuildingEnum {
    FARM (ColorEnum.RED),
    GRANARY (ColorEnum.RED),
    GRENHOUSE (ColorEnum.RED),
    ORCHARD (ColorEnum.RED),
    CHAPEL (ColorEnum.ORANGE),
    ABBEY (ColorEnum.ORANGE),
    CLOISTER (ColorEnum.ORANGE),
    TEMPLE (ColorEnum.ORANGE),
    COTTAGE (ColorEnum.BLUE),
    THEATER (ColorEnum.YELLOW),
    BAKERY (ColorEnum.YELLOW),
    MARKET (ColorEnum.YELLOW),
    TAILOR (ColorEnum.YELLOW),
    TAVERN (ColorEnum.GREEN),
    ALMSHOUSE (ColorEnum.GREEN),
    FEASTHALL (ColorEnum.GREEN),
    INN (ColorEnum.GREEN),
    WELL (ColorEnum.GRAY),
    FOUNTAIN (ColorEnum.GRAY),
    MILLSTONE (ColorEnum.GRAY),
    WAREHOUSE (ColorEnum.WHITE),
    FACTORY (ColorEnum.WHITE),
    BANK (ColorEnum.WHITE),
    TRDINGPST(ColorEnum.WHITE),

    AGUILD(ColorEnum.PINK),




    NONE (ColorEnum.COLORLESS);

    private final ColorEnum color;
    BuildingEnum(ColorEnum color) {
        this.color = color;
    }

    public ColorEnum getColor() {
        return color;
    }
}
