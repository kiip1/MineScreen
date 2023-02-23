package nl.kiipdevelopment.minescreen.widget.widgets;

import nl.kiipdevelopment.minescreen.widget.AbstractWidget;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class SpacerWidget extends AbstractWidget {
    public SpacerWidget(int width, int height) {
        super(width, height);
    }

    @Override
    public void draw(MapGraphics renderer) {}
}
