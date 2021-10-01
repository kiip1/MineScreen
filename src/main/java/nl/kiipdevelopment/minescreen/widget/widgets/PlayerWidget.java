package nl.kiipdevelopment.minescreen.widget.widgets;

import nl.kiipdevelopment.minescreen.widget.AbstractWidget;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class PlayerWidget extends AbstractWidget {
    private final ImageWidget image;

    public PlayerWidget(Type type, int width, int height, UUID player) {
        super(width, height);

        ImageWidget image1 = null;
        try {
            image1 = new ImageWidget(width(), height(), ImageIO.read(new URL("https://minotar.net/" + type.toString() + "/" + player.toString() + "/" + width())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = image1;
    }

    @Override
    public void draw(MapGraphics renderer) {
        if (image != null) {
            image.draw(renderer);
        }
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
