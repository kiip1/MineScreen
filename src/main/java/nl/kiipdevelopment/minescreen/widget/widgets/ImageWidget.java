package nl.kiipdevelopment.minescreen.widget.widgets;

import net.minestom.server.map.MapColors;
import nl.kiipdevelopment.minescreen.widget.AbstractWidget;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;

import java.awt.image.BufferedImage;

public class ImageWidget extends AbstractWidget {
    private final byte[] colors;

    public ImageWidget(int width, int height, BufferedImage image) {
        super(width, height);

        width = Math.min(image.getWidth(), width());
        height = Math.min(image.getHeight(), height());

        colors = new byte[width * height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                colors[x + y * width] = MapColors.closestColor(image.getRGB(x, y)).getIndex();
            }
        }
    }

    @Override
    public void draw(MapGraphics renderer) {
        for (int i = 0; i < colors.length; i++) {
            final int x = i % width();
            final int y = i / width();

            renderer.drawDot(colors[i], x, y);
        }
    }
}
