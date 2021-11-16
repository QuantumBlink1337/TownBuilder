package TownBuilder.Buildings;

import java.io.IOException;

public interface Monument extends Building {
    void onPlacement() throws IOException;
}
