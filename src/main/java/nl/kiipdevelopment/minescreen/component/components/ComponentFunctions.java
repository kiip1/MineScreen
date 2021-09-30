package nl.kiipdevelopment.minescreen.component.components;

import net.minestom.server.entity.Player;
import nl.kiipdevelopment.minescreen.component.ScreenComponent;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;

@ApiStatus.NonExtendable
@ApiStatus.Internal
public interface ComponentFunctions {
    /**
     * Contains the methods to generate buttons
     */
    @ApiStatus.NonExtendable
    @ApiStatus.Internal
    final class Buttons {
        public static Button hover(@NotNull ScreenComponent child, @Nullable TriConsumer<Player, Integer, Integer> hover) {
            return new Button(child, hover, null);
        }

        public static Button click(@NotNull ScreenComponent child, @Nullable TriConsumer<Player, Integer, Integer> click) {
            return new Button(child, null, click);
        }

        public static Button both(@NotNull ScreenComponent child, @Nullable TriConsumer<Player, Integer, Integer> hover, @Nullable TriConsumer<Player, Integer, Integer> click) {
            return new Button(child, hover, click);
        }
    }

    /**
     * Contains the methods to generate containers
     */
    @ApiStatus.NonExtendable
    @ApiStatus.Internal
    final class Containers {
        public static Container row(int width, int height, @Nullable ScreenComponent... screenComponents) {
            return new Container(Container.ContainerType.ROW, width, height, screenComponents);
        }

        public static Container column(int width, int height, @Nullable ScreenComponent... screenComponents) {
            return new Container(Container.ContainerType.COLUMN, width, height, screenComponents);
        }

        public static Container stack(int width, int height, @Nullable ScreenComponent... screenComponents) {
            return new Container(Container.ContainerType.STACK, width, height, screenComponents);
        }
    }

    /**
     * Contains the methods to generate images
     */
    @ApiStatus.NonExtendable
    @ApiStatus.Internal
    final class Images {
        public static Image buffered(@NotNull BufferedImage image) {
            return new Image(image.getWidth(), image.getHeight(), image);
        }
    }

    /**
     * Contains the methods to generate renders
     */
    @ApiStatus.NonExtendable
    @ApiStatus.Internal
    final class Renders {
        public static Render of(int width, int height, @Nullable Consumer<MapGraphics> draw) {
            return new Render(width, height, draw);
        }
    }

    /**
     * Contains the methods to generate spacers
     */
    @ApiStatus.NonExtendable
    @ApiStatus.Internal
    final class Spacers {
        public static Spacer of(int width, int height) {
            return new Spacer(width, height);
        }
    }
}
