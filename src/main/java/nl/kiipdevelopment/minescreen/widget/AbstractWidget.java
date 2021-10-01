package nl.kiipdevelopment.minescreen.widget;

import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;

public abstract class AbstractWidget implements Widget {
    private int width, height;

    public AbstractWidget(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int width() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int height() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract void draw(MapGraphics renderer);

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + width + ", " + height + ")";
    }
}