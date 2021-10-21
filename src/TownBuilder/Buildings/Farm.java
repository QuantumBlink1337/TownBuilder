package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.util.ArrayList;
import static TownBuilder.Buildings.BuildingFactory.patternBuilder;
public class Farm implements Building {
    private boolean condition;
    private int fed;
    private static final ResourceEnum[][] farmArray = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> farmPatternList = new ArrayList<>();

    public Farm() {
        condition = false;
        fed = 4;
    }
    static {
        farmArray[0] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.WHEAT};
        farmArray[1] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.WOOD};
        patternBuilder(farmArray, farmPatternList, 3);
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

    public void decrementFed() {
        --fed;
    }
    public int getFed() {
        return fed;
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
        Utility.arrayPrinter(farmArray);
    }
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {

        return farmPatternList;
    }
    public int scorer(Building[][] bArray, int row, int col, int scoreIncrement) {
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard, int row, int col) {
        for (Building[] buildingRow : buildingBoard) {
            for (Building building : buildingRow) {
                if (building.isFeedable() && !building.getCondition() && fed > 0) {
                    building.setCondition(true);
                    fed--;
                }
            }
        }
    }

}
