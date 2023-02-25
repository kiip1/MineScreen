package nl.kiipdevelopment.minescreen.widget.widgets;

import net.minestom.server.map.MapColors;
import nl.kiipdevelopment.minescreen.widget.AbstractWidget;
import nl.kiipdevelopment.minescreen.graphics.MapGraphics;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class ShapeWidget extends AbstractWidget {
    private final Type type;
    private final MapColors color;

    public ShapeWidget(Type type, int width, int height, MapColors color) {
        super(width, height);

        this.type = type;
        this.color = color;
    }

    @Override
    public void draw(MapGraphics renderer) {
        switch (type) {
            case SQUARE, RECTANGLE -> renderer.drawRectangle(color, 0, 0, width(), (type == Type.SQUARE ? width() : height()));
        }
    }

    public enum Type {
        RECTANGLE,
        SQUARE
    }
}
