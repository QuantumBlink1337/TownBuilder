package TownBuilder;

import TownBuilder.DebugApps.DebugTools;
import java.io.IOException;
import java.net.URISyntaxException;

public class Driver {

    public static void main (String[] args) throws IOException, URISyntaxException {
        GameInitializer gameInitializer = new GameInitializer();
        DebugTools.initFile();
        colorPrintingDetermination();
        gameInitializer.buildingSelection();
        PlayerManager playerManager = new PlayerManager(gameInitializer.getBuildingsForGame());
        playerManager.determineNumberOfBoards();
        Manual.tutorial();
        playerManager.getBoards().get(0).getManual().displayBuildings();
        do {
            playerManager.manageTurn();
        }
        while(playerManager.gameActive());
        System.out.println("All players have finished TownBuilder. Thanks for playing! -Matt");
    }
    private static void colorPrintingDetermination() {
        System.out.println("Are you playing on a Windows terminal? (Powershell or Command Line)");
        Utility.setColor(!Utility.prompt());
    }
}
