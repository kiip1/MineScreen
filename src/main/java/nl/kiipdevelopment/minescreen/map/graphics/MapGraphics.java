package nl.kiipdevelopment.minescreen.map.graphics;

import net.minestom.server.map.MapColors;

public interface MapGraphics {
    void fill(MapColors color);

    void drawRectangle(MapColors color, int startX, int startY, int endX, int endY);

    void drawString(MapColors color, String value, int x, int y);

    void drawDot(MapColors color, int x, int y);

    MapGraphics subGraphics(int x, int y, int width, int height);
}
