package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.BoardTraverser;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.DebugApps.DebugTools;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SilvaForum implements Monument{
    private static final ResourceEnum[][] pattern = new ResourceEnum[2][4];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private final Board board;
    private int score;
    private boolean condition;
    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.WHEAT, ResourceEnum.NONE};
        pattern[1] = new ResourceEnum[]{ResourceEnum.BRICK, ResourceEnum.BRICK, ResourceEnum.STONE, ResourceEnum.WOOD};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public SilvaForum(int r, int c, Board b) {
        row = r;
        col = c;
        board = b;
        condition = false;
    }
    @Override
    public String toString() {
        return "Silva Forum";
    }


    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.SILVAFRM;
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
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) throws IOException {
        int score = 1;
        int maxFound = 0;
        for (BuildingEnum buildingEnum : Utility.convertBuildingListToEnumList(board.getScorableBuildings())) {
            int currentFound = contiguousCheck(bArray, buildingEnum);
            if (currentFound > maxFound) {
                DebugTools.logging("[SILVA_SCORING] - currentFound is larger than maxFound for enum . " + buildingEnum +" Changing value to " + currentFound);
                maxFound = currentFound;
            }
            else {
                DebugTools.logging("[SILVA_SCORING] - currentFound is not larger than maxFound. Current value: " + currentFound);
            }
        }
        return score+maxFound;
    }
    private int contiguousCheck(Building[][] buildingBoard, BuildingEnum buildingEnum) throws IOException {
        DebugTools.logging("[SILVA_CONTIGUOUS_CHECK] - Beginning Contiguous Group check.");
        DebugTools.logging("[SILVA_CONTIGUOUS_CHECK] - SEARCHING FOR " + buildingEnum);
        ArrayList<Building> foundBuildings = new ArrayList<>();
        for (Building[] buildingRow : buildingBoard) {
            for (Building building : buildingRow) {
                if (building.getType() == buildingEnum) {
                    DebugTools.logging("[SILVA_CONTIGUOUS_CHECK] - Searching " + DebugTools.buildingInformation(building));
                    Building[] adjacentBuildings = BoardTraverser.getAdjacentBuildings(buildingBoard, building.getRow(), building.getCol());
                    if (BoardTraverser.searchForBuilding(adjacentBuildings, buildingEnum)) {
                        DebugTools.logging("[SILVA_CONTIGUOUS_CHECK] - Given building has at least one adjacent building of the same type. Continuing");
                        foundBuildings.add(building);
                    } else {
                        DebugTools.logging("[SILVA_CONTIGUOUS_CHECK] - Given building has no adjacent buildings of the same type. It is not connected.");
                        foundBuildings.clear();
                    }
                }
            }
        }
        Set<Building> hash = new HashSet<>(foundBuildings);
        System.out.println(buildingEnum);
        for (Building building : foundBuildings) {
            System.out.println(DebugTools.buildingInformation(building));
        }
        foundBuildings = new ArrayList<>(hash);

        return foundBuildings.size();

    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) throws IOException {

    }

    @Override
    public String getManualEntry() {
        return "+1 Point. Grants points equal to the size of the largest group of unique contiguous buildings.";
    }

    @Override
    public void onPlacement() throws IOException {

    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }
}
