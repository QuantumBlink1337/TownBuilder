package TownBuilder;

public class TownResource extends TownObjects{
    private ResourceEnum resource;
    private boolean scanned;
    public TownResource(ResourceEnum r) {
        resource = r;
        scanned = false;
    }




    public ResourceEnum getResource() {
        return resource;
    }
    public boolean getScannedStatus() {
        return scanned;
    }




    public void setResource(ResourceEnum r) {
        resource = r;
    }
    public void setScannedStatus() {
        scanned = true;
    }




    public String toString() {
        return resource.name();
    }



}
