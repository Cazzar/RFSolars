package net.cazzar.mods.rfsolars

import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.{SidedProxy, Mod}
//import net.cazzar.mods.rfsolars.proxy.CommonProxy

/**
 * @author Cayde
 */
@Mod(modid = "rfsolars", name = "RF Solars", modLanguage = "scala")
object RFSolars {
  val domain = "rfsolars"
//  @SidedProxy(clientSide = "net.cazzar.mods.rfsolars.proxy.ClientProxy", serverSide = "net.cazzar.mods.rfsolars.proxy.CommonProxy")
//  var proxy: CommonProxy = null

  @Mod.EventHandler
  def preInit(event: FMLPreInitializationEvent) {

  }
}
