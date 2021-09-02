package TownBuilder;

public class TownResource extends TownObjects{
    private ResourceEnum resource;
    public TownResource(ResourceEnum r) {
        resource = r;
    }
    public ResourceEnum getResource() {
        return resource;
    }
    public void setResource(ResourceEnum r) {
        resource = r;
    }
    public String toString() {
        return resource.name();
    }



}
