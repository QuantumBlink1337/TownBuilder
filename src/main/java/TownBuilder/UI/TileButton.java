package TownBuilder.UI;

import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.ResourceEnum;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;


public class TileButton extends JButton {


    private final int[] coords = new int[2];

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
        actionListenerActive = true;
        setFont(new Font("TimesRoman", Font.BOLD, (int) UI_Utilities.convertFontSize(35f)));
        resourceEnum = ResourceEnum.NONE;
        buildingEnum = BuildingEnum.NONE;
    }
    public void setResourceEnum(ResourceEnum resource) {
        if (resource == resourceEnum) {
            return;
        }
        resourceEnum = resource;
        updateButton();
    }
    public void setBuildingEnum(BuildingEnum building) {
        if (building == buildingEnum) {
            return;
        }
        setText("");
        buildingEnum = building;
        updateButton();
    }
    public ResourceEnum getResourceEnum() {
        return resourceEnum;
    }

    public BuildingEnum getBuildingEnum() {
        return buildingEnum;
    }

    public void updateButton() {
        if (resourceEnum != ResourceEnum.NONE && resourceEnum != ResourceEnum.OBSTRUCTED) {
            setBackground(resourceEnum.getColor().getOverallColor());
            setText(resourceEnum.toString());
            setActionListenerActive(false);
        }
        else if (resourceEnum == ResourceEnum.NONE) {
            setText("");
            setBackground(null);
        }
        else if (buildingEnum != BuildingEnum.NONE) {
            setText("");
            setBackground(buildingEnum.getColor().getOverallColor());
            updateImage(buildingEnum);
            setActionListenerActive(false);
        }
        else {
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
    private void updateImage(BuildingEnum buildingEnum) {
        setIcon(UI_Utilities.getBuildingIcon(buildingEnum.getColor()));

    }



}
