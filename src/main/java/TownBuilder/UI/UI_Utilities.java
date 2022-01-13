package TownBuilder.UI;

import java.awt.*;

public class UI_Utilities {

    static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
    static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    static final Dimension BUTTON_SIZE = new Dimension(50, 50);
    static final int INTERACTIVE_PANEL_WIDTH = (int) (SCREEN_WIDTH * (420.0/ SCREEN_WIDTH));


    public static String convertIntToPercentString(int value, int constant) {
        System.out.println((((float) value / constant) * 100) + "sp");
        return (((float) value / constant) * 100) + "sp";
    }
}
