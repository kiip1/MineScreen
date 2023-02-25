package nl.kiipdevelopment.minescreen.widget.widgets;

import nl.kiipdevelopment.minescreen.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.widget.AbstractWidget;
import nl.kiipdevelopment.minescreen.widget.Widget;
import org.jetbrains.annotations.ApiStatus;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ApiStatus.Internal
public final class PlayerWidget extends AbstractWidget {
    private final CompletableFuture<Widget> image;

    public PlayerWidget(Type type, int width, int height, UUID player) {
        super(width, height);

        image = CompletableFuture.supplyAsync(() -> {
            Widget widget = null;
            try {
                widget = new ImageWidget(width(), height(), ImageIO.read(new URL("https://minotar.net/" +
                        type.toString() + "/" + player.toString() + "/" + width())));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return widget;
        });
    }

    @Override
    public void draw(MapGraphics renderer) {
        Widget widget = this.image.getNow(null);
        if (widget != null) widget.draw(renderer);
    }

    public enum Type {
        AVATAR("avatar"),
        AVATAR_ARMOR("helm"),
        HEAD("cube"),
        BODY("body"),
        BODY_ARMOR("armor/body");

        private final String path;

        Type(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return path;
        }
    }
}
