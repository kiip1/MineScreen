package nl.kiipdevelopment.minescreen.widget.widgets;

import nl.kiipdevelopment.minescreen.widget.AbstractWidget;
import nl.kiipdevelopment.minescreen.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.widget.Widget;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public final class SpacerWidget extends AbstractWidget {
    private final int x;
    private final int y;
    private final Widget widget;

    public SpacerWidget(int width, int height, @Nullable Widget widget) {
        super(width + (widget == null ? 0 : widget.width()), height + (widget == null ? 0 : widget.height()));
        this.x = width;
        this.y = height;
        this.widget = widget;
    }

    @Override
    public void draw(MapGraphics renderer) {
        if (widget != null)
            widget.draw(renderer.relative(x, y, widget.width(), widget.height()));
    }
}
