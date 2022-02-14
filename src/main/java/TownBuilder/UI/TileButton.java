package TownBuilder.UI;

import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.ResourceEnum;

import javax.swing.*;
import java.awt.*;



public class TileButton extends JButton {


    private final int[] coords = new int[2];
    private String text;

    public boolean isActionListenerActive() {
        return actionListenerActive;
    }

    public void setActionListenerActive(boolean enabled) {
        this.actionListenerActive = enabled;
    }

    private boolean actionListenerActive;

    ResourceEnum resourceEnum;
    BuildingEnum buildingEnum;

    public void setActiveBuilding(boolean activeBuilding) {
        this.activeBuilding = activeBuilding;
    }
    boolean activeBuilding;

    public TileButton(int r, int c) {
        coords[0] = r;
        coords[1] = c;
        text = "EMPTY!";
        actionListenerActive = true;
        setText(text);
        setFont(new Font("TimesRoman", Font.BOLD, (int) UI_Utilities.convertFontSize(35f)));
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
        if (resourceEnum != ResourceEnum.NONE && resourceEnum != ResourceEnum.OBSTRUCTED) {
            setText(text = resourceEnum.toString());
            setBackground(resourceEnum.getColor().getOverallColor());
            setActionListenerActive(false);
        }
        else if (buildingEnum != BuildingEnum.NONE) {
            setText(text = buildingEnum.toString());
            setBackground(buildingEnum.getColor().getOverallColor());
            setActionListenerActive(false);
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
