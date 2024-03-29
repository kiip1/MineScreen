package nl.kiipdevelopment.minescreen.widget.widgets;

import net.minestom.server.entity.Player;
import nl.kiipdevelopment.minescreen.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.widget.Interactable;
import nl.kiipdevelopment.minescreen.widget.Widget;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@ApiStatus.Internal
public final class WrapperWidget implements Widget, Interactable {
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

    @Override
    public void onHover(Player player, int x, int y) {
        if (widget instanceof Interactable interactable)
            interactable.onHover(player, x, y);
    }

    @Override
    public void onClick(Player player, int x, int y) {
        if (widget instanceof Interactable interactable)
            interactable.onClick(player, x, y);
    }

}
