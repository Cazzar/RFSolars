package net.cazzar.mods.rfsolars;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CreativeTab {

	public static void load() {
		LanguageRegistry.instance().addStringLocalization("itemGroup.tabRFSolars", "en_US", "RF Solars");
	}

	public static CreativeTabs tabRFSolars = new CreativeTabs("tabRFSolars") {
		public ItemStack getIconItemStack() {
			return new ItemStack(Block.furnaceBurning);
		}
	};
}
