package nl.kiipdevelopment.minescreen.widget.widgets;

import nl.kiipdevelopment.minescreen.widget.AbstractWidget;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;

import java.util.function.Consumer;

public class RenderWidget extends AbstractWidget {
    private final Consumer<MapGraphics> draw;

    public RenderWidget(int width, int height, Consumer<MapGraphics> draw) {
        super(width, height);

        this.draw = draw;
    }

    @Override
    public void draw(MapGraphics renderer) {
        if (draw != null) {
            draw.accept(renderer);
        }
    }
}
