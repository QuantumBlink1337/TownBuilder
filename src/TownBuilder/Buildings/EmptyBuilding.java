package TownBuilder.Buildings;

public class EmptyBuilding extends Building{
    public EmptyBuilding() {}
    @Override
    public BuildingEnum getType() {
        return BuildingEnum.NONE;
    }

    @Override
    public void setCondition(boolean b) {

    }

    @Override
    public boolean getCondition() {
        return false;
    }

    @Override
    public int scorer(Building[][] bArray, int row, int col) {
        return 0;
    }
}
