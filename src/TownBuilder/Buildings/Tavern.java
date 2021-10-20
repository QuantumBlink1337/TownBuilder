package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;

public class Tavern extends Building {
    private final boolean condition;
    private static final ResourceEnum[][] tavernArray = new ResourceEnum[1][3];
    private static final ArrayList<ResourceEnum[][]> tavernPatternList = new ArrayList<>();
    private static final int SCORE_INCREMENT_STARTING_VALUE = 2;

    public Tavern() {
        condition = false;
    }
    static {
        tavernArray[0] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.BRICK, ResourceEnum.GLASS};
        patternBuilder(tavernArray, tavernPatternList, 3);
    }
    public BuildingEnum getType() {
        return BuildingEnum.TAVERN;
    }

    public boolean getCondition() {
        return condition;
    }
    public String toString() {
        return "Tavern";
    }
    public void printManualText() {
        System.out.println("The Tavern grants points based on how many Taverns you have:");
        System.out.print("1 Tavern: 2 Points | ");
        System.out.print("2 Taverns: 5 Points | ");
        System.out.print("3 Taverns: 9 Points | ");
        System.out.print("4 Taverns: 14 Points | ");
        System.out.print("5 Taverns: 20 Points");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(tavernArray);
    }
    public ArrayList<ResourceEnum[][]> getPatterns() {

        return tavernPatternList;
    }
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        if (condition) {
            return 0;
        }
        int score =0;
        //System.out.println("Adding scoreIncrement " + scoreIncrement + " to score " + score);
        score += scoreIncrement;

        return score;
    }

    public static int getSCORE_INCREMENT_STARTING_VALUE() {
        return SCORE_INCREMENT_STARTING_VALUE;
    }
}
