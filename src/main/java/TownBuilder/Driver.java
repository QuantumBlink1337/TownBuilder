package TownBuilder;

import TownBuilder.DebugApps.DebugTools;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Driver {


    public static JFrame gameFrame = new JFrame();


    public static void main (String[] args) throws IOException, URISyntaxException, InterruptedException {
        initFrame();
        GameInitializer gameInitializer = new GameInitializer();

        //DebugTools.initFile();
        gameInitializer.buildingSelection();
        PlayerManager playerManager = new PlayerManager(gameInitializer.getBuildingsForGame(), gameInitializer.getInitializationUI());
        playerManager.determineNumberOfBoards();

        do {
            playerManager.manageTurn();
            initFrame();
        }
        while(playerManager.gameActive());
        gameFrame.setVisible(false);
    }


    public static JFrame getGameFrame() {
        return gameFrame;
    }
    public static void initFrame() {
        gameFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        gameFrame.setVisible(true);
    }
}
