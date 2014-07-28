package net.cazzar.mods.rfsolars.util

import cpw.mods.fml.common.registry.GameRegistry
import net.cazzar.mods.rfsolars.blocks.{ItemBlockRFSolar, BlockRFSolar}
import net.cazzar.mods.rfsolars.tile.TileSolarBase

/**
 * @author Cayde
 */
object Registry {
  val baseSolar = new BlockRFSolar(Config.solarId).setUnlocalizedName("rfSolar")

  def register() {
    GameRegistry.registerBlock(baseSolar, classOf[ItemBlockRFSolar], "RFSolar")
    GameRegistry.registerTileEntity(classOf[TileSolarBase], "BaseSolarPanel")
  }
}
