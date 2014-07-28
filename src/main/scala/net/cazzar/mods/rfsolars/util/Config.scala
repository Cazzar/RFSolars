package net.cazzar.mods.rfsolars.util

import java.io.File

import net.minecraftforge.common.Configuration


/**
 * @author Cayde
 */
object Config {
  var solarId = 3000
  var baseGen = 10

  def create(conf: File) {
    val config = new Configuration(conf)
    config.load()

    solarId = config.getBlock("Solar Panel", 3000).getInt
    baseGen = config.get("Generation Values", "Basic Solar Panel Generation", 10, "This value is in RF/T").getInt

    if (config.hasChanged)
      config.save()
  }
}
