package nl.kiipdevelopment.minescreen.world;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.other.GlowItemFrameMeta;
import net.minestom.server.entity.metadata.other.ItemFrameMeta;
import net.minestom.server.item.ItemStack;

final class DisplayEntity extends Entity {
    public DisplayEntity() {
        super(EntityType.GLOW_ITEM_FRAME);

        setNoGravity(true);
        setInvisible(true);
        hasPhysics = false;
        ((GlowItemFrameMeta) getEntityMeta()).setOrientation(ItemFrameMeta.Orientation.NORTH);
    }

    public void updateItem(ItemStack item) {
        ((ItemFrameMeta) entityMeta).setItem(item);
    }
}
