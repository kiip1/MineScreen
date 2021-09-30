package nl.kiipdevelopment.minescreen.component.components;

import nl.kiipdevelopment.minescreen.component.ScreenComponent;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;

import java.util.function.Consumer;

public class Render extends ScreenComponent {
    private final Consumer<MapGraphics> draw;

    Render(int width, int height, Consumer<MapGraphics> draw) {
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
