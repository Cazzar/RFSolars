package net.cazzar.mods.rfsolars.blocks

import java.util

import cofh.api.block.IDismantleable
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.cazzar.mods.rfsolars.RFSolars
import net.cazzar.mods.rfsolars.tile.TileSolarBase
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Icon
import net.minecraft.world.{World, IBlockAccess}
import net.minecraftforge.common.ForgeDirection

/**
 * @author Cayde
 */
class BlockRFSolar(id: Int) extends BlockContainer(id, Material.iron) with IDismantleable {
  var damage = 0
  var baseSolar = new Array[Icon](2)

  //this.setCreativeTab(CreativeTab.tabRFSolars)
  this.setHardness(4.0F)


  @SideOnly(Side.CLIENT) override def registerIcons(ri: IconRegister) {
    this.baseSolar(0) = ri.registerIcon(RFSolars.domain + ":solarTop")
    this.baseSolar(1) = ri.registerIcon("planks_oak")
  }

  @SuppressWarnings(Array("rawtypes", "unchecked"))
  @SideOnly(Side.CLIENT) override def getSubBlocks(itemId: Int, creativeTab: CreativeTabs, list: util.List[_]) {
    def add[T](list: util.List[T], value: Any) = list.add(value.asInstanceOf[T])
    add(list, new ItemStack(itemId, 1, 0))
    add(list, new ItemStack(itemId, 1, 1))
  }


  override def getIcon(side: Int, meta: Int): Icon = {
    if (meta == 0 && side != 1) return baseSolar(1)
    return baseSolar(0)
  }

  override def getBlockTexture(access: IBlockAccess, i: Int, j: Int, k: Int, side: Int): Icon = {
    return super.getBlockTexture(access, i, j, k, side)
  }

  override def createTileEntity(world: World, meta: Int): TileEntity = {
    if (meta == 0) {
      return new TileSolarBase
    }
    return super.createTileEntity(world, meta)
  }

  def createNewTileEntity(world: World): TileEntity = {
    return null
  }

  override def damageDropped(meta: Int): Int = {
    return meta
  }

  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, par6: Int, par7: Float, par8: Float, par9: Float): Boolean = {
    if (!world.isRemote) {
      player.addChatMessage("Energy Stored: " + world.getBlockTileEntity(x, y, z).asInstanceOf[TileSolarBase].getEnergyStored(ForgeDirection.DOWN) + " damage: " + (world.getBlockTileEntity(x, y, z).asInstanceOf[TileSolarBase]).damage)
    }
    /*if (player.getHeldItem != null && player.getHeldItem eq new ItemStack(Item.bucketWater) && !world.isRemote && damage > 250) {
      player.inventory.consumeInventoryItem(player.getHeldItem.itemID)
      player.inventory.addItemStackToInventory(new ItemStack(Item.bucketEmpty))
      player.addChatMessage("Solar Panel Cleaned")
      if (world.getBlockTileEntity(x, y, z).isInstanceOf[TileSolarBase]) damage = 0
    }*/
    return true
  }

  def dismantleBlock(player: EntityPlayer, world: World, x: Int, y: Int, z: Int, returnBlock: Boolean): ItemStack = {
    val metadata: Int = world.getBlockMetadata(x, y, z)
    return new ItemStack(this, metadata)
  }

  def canDismantle(player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Boolean = {
    return true
  }
}
