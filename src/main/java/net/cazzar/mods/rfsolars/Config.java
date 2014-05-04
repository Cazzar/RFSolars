package net.cazzar.mods.rfsolars;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {

	public static int solarID;

	public static void create(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		solarID = config.getBlock("Solar Panel", 3000).getInt();

		config.save();
	}

}
