package net.cazzar.mods.rfsolars.blocks;

import java.util.List;

import net.cazzar.mods.rfsolars.RFSolars;
import net.cazzar.mods.rfsolars.creative.CreativeTab;
import net.cazzar.mods.rfsolars.tile.TileSolarBase;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cofh.api.block.IDismantleable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRFSolar extends BlockContainer implements IDismantleable {

	public static int damage;

	public Icon baseSolar[] = new Icon[2];

	public BlockRFSolar(int id) {
		super(id, Material.iron);
		this.setCreativeTab(CreativeTab.tabRFSolars);
		this.setHardness(4.0F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ri) {
		this.baseSolar[0] = ri.registerIcon(RFSolars.texturepath + ":solarTop");
		this.baseSolar[1] = ri.registerIcon("planks_oak");

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 1));
	}

	@Override
	public Icon getIcon(int side, int meta) {
		if (meta == 0 && side != 1)
			return baseSolar[1];
		return baseSolar[0];
	}

	@Override
	public Icon getBlockTexture(IBlockAccess access, int i, int j, int k, int side) {
		return super.getBlockTexture(access, i, j, k, side);
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		if (meta == 0) {
			return new TileSolarBase();
		}
		return super.createTileEntity(world, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if (player.getHeldItem() != null && player.getHeldItem() == new ItemStack(Item.bucketWater) && !world.isRemote && damage > 250) {
			player.inventory.consumeInventoryItem(player.getHeldItem().itemID);
			player.inventory.addItemStackToInventory(new ItemStack(Item.bucketEmpty));
			player.addChatMessage("Solar Panel Cleaned");
			if (world.getBlockTileEntity(x, y, z) instanceof TileSolarBase)
				damage = 0;
		}
		return true;
	}

	@Override
	public ItemStack dismantleBlock(EntityPlayer player, World world, int x, int y, int z, boolean returnBlock) {
		final int metadata = world.getBlockMetadata(x, y, z);
		return new ItemStack(this, metadata);
	}

	@Override
	public boolean canDismantle(EntityPlayer player, World world, int x, int y, int z) {
		return true;
	}
}
