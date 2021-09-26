package nl.kiipdevelopment.minescreen.world;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;

public interface GuiInstance {
    void placeMaps();

    Instance asInstance();

    Pos spawn();
}
