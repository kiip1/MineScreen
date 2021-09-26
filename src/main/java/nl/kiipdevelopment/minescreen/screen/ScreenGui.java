package nl.kiipdevelopment.minescreen.screen;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.map.MapColors;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.utils.validate.Check;
import nl.kiipdevelopment.minescreen.MineScreen;
import nl.kiipdevelopment.minescreen.component.Component;
import nl.kiipdevelopment.minescreen.map.Map;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.world.GuiInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScreenGui {
	private final short guiId;
	private final int fps;
	private final GuiInstance instance;
	private final Map map;
	private final MapGraphics mapGraphics;
	private final List<Target> targets = new ArrayList<>();

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
		instance = MineScreen.guiInstanceSupplier().get(this);
		mapGraphics = MineScreen.mapGraphicsSupplier().get(this);

		instance.placeMaps();

		MinecraftServer.getSchedulerManager().buildTask(MineScreen.refreshScreenSupplier().get(this))
			.repeat(1000 / fps, TimeUnit.MILLISECOND)
			.schedule();
	}

	public void addComponent(Component component) {
		addComponent(component, 0, 0);
	}

	public void addComponent(Component component, int x, int y) {
		Check.stateCondition(x + component.width() > map.width(), "{0} doesn't fit in gui.", component);
		Check.stateCondition(y + component.height() > map.height(), "{0} doesn't fit in gui.", component);

		targets.add(new Target(component, x, y));
	}

	public void updateBackground(MapColors color) {
		this.background = color;
	}

	public void show(Player player) {
		player.setInstance(instance.asInstance(), instance.spawn());
	}

	public void render(MapGraphics renderer) {
		renderer.fill(background);

		for (Target target : targets) {
			target.component.draw(renderer.subGraphics(
				target.x,
				target.y,
				target.component.width(),
				target.component.height()
			));
		}
	}

	public void sendPacket() {
		map().sendPacket(players());
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
		return instance.asInstance().getPlayers();
	}

	protected record Target(Component component, int x, int y) {}
}
