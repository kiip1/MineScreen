package nl.kiipdevelopment.minescreen.map.graphics;

import net.minestom.server.map.MapColors;

public interface MapGraphics {
    default void fill(MapColors.PreciseMapColor color) {
        fill(color.getIndex());
    }

    default void fill(MapColors color) {
        fill(color.baseColor());
    }

    void fill(byte color);

    default void drawRectangle(MapColors.PreciseMapColor color, int startX, int startY, int endX, int endY) {
        drawRectangle(color.getIndex(), startX, startY, endX, endY);
    }

    default void drawRectangle(MapColors color, int startX, int startY, int endX, int endY) {
        drawRectangle(color.baseColor(), startX, startY, endX, endY);
    }

    void drawRectangle(byte color, int startX, int startY, int endX, int endY);

    default void drawString(MapColors.PreciseMapColor color, String value, int x, int y) {
        drawString(color.getIndex(), value, x, y);
    }

    default void drawString(MapColors color, String value, int x, int y) {
        drawString(color.baseColor(), value, x, y);
    }

    void drawString(byte color, String value, int x, int y);

    default void drawDot(MapColors.PreciseMapColor color, int x, int y) {
        drawDot(color.getIndex(), x, y);
    }

    default void drawDot(MapColors color, int x, int y) {
        drawDot(color.baseColor(), x, y);
    }

    void drawDot(byte color, int x, int y);

    MapGraphics relative(int x, int y, int width, int height);
}
