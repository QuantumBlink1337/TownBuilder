package TownBuilder;

import TownBuilder.Buildings.BuildingEnum;

public class Resource {
    private ResourceEnum resource;
    private BuildingEnum scannedBuilding;
    public Resource(ResourceEnum r) {
        resource = r;
        scannedBuilding = BuildingEnum.NONE;
    }

    public ResourceEnum getResource() {
        return resource;
    }
    public BuildingEnum getScannedBuilding() {
        return scannedBuilding;
    }
    public void setResource(ResourceEnum r) {
        resource = r;
    }
    public void setScannedBuilding(BuildingEnum b) {
        scannedBuilding = b;
    }
    public String toString() {
        return resource.toString() ;
    }



}
