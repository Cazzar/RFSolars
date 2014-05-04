package net.cazzar.mods.rfsolars.blocks;

import java.util.List;

import net.cazzar.mods.rfsolars.tile.TileSolarBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockRFSolar extends ItemBlock {

	public ItemBlockRFSolar(int par1) {
		super(par1);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		String name = "";
		switch (itemstack.getItemDamage()) {
		case 0: {
			name = "baseSolar";
			break;
		}
		default: {
			name = "nothing";
			break;
		}

		}
		return getUnlocalizedName() + "." + name;
	}

	@Override
	public int getMetadata(int par1) {
		return par1;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		if (TileSolarBase.damaged)
			list.add("Damaged");
		else
			list.add("Durability Left: " + BlockRFSolar.damage);
	}
}
