package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

public class GreenBuilding extends Building{
    private BuildingEnum buildingEnum;
    private boolean condition;
    private static ResourceEnum[][] tavernArray = new ResourceEnum[1][3];
    private static ResourceEnum[][][] tavernPatternList = new ResourceEnum[1][2][2];
    static int scoreIncrement = 2;

    public GreenBuilding(BuildingEnum b) {
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
        tavernArray[0][0] = ResourceEnum.BRICK;
        tavernArray[0][1] = ResourceEnum.BRICK;
        tavernArray[0][2] = ResourceEnum.GLASS;
        tavernPatternList[0] = tavernArray;
        return tavernPatternList;
    }
    public int scorer(Building[][] bArray, int row, int col) {
        if (condition) {
            return 0;
        }
        int score =0;

        System.out.println("Adding scoreIncrement " + scoreIncrement + " to score " + score);
        score += scoreIncrement;
        scoreIncrement++;

        return score;
    }
    public BuildingEnum getBuildingEnum() {
        return buildingEnum;
    }
}
