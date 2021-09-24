package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

public class Farm extends Building {
    private boolean condition;
    private int fed;
    private static ResourceEnum[][] farmArray = new ResourceEnum[2][2];
    private static ResourceEnum[][][] farmPatternList = new ResourceEnum[1][2][2];

    public Farm() {
        condition = false;
        fed = 4;
    }

    public BuildingEnum getType() {
        return BuildingEnum.FARM;
    }
    public void setCondition(boolean b) {
        condition = b;
    }
    public boolean getCondition() {
        return condition;
    }
    public int decrementFed() {
        return --fed;
    }
    public int getFed() {
        return fed;
    }
    public static ResourceEnum[][][] getPatterns() {
        farmArray[0][0] = ResourceEnum.WHEAT;
        farmArray[0][1] = ResourceEnum.WHEAT;
        farmArray[1][0] = ResourceEnum.WOOD;
        farmArray[1][1] = ResourceEnum.WOOD;
        farmPatternList[0] = farmArray;
        return farmPatternList;
    }
    public int scorer(Building[][] bArray, int row, int col) {
//        BuildingEnum scoredType = this.buildingEnum;
//        if (scoredType == BuildingEnum.FARM) {
//            System.out.println("Farm scoring invoked.");
//            int i = 0;
//                for (int r = 0; r < bArray.length && i < 4 ; r++) {
//                    for (int c = 0; c < bArray[r].length && i < 4; c++) {
//                        if (bArray[r][c].getType() == BuildingEnum.COTTAGE && !bArray[r][c].getCondition()) {
//                            System.out.println("A cottage was fed." + i);
//                            bArray[r][c].setCondition(true);
//                            i++;
//                        }
//                    }
//                }
//        }
        return 0;
    }
}
