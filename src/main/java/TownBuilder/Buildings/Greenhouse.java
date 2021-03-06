package TownBuilder.Buildings;

import TownBuilder.BoardTraverser;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;

import java.io.IOException;
import java.util.ArrayList;

public class Greenhouse implements Building {
    private final int row;
    private final int col;
    private boolean condition;
    private static final ResourceEnum[][] greenhouseArray = new ResourceEnum[2][2];
    private static final ArrayList<ResourceEnum[][]> greenhousePatternList = new ArrayList<>();
    static {
        greenhouseArray[0] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.WOOD};
        greenhouseArray[1] = new ResourceEnum[]{ResourceEnum.WHEAT, ResourceEnum.GLASS};
        BuildingFactory.patternBuilder(greenhouseArray, greenhousePatternList);
    }

    public Greenhouse(int r, int c) {
        row = r;
        col =c;
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return greenhousePatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.GRENHOUSE;
    }

    @Override
    public boolean getCondition() {
        return condition;
    }

    @Override
    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    @Override
    public boolean getFedStatus() {
        return false;
    }

    @Override
    public void setFedStatus(boolean condition) {

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
        return "Greenhouse";
    }
    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) {
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) throws IOException {
        DebugTools.logging("Beginning Greenhouse Turn Interval");
        for (Building building : contiguousCheck(buildingBoard)) {
            DebugTools.logging("Feeding " + DebugTools.buildingInformation(building));
            building.setFedStatus(true);
        }
    }
    private ArrayList<Building> contiguousCheck(Building[][] buildingBoard) throws IOException {
        DebugTools.logging("Beginning Contiguous Group check.");
        ArrayList<Building> contiguousBuildings = new ArrayList<>();
        for (Building[] buildingRow : buildingBoard) {
            for (Building building : buildingRow) {
                DebugTools.logging("Searching " + DebugTools.buildingInformation(building));
                Building[] adjacentBuildings = BoardTraverser.getAdjacentBuildings(buildingBoard, building.getRow(), building.getCol());
                if (BoardTraverser.searchForBuilding(adjacentBuildings, BuildingFactory::determineFeedStatus)) {
                    DebugTools.logging("Given building has at least one adjacent unfed buildings. Continuing");
                    contiguousBuildings.add(building);
                }
                else {
                    DebugTools.logging("Given building has no adjacent unfed buildings. It is not connected. Returning list");
                }
            }
        }
        contiguousBuildings.removeIf(building -> !BuildingFactory.determineFeedStatus(building));
        return contiguousBuildings;
    }

    @Override
    public String getManualEntry() {
        return "The Greenhouse feeds a contiguous group of feedable buildings on the board.";
    }
}
