package nl.kiipdevelopment.minescreen.component.components;

import net.minestom.server.entity.Player;
import nl.kiipdevelopment.minescreen.component.Component;
import nl.kiipdevelopment.minescreen.component.Interactable;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.util.MathUtils;
import org.jetbrains.annotations.Nullable;

public class ContainerComponent extends Component implements Interactable {
    private final Component[] components;
    private final ContainerType type;

    private ContainerComponent(ContainerType type, int width, int height, Component[] components) {
        super(width, height);

        this.type = type;
        this.components = components;
    }

    public static ContainerComponent row(int width, int height, @Nullable Component... components) {
        return new ContainerComponent(ContainerType.ROW, width, height, components);
    }

    public static ContainerComponent column(int width, int height, @Nullable Component... components) {
        return new ContainerComponent(ContainerType.COLUMN, width, height, components);
    }

    public static ContainerComponent stack(int width, int height, @Nullable Component... components) {
        return new ContainerComponent(ContainerType.STACK, width, height, components);
    }

    @Override
    public void draw(MapGraphics renderer) {
        switch (type) {
            case ROW -> {
                int xOffset = 0;
                for (Component component : components) {
                    component.draw(renderer.subGraphics(xOffset, 0, component.width(), component.height()));

                    xOffset += component.width();
                }
            }
            case COLUMN -> {
                int yOffset = 0;
                for (Component component : components) {
                    component.draw(renderer.subGraphics(0, yOffset, component.width(), component.height()));

                    yOffset += component.height();
                }
            }
            case STACK -> {
                for (Component component : components) {
                    component.draw(renderer.subGraphics(0, 0, component.width(), component.height()));
                }
            }
        }
    }

    @Override
    public void onHover(Player player, int x, int y) {
        switch (type) {
            case ROW -> {
                int xOffset = 0;
                for (Component component : components) {
                    if (component instanceof Interactable interactable) {
                        if (
                            MathUtils.isBetween(x, xOffset, component.width() + xOffset) &&
                                MathUtils.isBetween(y, 0, component.height())
                        ) {
                            interactable.onHover(player, x, y);
                        }
                    }

                    xOffset += component.width();
                }
            }
            case COLUMN -> {
                int yOffset = 0;
                for (Component component : components) {
                    if (component instanceof Interactable interactable) {
                        if (
                            MathUtils.isBetween(x, 0, component.width()) &&
                                MathUtils.isBetween(y, yOffset, component.height() + yOffset)
                        ) {
                            interactable.onHover(player, x, y);
                        }
                    }

                    yOffset += component.height();
                }
            }
            case STACK -> {
                for (Component component : components) {
                    if (component instanceof Interactable interactable) {
                        if (x <= component.width() && y <= component.height()) {
                            interactable.onHover(player, x, y);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(Player player, int x, int y) {
        switch (type) {
            case ROW -> {
                int xOffset = 0;
                for (Component component : components) {
                    if (component instanceof Interactable interactable) {
                        if (
                            MathUtils.isBetween(x, xOffset, component.width() + xOffset) &&
                                MathUtils.isBetween(y, 0, component.height())
                        ) {
                            interactable.onClick(player, x, y);
                        }
                    }

                    xOffset += component.width();
                }
            }
            case COLUMN -> {
                int yOffset = 0;
                for (Component component : components) {
                    if (component instanceof Interactable interactable) {
                        if (
                            MathUtils.isBetween(x, 0, component.width()) &&
                                MathUtils.isBetween(y, yOffset, component.height() + yOffset)
                        ) {
                            interactable.onClick(player, x, y);
                        }
                    }

                    yOffset += component.height();
                }
            }
            case STACK -> {
                for (Component component : components) {
                    if (component instanceof Interactable interactable) {
                        if (x <= component.width() && y <= component.height()) {
                            interactable.onClick(player, x, y);
                        }
                    }
                }
            }
        }
    }

    enum ContainerType {
        ROW,
        COLUMN,
        STACK
    }
}
