package TownBuilder.Buildings;
/*
    this interface contains methods for buildings that do something other than generate points.
    black buildings and monuments should implement this interface.
 */

public interface ActiveBuilding extends Building {
    void onPlacement();
    void turnAction();
}
