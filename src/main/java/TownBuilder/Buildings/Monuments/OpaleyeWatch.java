package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class OpaleyeWatch implements Monument{
    private static final ResourceEnum[][] pattern = new ResourceEnum[3][4];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private final Board board;
    private boolean condition;
    private final ArrayList<BuildingEnum> chosenBuildings = new ArrayList<>();
    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.NONE,ResourceEnum.NONE,ResourceEnum.NONE};
        pattern[1] = new ResourceEnum[]{ResourceEnum.BRICK,ResourceEnum.GLASS,ResourceEnum.WHEAT,ResourceEnum.WHEAT};
        pattern[2] = new ResourceEnum[]{ResourceEnum.STONE,ResourceEnum.NONE,ResourceEnum.NONE,ResourceEnum.NONE};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public OpaleyeWatch(int r, int c, Board b) {
        row = r;
        col = c;
        board = b;
        condition = false;
    }


    public String toString() {
        return "Opaleye's Watch";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.OPALEYE;
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
        return 0;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) throws IOException {
        Board[] adjacentBoards = board.getPlayerManager().getAdjacentBoards(board);
        for (Board board : adjacentBoards) {
            if (board.getLastBuiltBuilding() == chosenBuildings.get(0) || board.getLastBuiltBuilding() == chosenBuildings.get(1) ||board.getLastBuiltBuilding() == chosenBuildings.get(2)) {
                board.getBuildingFactory().placeBuildingOnBoard(board.getLastBuiltBuilding(), board.getDetectableBuildings(), true, board);
                chosenBuildings.remove(board.getLastBuiltBuilding());
                break;
            }
        }
    }

    @Override
    public void printManualText() {
        System.out.println("Upon placement, declare three different buildings.\nWhenever a player to your left or right constructs one of those buildings,\nplace that building on an empty spot on your board\nand remove it from Opaleye's Watch.");
        System.out.println("Here's what it looks like:");
        Utility.arrayPrinter(pattern);
    }

    @Override
    public void onPlacement() throws IOException {
        System.out.println("Choose three buildings to track.");
        for (int i = 0; i < 3; i++) {
            chosenBuildings.add(board.buildingPlacer(board.getScorableBuildings(), false));
        }
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
