package TownBuilder.UI;

import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.ColorEnum;
import TownBuilder.ResourceEnum;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


public class TileButton extends JButton {


    private final int[] coords = new int[2];
    private String text;

    ResourceEnum resourceEnum;
    BuildingEnum buildingEnum;

    public TileButton(int r, int c) {
        coords[0] = r;
        coords[1] = c;
        text = "EMPTY!";
        setText(text);
        setFont(new Font("TimesRoman", Font.BOLD, 35));
        resourceEnum = ResourceEnum.NONE;
        buildingEnum = BuildingEnum.NONE;
    }
    public void setResourceEnum(ResourceEnum resource) {
        resourceEnum = resource;
    }
    public void setBuildingEnum(BuildingEnum building) {
        buildingEnum = building;
    }
    public ResourceEnum getResourceEnum() {
        return resourceEnum;
    }

    public BuildingEnum getBuildingEnum() {
        return buildingEnum;
    }

    public void updateButton() {
        System.out.println(Arrays.toString(coords));
        System.out.println(resourceEnum);
        System.out.println(buildingEnum);
        if (resourceEnum != ResourceEnum.NONE && resourceEnum != ResourceEnum.OBSTRUCTED) {
            setText(text = resourceEnum.toString());
            setBackground(resourceEnum.getColor().getOverallColor());
        }
        else if (buildingEnum != BuildingEnum.NONE) {
            setText(text = buildingEnum.toString());
            setBackground(buildingEnum.getColor().getOverallColor());
        }
        else {
            setText(text = "EMPTY!");
            setBackground(null);
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
