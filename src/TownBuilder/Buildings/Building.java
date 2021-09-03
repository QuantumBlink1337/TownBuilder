package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

public class Building {
    private BuildingEnum buildingEnum;

    public BuildingEnum getType() {
        return buildingEnum;

    }
    public static ResourceEnum[][] buildingFlipper(ResourceEnum[][] a) {
        ResourceEnum[][] resourceArray = a;
        System.out.println("No match found. Flipping array...");
        resourceArray = ResourceEnum.arrayFlipper(resourceArray);
        System.out.println("Flipped array:");
        System.out.println("["+resourceArray[0][0]+"]["+resourceArray[0][1]+"]");
        System.out.println("["+resourceArray[1][0]+"]["+resourceArray[1][1]+"]");
        return resourceArray;
    }


}

