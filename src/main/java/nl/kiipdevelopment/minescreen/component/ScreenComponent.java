package nl.kiipdevelopment.minescreen.component;

import nl.kiipdevelopment.minescreen.component.components.ComponentFunctions;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;

public abstract class ScreenComponent implements ComponentFunctions {
    private int width, height;

    public ScreenComponent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int width() {
        return width;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    public int height() {
        return height;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    public abstract void draw(MapGraphics renderer);

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + width + ", " + height + ")";
    }
}
