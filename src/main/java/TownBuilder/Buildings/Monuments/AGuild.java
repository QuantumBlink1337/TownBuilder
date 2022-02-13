package TownBuilder.Buildings.Monuments;

import TownBuilder.Board;
import TownBuilder.Buildings.Building;
import TownBuilder.Buildings.BuildingEnum;
import TownBuilder.Buildings.BuildingFactory;
import TownBuilder.Resource;
import TownBuilder.ResourceEnum;
import TownBuilder.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class AGuild implements Monument {
    private static final ResourceEnum[][] pattern = new ResourceEnum[3][3];
    private static final ArrayList<ResourceEnum[][]> patternList = new ArrayList<>();
    private final int row;
    private final int col;
    private final Board board;
    private int MAX_BUILDING_PLACEMENTS = 2;
    private boolean condition;


    static {
        pattern[0] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.NONE, ResourceEnum.GLASS};
        pattern[1] = new ResourceEnum[]{ResourceEnum.NONE, ResourceEnum.WHEAT, ResourceEnum.STONE};
        pattern[2] = new ResourceEnum[]{ResourceEnum.WOOD, ResourceEnum.BRICK, ResourceEnum.NONE};
        BuildingFactory.patternBuilder(pattern, patternList);
    }
    public AGuild(int r, int c, Board b) {
        row = r;
        col = c;
        board = b;
        condition = false;
    }
    @Override
    public String toString() {
        return "Architect's Guild";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return patternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.AGUILD;
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
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public boolean isFeedable() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray) {
        return 1;
    }

    @Override
    public void onTurnInterval(Building[][] buildingBoard) {
        //
    }

    @Override
    public void printManualText() {
        System.out.println("The Architect's Guild is worth one point. \nWhen built, you may replace two buildings on your board with a building of your choice.");
        System.out.println("Here's what it look's like:");
    }

    @Override
    public String getManualEntry() {
        return "The Architect's Guild is worth one point. \nWhen built, you may replace two buildings on your board with a building of your choice.";
    }

    @Override
    public void onPlacement() throws IOException {
        while (MAX_BUILDING_PLACEMENTS != 0) {
            board.getBoardUI().promptYesNoPrompt("Replace a building?");
            synchronized (Utility.getNotifier()) {
                try {
                    Utility.getNotifier().wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (board.getBoardUI().getUserYesNoAnswer()) {
                BuildingEnum selection= flagOverturnedResources(board.buildingPlacer(board.getDetectableBuildings(),false, false));
                board.getBoardUI().resetBoardTiles(true, true);
                board.getBoardUI().unlockBuildingTiles(getType());
                board.getBuildingFactory().placeBuildingOnBoard(selection, board.getDetectableBuildings(), false, board);
                board.updateBoard();
            }
            MAX_BUILDING_PLACEMENTS--;
        }


    }
    private BuildingEnum flagOverturnedResources(BuildingEnum buildingEnum) throws IOException {
        ArrayList<Resource> validResources = board.getBuildingFactory().getValidResources();
        board.getBuildingFactory().clearValidResourcesWithFlag(buildingEnum);
        board.getBuildingFactory().clearValidResourcesWithFlag(this.getType());
        for (Resource[] resourceRow : board.getGameResourceBoard()) {
            for (Resource resource : resourceRow) {
                if (resource.getResource() == ResourceEnum.OBSTRUCTED && board.getGameBuildingBoard()[resource.getRow()][resource.getCol()] != this) {
                    resource.setScannedBuilding(buildingEnum);
                    validResources.add(resource);
                }
            }
        }
        return buildingEnum;
    }
}
