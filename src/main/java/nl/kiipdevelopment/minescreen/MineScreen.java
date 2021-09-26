package nl.kiipdevelopment.minescreen;

import net.minestom.server.MinecraftServer;
import net.minestom.server.utils.validate.Check;
import nl.kiipdevelopment.minescreen.map.Map;
import nl.kiipdevelopment.minescreen.map.MapImpl;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphicsImpl;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;
import nl.kiipdevelopment.minescreen.util.suppliers.MonoSupplier;
import nl.kiipdevelopment.minescreen.util.suppliers.TriSupplier;
import nl.kiipdevelopment.minescreen.world.GuiInstance;
import nl.kiipdevelopment.minescreen.world.GuiInstanceImpl;

public final class MineScreen {
    private static MonoSupplier<ScreenGui, GuiInstance> guiInstanceSupplier = GuiInstanceImpl::new;
    private static MonoSupplier<ScreenGui, MapGraphics> mapGraphicsSupplier = MapGraphicsImpl::new;
    private static MonoSupplier<ScreenGui, Runnable> refreshScreenSupplier = gui -> () -> {
        gui.render(gui.mapGraphics());
        gui.sendPacket();
    };
    private static TriSupplier<Short, Integer, Integer, Map> mapSupplier = MapImpl::new;
    private static TriSupplier<Short, Integer, Integer, Integer> mapIdSupplier = (guiId, x, y) -> (guiId << 16) + (x << 8) + y;

    private static boolean initialized = false;

    private MineScreen() {}

    public static void init() {
        Check.stateCondition(initialized, "Already initialized.");

        MinecraftServer.getBiomeManager().addBiome(GuiInstanceImpl.biome);
        MinecraftServer.getDimensionTypeManager().addDimension(GuiInstanceImpl.dimensionType);

        initialized = true;
    }

    public static MonoSupplier<ScreenGui, GuiInstance> guiInstanceSupplier() {
        return guiInstanceSupplier;
    }

    public static void setGuiInstanceSupplier(MonoSupplier<ScreenGui, GuiInstance> guiInstanceSupplier) {
        ensureNotInitialized();

        MineScreen.guiInstanceSupplier = guiInstanceSupplier;
    }

    public static MonoSupplier<ScreenGui, MapGraphics> mapGraphicsSupplier() {
        return mapGraphicsSupplier;
    }

    public static void setMapGraphicsSupplier(MonoSupplier<ScreenGui, MapGraphics> mapGraphicsSupplier) {
        ensureNotInitialized();

        MineScreen.mapGraphicsSupplier = mapGraphicsSupplier;
    }

    public static MonoSupplier<ScreenGui, Runnable> refreshScreenSupplier() {
        return refreshScreenSupplier;
    }

    public static void setRefreshScreenSupplier(MonoSupplier<ScreenGui, Runnable> refreshScreenSupplier) {
        ensureNotInitialized();

        MineScreen.refreshScreenSupplier = refreshScreenSupplier;
    }

    public static TriSupplier<Short, Integer, Integer, Map> mapSupplier() {
        return mapSupplier;
    }

    public static void setMapSupplier(TriSupplier<Short, Integer, Integer, Map> mapSupplier) {
        ensureNotInitialized();

        MineScreen.mapSupplier = mapSupplier;
    }

    public static TriSupplier<Short, Integer, Integer, Integer> mapIdSupplier() {
        return mapIdSupplier;
    }

    public static void setMapIdSupplier(TriSupplier<Short, Integer, Integer, Integer> mapIdSupplier) {
        ensureNotInitialized();

        MineScreen.mapIdSupplier = mapIdSupplier;
    }

    private static void ensureNotInitialized() {
        Check.stateCondition(initialized, "Can't change settings after initialization.");
    }
}
