package nl.kiipdevelopment.minescreen.world;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.MapMeta;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.DimensionType;
import net.minestom.server.world.biomes.Biome;
import net.minestom.server.world.biomes.BiomeEffects;
import nl.kiipdevelopment.minescreen.MineScreen;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;
import org.jetbrains.annotations.ApiStatus;

import java.util.UUID;

@ApiStatus.Internal
public final class GuiInstanceImpl extends InstanceContainer implements GuiInstance {
    public static final DimensionType DIMENSION_TYPE = DimensionType.builder(NamespaceID.from("gui"))
        .ambientLight(15)
        .fixedTime(6000L)
        .effects("minecraft:the_nether")
        .build();
    public static final Biome BIOME = Biome.builder()
        .name(NamespaceID.from("gui"))
        .effects(BiomeEffects.builder()
            .fogColor(0x000000)
            .skyColor(0x000000)
            .build())
        .build();

    private final ScreenGui gui;

    public GuiInstanceImpl(ScreenGui gui) {
        super(UUID.randomUUID(), DIMENSION_TYPE);
        this.gui = gui;
        setGenerator(unit -> {
            unit.modifier().fillHeight(0, 1, Block.BARRIER);
            unit.modifier().fillBiome(BIOME);
        });
        MinecraftServer.getInstanceManager().registerInstance(this);
    }

    @Override
    public void placeMaps() {
        // TODO: Center the maps

        final short guiId = gui.guiId();
        final int mapWidth = gui.map().mapWidth();
        final int mapHeight = gui.map().mapHeight();

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                final int mapId = MineScreen.instance().mapIdSupplier().get(guiId, x, y);

                ItemStack map = ItemStack.builder(Material.FILLED_MAP)
                    .meta(MapMeta.class, meta -> meta.mapId(mapId))
                    .build();

                DisplayEntity entity = new DisplayEntity();
                entity.updateItem(map);

                Pos position = new Pos(
                    mapWidth / 2d - x - 1,
                    mapHeight - y,
                    Math.max(mapWidth, mapHeight) / 2f,
                    180,
                    0
                );

                entity.setInstance(this, position);
            }
        }
    }

    @Override
    public Instance asInstance() {
        return this;
    }

    @Override
    public Pos spawn() {
        return new Pos(0, 1, Math.max(gui.map().mapWidth(), gui.map().mapHeight()) / -2d);
    }
}