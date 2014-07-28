package net.cazzar.mods.rfsolars.util

/**
 * @author Cayde
 */
object MathHelper {
  def clamp(value: Double, max: Double, min: Double): Double = {
    return if (value > max) max else if (value < min) min else value
  }
}
