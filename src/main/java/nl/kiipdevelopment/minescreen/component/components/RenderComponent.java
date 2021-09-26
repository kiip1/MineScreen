package nl.kiipdevelopment.minescreen.component.components;

import nl.kiipdevelopment.minescreen.component.Component;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class RenderComponent extends Component {
    private final Consumer<MapGraphics> draw;

    private RenderComponent(int width, int height, Consumer<MapGraphics> draw) {
        super(width, height);

        this.draw = draw;
    }

    public static RenderComponent of(int width, int height, @Nullable Consumer<MapGraphics> draw) {
        return new RenderComponent(width, height, draw);
    }

    @Override
    public void draw(MapGraphics renderer) {
        if (draw != null) {
            draw.accept(renderer);
        }
    }
}
