package TownBuilder;

import TownBuilder.Buildings.BuildingEnum;

public class Resource {
    private ResourceEnum resource;
    private BuildingEnum scannedBuilding;
    private final int row;
    private final int col;
    public Resource(ResourceEnum resource, int r, int c) {
        resource = resource;
        scannedBuilding = BuildingEnum.NONE;
        row = r;
        col = c;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
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
