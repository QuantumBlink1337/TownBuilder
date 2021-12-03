package TownBuilder.Buildings;

import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;
import static TownBuilder.Buildings.BuildingFactory.patternBuilder;
public class Farm implements Building {
    private boolean condition;
    private int fed;
    private final int row;
    private final int col;
    private static final ResourceEnum[][] farmArray = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> farmPatternList = new ArrayList<>();

    public Farm(int r, int c) {
        row = r;
        col = c;
        condition = false;
        fed = 4;
    }
    static {
        farmArray[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.WHEAT};
        farmArray[1] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.WOOD};
        patternBuilder(farmArray, farmPatternList);
    }

    public BuildingEnum getType() {
        return BuildingEnum.FARM;
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
        return "Farm";
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    public void printManualText() {
        System.out.println("The Farm feeds up to four buildings on the board.");
        System.out.println("Here's what it looks like:");
        Utility.printFormattedResourcePattern(farmArray);
    }
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return farmPatternList;
    }
    public int scorer(Building[][] bArray) throws IOException {
        DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - SCORING: Beginning scoring protocol. Returning 0.");
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) throws IOException {
        DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - FEEDING: Beginning feed protocol.");
        for (Building[] buildingRow : buildingBoard) {
            for (Building building : buildingRow) {
                DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - FEEDING: Searching for feedable status - " + DebugTools.buildingInformation(building));
                if (BuildingFactory.determineFeedStatus(building) && fed > 0) {
                    Utility.feedBuildings(building);
                    DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - FEEDING: Found a feedable building at " + DebugTools.buildingInformation(building));
                    fed--;
                    DebugTools.logging("["+Utility.lengthResizer(this.getType().toString(), 9)+"] - FEEDING: Fed condition of Farm building after increment: " + fed);
                }
            }
        }
    }

}
