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
//        public static ResourceEnum[][] arrayFlipper(ResourceEnum[][] a) {
//            ResourceEnum[][] flippedArray = new ResourceEnum[a[0].length][a.length];
//            for(int i=0; i<a[0].length; i++){
//                for(int j=a.length-1; j>=0; j--){
//                    flippedArray[i][j] = a[j][i];
//
//                }
//            }
//            return flippedArray;
//        }
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


