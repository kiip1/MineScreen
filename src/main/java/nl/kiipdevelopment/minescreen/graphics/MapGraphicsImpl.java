package nl.kiipdevelopment.minescreen.graphics;

import net.minestom.server.utils.validate.Check;
import nl.kiipdevelopment.minescreen.map.Map;
import nl.kiipdevelopment.minescreen.map.SubMap;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;

import java.util.Arrays;

sealed class MapGraphicsImpl implements MapGraphics permits RelativeMapGraphicsImpl {
    protected final ScreenGui gui;
    protected final Map map;

    public MapGraphicsImpl(ScreenGui gui) {
        this.gui = gui;
        map = gui.map();
    }

    @Override
    public void fill(byte color) {
        for (SubMap subView : gui.map().subs())
            Arrays.fill(subView.colors(), color);
    }

    @Override
    public void drawRectangle(byte color, int startX, int startY, int endX, int endY) {
        ensureWithinBounds(startX, startY, "Top left");
        ensureWithinBounds(endX, endY, "Bottom right");

        // TODO: Optimize this somehow
        for (int x = startX; x < endX; x++)
            for (int y = startY; y < endY; y++)
                drawDirectDot(color, x, y);
    }

    @Override
    public void drawString(byte color, String value, int x, int y) {
        ensureWithinBounds(x, y);

        throw new UnsupportedOperationException("MapGraphicsImpl#drawString isn't implemented yet.");
    }

    @Override
    public void drawDot(byte color, int x, int y) {
        ensureWithinBounds(x, y);

        drawDirectDot(color, x, y);
    }

    @Override
    public void drawDirectDot(byte color, int x, int y) {
        Map.SubMapAndIndex subMapAndIndex = map.sub(x, y);

        subMapAndIndex.map().colors()[subMapAndIndex.index()] = color;
    }

    @Override
    public MapGraphics relative(int x, int y, int width, int height) {
        return new RelativeMapGraphicsImpl(gui, x, y, width, height);
    }

    private void ensureWithinBounds(int x, int y) {
        boolean outOfBounds = x >= map.width() ||
            x < 0 ||
            y >= map.height() ||
            y < 0;

        Check.stateCondition(outOfBounds, "Trying to access pixel at {0}, {1}, which is out of bounds.", x, y);
    }

    private void ensureWithinBounds(int x, int y, String extra) {
        boolean outOfBounds = x >= map.width() ||
            x < 0 ||
            y >= map.height() ||
            y < 0;

        Check.stateCondition(outOfBounds, "Trying to access pixel at {0}, {1}, which is out of bounds. Extra info: {2}", x, y, extra);
    }
}
