package net.cazzar.mods.rfsolars;

import net.cazzar.mods.rfsolars.blocks.BlockRegistry;
import net.cazzar.mods.rfsolars.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "rfsolars")
public class RFSolars {
	@Mod.Instance
	public static RFSolars instance;

	@SidedProxy(clientSide = "net.cazzar.mods.rfsolars.proxy.ClientProxy", serverSide = "net.cazzar.mods.rfsolars.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		BlockRegistry.registerBlocks();
	}
}
