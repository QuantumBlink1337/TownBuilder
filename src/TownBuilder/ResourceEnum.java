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

    // credit to Alex Martelli of StackExchange

    public static ResourceEnum[][] arrayFlipper(ResourceEnum[][] mat) {
            final int M = mat.length;
            final int N = mat[0].length;
            ResourceEnum[][] ret = new ResourceEnum[N][M];
            for (int r = 0; r < M; r++) {
                for (int c = 0; c < N; c++) {
                    ret[c][M-1-r] = mat[r][c];
                }
            }
            return ret;
        }

    }


