package nl.kiipdevelopment.minescreen.demo;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import nl.kiipdevelopment.minescreen.MineScreen;
import nl.kiipdevelopment.minescreen.demo.commands.FixCommand;
import nl.kiipdevelopment.minescreen.demo.commands.GuiCommand;

public class Test {
    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();
        MineScreen.init();

        MinecraftServer.getCommandManager().register(new FixCommand());
        MinecraftServer.getCommandManager().register(new GuiCommand());
        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, event -> {
            DemoInstance demoInstance = new DemoInstance();
            MinecraftServer.getInstanceManager().registerInstance(demoInstance);

            event.setSpawningInstance(demoInstance);
            event.getPlayer().setRespawnPoint(new Pos(0, 1, 0));
        });
        MinecraftServer.getGlobalEventHandler().addListener(PlayerSpawnEvent.class, event -> {
            Player player = event.getPlayer();

            player.setPermissionLevel(4);
            player.setGameMode(GameMode.CREATIVE);
        });

        minecraftServer.start("0.0.0.0", 25565);
    }
}
