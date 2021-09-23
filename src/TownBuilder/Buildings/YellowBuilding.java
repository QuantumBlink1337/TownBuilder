package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;
import java.util.Arrays;

public class YellowBuilding extends Building
{
    private BuildingEnum buildingEnum;
    private boolean condition;
    private ArrayList<BuildingEnum> resources = new ArrayList<>(Arrays.asList(BuildingEnum.COTTAGE, BuildingEnum.FARM, BuildingEnum.WELL, BuildingEnum.WHOUSE, BuildingEnum.THEATER, BuildingEnum.TEMPLE, BuildingEnum.TAVERN));

    private static ResourceEnum[][] theaterArray = new ResourceEnum[2][3];
    private static ResourceEnum[][][] theaterPatternList = new ResourceEnum[1][2][2];

    public YellowBuilding(BuildingEnum b) {
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
        theaterArray[0][0] = ResourceEnum.NONE;
        theaterArray[0][1] = ResourceEnum.STONE;
        theaterArray[0][2] = ResourceEnum.NONE;
        theaterArray[1][0] = ResourceEnum.WOOD;
        theaterArray[1][1] = ResourceEnum.GLASS;
        theaterArray[1][2] = ResourceEnum.WOOD;
        theaterPatternList[0] = theaterArray;
        return theaterPatternList;
    }
    public int scorer(Building[][] bArray, int row, int col) {
        int score = 0;

        for (int r = 0; r < bArray.length; r++) {
            score += buildingMatch(bArray[r][col], this);
        }
        for (int c = 0; c < bArray[row].length; c++) {
            score += buildingMatch(bArray[row][c], this);
        }
        return score;
    }
    private int buildingMatch(Building toBeChecked, Building scoreTarget) {
        int score = 0;
        for (int i = 0;  i < resources.size(); i++) {
            if (toBeChecked.getType() == resources.get(i)) {
                if (toBeChecked.equals(scoreTarget)) {}
                else {
                    resources.remove(i);
                    score++;
                }
            }
        }
        return score;
    }
    public BuildingEnum getBuildingEnum() {
        return buildingEnum;
    }
}

