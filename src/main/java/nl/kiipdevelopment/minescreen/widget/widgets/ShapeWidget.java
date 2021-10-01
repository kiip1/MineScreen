package nl.kiipdevelopment.minescreen.widget.widgets;

import net.minestom.server.map.MapColors;
import nl.kiipdevelopment.minescreen.widget.AbstractWidget;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;

public class ShapeWidget extends AbstractWidget {
    private final int xOffset, yOffset;
    private final Type type;
    private final MapColors color;

    public ShapeWidget(Type type, int width, int height, int xOffset, int yOffset, MapColors color) {
        super(width, height);

        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.type = type;
        this.color = color;
    }

    @Override
    public void draw(MapGraphics renderer) {
        switch (type) {
            case SQUARE, RECTANGLE -> renderer.drawRectangle(color, xOffset, yOffset, width(), type == Type.SQUARE ? width() : height());
        }
    }

    public enum Type {
        RECTANGLE,
        SQUARE
    }
}
