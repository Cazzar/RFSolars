package net.cazzar.mods.rfsolars.tile

import cofh.api.energy.{EnergyStorage, IEnergyHandler}
import net.cazzar.mods.rfsolars.util.{Config, MathHelper}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.ForgeDirection

/**
 * @author Cayde
 */
class TileSolarBase extends TileEntity with IEnergyHandler {
  private val storage: EnergyStorage = new EnergyStorage(1000)
  var damage: Int = 0

  storage.setMaxTransfer(3200)

  def receiveEnergy(from: ForgeDirection, maxReceive: Int, simulate: Boolean): Int = {
    if (!canInterface(from)) return 0
    return storage.receiveEnergy(maxReceive, simulate)
  }

  def extractEnergy(from: ForgeDirection, maxExtract: Int, simulate: Boolean): Int = {
    if (!canInterface(from)) return 0
    return storage.extractEnergy(maxExtract, simulate)
  }

  def canInterface(from: ForgeDirection): Boolean = {
    return from ne ForgeDirection.UP
  }

  def getEnergyStored(from: ForgeDirection): Int = {
    return storage.getEnergyStored
  }

  def getMaxEnergyStored(from: ForgeDirection): Int = {
    return 1000
  }

  override def updateEntity() {
    transferEnergy()
    //damagePanel() disable for now.
    if (canGenerate) generate()
  }

  def canGenerate: Boolean = {
    return !(worldObj.provider.hasNoSky || worldObj.isRaining || worldObj.isThundering || !worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord) || !worldObj.isDaytime || damage >= getMaxDamage)
  }

  def damagePanel() {
    if (canGenerate && worldObj.rand.nextInt(100) == 0) incrementDamage(1)
    if ((worldObj.isRaining || worldObj.isThundering) && worldObj.rand.nextInt(500) == 0) incrementDamage(5)
  }

  private[tile] def incrementDamage(amount: Int) {
    if (damage + amount >= getMaxDamage) {
      damage = getMaxDamage
      return
    }
    damage += amount
  }

  private[tile] def getMaxDamage: Int = {
    return 500
  }

  def transferEnergy() {
    val direction: ForgeDirection = ForgeDirection.getOrientation(getBlockMetadata % 6)
    val tile: TileEntity = worldObj.getBlockTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ)
    tile match {
      case energyHandler: IEnergyHandler if !tile.isInstanceOf[TileSolarBase] && canGenerate =>
        storage.extractEnergy(energyHandler.receiveEnergy(direction.getOpposite, if (storage.getEnergyStored > 500) 500 else storage.getEnergyStored, false), false)
      case _ =>
    }
  }

  override def readFromNBT(tagCompound: NBTTagCompound) {
    super.readFromNBT(tagCompound)
    damage = tagCompound.getInteger("damage")
  }

  override def writeToNBT(tagCompound: NBTTagCompound) {
    super.writeToNBT(tagCompound)
    tagCompound.setInteger("damage", damage)
  }

  /** This is the method you need to override to add different gen values. Don't touch anything else. Period. :D **/
  def getGenerationValue: Int = {
    return Config.baseGen
  }

  def getGenerationFactor: Double = {
    val x: Long = worldObj.getWorldInfo.getWorldTime
    return MathHelper.clamp(1.5D * Math.sin((2 * Math.PI) / 24000d * x), 1, 0)
  }

  def generate() {
    storage.receiveEnergy(Math.round(getGenerationValue * getGenerationFactor).asInstanceOf[Int], false)
  }

}
