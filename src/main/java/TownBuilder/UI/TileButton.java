package TownBuilder.UI;

import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.ResourceEnum;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


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
        actionListenerActive = true;
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
            setBackground(resourceEnum.getColor().getOverallColor());
            setActionListenerActive(false);
        }
        else if (buildingEnum != BuildingEnum.NONE) {
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
        Image img = null;
        try {
            img = ImageIO.read(new File("src/assets/buildings/"+buildingEnum.getColor().toString()+".png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        img = Objects.requireNonNull(img).getScaledInstance(UI_Utilities.convertBaseValueToScaledValue(128, true), UI_Utilities.convertBaseValueToScaledValue(128, false), Image.SCALE_DEFAULT);
        setIcon(new ImageIcon(img));
    }



}
