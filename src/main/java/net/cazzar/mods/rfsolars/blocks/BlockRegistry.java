package net.cazzar.mods.rfsolars.blocks;

import net.cazzar.mods.rfsolars.Config;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class BlockRegistry {

	public static Block solar;

	public static void registerBlocks() {
		solar = new BlockRFSolar(Config.solarID).setUnlocalizedName("rfSolar");
		GameRegistry.registerBlock(solar, ItemBlockRFSolar.class, "RFSolar");
		LanguageRegistry.addName(solar, "Solar Panel");
	}
}
