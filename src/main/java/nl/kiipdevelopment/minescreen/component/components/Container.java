package nl.kiipdevelopment.minescreen.component.components;

import net.minestom.server.entity.Player;
import nl.kiipdevelopment.minescreen.component.Interactable;
import nl.kiipdevelopment.minescreen.component.ScreenComponent;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.util.MathUtils;

public class Container extends ScreenComponent implements Interactable {
    private final ScreenComponent[] screenComponents;
    private final ContainerType type;

    Container(ContainerType type, int width, int height, ScreenComponent[] screenComponents) {
        super(width, height);

        this.type = type;
        this.screenComponents = screenComponents;
    }

    @Override
    public void draw(MapGraphics renderer) {
        switch (type) {
            case ROW -> {
                int xOffset = 0;
                for (ScreenComponent screenComponent : screenComponents) {
                    screenComponent.draw(renderer.subGraphics(xOffset, 0, screenComponent.width(), screenComponent.height()));

                    xOffset += screenComponent.width();
                }
            }
            case COLUMN -> {
                int yOffset = 0;
                for (ScreenComponent screenComponent : screenComponents) {
                    screenComponent.draw(renderer.subGraphics(0, yOffset, screenComponent.width(), screenComponent.height()));

                    yOffset += screenComponent.height();
                }
            }
            case STACK -> {
                for (ScreenComponent screenComponent : screenComponents) {
                    screenComponent.draw(renderer.subGraphics(0, 0, screenComponent.width(), screenComponent.height()));
                }
            }
        }
    }

    @Override
    public void onHover(Player player, int x, int y) {
        switch (type) {
            case ROW -> {
                int xOffset = 0;
                for (ScreenComponent screenComponent : screenComponents) {
                    if (screenComponent instanceof Interactable interactable) {
                        if (
                            MathUtils.isBetween(x, xOffset, screenComponent.width() + xOffset) &&
                                MathUtils.isBetween(y, 0, screenComponent.height())
                        ) {
                            interactable.onHover(player, x, y);
                        }
                    }

                    xOffset += screenComponent.width();
                }
            }
            case COLUMN -> {
                int yOffset = 0;
                for (ScreenComponent screenComponent : screenComponents) {
                    if (screenComponent instanceof Interactable interactable) {
                        if (
                            MathUtils.isBetween(x, 0, screenComponent.width()) &&
                                MathUtils.isBetween(y, yOffset, screenComponent.height() + yOffset)
                        ) {
                            interactable.onHover(player, x, y);
                        }
                    }

                    yOffset += screenComponent.height();
                }
            }
            case STACK -> {
                for (ScreenComponent screenComponent : screenComponents) {
                    if (screenComponent instanceof Interactable interactable) {
                        if (x <= screenComponent.width() && y <= screenComponent.height()) {
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
                for (ScreenComponent screenComponent : screenComponents) {
                    if (screenComponent instanceof Interactable interactable) {
                        if (
                            MathUtils.isBetween(x, xOffset, screenComponent.width() + xOffset) &&
                                MathUtils.isBetween(y, 0, screenComponent.height())
                        ) {
                            interactable.onClick(player, x, y);
                        }
                    }

                    xOffset += screenComponent.width();
                }
            }
            case COLUMN -> {
                int yOffset = 0;
                for (ScreenComponent screenComponent : screenComponents) {
                    if (screenComponent instanceof Interactable interactable) {
                        if (
                            MathUtils.isBetween(x, 0, screenComponent.width()) &&
                                MathUtils.isBetween(y, yOffset, screenComponent.height() + yOffset)
                        ) {
                            interactable.onClick(player, x, y);
                        }
                    }

                    yOffset += screenComponent.height();
                }
            }
            case STACK -> {
                for (ScreenComponent screenComponent : screenComponents) {
                    if (screenComponent instanceof Interactable interactable) {
                        if (x <= screenComponent.width() && y <= screenComponent.height()) {
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
