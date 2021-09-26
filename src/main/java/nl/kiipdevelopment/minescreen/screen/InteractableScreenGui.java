package nl.kiipdevelopment.minescreen.screen;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerHandAnimationEvent;
import nl.kiipdevelopment.minescreen.component.Component;
import nl.kiipdevelopment.minescreen.component.Interactable;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.util.Intersection3dUtils;
import nl.kiipdevelopment.minescreen.util.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class InteractableScreenGui extends ScreenGui {
	private final List<Mouse> mice = new ArrayList<>();
	private final double[] plane;

	/**
	 * Creates a new interactable screen gui.
	 *
	 * @param guiId The id of the gui
	 * @param width The width in pixels
	 * @param height The height in pixels
	 * @param fps The refresh rate
	 */
	public InteractableScreenGui(short guiId, int width, int height, int fps) {
		super(guiId, width, height, fps);

		int mapWidth = map().mapWidth();
		int mapHeight = map().mapHeight();

		MinecraftServer.getGlobalEventHandler().addListener(PlayerHandAnimationEvent.class, event -> {
			Player player = event.getPlayer();

			if (players().contains(player)) {
				for (Mouse mouse : mouse()) {
					if (mouse.owner == player) {
						click(player, mouse.x, mouse.y);
					}
				}
			}
		});

		plane = new double[] {
			-mapWidth / 2d,
			1,
			Math.max(mapWidth, mapHeight) / 2d + 1,
			Math.abs(-mapWidth / 2d),
			mapHeight + 1,
			Math.max(mapWidth, mapHeight) / 2d + 1
		};
	}

	public List<Mouse> mouse() {
		return mice;
	}

	public void hover(Player player, int x, int y) {
		for (Target target : targets()) {
			Component component = target.component();

			if (component instanceof Interactable interactable) {
				if (
					MathUtils.isBetween(x, target.x(), component.width() + target.x()) &&
					MathUtils.isBetween(y, target.y(), component.height() + target.y())
				) {
					interactable.onHover(player, x - target.x(), y - target.y());
				}
			}
		}
	}

	public void click(Player player, int x, int y) {
		for (Target target : targets()) {
			Component component = target.component();

			if (component instanceof Interactable interactable) {
				if (
					MathUtils.isBetween(x, target.x(), component.width() + target.x()) &&
					MathUtils.isBetween(y, target.y(), component.height() + target.y())
				) {
					interactable.onClick(player, x - target.x(), y - target.y());
				}
			}
		}
	}

	@Override
	public void render(MapGraphics renderer) {
		mice.clear();

		for (Player player : players()) {
			Pos position = player.getPosition().add(0, player.getEyeHeight() + 0.09375, 0);
			Vec looking = player.getPosition().direction();

			Vec vector = Intersection3dUtils.planeIntersection(
				// Line
				position.x(), position.y(), position.z(), // Position vector
				looking.x(), looking.y(), looking.z(), // Direction vector
				// Plane
				plane[0], plane[1], plane[2],
				plane[0], plane[4], plane[2],
				plane[3], plane[4], plane[2]
			);

			if (vector != null) {
				int width = map().width();
				int height = map().height();

				mice.add(new Mouse(
					width - 1 - (int) MathUtils.clamp(vector.x() * 128 + width / 2f, 0, width - 1),
					height - 1 - (int) MathUtils.clamp(vector.y() * 128 - 128, 0, height - 1),
					player
				));
			}
		}

		super.render(renderer);

		for (Mouse mouse : mouse()) {
			hover(mouse.owner, mouse.x, mouse.y);
		}
	}

	public record Mouse(int x, int y, Player owner) {}
}
