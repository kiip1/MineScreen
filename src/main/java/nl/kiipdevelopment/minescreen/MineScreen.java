package nl.kiipdevelopment.minescreen;

import nl.kiipdevelopment.minescreen.map.Map;
import nl.kiipdevelopment.minescreen.map.graphics.MapGraphics;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;
import nl.kiipdevelopment.minescreen.util.suppliers.MonoSupplier;
import nl.kiipdevelopment.minescreen.util.suppliers.TriSupplier;
import nl.kiipdevelopment.minescreen.world.GuiInstance;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface MineScreen {

	static MineScreen instance() {
		return MineScreenImpl.instance();
	}

	void init();

	MonoSupplier<ScreenGui, GuiInstance> guiInstanceSupplier();

	MonoSupplier<ScreenGui, MapGraphics> mapGraphicsSupplier();

	MonoSupplier<ScreenGui, Runnable> refreshScreenSupplier();

	TriSupplier<Short, Integer, Integer, Map> mapSupplier();

	TriSupplier<Short, Integer, Integer, Integer> mapIdSupplier();

}
