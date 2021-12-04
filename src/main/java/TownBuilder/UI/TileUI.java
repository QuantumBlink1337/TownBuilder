package TownBuilder.UI;

import TownBuilder.ColorEnum;

import javax.swing.*;

public class TileUI extends JButton {
    JButton tile = new JButton();
    int[] coords;
    ColorEnum color;
    public TileUI(int r, int c, ColorEnum co) {
        coords[0] = r;
        coords[1] = c;
        color = co;
    }

}
