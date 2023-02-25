package nl.kiipdevelopment.minescreen;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface MineScreen {

	static MineScreen instance() {
		return MineScreenImpl.instance();
	}

	void init();

}
