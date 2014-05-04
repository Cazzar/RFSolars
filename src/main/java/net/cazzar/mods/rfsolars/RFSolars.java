package net.cazzar.mods.rfsolars;

import net.cazzar.mods.rfsolars.blocks.BlockRegistry;
import net.cazzar.mods.rfsolars.proxy.CommonProxy;
import net.cazzar.mods.rfsolars.tile.TileRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "RFSolars", name = "RF Solar Panels", version = "1.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class RFSolars {
	@Mod.Instance
	public static RFSolars instance;
	public static String texturepath = "rfsolars";
	@SidedProxy(clientSide = "net.cazzar.mods.rfsolars.proxy.ClientProxy", serverSide = "net.cazzar.mods.rfsolars.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		BlockRegistry.registerBlocks();
		TileRegistry.register();
	}
}
