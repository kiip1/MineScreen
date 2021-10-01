package nl.kiipdevelopment.minescreen.demo.commands;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.map.MapColors;
import nl.kiipdevelopment.minescreen.screen.InteractableScreenGui;
import nl.kiipdevelopment.minescreen.widget.Widget;

import java.util.UUID;

import static nl.kiipdevelopment.minescreen.widget.Widget.*;

public class GuiCommand extends Command {
    public GuiCommand() {
        super("gui");

        setDefaultExecutor(this::execute);
    }

    private void execute(CommandSender sender, CommandContext context) {
        InteractableScreenGui gui = new InteractableScreenGui((short) 1, 512, 512, 20);

        // Set background color
        gui.updateBackground(MapColors.COLOR_BLACK);

        // Create container
        var containerWidget = Containers.column(
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
                    (player1, x, y) -> player1.sendMessage(Component.text("GREEN"))
                ),
                Spacers.spacer(10, 10),
                Buttons.click(
                    Containers.stack(
                        128,
                        128,
                        Shapes.square(128, 128, 0, 0, MapColors.COLOR_BLUE),
                        Shapes.square(127, 127, 1, 1, MapColors.COLOR_LIGHT_BLUE)
                    ),
                    (player1, x, y) -> player1.sendMessage(Component.text("BLUE"))
                )
            ),
            Containers.row(
                448,
                214,
                Widget.Players.avatar(128, 128, sender.isPlayer() ? sender.asPlayer().getUuid() : UUID.randomUUID())
            )
        );

        // Display mouse
        final int cursorWidth = 5;
        var mouseWidget = Buttons.hover(
            Spacers.spacer(gui.map().width(), gui.map().height()),
            (player1, x, y) -> {
                for (InteractableScreenGui.Mouse mouse : gui.mice()) {
                    var graphics = gui.mapGraphics();

                    graphics.drawRectangle(MapColors.FIRE,
                        Math.max(mouse.x() - cursorWidth, 0),
                        Math.max(mouse.y() - cursorWidth, 0),
                        Math.min(mouse.x() + cursorWidth, gui.map().width() - 1),
                        Math.min(mouse.y() + cursorWidth, gui.map().height() - 1)
                    );
                }
            }
        );

        // Add the components
        gui.addWidget(containerWidget, 64, 64);
        gui.addWidget(mouseWidget);

        // Show the gui to the player
        if (sender.isPlayer()) {
            gui.show(sender.asPlayer());
        }
    }
}
