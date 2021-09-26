package commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.MapMeta;
import net.minestom.server.network.packet.server.play.MapDataPacket;

public class TestCommand2 extends Command {
	public TestCommand2() {
		super("test2");

		addConditionalSyntax(Conditions::playerOnly, this::execute);
	}

	private void execute(CommandSender sender, CommandContext context) {
		Player player = (Player) sender;

		player.getInventory().addItemStack(ItemStack.builder(Material.FILLED_MAP).meta($ -> new MapMeta.Builder().mapId(1)).build());

		MapDataPacket packet = new MapDataPacket();
		packet.mapId = 1;
		packet.columns = (short) 8;
		packet.rows = (short) 8;
		packet.icons = new MapDataPacket.Icon[0];
		packet.x = (byte) 0;
		packet.z = (byte) 0;
		packet.data = new byte[64];

		packet.data[0] = 50;

		player.getPlayerConnection().sendPacket(packet);
	}
}
