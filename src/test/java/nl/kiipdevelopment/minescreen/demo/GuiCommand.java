package nl.kiipdevelopment.minescreen.demo;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.Player;
import net.minestom.server.map.MapColors;
import static nl.kiipdevelopment.minescreen.component.ScreenComponent.*;

import nl.kiipdevelopment.minescreen.component.components.*;
import nl.kiipdevelopment.minescreen.screen.InteractableScreenGui;

public class GuiCommand extends Command {
    public GuiCommand() {
        super("gui");

        addConditionalSyntax(Conditions::playerOnly, this::execute);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Player player = (Player) sender;

        InteractableScreenGui gui = new InteractableScreenGui((short) 1, 512, 512, 20);

        // Set background color
        gui.updateBackground(MapColors.COLOR_BLACK);

        // Create container
        Container container = Containers.column(
            448,
            448,
            Containers.row(
                448,
                214,
                Buttons.click(
                        Renders.of(
                        128,
                        128,
                        renderer -> {
                            renderer.drawRectangle(MapColors.COLOR_GREEN, 0, 0, 128, 128);
                            renderer.drawRectangle(MapColors.COLOR_LIGHT_GREEN, 1, 1, 127, 127);
                        }
                    ),
                    (player1, x, y) -> player1.sendMessage(Component.text("GREEN"))
                ),
                Spacers.of(10, 10),
                Buttons.click(
                    Renders.of(
                        128,
                        128,
                        renderer -> {
                            renderer.drawRectangle(MapColors.COLOR_BLUE, 0, 0, 128, 128);
                            renderer.drawRectangle(MapColors.COLOR_LIGHT_BLUE, 1, 1, 127, 127);
                        }
                    ),
                    (player1, x, y) -> player1.sendMessage(Component.text("BLUE"))
                )
            )
        );

        // Display mouse
        final int cursorWidth = 5;
        Button mouseComponent = Buttons.hover(
            Spacers.of(gui.map().width(), gui.map().height()),
            (player1, x, y) -> {
                for (InteractableScreenGui.Mouse mouse : gui.mouse()) {
                    var graphics = gui.mapGraphics();
                    graphics.drawRectangle(MapColors.FIRE,
                            mouse.x() - cursorWidth,
                            mouse.y() - cursorWidth,
                            mouse.x() + cursorWidth,
                            mouse.y() + cursorWidth
                    );
                }
            }
        );

        // Add the components
        gui.addComponent(container, 64, 64);
        gui.addComponent(mouseComponent);

        // Show the gui to the player
        gui.show(player);
    }
}
