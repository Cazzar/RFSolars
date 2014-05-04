package net.cazzar.mods.rfsolars;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class BlockRegistry {

	public static Block solar;

	public static void registerBlocks() {
		solar = new BlockRFSolar(Config.solarID).setUnlocalizedName("rfSolar");
		GameRegistry.registerBlock(solar, "RFSolar");
		LanguageRegistry.addName(solar, "Solar Panel");
	}

}
