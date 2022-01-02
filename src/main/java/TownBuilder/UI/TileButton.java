package TownBuilder.UI;

import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.ColorEnum;
import TownBuilder.ResourceEnum;

import javax.swing.*;


public class TileButton extends JButton {


    private final int[] coords = new int[2];
    private String text;



    ResourceEnum resourceEnum;
    BuildingEnum buildingEnum;



    private boolean clicked = false;
    public TileButton(int r, int c) {
        coords[0] = r;
        coords[1] = c;
        text = "EMPTY!";
        setText(text);
        resourceEnum = ResourceEnum.NONE;
        buildingEnum = BuildingEnum.NONE;
    }
    public void setResourceEnum(ResourceEnum resource) {
        resourceEnum = resource;
        buildingEnum = BuildingEnum.NONE;
    }
    public void setBuildingEnum(BuildingEnum building) {
        buildingEnum = building;
        resourceEnum = ResourceEnum.NONE;
    }
    public ResourceEnum getResourceEnum() {
        return resourceEnum;
    }

    public BuildingEnum getBuildingEnum() {
        return buildingEnum;
    }

    public void updateButton() {
        if (resourceEnum != ResourceEnum.NONE) {
            text = resourceEnum.toString();
            setBackground(resourceEnum.getColor().getOverallColor());
        }
    }

    public int[] getCoords() {
        return coords;
    }
    public int getRow() {
        return coords[0];
    }
    public int getCol() {
        return coords[1];
    }




}
