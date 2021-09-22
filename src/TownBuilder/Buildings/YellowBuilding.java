package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

public class YellowBuilding extends Building
{
    private BuildingEnum buildingEnum;
    private boolean condition;
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
        for (int r = 0; r < bArray[col].length; r++) {
            for (int c = 0; c < bArray[row].length; c++) {
                System.out.println("Scanning for uniqueness: " + Utility.coordsToOutput(r, c));
//                if (bArray[r][c].getType() == BuildingEnum.THEATER && !(bArray[r][c].equals(this)))  {
//
//                }
//                if (bArray[r][c].getType() == BuildingEnum.COTTAGE) {
//
//                }
//                if (bArray[r][c].getType() == BuildingEnum.WELL) {
//
//                }
//                if (bArray[r][c].getType() == BuildingEnum.FARM) {
//
//                }
//                if (bArray[r][c].getType() == )
            }
        }
        return 0;
    }
    public BuildingEnum getBuildingEnum() {
        return buildingEnum;
    }
}

