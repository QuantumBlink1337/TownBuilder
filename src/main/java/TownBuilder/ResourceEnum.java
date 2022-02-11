package TownBuilder;

public enum ResourceEnum {
    GLASS (ColorEnum.TURQUIOSE) {
        public String toString() {
            return "Glass";
        }
    },
    BRICK (ColorEnum.ORANGERED) {
      public String toString() {
          return "Brick";
      }
    },
    WOOD (ColorEnum.BROWN) {
        public String toString() {
            return "Wood";
        }
    },
    WHEAT (ColorEnum.GOLD) {
        public String toString() {
            return "Wheat";
        }
    },
    STONE (ColorEnum.LIGHTGRAY) {
        public String toString() {
            return "Stone";
        }
    },
    TPOST (ColorEnum.COLORLESS),
    OBSTRUCTED (ColorEnum.COLORLESS),
    NONE (ColorEnum.COLORLESS);

    private final ColorEnum color;
    ResourceEnum(ColorEnum color) {
        this.color = color;
    }

    public ColorEnum getColor() {
        return color;
    }



}


