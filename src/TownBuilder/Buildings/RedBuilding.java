package TownBuilder.Buildings;

public class RedBuilding extends Building {
    private BuildingType buildingType;
    public RedBuilding(BuildingType b) {
        buildingType = b;
    }
    public int scorer() {
        BuildingType scoredType = this.buildingType;
        if (scoredType == BuildingType.FARM) {
            //return 1;
        }
        return 0;
    }
}
