package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

public class Tavern extends Building{
    private boolean condition;
    private static ResourceEnum[][] tavernArray = new ResourceEnum[1][3];
    private static ResourceEnum[][][] tavernPatternList = new ResourceEnum[1][2][2];
    static int scoreIncrement = 2;

    public Tavern() {
        tavernArray[0][0] = ResourceEnum.BRICK;
        tavernArray[0][1] = ResourceEnum.BRICK;
        tavernArray[0][2] = ResourceEnum.GLASS;
        tavernPatternList[0] = tavernArray;
        condition = false;
    }
    public BuildingEnum getType() {
        return BuildingEnum.TAVERN;
    }
    public void setCondition(boolean b) {
        condition = b;
    }
    public boolean getCondition() {
        return condition;
    }
    public String wordDefinition() {
        return "Tavern";
    }
    public static void printPattern() {
        Utility.arrayPrinter(tavernPatternList[0]);
    }
    public ResourceEnum[][][] getPatterns() {

        return tavernPatternList;
    }
    public int scorer(Building[][] bArray, int row, int col) {
        if (condition) {
            return 0;
        }
        int score =0;
        //System.out.println("Adding scoreIncrement " + scoreIncrement + " to score " + score);
        score += scoreIncrement;
        scoreIncrement++;

        return score;
    }
}
