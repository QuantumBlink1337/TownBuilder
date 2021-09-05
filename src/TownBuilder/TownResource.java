package TownBuilder;

import TownBuilder.Buildings.BuildingEnum;

public class TownResource {
    private ResourceEnum resource;
    private BuildingEnum scannedBuilding;
    public TownResource(ResourceEnum r) {
        resource = r;
        scannedBuilding = BuildingEnum.NULL;
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
    public void clearScannedBuilding() {
        scannedBuilding = BuildingEnum.NULL;
    }
    public void setScannedBuilding(BuildingEnum b) {
        scannedBuilding = b;
    }




    public String toString() {
        return "[" + resource.name() + "]";
    }



}
