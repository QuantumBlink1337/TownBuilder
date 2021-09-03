package TownBuilder;

public class TownResource {
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



    // credit to Alex Martelli of StackExchange

    public String toString() {
        return resource.name();
    }



}
