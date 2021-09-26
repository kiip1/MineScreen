package nl.kiipdevelopment.minescreen.component.components;

import nl.kiipdevelopment.minescreen.component.Component;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;

public class SpacerComponent extends Component {
    private SpacerComponent(int width, int height) {
        super(width, height);
    }

    public static SpacerComponent of(int width, int height) {
        return new SpacerComponent(width, height);
    }

    @Override
    public void draw(MapGraphics renderer) {}
}
