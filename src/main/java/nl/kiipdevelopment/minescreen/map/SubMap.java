package nl.kiipdevelopment.minescreen.map;

import net.minestom.server.entity.Player;

import java.util.Collection;

public interface SubMap {
    int mapX();

    int mapY();

    int width();

    int height();

    int id();

    byte[] colors();

    void sendPacket(Collection<Player> players);

    default int index(int localX, int localY) {
        return localX + localY * width();
    }

    default int globalIndex(int x, int y) {
        return index(toLocalX(x), toLocalY(y));
    }

    default int toLocalX(int x) {
        return x % width();
    }

    default int toLocalY(int y) {
        return y % height();
    }
}
