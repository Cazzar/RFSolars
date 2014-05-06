package net.cazzar.mods.rfsolars.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.cazzar.mods.rfsolars.Config;
import net.minecraft.block.Block;

public class BlockRegistry {

	public static Block solar;

	public static void registerBlocks() {
		solar = new BlockRFSolar(Config.solarID).setUnlocalizedName("rfSolar");
        GameRegistry.registerBlock(solar, ItemBlockRFSolar.class, "RFSolar");
        //please, NO LANGUAGE REGISTRY!
//		LanguageRegistry.addName(solar, "Solar Panel");
	}
}
