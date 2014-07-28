package net.cazzar.mods.rfsolars.blocks

import java.util
import cofh.util.StringHelper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{ItemStack, ItemBlock}
import net.minecraft.nbt.NBTTagCompound

/**
 * @author Cayde
 */
class ItemBlockRFSolar(id: Int) extends ItemBlock(id) {
  setHasSubtypes(true)

  override def getUnlocalizedName(itemstack: ItemStack): String = {
    var name: String = null
    itemstack.getItemDamage match {
      case 0 => name = "baseSolar"
      case _ => name = "nothing"
    }
    return super.getUnlocalizedName + "." + name
  }

  override def getMetadata(par1: Int): Int = {
    return par1
  }

  /*@SuppressWarnings(Array("unchecked", "rawtypes")) override def addInformation(itemstack: ItemStack, player: EntityPlayer, list: util.List[_], par4: Boolean) {
    if (itemstack.getTagCompound == null) {
      val tag: NBTTagCompound = new NBTTagCompound
      tag.setInteger("damage", 0)
      itemstack.setTagCompound(tag)
    }
    val damage: Int = itemstack.getTagCompound.getInteger("damage")
    var prefix: String = null
    if (damage < 50) prefix = ""
    else if (damage < 250) prefix = "Partially Damaged"
    else prefix = "Very Damaged"
    if (StringHelper.isShiftDown) list.add("Durability Left: " + (500 - damage))
    else if (!prefix.isEmpty) list.add(prefix)
  }*/
}
