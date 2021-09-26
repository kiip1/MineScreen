package nl.kiipdevelopment.minescreen.world;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.other.ItemFrameMeta;
import net.minestom.server.item.ItemStack;

public class DisplayEntity extends Entity {
    public DisplayEntity() {
        super(EntityType.GLOW_ITEM_FRAME);

        setNoGravity(true);
        setInvisible(true);
        hasPhysics = false;
    }

    public void updateItem(ItemStack item) {
        ((ItemFrameMeta) entityMeta).setItem(item);
    }
}
