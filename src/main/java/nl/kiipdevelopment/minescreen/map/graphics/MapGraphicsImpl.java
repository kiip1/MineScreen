package nl.kiipdevelopment.minescreen.map.graphics;

import net.minestom.server.map.MapColors;
import net.minestom.server.utils.validate.Check;
import nl.kiipdevelopment.minescreen.map.Map;
import nl.kiipdevelopment.minescreen.map.SubMap;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;
import nl.kiipdevelopment.minescreen.util.Unimplemented;

import java.util.Arrays;

public class MapGraphicsImpl implements MapGraphics {
    protected final ScreenGui gui;
    protected final Map map;

    public MapGraphicsImpl(ScreenGui gui) {
        this.gui = gui;
        map = gui.map();
    }

    @Override
    public void fill(MapColors color) {
        fill(color.baseColor());
    }

    private void fill(byte color) {
        for (SubMap subView : gui.map().subMaps()) {
            Arrays.fill(subView.colors(), color);
        }
    }

    @Override
    public void drawRectangle(MapColors color, int startX, int startY, int endX, int endY) {
        ensureWithinBounds(startX, startY);
        ensureWithinBounds(endX, endY);

        drawRectangle(color.baseColor(), startX, startY, endX, endY);
    }

    private void drawRectangle(byte color, int startX, int startY, int endX, int endY) {
        // TODO: Optimize this somehow
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                Map.SubMapAndIndex subMapAndIndex = map.subMap(x, y);

                subMapAndIndex.subMap().colors()[subMapAndIndex.index()] = color;
            }
        }
    }

    @Unimplemented
    @Override
    public void drawString(MapColors color, String value, int x, int y) {
        ensureWithinBounds(x, y);

        drawString(color.baseColor(), value, x, y);
    }

    private void drawString(byte color, String value, int x, int y) {
        // TODO Implement
        throw new UnsupportedOperationException("MapGraphicsImpl#drawString isn't implemented yet.");
    }

    @Override
    public void drawDot(MapColors color, int x, int y) {
        ensureWithinBounds(x, y);

        drawDot(color.baseColor(), x, y);
    }

    private void drawDot(byte color, int x, int y) {
        Map.SubMapAndIndex subMapAndIndex = map.subMap(x, y);

        subMapAndIndex.subMap().colors()[subMapAndIndex.index()] = color;
    }

    @Override
    public MapGraphics subGraphics(int x, int y, int width, int height) {
        return new RelativeMapGraphicsImpl(gui, x, y, width, height);
    }

    private void ensureWithinBounds(int x, int y) {
        Check.stateCondition(isInBounds(x, y), "Trying to access pixel at {0}, {1}, which is out of bounds.", x, y);
    }

    private boolean isInBounds(int x, int y) {
        return x >= map.width() ||
                x < 0 ||
                y >= map.height() ||
                y < 0;
    }
}
