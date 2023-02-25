package nl.kiipdevelopment.minescreen.world;

import net.minestom.server.instance.Instance;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.DimensionType;
import net.minestom.server.world.biomes.Biome;
import net.minestom.server.world.biomes.BiomeEffects;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;

public interface GuiInstance {
    DimensionType DIMENSION_TYPE = DimensionType.builder(NamespaceID.from("gui"))
            .ambientLight(15)
            .fixedTime(6000L)
            .effects("minecraft:the_nether")
            .build();
    Biome BIOME = Biome.builder()
            .name(NamespaceID.from("gui"))
            .effects(BiomeEffects.builder()
                    .fogColor(0x000000)
                    .skyColor(0x000000)
                    .build())
            .build();

    static GuiInstance of(ScreenGui gui) {
        return new GuiInstanceImpl(gui);
    }

    void placeMaps();

    Instance asInstance();
}
