package TownBuilder;

import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class Scorer {
    private int previousScore;
    private final Board board;
    private final ArrayList<Building> buildingsForGame;
    public Scorer(Board b, ArrayList<Building> bu) {
        board = b;
        buildingsForGame = new ArrayList<>(bu);


    }
    public int scoring(boolean isMidGameCheck) {
        int totalScore = 0;
        int score;
        int resourcePenalty =0;
        HashMap<BuildingEnum, Integer> scores = new HashMap<>(buildingsForGame.size());
        DebugTools.logging("Scoring check: buildingsForGame size: " + buildingsForGame.size(), 3);
        for (Building value : buildingsForGame) {
            scores.put(value.getType(), 0);
        }
        for (int r = 0; r < board.getGameBuildingBoard().length; r++) {
            for (int c = 0; c < board.getGameBuildingBoard()[r].length; c++) {
                Building building = board.getGameBuildingBoard()[r][c];
                if (building.getType() != BuildingEnum.NONE) {
                    //System.out.println("A building was found at " + Utility.coordsToOutput(r, c) + ". Scoring it.");
                        score = building.scorer(board.getGameBuildingBoard());
                        scores.put(building.getType(), scores.get(building.getType())+score);
                        //System.out.println("Score of "+gameBuildingBoard[r][c] + " at " + Utility.coordsToOutput(r, c) + " : "+ score);
                        totalScore += score;
                        //System.out.println("Total score now: "+totalScore);

                }
                else if (board.getGameResourceBoard()[r][c].getResource() != ResourceEnum.NONE && board.getGameResourceBoard()[r][c].getResource() != ResourceEnum.OBSTRUCTED &&
                board.getGameBuildingBoard()[r][c].getType() == BuildingEnum.CATERINA) {
                    //System.out.println("Resource found. Decrementing. Resource: "+board.getGameResourceBoard()[r][c].getResource());
                    resourcePenalty++;
                }
            }
        }
        totalScore -= resourcePenalty;
        for (int i = 0; i < scores.size(); i++) {

            System.out.println("Score contribution of "+ Utility.generateColorizedString(buildingsForGame.get(i).toString(), buildingsForGame.get(i).getType())+ ": " +scores.get(buildingsForGame.get(i).getType()));

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
        if (!isMidGameCheck) {
            scoreMessage(totalScore);
        }
        return totalScore;
    }
    private void scoreMessage(int score) {
        if (score > 37) {
            System.out.println("Wow! You are a Master Architect. Congratulations!");
        }
        else if (score > 31) {
            System.out.println("Nice work! You are a Town Planner.");
        }
        else if (score > 24) {
            System.out.println("Good work! You are an Engineer.");
        }
        else if (score > 18) {
            System.out.println("You earned the rank of Carpenter.");
        }
        else if (score > 9) {
            System.out.println("You are a Builder's Apprentice.");
        }
        else {
            System.out.println("You are an Aspiring Architect.");
        }
    }
}