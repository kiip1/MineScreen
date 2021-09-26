package nl.kiipdevelopment.minescreen.screen;

import dev.emortal.rayfast.area.Intersection;
import dev.emortal.rayfast.area.area3d.Area3dRectangularPrism;
import dev.emortal.rayfast.vector.Vector;
import dev.emortal.rayfast.vector.Vector3d;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerHandAnimationEvent;
import nl.kiipdevelopment.minescreen.component.Component;
import nl.kiipdevelopment.minescreen.component.Interactable;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.util.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class InteractableScreenGui extends ScreenGui {
	private final List<Mouse> mice = new ArrayList<>();
	private final Area3dRectangularPrism area;

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

		area = new Area3dRectangularPrism() {
			@Override
			public double getMinX() {
				return -mapWidth / 2d;
			}

			@Override
			public double getMinY() {
				return 1;
			}

			@Override
			public double getMinZ() {
				return Math.max(mapWidth, mapHeight) / 2d + 1;
			}

			@Override
			public double getMaxX() {
				return Math.abs(getMinX());
			}

			@Override
			public double getMaxY() {
				return getMinY() + mapHeight;
			}

			@Override
			public double getMaxZ() {
				return getMinZ();
			}
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

			Vector vector = area.lineIntersection(
				position.x(), position.y(), position.z(),
				looking.x(), looking.y(), looking.z(),
				Intersection.ANY
			);

			if (vector instanceof Vector3d vector3d) {
				int width = map().width();
				int height = map().height();

				mice.add(new Mouse(
					width - 1 - (int) MathUtils.clamp(vector3d.x() * 128 + width / 2f, 0, width - 1),
					height - 1 - (int) MathUtils.clamp(vector3d.y() * 128 - 128, 0, height - 1),
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
