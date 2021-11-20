package TownBuilder.DebugApps;

import TownBuilder.Board;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameManager {


    private ArrayList<Board> boards;
    private boolean isSingleplayer;

    public GameManager(ArrayList<Board> b) {
        boards = b;
        isSingleplayer = (boards.size() > 1);
    }
    public ArrayList<Board> getBoards() {
        return boards;
    }
    public Board[] getAdjacentBoards(Board b) {
        if (isSingleplayer) {
            return null;
        }
        else {
        }
    }



    public void manageTurn() {

    }
}
