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

final class MineScreenImpl implements MineScreen {
    private static volatile MineScreen instance;

    public static MineScreen instance() {
        MineScreen instance = MineScreenImpl.instance;
        if (instance == null) {
            instance = new MineScreenImpl();
            MineScreenImpl.instance = instance;
        }

        return instance;
    }

    // TODO This stuff is stupid
    private final MonoSupplier<ScreenGui, GuiInstance> guiInstanceSupplier = GuiInstanceImpl::new;
    private final MonoSupplier<ScreenGui, MapGraphics> mapGraphicsSupplier = MapGraphicsImpl::new;
    private final MonoSupplier<ScreenGui, Runnable> refreshScreenSupplier = gui -> () -> {
        gui.render(gui.mapGraphics());
        gui.sendPacketUpdate();
    };
    private final TriSupplier<Short, Integer, Integer, Map> mapSupplier = MapImpl::new;
    private final TriSupplier<Short, Integer, Integer, Integer> mapIdSupplier = (guiId, x, y) -> (guiId << 16) + (x << 8) + y;

    private boolean initialized = false;

    private MineScreenImpl() {}

    @Override
    public void init() {
        Check.stateCondition(initialized, "Already initialized.");

        MinecraftServer.getBiomeManager().addBiome(GuiInstanceImpl.BIOME);
        MinecraftServer.getDimensionTypeManager().addDimension(GuiInstanceImpl.DIMENSION_TYPE);

        initialized = true;
    }

    @Override
    public MonoSupplier<ScreenGui, GuiInstance> guiInstanceSupplier() {
        return guiInstanceSupplier;
    }

    @Override
    public MonoSupplier<ScreenGui, MapGraphics> mapGraphicsSupplier() {
        return mapGraphicsSupplier;
    }

    @Override
    public MonoSupplier<ScreenGui, Runnable> refreshScreenSupplier() {
        return refreshScreenSupplier;
    }

    @Override
    public TriSupplier<Short, Integer, Integer, Map> mapSupplier() {
        return mapSupplier;
    }

    @Override
    public TriSupplier<Short, Integer, Integer, Integer> mapIdSupplier() {
        return mapIdSupplier;
    }
}
