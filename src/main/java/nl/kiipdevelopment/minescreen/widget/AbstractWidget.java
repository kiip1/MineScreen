package nl.kiipdevelopment.minescreen.widget;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public abstract class AbstractWidget implements Widget {
    private final int width;
    private final int height;

    public AbstractWidget(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + width + ", " + height + ")";
    }
}