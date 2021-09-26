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

import java.util.Random;

public class TestCommand1 extends Command {
	public TestCommand1() {
		super("test1");

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

		for (int i = 0, len = packet.data.length; i < len; )
			for (int rnd = new Random().nextInt(50),
				 n = Math.min(len - i, Integer.SIZE/Byte.SIZE);
				 n-- > 0; rnd >>= Byte.SIZE)
				packet.data[i++] = (byte)rnd;

		player.getPlayerConnection().sendPacket(packet);
	}
}
