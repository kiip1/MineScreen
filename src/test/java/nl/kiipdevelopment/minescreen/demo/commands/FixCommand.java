package nl.kiipdevelopment.minescreen.demo.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;

import java.util.Objects;

public class FixCommand extends Command {
    public FixCommand() {
        super("fix");

        addConditionalSyntax(Conditions::playerOnly, this::execute);
    }

    private void execute(CommandSender sender, CommandContext context) {
        if (sender instanceof Player player) {
            for (Entity entity : Objects.requireNonNull(player.getInstance()).getNearbyEntities(player.getPosition(), 50.0d)) {
                if (entity.getEntityType() == EntityType.GLOW_ITEM_FRAME) {
                    entity.teleport(entity.getPosition().withView(180, 0));
                }
            }
        }
    }
}
