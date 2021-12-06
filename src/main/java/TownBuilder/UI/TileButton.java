package TownBuilder.UI;

import TownBuilder.ColorEnum;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TileButton extends JButton implements ActionListener {


    private final JButton tile = new JButton();
    private final int[] coords = new int[2];
    private String text;
    private ColorEnum color;



    private boolean clicked = false;
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
    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        this.clicked = true;
    }
}
