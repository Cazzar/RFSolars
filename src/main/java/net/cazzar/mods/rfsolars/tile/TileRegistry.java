package net.cazzar.mods.rfsolars.tile;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileRegistry {

	public static void register() {
		GameRegistry.registerTileEntity(TileSolarBase.class, "BaseSolarPanel");
	}
}
