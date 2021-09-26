import net.kyori.adventure.text.Component;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.Player;
import net.minestom.server.map.MapColors;
import nl.kiipdevelopment.minescreen.component.components.*;
import nl.kiipdevelopment.minescreen.screen.InteractableScreenGui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

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

        // Fetch smiley image
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL("https://icons.iconarchive.com/icons/custom-icon-design/pretty-office-13/128/Emoticons-icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create container
        ContainerComponent containerComponent = ContainerComponent.column(
            448,
            448,
            ContainerComponent.row(
                448,
                214,
                ButtonComponent.click(
                    RenderComponent.of(
                        128,
                        128,
                        renderer -> {
                            renderer.drawRectangle(MapColors.COLOR_GREEN, 0, 0, 128, 128);
                            renderer.drawRectangle(MapColors.COLOR_LIGHT_GREEN, 1, 1, 127, 127);
                        }
                    ),
                    (player1, x, y) -> player1.sendMessage(Component.text("GREEN"))
                ),
                SpacerComponent.of(10, 10),
                ButtonComponent.click(
                    RenderComponent.of(
                        128,
                        128,
                        renderer -> {
                            renderer.drawRectangle(MapColors.COLOR_BLUE, 0, 0, 128, 128);
                            renderer.drawRectangle(MapColors.COLOR_LIGHT_BLUE, 1, 1, 127, 127);
                        }
                    ),
                    (player1, x, y) -> player1.sendMessage(Component.text("BLUE"))
                )
            ),
            SpacerComponent.of(10, 10),
            ContainerComponent.row(
                448,
                214,
                ImageComponent.of(Objects.requireNonNull(image))
            )
        );

        // Display mouse
        ButtonComponent mouseComponent = ButtonComponent.hover(
            SpacerComponent.of(gui.map().width(), gui.map().height()),
            (player1, x, y) -> {
                for (InteractableScreenGui.Mouse mouse : gui.mouse()) {
                    gui.mapGraphics().drawDot(MapColors.FIRE, mouse.x(), mouse.y());
                }
            }
        );

        // Add the components
        gui.addComponent(containerComponent, 64, 64);
        gui.addComponent(mouseComponent);

        // Show the gui to the player
        gui.show(player);
    }
}
