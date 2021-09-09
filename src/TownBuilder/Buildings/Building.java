package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.BuildingColor;

public class Building {
    private BuildingEnum buildingEnum;
    private BuildingColor color;
    public Building(BuildingEnum b, BuildingColor c) {
        buildingEnum = b;
        color = c;
    }
    public BuildingEnum getType() {
        return buildingEnum;
    }
    public BuildingColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "[" + buildingEnum +"]";
    }

    public static ResourceEnum[][] buildingRotation(ResourceEnum[][] a) {
        System.out.println("No match found. Flipping array...");
        ResourceEnum[][] resourceArray = a;
        final int M = resourceArray.length;
        final int N = resourceArray[0].length;
        ResourceEnum[][] ret = new ResourceEnum[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M - 1 - r] = resourceArray[r][c];
            }
        }
        System.out.println("Flipped array:");
        System.out.println("["+ret[0][0]+"]["+ret[0][1]+"]");
        System.out.println("["+ret[1][0]+"]["+ret[1][1]+"]");
        return ret;
    }
}

