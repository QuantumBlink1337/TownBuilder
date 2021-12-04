package TownBuilder.UI;

import TownBuilder.ColorEnum;

import javax.swing.*;

public class TileUI extends JButton {


    JButton tile = new JButton();
    int[] coords;
    String text;
    ColorEnum color;
    public TileUI(int r, int c, ColorEnum co, String t) {
        coords[0] = r;
        coords[1] = c;
        color = co;
        text = t;
        tile.setText(text);
    }
    public TileUI(int r, int c) {
        coords[0] = r;
        coords[1] = c;
        color = ColorEnum.COLORLESS;
        text = "EMPTY!";
        tile.setText(text);
    }
    public JButton getTile() {
        return tile;
    }

    public void setTile(JButton tile) {
        this.tile = tile;
    }

    public int[] getCoords() {
        return coords;
    }

    public void setCoords(int[] coords) {
        this.coords = coords;
    }
    public int getRow() {
        return coords[0];
    }
    public int getCol() {
        return coords[1];
    }
    public void setRow(int r) {
        coords[0] = r;
    }
    public void setCol(int c) {
        coords[1] = c;
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
