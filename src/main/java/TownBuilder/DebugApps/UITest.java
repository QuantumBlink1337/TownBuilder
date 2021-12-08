package TownBuilder.DebugApps;

import TownBuilder.UI.BoardUILayer;


public class UITest {
    public static void main(String[] args) {
        BoardUILayer layer = new BoardUILayer();
        layer.setVisible(true);
//        TileUI buttonTest = new TileUI(1, 2);
//        buttonTest.setBounds(50, 50, 100, 100);
//        layer.add(buttonTest);
        //layer.failedResourcePlacement(1);
        layer.triggerResourcePlacementPrompt();

        layer.listenForTilePress();
        layer.clearErrorLabel();
    }
}
