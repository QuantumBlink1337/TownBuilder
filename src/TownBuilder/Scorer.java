package TownBuilder;

import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.Tavern;

import java.util.ArrayList;
import java.util.HashMap;

public class Scorer {
    private int previousScore;
    private Board board;
    private ArrayList<Building> buildingsForGame;
    public Scorer(Board b, ArrayList<Building> bu) {
        board = b;
        buildingsForGame = new ArrayList<>(bu);


    }
    public int scoring(boolean isMidGameCheck) {
        int totalScore = 0;
        int score = 0;
        int resourcePenalty =0;
        int scoreIncrement = 0;
        BuildingEnum scoreIncrementType = BuildingEnum.NONE;
        HashMap<BuildingEnum, Integer> scores = new HashMap<>(buildingsForGame.size());
        for (int i = 0; i < buildingsForGame.size(); i++) {
            scores.put(buildingsForGame.get(i).getType(), 0);
            if (buildingsForGame.get(i).getType() == BuildingEnum.TAVERN) {
                scoreIncrement = Tavern.getSCORE_INCREMENT_STARTING_VALUE();
                scoreIncrementType = BuildingEnum.TAVERN;

            }
        }
        ArrayList<Building> chapels = new ArrayList<>();
        for (int r = 0; r < board.getGameBuildingBoard().length; r++) {
            for (int c = 0; c < board.getGameBuildingBoard()[r].length; c++) {
                if (board.getGameBuildingBoard()[r][c].getType() != BuildingEnum.NONE) {
                    //System.out.println("A building was found at " + Utility.coordsToOutput(r, c) + ". Scoring it.");
                    if (board.getGameBuildingBoard()[r][c].getType() == BuildingEnum.CHAPEL) {
                        chapels.add(board.getGameBuildingBoard()[r][c]);
                    }
                    else {
                        score = board.getGameBuildingBoard()[r][c].scorer(board.getGameBuildingBoard(), r, c, scoreIncrement);
                        if (scoreIncrementType == board.getGameBuildingBoard()[r][c].getType()) {
                            scoreIncrement++;
                        }
                        scores.put(board.getGameBuildingBoard()[r][c].getType(), scores.get(board.getGameBuildingBoard()[r][c].getType())+score);
                        //System.out.println("Score of "+gameBuildingBoard[r][c] + " at " + Utility.coordsToOutput(r, c) + " : "+ score);
                        totalScore += score;
                        //System.out.println("Total score now: "+totalScore);
                    }
                }
                else if (board.getGameResourceBoard()[r][c].getResource() != ResourceEnum.NONE && board.getGameResourceBoard()[r][c].getResource() != ResourceEnum.OBSTRUCTED) {
                    //System.out.println("Resource found. Decrementing. Resource: "+board.getGameResourceBoard()[r][c].getResource());
                    resourcePenalty++;
                }
            }
        }
        for (Building chapel : chapels) {
            score = chapel.scorer(board.getGameBuildingBoard(), 0, 0, 0);
            totalScore += score;
            scores.put(chapel.getType(), score);
        }
        totalScore -= resourcePenalty;
        for (int i = 0; i < scores.size(); i++) {
            System.out.println("Score contribution of "+buildingsForGame.get(i).toString() + ": " +scores.get(buildingsForGame.get(i).getType()));

        }
        System.out.println("Resource penalty: -" + resourcePenalty);
        if (isMidGameCheck) {
            System.out.println("Current score: " + totalScore);
            if (totalScore > previousScore && previousScore !=0) {
                System.out.println("Nice! You're improving your board's score! Keep playing!");
            }
            else if (totalScore < previousScore && previousScore !=0) {
                System.out.println("Your board's score has decreased since the last time you scored it.");
            }
            previousScore = totalScore;
        }
        else {
            System.out.println("Total score: " + totalScore);
        }
        return totalScore;
    }
}