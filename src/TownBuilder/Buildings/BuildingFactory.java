package TownBuilder.Buildings;

public class BuildingFactory {
    public Building getBuilding(String buildingName) {
        if (buildingName.equalsIgnoreCase("Chapel")) {
            return new Chapel();
        }
        else if (buildingName.equalsIgnoreCase("Cottage")) {
            return new Cottage();
        }
        else if (buildingName.equalsIgnoreCase("Farm")) {
            return new Farm();
        }
        else if (buildingName.equalsIgnoreCase("Tavern")) {
            return new Tavern();
        }
        else if (buildingName.equalsIgnoreCase("Theater")) {
            return new Theater();
        }
        else if (buildingName.equalsIgnoreCase("Warehouse")) {
            return new Warehouse();
        }
        else if (buildingName.equalsIgnoreCase("Well")) {
            return new Well();
        }
        return null;
    }
}
