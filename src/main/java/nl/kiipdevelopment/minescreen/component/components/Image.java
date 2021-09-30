package nl.kiipdevelopment.minescreen.component.components;

import net.minestom.server.map.MapColors;
import nl.kiipdevelopment.minescreen.component.ScreenComponent;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Image extends ScreenComponent {
    private final BufferedImage image;

    Image(int width, int height, BufferedImage image) {
        super(width, height);

        this.image = image;
    }

    @Override
    public void draw(MapGraphics renderer) {
        byte[] bytes = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int x = 0;
        int y = 0;
        if (hasAlphaChannel) {
            for (int pixel = 0; pixel + 3 < bytes.length; pixel += 4) {
                int argb = 0;
                argb += (((int) bytes[pixel] & 0xff) << 24); // alpha
                argb += (((int) bytes[pixel + 3] & 0xff) << 16); // red
                argb += (((int) bytes[pixel + 2] & 0xff) << 8); // green
                argb += ((int) bytes[pixel + 1] & 0xff); // blue

                renderer.drawDot(MapColors.closestColor(argb).getBaseColor(), x, y);

                x++;

                if (x >= width()) {
                    x = 0;
                    y++;
                }
            }
        } else {
            for (int pixel = 0; pixel + 2 < bytes.length; pixel += 3) {
                int argb = -16777216; // 255 alpha
                argb += (((int) bytes[pixel + 2] & 0xff) << 16); // red
                argb += (((int) bytes[pixel + 1] & 0xff) << 8); // green
                argb += ((int) bytes[pixel] & 0xff); // blue

                renderer.drawDot(MapColors.closestColor(argb).getBaseColor(), x, y);

                x++;

                if (x >= width()) {
                    x = 0;
                    y++;
                }
            }
        }
    }
}
