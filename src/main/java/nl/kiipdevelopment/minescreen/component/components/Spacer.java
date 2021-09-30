package nl.kiipdevelopment.minescreen.component.components;

import nl.kiipdevelopment.minescreen.component.ScreenComponent;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;

public class Spacer extends ScreenComponent {
    Spacer(int width, int height) {
        super(width, height);
    }

    @Override
    public void draw(MapGraphics renderer) {}
}
