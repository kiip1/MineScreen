package nl.kiipdevelopment.minescreen.widget.widgets;

import net.minestom.server.entity.Player;
import nl.kiipdevelopment.minescreen.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.util.MathUtils;
import nl.kiipdevelopment.minescreen.widget.AbstractWidget;
import nl.kiipdevelopment.minescreen.widget.Interactable;
import nl.kiipdevelopment.minescreen.widget.Widget;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiStatus.Internal
public final class GridWidget extends AbstractWidget implements Interactable {

	private final Map<Area, Widget> widgets = new HashMap<>();

	public GridWidget(int spacing, int width, int height, List<Widget> widgets) {
		super(width, height);

		int xOffset = 0;
		int yOffset = 0;
		int largestY = 0;
		for (Widget widget : widgets) {
			if (widget.height() > largestY)
				largestY = widget.height();
			if (xOffset + widget.width() > width()) {
				yOffset += largestY + spacing;
				largestY = 0;
				xOffset = 0;
			}

			this.widgets.put(new Area(xOffset, yOffset, widget.width(), widget.height()), widget);
			xOffset += widget.width() + spacing;
		}
	}

	@Override
	public void draw(MapGraphics renderer) {
		for (Map.Entry<Area, Widget> entry : widgets.entrySet()) {
			final Area area = entry.getKey();
			entry.getValue().draw(renderer.relative(area.x(), area.y(), area.width(), area.height()));
		}
	}

	@Override
	public void onHover(Player player, int x, int y) {
		for (Map.Entry<Area, Widget> entry : widgets.entrySet()) {
			final Area area = entry.getKey();
			if (entry.getValue() instanceof Interactable interactable && area.intersect(x, y))
				interactable.onHover(player, x, y);
		}
	}

	@Override
	public void onClick(Player player, int x, int y) {
		for (Map.Entry<Area, Widget> entry : widgets.entrySet()) {
			final Area area = entry.getKey();
			if (entry.getValue() instanceof Interactable interactable && area.intersect(x, y))
				interactable.onClick(player, x, y);
		}
	}

	private record Area(int x, int y, int width, int height) {
		public boolean intersect(int x, int y) {
			return MathUtils.isBetween(x, this.x, this.x + width) &&
					MathUtils.isBetween(y, this.y, this.y + height);
		}
	}

}
