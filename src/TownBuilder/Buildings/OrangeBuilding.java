package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

public class OrangeBuilding extends Building{
    private BuildingEnum buildingEnum;
    private boolean condition;
    private static ResourceEnum[][] templeArray = new ResourceEnum[2][3];
    private static ResourceEnum[][][] templePatternList = new ResourceEnum[1][2][2];

    public OrangeBuilding(BuildingEnum b) {
        buildingEnum = b;
        condition = false;
    }
    public BuildingEnum getType() {
        return buildingEnum;
    }
    public void setCondition(boolean b) {
        condition = b;
    }
    public boolean getCondition() {
        return condition;
    }
    public static ResourceEnum[][][] getPatterns() {
        templeArray[0][0] = ResourceEnum.NONE;
        templeArray[0][1] = ResourceEnum.NONE;
        templeArray[0][2] = ResourceEnum.GLASS;
        templeArray[1][0] = ResourceEnum.STONE;
        templeArray[1][1] = ResourceEnum.GLASS;
        templeArray[1][2] = ResourceEnum.STONE;
        templePatternList[0] = templeArray;
        return templePatternList;
    }
    public int scorer(Building[][] bArray, int row, int col) {
        int score = 0;
        for (int r = 0; r < bArray.length; r++) {
            for (int c = 0; c < bArray[r].length; c++) {
                if (bArray[r][c].getType() == BuildingEnum.COTTAGE && bArray[r][c].getCondition()) {
                    score += 1;
                }
            }
        }
        return score;
    }
    public BuildingEnum getBuildingEnum() {
        return buildingEnum;
    }
}
