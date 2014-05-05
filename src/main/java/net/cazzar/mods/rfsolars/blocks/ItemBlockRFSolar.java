package net.cazzar.mods.rfsolars.blocks;

import net.cazzar.mods.rfsolars.util.KeyboardHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockRFSolar extends ItemBlock {

	public ItemBlockRFSolar(int par1) {
		super(par1);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		String name;
		switch (itemstack.getItemDamage()) {
		    case 0:
			    name = "baseSolar";
			    break;
            default:
                name = "nothing";
                break;
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
        final int damage = itemstack.getTagCompound().getInteger("damage");

        String prefix;
        if (damage < 50)
            prefix = "";
        else if (damage < 250)
            prefix = "Partially ";
        else
            prefix = "Very ";

        if (KeyboardHelper.isShiftDown()) list.add("Durability Left: " + BlockRFSolar.damage);
        else list.add(prefix + "Damaged");
    }
}
