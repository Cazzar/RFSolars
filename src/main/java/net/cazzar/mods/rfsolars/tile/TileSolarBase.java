package net.cazzar.mods.rfsolars.tile;

import java.util.Random;

import net.cazzar.mods.rfsolars.Config;
import net.cazzar.mods.rfsolars.util.MathHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class TileSolarBase extends TileEntity implements IEnergyHandler {

	public static Random random = new Random();
	private EnergyStorage storage = new EnergyStorage(1000);
	public int damage;

	public TileSolarBase() {
		damage = 0;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return 0;
	}

	@Override
	public void updateEntity() {
		transferEnergy();
		if (canGenerate()) {
			generate();
			damagePanel();
		}
	}

	public boolean canGenerate() {
		return !(worldObj.provider.hasNoSky || worldObj.isRaining() || worldObj.isThundering() || !worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord) || !worldObj.isDaytime() || damage >= 500);
	}

	public void damagePanel() {
		if (canGenerate() && random.nextInt(10) == 0)
			damage++;
		else if (worldObj.isRaining() || worldObj.isThundering() && random.nextInt(50) == 0 && damage >= 250)
			damage -= 250;
	}

	public void transferEnergy() {
		ForgeDirection direction = ForgeDirection.getOrientation(getBlockMetadata() % 6);
		TileEntity tile = worldObj.getBlockTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
		if (tile instanceof IEnergyHandler && !(tile instanceof TileSolarBase) && canGenerate()) {
			IEnergyHandler energyHandler = (IEnergyHandler) tile;
			storage.extractEnergy(energyHandler.receiveEnergy(direction.getOpposite(), storage.getEnergyStored() > 500 ? 500 : storage.getEnergyStored(), false), false);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		damage = tagCompound.getInteger("damage");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("damage", damage);
	}

	/** This is the method you need to override to add different gen values. Don't touch anything else. Period. :D **/
	public int getGenerationValue() {
		return Config.baseGen;
	}

	public double getGenerationFactor() {
		long x = worldObj.getWorldInfo().getWorldTime();

		return MathHelper.clamp(1.5D * Math.sin((2 * Math.PI) / 24000d * x), 1, 0);
	}

	public void generate() {
		storage.receiveEnergy((int) Math.round(getGenerationValue() * getGenerationFactor()), false);
	}
}
