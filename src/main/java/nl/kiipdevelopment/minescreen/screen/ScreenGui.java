package nl.kiipdevelopment.minescreen.screen;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.map.MapColors;
import net.minestom.server.timer.Task;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.utils.validate.Check;
import nl.kiipdevelopment.minescreen.MineScreen;
import nl.kiipdevelopment.minescreen.map.Map;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.util.MathUtils;
import nl.kiipdevelopment.minescreen.widget.Widget;
import nl.kiipdevelopment.minescreen.world.GuiInstance;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ScreenGui {
    private final short guiId;
    private final int fps;
    private final GuiInstance guiInstance;
    private final Map map;
    private final MapGraphics mapGraphics;
    private final List<Target> targets = new ArrayList<>();
    private final Task refreshScreenTask;

    private MapColors background = MapColors.NONE;

    /**
     * Creates a new screen gui.
     *
     * @param guiId The id of the gui
     * @param width The width in pixels
     * @param height The height in pixels
     * @param fps The refresh rate
     */
    public ScreenGui(short guiId, int width, int height, int fps) {
        Check.argCondition(width > 2048, "Maximum width is 2048.");
        Check.argCondition(height > 2048, "Maximum height is 2048.");
        Check.argCondition(guiId < 0, "guiId mustn't be negative.");
        Check.argCondition(fps > 60, "Maximum fps is 60.");

        this.guiId = guiId;
        this.fps = fps;

        map = MineScreen.mapSupplier().get(guiId, width, height);
        guiInstance = MineScreen.guiInstanceSupplier().get(this);
        mapGraphics = MineScreen.mapGraphicsSupplier().get(this);

        guiInstance.placeMaps();

        refreshScreenTask = MinecraftServer.getSchedulerManager().buildTask(MineScreen.refreshScreenSupplier().get(this))
            .repeat(1000 / fps, TimeUnit.MILLISECOND)
            .schedule();
    }

    public void addWidget(Widget widget) {
        addWidget(widget, 0, 0);
    }

    public void addWidget(Widget widget, int x, int y) {
        Check.stateCondition(x + widget.width() > map.width(), "{0} doesn't fit in gui.", widget);
        Check.stateCondition(y + widget.height() > map.height(), "{0} doesn't fit in gui.", widget);

        targets.add(new Target(widget, x, y));
    }

    public void updateBackground(MapColors color) {
        this.background = color;
    }

    public void show(Player player) {
        map.sendPacket(Collections.singleton(player));
        player.setInstance(guiInstance.asInstance(), guiInstance.spawn());
    }

    @MustBeInvokedByOverriders
    public void render(MapGraphics renderer) {
        long loopStartTime = System.nanoTime();

        renderer.fill(background);

        for (Target target : targets) {
            target.widget.draw(renderer.relative(
                target.x,
                target.y,
                target.widget.width(),
                target.widget.height()
            ));
        }

        final double loopTime = MathUtils.round((System.nanoTime() - loopStartTime) / 1_000_000D, 2);
        if (loopTime >= 1000d / fps) {
            final double loopTimeTooMuch = MathUtils.round(loopTime - (1000d / fps), 2);

            System.err.printf("Loop took %sms, that's %sms too much.\n", loopTime, loopTimeTooMuch);
        }
    }

    public void sendPacket() {
        map().sendPacket(players());
    }

    public void sendPacketUpdate() {
        map().sendPacketUpdate(players());
    }

    public short guiId() {
        return guiId;
    }

    public int fps() {
        return fps;
    }

    public Map map() {
        return map;
    }

    public MapGraphics mapGraphics() {
        return mapGraphics;
    }

    public List<Target> targets() {
        return targets;
    }

    public Set<Player> players() {
        return guiInstance.asInstance().getPlayers();
    }

    protected record Target(Widget widget, int x, int y) {}
}
