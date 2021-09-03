package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.TownResource;

public class RedBuilding extends Building {
    private BuildingEnum buildingEnum;
    public RedBuilding(BuildingEnum b) {
        buildingEnum = b;
    }
    public int scorer() {
        BuildingEnum scoredType = this.buildingEnum;
        if (scoredType == BuildingEnum.FARM) {
            //return 1;
        }
        return 0;
    }
    public static boolean redDetection(int row, int col, TownResource[][] resourceArray) {


        return false;
     }
}
