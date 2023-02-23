package nl.kiipdevelopment.minescreen.demo;

import net.kyori.adventure.text.Component;
import net.minestom.server.map.MapColors;
import nl.kiipdevelopment.minescreen.screen.InteractableScreenGui;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;
import nl.kiipdevelopment.minescreen.widget.Widget;
import org.jetbrains.skija.Canvas;
import org.jetbrains.skija.Paint;
import org.jetbrains.skija.PaintMode;
import org.jetbrains.skija.Point;
import org.jetbrains.skija.Surface;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static nl.kiipdevelopment.minescreen.widget.Widget.*;

final class TestGui {
    private TestGui() {}

    public static ScreenGui init() {
        InteractableScreenGui gui = new InteractableScreenGui((short) 1, 512, 512, 20);

        // Set background color
        gui.updateBackground(MapColors.COLOR_BLACK);

        // Create container
        Widget containerWidget = Containers.column(
            448,
            448,
            Containers.row(
                448,
                214,
                Buttons.click(
                    Containers.stack(
                        128,
                        128,
                        Shapes.square(128, 128, 0, 0, MapColors.COLOR_GREEN),
                        Shapes.square(127, 127, 1, 1, MapColors.COLOR_LIGHT_GREEN)
                    ),
                    (player1, x, y) -> {
                        player1.sendMessage(Component.text("GREEN"));
                        gui.updateBackground(MapColors.COLOR_GREEN);
                    }
                ),
                Spacers.spacer(10, 10),
                Buttons.click(
                    Containers.stack(
                        128,
                        128,
                        Shapes.square(128, 128, 0, 0, MapColors.COLOR_BLUE),
                        Shapes.square(127, 127, 1, 1, MapColors.COLOR_LIGHT_BLUE)
                    ),
                    (player1, x, y) -> {
                        player1.sendMessage(Component.text("BLUE"));
                        gui.updateBackground(MapColors.COLOR_BLUE);
                    }
                ),
                Spacers.spacer(10, 10),
                Buttons.click(
                        Containers.stack(
                                128,
                                128,
                                Shapes.square(128, 128, 0, 0, MapColors.COLOR_BLACK),
                                Shapes.square(127, 127, 1, 1, MapColors.COLOR_GRAY)
                        ),
                        (player1, x, y) -> {
                            player1.sendMessage(Component.text("BLACK"));
                            gui.updateBackground(MapColors.COLOR_BLACK);
                        }
                )
            ),
            Containers.row(
                448,
                214,
                Wrappers.wrapper(() -> gui.players().isEmpty()
                        ? null
                        : Players.avatar(128, 128, gui.players().stream().findFirst().get().getUuid())
                ),
                Spacers.spacer(10, 10),
                Renders.render(128, 128, graphics -> {
                    Surface surface = Surface.makeRasterN32Premul(128, 128);
                    Canvas canvas = surface.getCanvas();

                    // Draw to canvas
                    Paint paint = new Paint()
                            .setColor(0xFF1D7AA2)
                            .setMode(PaintMode.STROKE)
                            .setStrokeWidth(1f);
                    Random random = ThreadLocalRandom.current();
                    canvas.drawTriangles(
                            new Point[] {
                                    new Point(random.nextInt(20), random.nextInt(70)),
                                    new Point(94, 87),
                                    new Point(46, 87)
                            },
                            new int[] { 0xFFFF0000, 0xFF00FF00, 0xFF0000FF },
                            paint
                    );

                    // Render
                    // TODO This can be better
                    try {
                        BufferedImage buffer = ImageIO.read(new ByteArrayInputStream(surface.makeImageSnapshot().encodeToData().getBytes()));
                        for (int x = 0; x < 128; x++) {
                            for (int y = 0; y < 128; y++) {
                                MapColors.PreciseMapColor color = MapColors.closestColor(buffer.getRGB(x, y));
                                graphics.drawDot(color.getBaseColor(), x, y);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
            )
        );

        // Display mouse
        final int cursorWidth = 4;
        var mouseWidget = Buttons.hover(
            Spacers.spacer(gui.map().width(), gui.map().height()),
            (player1, x, y) -> {
                for (InteractableScreenGui.Mouse mouse : gui.mice()) {
                    var graphics = gui.mapGraphics();

                    graphics.drawRectangle(MapColors.SNOW,
                        Math.max(mouse.x() - cursorWidth, 0),
                        Math.max(mouse.y(), 0),
                        Math.min(mouse.x() + cursorWidth + 1, gui.map().width() - 1),
                        Math.min(mouse.y() + 1, gui.map().height() - 1)
                    );
                    graphics.drawRectangle(MapColors.SNOW,
                            Math.max(mouse.x(), 0),
                            Math.max(mouse.y() - cursorWidth, 0),
                            Math.min(mouse.x() + 1, gui.map().width() - 1),
                            Math.min(mouse.y() + cursorWidth + 1, gui.map().height() - 1)
                    );
                }
            }
        );

        // Add the components
        gui.addWidget(containerWidget, 64, 64);
        gui.addWidget(mouseWidget);

        return gui;
    }
}
