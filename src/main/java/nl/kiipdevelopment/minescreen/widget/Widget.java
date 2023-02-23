package nl.kiipdevelopment.minescreen.widget;

import net.minestom.server.entity.Player;
import net.minestom.server.map.MapColors;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.widget.widgets.ButtonWidget;
import nl.kiipdevelopment.minescreen.widget.widgets.ContainerWidget;
import nl.kiipdevelopment.minescreen.widget.widgets.ImageWidget;
import nl.kiipdevelopment.minescreen.widget.widgets.PlayerWidget;
import nl.kiipdevelopment.minescreen.widget.widgets.RenderWidget;
import nl.kiipdevelopment.minescreen.widget.widgets.ShapeWidget;
import nl.kiipdevelopment.minescreen.widget.widgets.SpacerWidget;
import nl.kiipdevelopment.minescreen.widget.widgets.WrapperWidget;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ApiStatus.Experimental
public interface Widget {
    int width();

    int height();

    void draw(MapGraphics renderer);

    /**
     * Contains the methods to generate buttons
     */
    @ApiStatus.Internal
    final class Buttons {
        private Buttons() {}

        public static Widget hover(@NotNull Widget child, @Nullable Hover hover) {
            return new ButtonWidget(child, hover, null);
        }

        public static Widget click(@NotNull Widget child, @Nullable Click click) {
            return new ButtonWidget(child, null, click);
        }

        public static Widget both(@NotNull Widget child, @Nullable Hover hover, @Nullable Click click) {
            return new ButtonWidget(child, hover, click);
        }

        @FunctionalInterface
        public interface Hover {
            void hover(Player player, int x, int y);
        }

        @FunctionalInterface
        public interface Click {
            void click(Player player, int x, int y);
        }
    }

    /**
     * Contains the methods to generate containers
     */
    @ApiStatus.Internal
    final class Containers {
        private Containers() {}

        public static Widget row(int width, int height, @Nullable Widget... widgets) {
            return new ContainerWidget(ContainerWidget.Type.ROW, width, height, Arrays.stream(widgets)
                    .filter(Objects::nonNull)
                    .toList());
        }

        public static Widget column(int width, int height, @Nullable Widget... widgets) {
            return new ContainerWidget(ContainerWidget.Type.COLUMN, width, height, Arrays.stream(widgets)
                    .filter(Objects::nonNull)
                    .toList());
        }

        public static Widget stack(int width, int height, @Nullable Widget... widgets) {
            return new ContainerWidget(ContainerWidget.Type.STACK, width, height, Arrays.stream(widgets)
                    .filter(Objects::nonNull)
                    .toList());
        }
    }

    /**
     * Contains the methods to generate images
     */
    @ApiStatus.Internal
    final class Images {
        private Images() {}

        public static Widget buffered(int width, int height, BufferedImage image) {
            return new ImageWidget(width, height, image);
        }
    }

    /**
     * Contains the methods to generate images of players
     */
    @ApiStatus.Internal
    final class Players {
        private Players() {}

        public static Widget avatar(int width, int height, UUID player) {
            return new PlayerWidget(PlayerWidget.Type.AVATAR, width, height, player);
        }

        public static Widget head(int width, int height, UUID player) {
            return new PlayerWidget(PlayerWidget.Type.HEAD, width, height, player);
        }

        public static Widget body(int width, int height, UUID player) {
            return new PlayerWidget(PlayerWidget.Type.BODY, width, height, player);
        }
    }

    /**
     * Contains the methods to generate renders
     */
    @ApiStatus.Internal
    final class Renders {
        private Renders() {}

        public static Widget render(int width, int height, @Nullable Consumer<MapGraphics> draw) {
            return new RenderWidget(width, height, draw);
        }
    }

    /**
     * Contains the methods to generate shapes
     */
    @ApiStatus.Internal
    final class Shapes {
        private Shapes() {}

        public static Widget rectangle(int width, int height, int xOffset, int yOffset, MapColors color) {
            return new ShapeWidget(ShapeWidget.Type.RECTANGLE, width, height, xOffset, yOffset, color);
        }

        public static Widget square(int width, int height, int xOffset, int yOffset, MapColors color) {
            return new ShapeWidget(ShapeWidget.Type.SQUARE, width, height, xOffset, yOffset, color);
        }
    }

    /**
     * Contains the methods to generate spacers
     */
    @ApiStatus.Internal
    final class Spacers {
        private Spacers() {}

        public static Widget spacer(int width, int height) {
            return new SpacerWidget(width, height);
        }
    }

    /**
     * Contains the methods to generate wrappers
     */
    @ApiStatus.Internal
    final class Wrappers {
        private Wrappers() {}

        public static Widget wrapper(Supplier<Widget> supplier) {
            return new WrapperWidget(supplier);
        }
    }
}