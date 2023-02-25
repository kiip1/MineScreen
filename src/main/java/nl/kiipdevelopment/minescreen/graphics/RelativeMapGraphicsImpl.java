package nl.kiipdevelopment.minescreen.graphics;

import nl.kiipdevelopment.minescreen.screen.ScreenGui;

final class RelativeMapGraphicsImpl extends MapGraphicsImpl {
    private final int x;
    private final int y;

    private final int width;
    private final int height;

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
    public void drawDirectDot(byte color, int x, int y) {
        super.drawDirectDot(color, absX(x), absY(y));
    }

    @Override
    public MapGraphics relative(int x, int y, int width, int height) {
        return new RelativeMapGraphicsImpl(
                gui,
                absX(x) - (absX(x) + width > this.x + this.width ? width : 0),
                absY(y) - (absY(y) + height > this.y + this.height ? height : 0),
                Math.min(width, this.width),
                Math.min(height, this.height)
        );
    }

    private int absX(int x) {
        return this.x + Math.min(x, width);
    }

    private int absY(int y) {
        return this.y + Math.min(y, height);
    }
}
