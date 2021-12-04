package TownBuilder.UI;

import TownBuilder.ColorEnum;

import javax.swing.*;

public class TileButton extends JButton {


    JButton tile = new JButton();
    final int[] coords = new int[2];
    String text;
    ColorEnum color;
    public TileButton(int r, int c, ColorEnum co, String t) {
        coords[0] = r;
        coords[1] = c;
        color = co;
        text = t;
        tile.setText(text);
        setBorder(BorderFactory.createBevelBorder(2));
    }
    public TileButton(int r, int c) {
        coords[0] = r;
        coords[1] = c;
        color = ColorEnum.COLORLESS;
        text = "EMPTY!";
        tile.setText(text);

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

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

}
