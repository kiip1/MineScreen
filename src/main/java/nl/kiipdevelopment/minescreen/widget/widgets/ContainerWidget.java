package nl.kiipdevelopment.minescreen.widget.widgets;

import net.minestom.server.entity.Player;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.util.MathUtils;
import nl.kiipdevelopment.minescreen.widget.AbstractWidget;
import nl.kiipdevelopment.minescreen.widget.Interactable;
import nl.kiipdevelopment.minescreen.widget.Widget;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public final class ContainerWidget extends AbstractWidget implements Interactable {
    private final Type type;
    private final List<Widget> widgets;

    public ContainerWidget(Type type, int width, int height, List<Widget> widgets) {
        super(width, height);

        this.type = type;
        this.widgets = List.copyOf(widgets);
    }

    @Override
    public void draw(MapGraphics renderer) {
        switch (type) {
            case ROW -> {
                int xOffset = 0;
                for (Widget widget : widgets) {
                    widget.draw(renderer.relative(xOffset, 0, widget.width(), widget.height()));

                    xOffset += widget.width();
                }
            }
            case COLUMN -> {
                int yOffset = 0;
                for (Widget widget : widgets) {
                    widget.draw(renderer.relative(0, yOffset, widget.width(), widget.height()));

                    yOffset += widget.height();
                }
            }
            case STACK -> {
                for (Widget widget : widgets) {
                    widget.draw(renderer.relative(0, 0, widget.width(), widget.height()));
                }
            }
        }
    }

    @Override
    public void onHover(Player player, int x, int y) {
        switch (type) {
            case ROW -> {
                int xOffset = 0;
                for (Widget widget : widgets) {
                    if (widget instanceof Interactable interactable) {
                        if (
                            MathUtils.isBetween(x, xOffset, widget.width() + xOffset) &&
                            MathUtils.isBetween(y, 0, widget.height())
                        ) {
                            interactable.onHover(player, x, y);
                        }
                    }

                    xOffset += widget.width();
                }
            }
            case COLUMN -> {
                int yOffset = 0;
                for (Widget widget : widgets) {
                    if (widget instanceof Interactable interactable) {
                        if (
                            MathUtils.isBetween(x, 0, widget.width()) &&
                            MathUtils.isBetween(y, yOffset, widget.height() + yOffset)
                        ) {
                            interactable.onHover(player, x, y);
                        }
                    }

                    yOffset += widget.height();
                }
            }
            case STACK -> {
                for (Widget widget : widgets) {
                    if (widget instanceof Interactable interactable) {
                        if (x <= widget.width() && y <= widget.height()) {
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
                for (Widget widget : widgets) {
                    if (widget instanceof Interactable interactable) {
                        if (
                            MathUtils.isBetween(x, xOffset, widget.width() + xOffset) &&
                            MathUtils.isBetween(y, 0, widget.height())
                        ) {
                            interactable.onClick(player, x, y);
                        }
                    }

                    xOffset += widget.width();
                }
            }
            case COLUMN -> {
                int yOffset = 0;
                for (Widget widget : widgets) {
                    if (widget instanceof Interactable interactable) {
                        if (
                            MathUtils.isBetween(x, 0, widget.width()) &&
                            MathUtils.isBetween(y, yOffset, widget.height() + yOffset)
                        ) {
                            interactable.onClick(player, x, y);
                        }
                    }

                    yOffset += widget.height();
                }
            }
            case STACK -> {
                for (Widget widget : widgets) {
                    if (widget instanceof Interactable interactable) {
                        if (x <= widget.width() && y <= widget.height()) {
                            interactable.onClick(player, x, y);
                        }
                    }
                }
            }
        }
    }

    public enum Type {
        ROW,
        COLUMN,
        STACK
    }
}
