package TownBuilder.Buildings.Monuments;

import TownBuilder.Buildings.Building;

import java.io.IOException;

public interface Monument extends Building {
    void onPlacement() throws IOException;
}
