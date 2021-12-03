package TownBuilder.Buildings;

import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;


import java.io.IOException;
import java.util.ArrayList;

import static TownBuilder.Buildings.BuildingFactory.patternBuilder;

public class Tavern implements Building {
    private boolean condition;
    private final int row;
    private final int col;
    private static final ResourceEnum[][] tavernArray = new ResourceEnum[1][3];
    private static final ArrayList<ResourceEnum[][]> tavernPatternList = new ArrayList<>();

    public Tavern(int r, int c) {
        row = r;
        col =c;
        condition = false;
    }
    static {
        tavernArray[0] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.BRICK, ResourceEnum.GLASS};
        patternBuilder(tavernArray, tavernPatternList);
    }
    public BuildingEnum getType() {
        return BuildingEnum.TAVERN;
    }

    public boolean getCondition() {
        return condition;
    }
    @Override
    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    public String toString() {
        return "Tavern";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    public void printManualText() {
        System.out.println("The Tavern grants points based on how many Taverns you have:");
        System.out.print("1 Tavern: 2 Points | ");
        System.out.print("2 Taverns: 5 Points | ");
        System.out.print("3 Taverns: 9 Points | ");
        System.out.print("4 Taverns: 14 Points | ");
        System.out.print("5 Taverns: 20 Points\n");
        System.out.println("Here's what it looks like:");
        Utility.printFormattedResourcePattern(tavernArray);
    }
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {

        return tavernPatternList;
    }
    public int scorer(Building[][] bArray) throws IOException {
        int index = 0;
        int score = 0;
        int[] scores = new int[]{2, 5, 9, 14, 20, 27};
        DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - SCORING: Beginning scoring protocol.");
        for (Building[] buildingRow : bArray) {
            for (Building building : buildingRow) {
                DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - SCORING: Checking building: " + DebugTools.buildingInformation(building));
                if (index > 5) {
                    break;
                }
                if (building.getType() == BuildingEnum.TAVERN && !building.getCondition()) {
                    DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - SCORING: Building is a Tavern and condition is false. Setting condition to true. Score: " + score);
                    building.setCondition(true);
                    score = scores[index];
                    index++;
                }
            }
        }
        return score;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        this.setCondition(false);
        // nothing
    }

}
