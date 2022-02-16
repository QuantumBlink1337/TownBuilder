package TownBuilder.Buildings;

import TownBuilder.ResourceEnum;

import java.util.ArrayList;

public class Almshouse implements Building{
    private static final ResourceEnum[][] almshousePattern = new ResourceEnum[1][3];
    private static final ArrayList<ResourceEnum[][]> almshousePatternList = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean condition;
    static {
        almshousePattern[0] = new ResourceEnum[]{ResourceEnum.STONE, ResourceEnum.STONE, ResourceEnum.GLASS};
        BuildingFactory.patternBuilder(almshousePattern, almshousePatternList);
    }
    public Almshouse(int r, int c) {
        row = r;
        col = c;
        condition = false;
    }
    @Override
    public String toString() {
        return "Almshouse";
    }
    @Override
    public ArrayList<ResourceEnum[][]> getBuildingPatternsList() {
        return almshousePatternList;
    }

    @Override
    public BuildingEnum getType() {
        return BuildingEnum.ALMSHOUSE;
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
        int index = 0;
        int score = 0;
        int[] scores = new int[]{-1, 5, -3, 15, -5, 26};
        for (Building[] buildingRow : bArray) {
            for (Building building : buildingRow) {
                if (index > 5) {
                    break;
                }
                if (building.getType() == BuildingEnum.ALMSHOUSE && !building.getCondition()) {
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
        condition = false;
    }

    @Override
    public String getManualEntry() {
        return "The Almshouse grants points based on how many Almshouses you have:\n1 Almshouse: -1 Points\n2 Almshouses: 5 Points\n3 Almshouses: -3 Points\n4 Almshouses: 15 Points\n5 Almshouses: -5 Points\n6 Almshouses: 26 Points";
    }
}
