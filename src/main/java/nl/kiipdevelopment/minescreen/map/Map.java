package nl.kiipdevelopment.minescreen.map;

import net.minestom.server.entity.Player;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;

import java.util.Collection;

public interface Map {
    static Map of(ScreenGui gui, int x, int y) {
        return new MapImpl(gui.guiId(), x, y);
    }

    int width();

    int height();

    int mapWidth();

    int mapHeight();

    SubMap[] subs();

    SubMapAndIndex sub(int x, int y);

    void sendPacket(Collection<Player> players);

    void sendPacketUpdate(Collection<Player> players);

    default int index(int mapX, int mapY) {
        return mapX + mapY * mapWidth();
    }

    default int globalIndex(int x, int y) {
        return index(toMapX(x), toMapY(y));
    }

    default int toMapX(int x) {
        return x / 128;
    }

    default int toMapY(int y) {
        return y / 128;
    }

    record SubMapAndIndex(SubMap map, int index) {}
}
