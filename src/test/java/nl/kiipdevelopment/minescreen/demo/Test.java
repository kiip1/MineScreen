package nl.kiipdevelopment.minescreen.demo;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.extras.MojangAuth;
import nl.kiipdevelopment.minescreen.MineScreen;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;

public final class Test {
    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        MineScreen.instance().init();
        ScreenGui gui = TestGui.init();

        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, event -> {
            event.setSpawningInstance(gui.instance());
            event.getPlayer().setRespawnPoint(new Pos(0, 1, 0));
        }).addListener(PlayerSpawnEvent.class, event -> {
            Player player = event.getPlayer();
            player.setPermissionLevel(4);
            player.setGameMode(GameMode.ADVENTURE);
            gui.show(player);
        });
        MojangAuth.init();

        minecraftServer.start("0.0.0.0", 25565);
    }
}
