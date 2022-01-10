package TownBuilder;

import TownBuilder.DebugApps.DebugTools;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Driver {


    public static JFrame gameFrame = new JFrame();


    public static void main (String[] args) throws IOException, URISyntaxException, InterruptedException {
        initFrame();
        GameInitializer gameInitializer = new GameInitializer();

        DebugTools.initFile();
        gameInitializer.buildingSelection();
        PlayerManager playerManager = new PlayerManager(gameInitializer.getBuildingsForGame(), gameInitializer.getInitializationUI());
        playerManager.determineNumberOfBoards();
//        Manual.tutorial();
//        playerManager.getBoards().get(0).getManual().displayBuildings();
        do {
            playerManager.manageTurn();
        }
        while(playerManager.gameActive());
        System.out.println("All players have finished TownBuilder. Thanks for playing! -Matt");
        gameFrame.setVisible(false);
    }


    public static JFrame getGameFrame() {
        return gameFrame;
    }
    public static void initFrame() {
        gameFrame.setSize(2560, 1440);
        gameFrame.setVisible(true);
    }
}
