package nl.kiipdevelopment.minescreen.widget.widgets;

import net.minestom.server.entity.Player;
import nl.kiipdevelopment.minescreen.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.widget.AbstractWidget;
import nl.kiipdevelopment.minescreen.widget.Interactable;
import nl.kiipdevelopment.minescreen.widget.Widget;
import nl.kiipdevelopment.minescreen.widget.Widget.Buttons.Click;
import nl.kiipdevelopment.minescreen.widget.Widget.Buttons.Hover;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class ButtonWidget extends AbstractWidget implements Interactable {
    private final Widget child;
    private final Hover hover;
    private final Click click;

    public ButtonWidget(Widget child, Hover hover, Click click) {
        super(child.width(), child.height());

        this.child = child;
        this.hover = hover;
        this.click = click;
    }

    @Override
    public void draw(MapGraphics renderer) {
        child.draw(renderer);
    }

    @Override
    public void onHover(Player player, int x, int y) {
        if (hover != null)
            hover.hover(player, x, y);
    }

    @Override
    public void onClick(Player player, int x, int y) {
        if (click != null)
            click.click(player, x, y);
    }
}
