package nl.kiipdevelopment.minescreen.map;

import net.minestom.server.entity.Player;

import java.util.Collection;

final class MapImpl implements Map {
    private final SubMap[] subs;
    private final int width, height;

    public MapImpl(short guiId, int width, int height) {
        this.width = width;
        this.height = height;

        subs = new SubMap[mapWidth() * mapHeight()];

        final int mapWidth = mapWidth();
        final int mapHeight = mapHeight();
        final int lastWidth = 128 - (mapWidth * 128 - width);
        final int lastHeight = 128 - (mapHeight * 128 - height);

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                int givenWidth = 128;
                int givenHeight = 128;

                if (x == mapWidth - 1) givenWidth = lastWidth;
                if (y == mapHeight - 1) givenHeight = lastHeight;

                subs[index(x, y)] = new SubMapImpl(guiId, x, y, givenWidth, givenHeight);
            }
        }
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int mapWidth() {
        return (int) Math.ceil(width / 128d);
    }

    @Override
    public int mapHeight() {
        return (int) Math.ceil(height / 128d);
    }

    @Override
    public SubMap[] subs() {
        return subs;
    }

    @Override
    public SubMapAndIndex sub(int x, int y) {
        SubMap subMap = subs[globalIndex(x, y)];

        return new SubMapAndIndex(subMap, subMap.globalIndex(x, y));
    }

    @Override
    public void sendPacket(Collection<Player> players) {
        for (SubMap sub : subs)
            sub.sendPacket(players);
    }

    @Override
    public void sendPacketUpdate(Collection<Player> players) {
        for (SubMap sub : subs)
            sub.sendPacketUpdate(players);
    }
}
