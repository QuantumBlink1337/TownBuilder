package TownBuilder.UI;

import TownBuilder.Buildings.Building;
import TownBuilder.ResourceEnum;

import javax.swing.*;
import java.awt.*;


public class UI_Utilities {

    static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
    static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    static final String INTERACTIVE_PANEL_WIDTH = convertIntToPercentString(420, true);


    public static String convertIntToPercentString(int value, boolean isWidth) {
        int constant = isWidth ? SCREEN_WIDTH : SCREEN_HEIGHT;
        String string = (((float) convertBaseValueToScaledValue(value, isWidth)  / constant) * 100) + "sp";
        System.out.println(string);
        return string;
    }
    public static int convertBaseValueToScaledValue(int value, boolean isWidth) {
        if (isWidth) {
            return (value * SCREEN_WIDTH) / 2560;
        }
        else {
            return (value * SCREEN_HEIGHT) / 1440;
        }
    }
    public static float convertFontSize(float value) {
        return (float) (value * (SCREEN_WIDTH/2560.0));
    }
    public static JPanel initializeBuildingMatrix(Building building) {
        ResourceEnum[][] buildingPattern = building.getBuildingPatternsList().get(0);
        JPanel tilePanel = new JPanel(new GridLayout(buildingPattern.length, buildingPattern[buildingPattern.length-1].length, 2, 0));
        for (ResourceEnum[] resourceEnums : buildingPattern) {
            for (ResourceEnum resourceEnum : resourceEnums) {
                JButton temp = new JButton(resourceEnum.toString());
                temp.setBackground(resourceEnum.getColor().getOverallColor());
                if (temp.getText().equals("NONE")) {
                    temp.setVisible(false);
                }
                tilePanel.add(temp);
            }
        }
        return tilePanel;
    }


}
