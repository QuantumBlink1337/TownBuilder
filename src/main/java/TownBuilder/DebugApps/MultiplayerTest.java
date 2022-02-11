package TownBuilder.DebugApps;

import TownBuilder.*;
import TownBuilder.Buildings.Monuments.OpaleyeWatch;
import com.diogonunes.jcolor.Attribute;

import java.io.IOException;
import java.net.URISyntaxException;

import static TownBuilder.DebugApps.DebugTools.*;

public class MultiplayerTest {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        logging("THIS IS NOT THE ACTUAL GAME. THIS IS A TEST OF THE MULTIPLAYER SYSTEM.");
        Utility.setColor(true);
        //System.out.println(Utility.generateColorizedString("THIS IS NOT THE ACTUAL GAME. THIS IS A TEST OF THE MULTIPLAYER SYSTEM.", Attribute.RED_TEXT()));
        GameInitializer gameInitializer = new GameInitializer();
        gameInitializer.buildingSelection();
        PlayerManager playerManager = new PlayerManager(gameInitializer.getBuildingsForGame(), gameInitializer.getInitializationUI());
        playerManager.determineNumberOfBoards();
        Board OpaleyeBoard = null;
        for (Board board : playerManager.getBoards()) {
            if (board.getScorableBuildings().get(board.getScorableBuildings().size() - 1) instanceof OpaleyeWatch) {
                OpaleyeBoard = board;
            }
        }
        Resource[][] rBoard = OpaleyeBoard.getGameResourceBoard();
        rBoard[0][0] = new Resource(ResourceEnum.WOOD, 0, 0);
        rBoard[1][0] = new Resource(ResourceEnum.BRICK, 1, 0);
        rBoard[1][1] = new Resource(ResourceEnum.GLASS, 1, 1);
        rBoard[1][2] = new Resource(ResourceEnum.WHEAT, 1, 2);
        rBoard[1][3] = new Resource(ResourceEnum.WHEAT, 1, 3);
        do {
            playerManager.manageTurn();
        }
        while (playerManager.gameActive());
    }
}
