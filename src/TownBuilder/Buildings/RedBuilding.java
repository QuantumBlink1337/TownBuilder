package TownBuilder.Buildings;

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
}
