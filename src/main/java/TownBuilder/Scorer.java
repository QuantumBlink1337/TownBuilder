package TownBuilder;

import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.DebugApps.DebugTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Scorer {

    private final Board board;
    private final ArrayList<Building> buildingsForGame;
    public int getResourcePenalty() {
        return resourcePenalty;
    }
    private int resourcePenalty;
    public HashMap<BuildingEnum, Integer> getScores() {
        return scores;
    }

    private HashMap<BuildingEnum, Integer> scores;
    public Scorer(Board b, ArrayList<Building> bu) {
        board = b;
        buildingsForGame = new ArrayList<>(bu);


    }
    public int scoring() throws IOException {
        int totalScore = 0;
        int score;
        resourcePenalty =0;
        /*
            We need to check for the Caterina monument on the board, because that monument changes the scoring conditions.
            With that monument on a Board, resources are now worth 0 instead of -1.
         */
        boolean caterina = false;
        scores = new HashMap<>(buildingsForGame.size()); // used to itemize scores from each color type
        DebugTools.logging("Scoring check: buildingsForGame size: " + buildingsForGame.size());
        for (Building value : buildingsForGame) {
            scores.put(value.getType(), 0);
        }
        for (int r = 0; r < board.getGameBuildingBoard().length; r++) {
            for (int c = 0; c < board.getGameBuildingBoard()[r].length; c++) {
                Building building = board.getGameBuildingBoard()[r][c];
                if (building.getType() != BuildingEnum.NONE) {
                        score = building.scorer(board.getGameBuildingBoard());
                        scores.put(building.getType(), scores.get(building.getType())+score);
                        totalScore += score;

                }
                if (building.getType() == BuildingEnum.CATERINA) {
                    resourcePenalty = 0;
                    caterina = true;
                }
                else if (board.getGameResourceBoard()[r][c].getResource() != ResourceEnum.NONE && board.getGameResourceBoard()[r][c].getResource() != ResourceEnum.OBSTRUCTED && !caterina) {
                    resourcePenalty++;
                }
            }
        }
        // subtract however many resources we found (if Caterina was found, this just is 0)
        totalScore -= resourcePenalty;


        return totalScore;
    }
}