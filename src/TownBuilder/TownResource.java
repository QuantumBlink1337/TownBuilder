package TownBuilder;

import TownBuilder.Buildings.BuildingEnum;

public class TownResource {
    private ResourceEnum resource;
    private ResourceEnum status;
    private BuildingEnum scannedBuilding;
    public TownResource(ResourceEnum r) {
        resource = r;
        status = ResourceEnum.EXISTS;
        scannedBuilding = BuildingEnum.NONE;
    }




    public ResourceEnum getResource() {
        return resource;
    }
    public BuildingEnum getScannedBuilding() {
        return scannedBuilding;
    }
    public ResourceEnum getStatus() {
        return status;
    }




    public void setResource(ResourceEnum r) {
        resource = r;
    }
    public void clearScannedBuilding() {
        scannedBuilding = BuildingEnum.NONE;
    }
    public void setScannedBuilding(BuildingEnum b) {
        scannedBuilding = b;
    }




    public String toString() {
        return "[" + resource + "]";
    }



}
