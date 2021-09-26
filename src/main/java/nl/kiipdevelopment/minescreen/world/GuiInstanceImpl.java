package nl.kiipdevelopment.minescreen.world;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.*;
import net.minestom.server.instance.batch.ChunkBatch;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GuiInstanceImpl extends InstanceContainer implements GuiInstance {
    public static final DimensionType dimensionType = DimensionType.builder(NamespaceID.from("gui"))
        .ambientLight(15)
        .fixedTime(6000L)
        .effects("minecraft:the_nether")
        .build();
    public static final Biome biome = Biome.builder()
        .name(NamespaceID.from("gui"))
        .effects(BiomeEffects.builder()
            .fogColor(0x000000)
            .skyColor(0x000000)
            .build())
        .build();

    private final ScreenGui gui;

    public GuiInstanceImpl(ScreenGui gui) {
        super(UUID.randomUUID(), dimensionType);

        this.gui = gui;

        setChunkGenerator(new ChunkGenerator() {
            @Override
            public void generateChunkData(@NotNull ChunkBatch batch, int chunkX, int chunkZ) {
                for (int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
                    for (int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                        batch.setBlock(x, 0, z, Block.BARRIER);
                    }
                }
            }

            @Override
            public void fillBiomes(@NotNull Biome[] biomes, int chunkX, int chunkZ) {
                Arrays.fill(biomes, GuiInstanceImpl.biome);
            }

            @Override
            public @Nullable List<ChunkPopulator> getPopulators() {
                return null;
            }
        });

        MinecraftServer.getInstanceManager().registerInstance(this);
    }

    @Override
    public void placeMaps() {
        // TODO Center the maps

        final short guiId = gui.guiId();
        final int mapWidth = gui.map().mapWidth();
        final int mapHeight = gui.map().mapHeight();

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                final int mapId = MineScreen.mapIdSupplier().get(guiId, x, y);

                ItemStack map = ItemStack.builder(Material.FILLED_MAP)
                    .meta($ -> new MapMeta.Builder().mapId(mapId))
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