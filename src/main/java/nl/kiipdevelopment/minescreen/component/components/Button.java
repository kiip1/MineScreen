package nl.kiipdevelopment.minescreen.component.components;

import net.minestom.server.entity.Player;
import nl.kiipdevelopment.minescreen.component.Interactable;
import nl.kiipdevelopment.minescreen.component.ScreenComponent;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import org.apache.logging.log4j.util.TriConsumer;

public class Button extends ScreenComponent implements Interactable {
    private final ScreenComponent child;
    private final TriConsumer<Player, Integer, Integer> hover;
    private final TriConsumer<Player, Integer, Integer> click;

    Button(ScreenComponent child, TriConsumer<Player, Integer, Integer> hover, TriConsumer<Player, Integer, Integer> click) {
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
