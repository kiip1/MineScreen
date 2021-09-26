package nl.kiipdevelopment.minescreen.map;

import net.minestom.server.entity.Player;

import java.util.Collection;

public interface Map {
    int width();

    int height();

    int mapWidth();

    int mapHeight();

    SubMap[] subMaps();

    SubMapAndIndex subMap(int x, int y);

    void sendPacket(Collection<Player> players);

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

    record SubMapAndIndex(SubMap subMap, int index) {}
}
