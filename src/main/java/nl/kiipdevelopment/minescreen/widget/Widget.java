package nl.kiipdevelopment.minescreen.widget;

import net.minestom.server.entity.Player;
import net.minestom.server.map.MapColors;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.widget.widgets.*;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.function.Consumer;

public interface Widget {
    int width();

    void setWidth(int width);

    int height();

    void setHeight(int height);

    void draw(MapGraphics renderer);

    /**
     * Contains the methods to generate buttons
     */
    @ApiStatus.Internal
    final class Buttons {
        private Buttons() {}

        public static ButtonWidget hover(@NotNull Widget child, @Nullable TriConsumer<Player, Integer, Integer> hover) {
            return new ButtonWidget(child, hover, null);
        }

        public static ButtonWidget click(@NotNull Widget child, @Nullable TriConsumer<Player, Integer, Integer> click) {
            return new ButtonWidget(child, null, click);
        }

        public static ButtonWidget both(@NotNull Widget child, @Nullable TriConsumer<Player, Integer, Integer> hover, @Nullable TriConsumer<Player, Integer, Integer> click) {
            return new ButtonWidget(child, hover, click);
        }
    }

    /**
     * Contains the methods to generate containers
     */
    @ApiStatus.Internal
    final class Containers {
        private Containers() {}

        public static ContainerWidget row(int width, int height, @Nullable Widget... widgets) {
            return new ContainerWidget(ContainerWidget.Type.ROW, width, height, widgets);
        }

        public static ContainerWidget column(int width, int height, @Nullable Widget... widgets) {
            return new ContainerWidget(ContainerWidget.Type.COLUMN, width, height, widgets);
        }

        public static ContainerWidget stack(int width, int height, @Nullable Widget... widgets) {
            return new ContainerWidget(ContainerWidget.Type.STACK, width, height, widgets);
        }
    }

    /**
     * Contains the methods to generate images
     */
    @ApiStatus.Internal
    final class Images {
        private Images() {}

        public static ImageWidget buffered(int width, int height, BufferedImage image) {
            return new ImageWidget(width, height, image);
        }
    }

    /**
     * Contains the methods to generate images of players
     */
    @ApiStatus.Internal
    final class Players {
        private Players() {}

        public static PlayerWidget avatar(int width, int height, UUID player) {
            return new PlayerWidget(PlayerWidget.Type.AVATAR, width, height, player);
        }

        public static PlayerWidget head(int width, int height, UUID player) {
            return new PlayerWidget(PlayerWidget.Type.HEAD, width, height, player);
        }

        public static PlayerWidget body(int width, int height, UUID player) {
            return new PlayerWidget(PlayerWidget.Type.BODY, width, height, player);
        }
    }

    /**
     * Contains the methods to generate renders
     */
    @ApiStatus.Internal
    final class Renders {
        private Renders() {}

        public static RenderWidget render(int width, int height, @Nullable Consumer<MapGraphics> draw) {
            return new RenderWidget(width, height, draw);
        }
    }

    /**
     * Contains the methods to generate shapes
     */
    @ApiStatus.Internal
    final class Shapes {
        private Shapes() {}

        public static ShapeWidget rectangle(int width, int height, int xOffset, int yOffset, MapColors color) {
            return new ShapeWidget(ShapeWidget.Type.RECTANGLE, width, height, xOffset, yOffset, color);
        }

        public static ShapeWidget square(int width, int height, int xOffset, int yOffset, MapColors color) {
            return new ShapeWidget(ShapeWidget.Type.SQUARE, width, height, xOffset, yOffset, color);
        }
    }

    /**
     * Contains the methods to generate spacers
     */
    @ApiStatus.Internal
    final class Spacers {
        private Spacers() {}

        public static SpacerWidget spacer(int width, int height) {
            return new SpacerWidget(width, height);
        }
    }
}