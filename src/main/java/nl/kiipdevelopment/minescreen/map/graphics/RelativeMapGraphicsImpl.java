package nl.kiipdevelopment.minescreen.map.graphics;

import net.minestom.server.map.MapColors;
import net.minestom.server.utils.validate.Check;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;

public class RelativeMapGraphicsImpl extends MapGraphicsImpl {
    private final int x, y;
    private final int width, height;

    public RelativeMapGraphicsImpl(ScreenGui gui, int x, int y, int width, int height) {
        super(gui);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void fill(MapColors color) {
        fill(color.baseColor());
    }

    protected void fill(byte color) {
        drawRectangle(color, 0, 0, width, height);
    }

    @Override
    public void drawRectangle(MapColors color, int startX, int startY, int endX, int endY) {
        ensureWithinBounds(startX, startY);
        ensureWithinBounds(endX, endY);

        startX = fixX(startX);
        startY = fixY(startY);
        endX = fixX(endX);
        endY = fixY(endY);

        drawRectangle(color.baseColor(), startX, startY, endX, endY);
    }

    @Override
    public void drawString(MapColors color, String value, int x, int y) {
        ensureWithinBounds(x, y);

        x = fixX(x);
        y = fixY(y);

        drawString(color.baseColor(), value, x, y);
    }

    @Override
    public void drawDot(MapColors color, int x, int y) {
        ensureWithinBounds(x, y);

        x = fixX(x);
        y = fixY(y);

        drawDot(color.baseColor(), x, y);
    }

    @Override
    public MapGraphics subGraphics(int x, int y, int width, int height) {
        x = fixX(x);
        y = fixY(y);
        width = fixX(width);
        height = fixY(height);

        return new RelativeMapGraphicsImpl(gui, x, y, width, height);
    }

    private void ensureWithinBounds(int x, int y) {
        boolean outOfBounds = x >= width ||
            x < 0 ||
            y >= height ||
            y < 0;

        Check.stateCondition(outOfBounds, "Trying to access pixel at {0}, {1}, which is out of bounds.", x, y);
    }

    private int fixX(int x) {
        return x + this.x;
    }

    private int fixY(int y) {
        return y + this.y;
    }
}
