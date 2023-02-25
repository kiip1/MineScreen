package nl.kiipdevelopment.minescreen.widget.widgets;

import net.minestom.server.map.MapColors;
import nl.kiipdevelopment.minescreen.widget.AbstractWidget;
import nl.kiipdevelopment.minescreen.graphics.MapGraphics;
import org.jetbrains.annotations.ApiStatus;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

@ApiStatus.Internal
public final class ImageWidget extends AbstractWidget {
    private final ByteBuffer colors;

    public ImageWidget(int width, int height, BufferedImage image) {
        super(width, height);

        width = Math.min(image.getWidth(), width());
        height = Math.min(image.getHeight(), height());

        colors = ByteBuffer.allocate(width * height);

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                colors.put(x + y * width, MapColors.closestColor(image.getRGB(x, y)).getIndex());
    }

    @Override
    public void draw(MapGraphics renderer) {
        int i = 0;
        colors.rewind();
        while (colors.hasRemaining()) {
            final int x = i % width();
            final int y = i++ / width();

            renderer.drawDot(colors.get(), x, y);
        }
    }
}
