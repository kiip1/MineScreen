package nl.kiipdevelopment.minescreen.widget.widgets;

import nl.kiipdevelopment.minescreen.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.widget.Widget;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@ApiStatus.Internal
public final class WrapperWidget implements Widget {
    private Supplier<Widget> supplier;
    private Widget widget;

    public WrapperWidget(Supplier<Widget> supplier) {
        this.supplier = supplier;
    }

    @Override
    public void draw(MapGraphics renderer) {
        if (supplier != null) {
            Widget widget = supplier.get();
            if (widget == null) return;
            this.widget = widget;
            supplier = null;
        }

        widget.draw(renderer);
    }

    @Override
    public int width() {
        return widget == null ? 0 : widget.width();
    }

    @Override
    public int height() {
        return widget == null ? 0 : widget.height();
    }
}
