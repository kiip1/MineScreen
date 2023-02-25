package nl.kiipdevelopment.minescreen.graphics;

import nl.kiipdevelopment.minescreen.screen.ScreenGui;

final class RelativeMapGraphicsImpl extends MapGraphicsImpl {
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
    public void fill(byte color) {
        drawRectangle(color, 0, 0, width, height);
    }

    @Override
    public void drawRectangle(byte color, int startX, int startY, int endX, int endY) {
        startX = fixX(startX);
        startY = fixY(startY);
        endX = fixX(endX);
        endY = fixY(endY);

        super.drawRectangle(color, startX, startY, endX, endY);
    }

    @Override
    public void drawString(byte color, String value, int x, int y) {
        x = fixX(x);
        y = fixY(y);

        super.drawString(color, value, x, y);
    }

    @Override
    public void drawDot(byte color, int x, int y) {
        x = fixX(x);
        y = fixY(y);

        super.drawDot(color, x, y);
    }

    @Override
    public MapGraphics relative(int x, int y, int width, int height) {
        x = fixX(x);
        y = fixY(y);
        width = fixX(width);
        height = fixY(height);

        return super.relative(x, y, width, height);
    }

    private int fixX(int x) {
        return x + this.x;
    }

    private int fixY(int y) {
        return y + this.y;
    }
}
