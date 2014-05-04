package net.cazzar.mods.rfsolars.tile;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class TileSolarBase extends TileEntity implements IEnergyHandler {

	public static final int buffer = 1000;
	protected EnergyStorage storage = new EnergyStorage(1000);
	public static Random random = new Random();
	public static int damage;

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
		return false;
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
		damagePanel();
	}

	public boolean canGenerate() {
		if (worldObj.provider.hasNoSky || worldObj.isRaining() || worldObj.isThundering() || !worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord) || !worldObj.isDaytime() || damage >= 300)
			return false;
		else
			return true;
	}

	public void damagePanel() {
		if (canGenerate() && random.nextInt(10) == 0)
			damage++;
		else if (worldObj.isRaining() || worldObj.isThundering() && random.nextInt(50) == 0)
			damage--;
	}
}
