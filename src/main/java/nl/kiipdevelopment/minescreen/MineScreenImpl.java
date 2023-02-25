package nl.kiipdevelopment.minescreen;

import net.minestom.server.MinecraftServer;
import net.minestom.server.utils.validate.Check;
import nl.kiipdevelopment.minescreen.world.GuiInstance;

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

    private volatile boolean initialized = false;

    private MineScreenImpl() {}

    @Override
    public void init() {
        Check.stateCondition(initialized, "Already initialized.");

        MinecraftServer.getBiomeManager().addBiome(GuiInstance.BIOME);
        MinecraftServer.getDimensionTypeManager().addDimension(GuiInstance.DIMENSION_TYPE);

        initialized = true;
    }

}
