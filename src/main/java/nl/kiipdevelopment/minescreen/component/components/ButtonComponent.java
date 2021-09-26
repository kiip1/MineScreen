package nl.kiipdevelopment.minescreen.component.components;

import net.minestom.server.entity.Player;
import nl.kiipdevelopment.minescreen.component.Component;
import nl.kiipdevelopment.minescreen.component.Interactable;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ButtonComponent extends Component implements Interactable {
    private final Component child;
    private final TriConsumer<Player, Integer, Integer> hover;
    private final TriConsumer<Player, Integer, Integer> click;

    private ButtonComponent(Component child, TriConsumer<Player, Integer, Integer> hover, TriConsumer<Player, Integer, Integer> click) {
        super(child.width(), child.height());

        this.child = child;
        this.hover = hover;
        this.click = click;
    }

    public static ButtonComponent hover(@NotNull Component child, @Nullable TriConsumer<Player, Integer, Integer> hover) {
        return new ButtonComponent(child, hover, null);
    }

    public static ButtonComponent click(@NotNull Component child, @Nullable TriConsumer<Player, Integer, Integer> click) {
        return new ButtonComponent(child, null, click);
    }

    public static ButtonComponent both(@NotNull Component child, @Nullable TriConsumer<Player, Integer, Integer> hover, @Nullable TriConsumer<Player, Integer, Integer> click) {
        return new ButtonComponent(child, hover, click);
    }

    @Override
    public void draw(MapGraphics renderer) {
        child.draw(renderer);
    }

    @Override
    public void onHover(Player player, int x, int y) {
        if (hover != null) {
            hover.accept(player, x, y);
        }
    }

    @Override
    public void onClick(Player player, int x, int y) {
        if (click != null) {
            click.accept(player, x, y);
        }
    }
}
