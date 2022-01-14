package TownBuilder.UI;

import java.awt.*;

public class UI_Utilities {

    static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    //static final int SCREEN_HEIGHT = 1440;
    static final int SCREEN_HEIGHT = SCREEN_SIZE.height;

    //static final int SCREEN_WIDTH = 2560;
    static final int SCREEN_WIDTH = SCREEN_SIZE.width;


    static final Dimension BUTTON_SIZE = new Dimension(50, 50);
    static final String INTERACTIVE_PANEL_WIDTH = convertIntToPercentString(420, SCREEN_WIDTH, true);


    public static String convertIntToPercentString(int value, int constant, boolean isWidth) {
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
}
